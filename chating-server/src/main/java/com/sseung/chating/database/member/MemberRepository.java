package com.sseung.chating.database.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Member> getAllMember() {
        List<Member> list = jdbcTemplate.query("select * from member;", rowMapper());
        return list;
    }

    public RowMapper<Member> rowMapper() {
        return (rs, rowNum) -> {
            return new Member(rs.getString("id"), rs.getString("password"),
                    rs.getString("nickname"), rs.getDate("birthday"));
        };
    }
}
