package exception;

public class WrongInput extends Exception {
    public WrongInput(){
        System.out.println("잘못 입력하셨습니다.");
    }
    public WrongInput(String message) {
        super(message);
    }
}
