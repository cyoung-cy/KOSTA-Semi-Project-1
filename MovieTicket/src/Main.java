import java.util.List;

import common.config.AppConfiguration;
import dao.RoomDAO;
import dao.impl.RoomDAOImpl;
import dto.request.RoomCreateRequest;
import util.SeatUtil;
import view.StartView;

public class Main {
    public static void main(String[] args) {
    	
    	AppConfiguration appConfiguration = AppConfiguration.getInstance();
    	RoomCreateRequest request = new RoomCreateRequest(false, "1");
    	
    	RoomDAOImpl.getInstance().insert(request);
    	
//        StartView.menu();
//    	System.out.println(SeatUtil.convertToIndices("b-10"));
    }
}