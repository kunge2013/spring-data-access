package com.convertools.entity;

import com.convertools.annotations.QueryMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@QueryMapper(clazz = Userlogin.QueryMapper.class)
//@NoArgsConstructor
@Getter
@Setter
public class Userlogin extends BaseEntity {



    @Getter
    @Setter
    private String loginName;

    @Getter
    @Setter
    private int loginOk;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public int getLoginOk() {
        return loginOk;
    }

    public void setLoginOk(int loginOk) {
        this.loginOk = loginOk;
    }

    /**
     * 查询条件
     */
    public static final class QueryMapper extends MappingSqlQuery<Userlogin> {
        @Override
        protected Userlogin mapRow(ResultSet resultSet, int rowno) throws SQLException {
            Userlogin userlogin = new Userlogin();
            userlogin.setLoginName(resultSet.getString("LoginName"));
            userlogin.setLoginOk(resultSet.getInt("LoginOk"));
            return userlogin;
        }
        public QueryMapper() {
        }
        public QueryMapper(DataSource ds, String sql) {
            super(ds, sql);
        }

        @Override
        public List<Userlogin> execute(Object... params) throws DataAccessException {
            return super.execute(params);
        }
    }
}
