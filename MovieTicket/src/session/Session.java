package session;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private String sessionId; // =userId (pk)
    private Map<String,Object> attributes;


    public Session() {}

    public Session(String sessionId) {
        this.sessionId = sessionId;
        attributes = new HashMap<>();
    }


    public String getSessionId() {
        return sessionId;
    }

    //추가
    public void setAttribute(String name, Object value) {//cart , Map<Goods, Integer>
        attributes.put(name,value);
    }

    //조회(Map에 key에 해당하는 value 찾기)
    public Object getAttribute(String name) {//cart
        return attributes.get(name);
    }

    //제거(장바구니를 비울대 사용한다)
    public void removeAttribute(String name) {//cart
        attributes.remove(name);
    }


    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    //Map에 뭐 추가(cart 정보 외에 다른 정보 추가)
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    @Override
    public String toString() {
        return "Session [sessionId=" + sessionId + ", attributes=" + attributes + "]"+"\n";
    }


    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Session other = (Session) obj;
        if(sessionId.equals(other.sessionId)) {
            return true;
        }else {
            return false;
        }

    }
}
