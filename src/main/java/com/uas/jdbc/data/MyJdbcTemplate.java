package com.uas.jdbc.data;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pro1 on 2018/3/6.
 */
public class MyJdbcTemplate extends JdbcTemplate{

    public MyJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public <T> T insertAndReturnKey(String sql, PreparedStatementCallback<T> action) throws DataAccessException {
        return execute(new GenerateKeyStatementCreator(sql), action);
    }

    private static class GenerateKeyStatementCreator implements PreparedStatementCreator, SqlProvider {
        private final String sql;

        public GenerateKeyStatementCreator(String sql) {
            Assert.notNull(sql, "SQL must not be null");
            this.sql = sql;
        }

        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            return con.prepareStatement(this.sql, PreparedStatement.RETURN_GENERATED_KEYS);
        }

        public String getSql() {
            return this.sql;
        }
    }

    public <T> List<T> batchInsert(String sql, final BatchPreparedStatementSetter pss) throws DataAccessException {
        if (logger.isDebugEnabled()) {
            logger.debug("Executing SQL batch insert [" + sql + "]");
        }

        return insertAndReturnKey(sql, new PreparedStatementCallback<List<T>>() {
            @Override
            public List<T> doInPreparedStatement(PreparedStatement ps) throws SQLException {
                try {
                    int batchSize = pss.getBatchSize();
                    InterruptibleBatchPreparedStatementSetter ipss =
                            (pss instanceof InterruptibleBatchPreparedStatementSetter ?
                                    (InterruptibleBatchPreparedStatementSetter) pss : null);
                    for (int i = 0; i < batchSize; i++) {
                        pss.setValues(ps, i);
                        if (ipss != null && ipss.isBatchExhausted(i)) {
                            break;
                        }
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    ResultSet rs = null;
                    try {
                        rs = ps.getGeneratedKeys();
                        List<T> generated = new ArrayList<T>();
                        while (rs.next()) {
                            generated.add((T) rs.getObject(1));
                        }
                        return generated;
                    } finally {
                        JdbcUtils.closeResultSet(rs);
                    }
                } finally {
                    if (pss instanceof ParameterDisposer) {
                        ((ParameterDisposer) pss).cleanupParameters();
                    }
                }
            }
        });
    }

}
