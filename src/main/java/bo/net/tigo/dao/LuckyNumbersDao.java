package bo.net.tigo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;

/**
 * Created by aralco on 11/21/14.
 */
@Repository
public class LuckyNumbersDao {

    private static final Logger logger = LoggerFactory.getLogger(LuckyNumbersDao.class);

    private SimpleJdbcCall unReserveNumberProc;

    @Autowired
    public void setDataSource(@Qualifier("lnOracleDataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        this.unReserveNumberProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CONCILI_RESERVE_LUCKY_BCCS")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("NUMERO", Types.VARCHAR),
                        new SqlParameter("RESERVE_BCCS", Types.VARCHAR)
                );

    }

    public String unReserveNumber(String number, Boolean reserve)    {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("NUMERO", number)
                .addValue("RESERVE_BCCS", String.valueOf(reserve));
        logger.info("unReserveNumber::"+number+","+reserve);
        Map result = unReserveNumberProc.execute(parameterSource);
        return (result!=null?"Rollback successful.":"Rollback failed.");
    }

}