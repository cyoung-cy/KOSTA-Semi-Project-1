import common.config.AppConfiguration;
import dao.impl.RoomDAOImpl;

public class Main {
    public static void main(String[] args) {
    	
    	AppConfiguration appConfiguration = AppConfiguration.getInstance();
    	
    	RoomDAOImpl.getInstance().insert("A-11");
    	
//        StartView.menu();
//    	System.out.println(SeatUtil.convertToIndices("b-10"));
    }
}