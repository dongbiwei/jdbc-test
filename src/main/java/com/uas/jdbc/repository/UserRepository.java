package com.uas.jdbc.repository;

import com.uas.jdbc.data.MyJdbcTemplate;
import com.uas.jdbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Pro1 on 2018/3/6.
 */
@Repository
public class UserRepository {

    @Autowired
    private MyJdbcTemplate jdbcTemplate;

    public List<Long> save(final List<User> users) {

        return jdbcTemplate.batchInsert("insert into user(name,email,mobile) values (?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                User user = users.get(i);
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getMobile());
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
    }

}
