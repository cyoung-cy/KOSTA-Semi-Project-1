package dao.impl;

import dao.TicketDAO;
import dto.Seat;
import util.DbManager;
import vo.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TicketDAOImpl implements TicketDAO {

    @Override
    public List<Ticket> selectTicketsByMemberId(int memberId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from v_TICKET where MEMBER_ID = ?";
        List<Ticket> list = null;

        // MAP으로 데이터를 담아두기
        Map<Integer, Ticket> map = new LinkedHashMap<>();

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, memberId);
            rs = ps.executeQuery();
            while(rs.next()){
                int reservationId = rs.getInt(1);

                // 같은 예약번호의 티켓이 만들어진 적 있는지 체크
                Ticket ticket = map.get(reservationId);
                if(ticket == null) {
                    ticket = new Ticket();
                    ticket.setReservationId(reservationId);
                    ticket.setMovieTitle(rs.getString(3));
                    ticket.setTotalPrice(rs.getInt(4));
                    ticket.setCount(rs.getInt(5));
                    ticket.setSeats(new ArrayList<>());
                    ticket.setRoomName(rs.getString(7));
                    ticket.setStartTime(rs.getTimestamp(8));
                    ticket.setEndTime(rs.getTimestamp(9));
                    map.put(reservationId, ticket);
                }
                // 좌석을 티켓에 담기
                Seat seat = new Seat();
                seat.setName(rs.getString(6));

                ticket.getSeats().add(seat);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, rs);
        }

        return new ArrayList<>(map.values());
    }
}
