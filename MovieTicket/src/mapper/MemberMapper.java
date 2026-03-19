package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.jdbc.RowMapper;
import dto.Member;

public class MemberMapper implements RowMapper<Member> {
    
    // 싱글톤 패턴 적용
    private static final MemberMapper instance = new MemberMapper();
    
    private MemberMapper() {}
    
    public static MemberMapper getInstance() {
        return instance;
    }

    @Override
    public Member mapRow(ResultSet rs) throws SQLException {
        
        Member member = new Member();
        
        // ResultSet에서 데이터를 꺼내 DTO에 세팅
        member.setMemberId(rs.getInt("MEMBER_ID"));
        member.setUserId(rs.getString("USER_ID"));
        member.setPassword(rs.getString("PASSWORD"));
        member.setName(rs.getString("NAME"));
        member.setPhone(rs.getString("PHONE"));
        member.setAddress(rs.getString("ADDRESS"));
        member.setBirthDate(rs.getString("BIRTH_DATE"));
        member.setCreateAt(rs.getString("CREATE_AT"));
        member.setCardInfo(rs.getString("CARD_INFO"));
        member.setRole(rs.getString("ROLE"));
        
        return member;
    }
}