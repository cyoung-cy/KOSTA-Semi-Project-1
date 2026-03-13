package session;

import java.util.HashMap;
import java.util.Map;

public class Session {
	/*
	 * 2026-03-13
	 * 이동혁
	 * 세션에 memberId(PK)와 userId(사용자 Id) 정보 담도록 수정
	 */
    private int sessionId; // memberId (pk)
    private String userId; // userId (사용자 Id)
    private Map<String,Object> attributes;


    public Session() {}

    public Session(int sessionId, String userId) {
        this.sessionId = sessionId;
        this.userId = userId;
        attributes = new HashMap<>();
    }

    

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getSessionId() {
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


    public void setSessionId(int sessionId) {
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
        return "Session [sessionId=" + sessionId + ", userId=" + userId + ", attributes=" + attributes + "]"+"\n";
    }
    


    @Override
    public int hashCode() {
        return sessionId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        
        Session other = (Session) obj;
        return this.sessionId == other.sessionId;

    }
}
