package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.*;
import dto.Reservation;
import dto.ReservationInfo;
import dto.ReservationRequest;
import dto.Schedules;
import exception.AppConfigException;
import exception.NotFoundException;
import exception.ReservationException;
import util.DbManager;

public class ReservationService {

	private final ReservationDAO reservationDAO;

	private final ReservationInfoDAO reservationInfoDAO;

	private final SchedulesDAO schedulesDAO;

	private final MemberDAO memberDAO; 
	
	private final SeatDAO seatDAO;

    private static ReservationService instance;

	private final MovieDAO movieDAO;  // 추가

	private ReservationService(ReservationDAO reservationDAO, ReservationInfoDAO reservationInfoDAO,
							   SchedulesDAO schedulesDAO, MemberDAO memberDAO, SeatDAO seatDAO, MovieDAO movieDAO) {  // 추가
		this.reservationDAO = reservationDAO;
		this.reservationInfoDAO = reservationInfoDAO;
		this.schedulesDAO = schedulesDAO;
		this.memberDAO = memberDAO;
		this.seatDAO = seatDAO;
		this.movieDAO = movieDAO;  // 추가
	}


	public static ReservationService getInstance() {
		if (instance == null)
			throw new AppConfigException("ReservationService 초기화 안 됨");

		return instance;
	}

	public static ReservationService init(ReservationDAO reservationDAO, ReservationInfoDAO reservationInfoDAO,
										  SchedulesDAO schedulesDAO, MemberDAO memberDAO, SeatDAO seatDAO, MovieDAO movieDAO) {  // 추가
		if (instance == null)
			instance = new ReservationService(reservationDAO, reservationInfoDAO,
					schedulesDAO, memberDAO, seatDAO, movieDAO);
		return instance;
	}

	/*
	 * 0314 김채영 TODO: 사용자 예매 조회 서비스
	 */
	public List<Reservation> selectReservationsByMemberId(int memberId) throws NotFoundException, SQLException {
		List<Reservation> list = reservationDAO.selectReservationsByMemberId(memberId);
		if (list.isEmpty())
			throw new NotFoundException("\'" + memberId + "\' 이(가) 얘매한 영화가 없습니다.");
		return list;
	}

	/*
	 * 0318 한상혁 TODO: 사용자 입력을 받아 예매 처리
	 */
	public void executeReservation(ReservationRequest req) {
		Connection conn = null;
	    
	    try {
	        conn = DbManager.getConnection();
	        conn.setAutoCommit(false); // 자동 저장 끄기
	        
	        int totalTargetCount = req.getAdultCount() + req.getTeenCount() + req.getBabyCount();
	        int selectedSeatCount = req.getSelectSeats().size();

	        if (totalTargetCount != selectedSeatCount) {
	            throw new ReservationException(
	                String.format("인원수(%d명)와 선택한 좌석 수(%d석)가 일치하지 않습니다.", 
	                totalTargetCount, selectedSeatCount)
	            );
	        }
	        
	        List<Integer> seatIds = seatDAO.findIdsByNames(conn, req.getScheduleId(), req.getSelectSeats());
	        
	        // 결제카드정보 확인
	        if (!memberDAO.selectOneById(conn, req.getMemberId()).getCardInfo().equals(req.getCardInfo())) {
	            throw new ReservationException("등록한 카드와 결제 카드가 일치하지 않습니다.");
	        }
	        
	        // 선택한 좌석 수와 DB에서 찾아온 PK 수가 맞는지 체크
	        if (seatIds.size() != req.getSelectSeats().size()) {
	            throw new ReservationException("존재하지 않거나 중복된 좌석이 포함되어 있습니다.");
	        }
	        
	        // 업데이트에 조건을 걸어 예약 완료 좌석인지도 확인
	        for (int seatId : seatIds) {
	            if (seatDAO.updateIsReserved(conn, seatId, true) <= 0) {
	                throw new ReservationException("죄송합니다. 선택하신 좌석 중 이미 예약 완료된 좌석이 포함되어 있습니다.");
	            }
	        }
	        
	        Reservation reservation = new Reservation();
	        reservation.setMemberId(req.getMemberId());
	        reservation.setScheduleId(req.getScheduleId());
	        reservation.setCardInfo(req.getCardInfo());
	        reservation.setMovieId(req.getMovieId());
	        
	        int reservationId = reservationDAO.insert(conn, reservation);
	        
	        if (reservationId <= 0) 
	            throw new ReservationException("예약 메인 생성 실패");

	        // 인원 카운트 기반 상세 저장
	        int currentIdx = 0;

	        // 성인 (15,000원)
	        for (int i = 0; i < req.getAdultCount(); i++) {
	        	saveInfo(conn, reservationId, seatIds.get(currentIdx++), 15000, "성인");
	        }
	        
	        // 청소년 (10,000원)
	        for (int i = 0; i < req.getTeenCount(); i++) {
	        	saveInfo(conn, reservationId, seatIds.get(currentIdx++), 10000, "청소년");
	        }
	        
	        // 영유아 (0원)
	        for (int i = 0; i < req.getBabyCount(); i++) {
	        	saveInfo(conn, reservationId, seatIds.get(currentIdx++), 0,"어린이");
	        }

			// ── AUDI_ACC 업데이트 추가 ──
			int totalCount = req.getAdultCount() + req.getTeenCount() + req.getBabyCount();
			movieDAO.updateAudiAcc(conn, req.getMovieId(), totalCount);

			// 모든 과정 성공 시 커밋
	        conn.commit(); 
	        System.out.println("예약 성공! 예약 번호: " + reservationId);

	    } catch (Exception e) {
	        if (conn != null) {
	            try { conn.rollback(); } catch (SQLException se) { se.printStackTrace(); }
	        }
	        throw new RuntimeException("예매 실패: " + e.getMessage(), e);
	    } finally {
	        if (conn != null) {
	            try { 
	                conn.setAutoCommit(true); 
	            } catch (SQLException se) { se.printStackTrace(); }
	            
	            try { 
	                conn.close(); 
	            } catch (SQLException se) { se.printStackTrace(); }
	        }
	    }
	}
	
	private void saveInfo(Connection conn, int resId, int seatId, int price, String category) {
	    ReservationInfo info = new ReservationInfo();
	    info.setReservationId(resId);
	    info.setSeatId(seatId);
	    info.setPrice(price);
	    info.setCategory(category);
	    
	    if (reservationInfoDAO.insert(conn, info) <= 0) {
	        throw new ReservationException("좌석 상세 등록 실패");
	    }
	}
}

