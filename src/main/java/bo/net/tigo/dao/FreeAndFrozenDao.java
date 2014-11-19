package bo.net.tigo.dao;

import bo.net.tigo.model.InAudit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * Created by aralco on 11/18/14.
 */
@Repository
public class FreeAndFrozenDao {

    private SimpleJdbcCall freeNumbersProc;
    private SimpleJdbcCall frozenNumbersProc;
    private SimpleJdbcCall reserveNumbersProc;
    private SimpleJdbcCall unBlockeNumbersProc;

    private static final Logger logger = LoggerFactory.getLogger(FreeAndFrozenDao.class);

    @Autowired
    public void setDataSource(@Qualifier("as400DataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        this.frozenNumbersProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP1_LNROSCOXSUCURSALNRODESDEHASTA")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("sucursal", Types.NUMERIC),
                        new SqlParameter("nro_desde", Types.VARCHAR),
                        new SqlParameter("nro_hasta", Types.VARCHAR)
                )
                .returningResultSet("frozenNumbers", new ParameterizedRowMapper<InAudit>() {
                    @Override
                    public InAudit mapRow(ResultSet resultSet, int i) throws SQLException {
                        InAudit inAudit = new InAudit();
                        inAudit.setRow(resultSet.getString(1));
                        return inAudit;
                    }
                });

        this.freeNumbersProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP2_LNROSLIXSUCURSALNRODESDEHASTAPORC_ACTESTLC")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                    new SqlParameter("sucursal", Types.NUMERIC),
                    new SqlParameter("nro_desde", Types.VARCHAR),
                    new SqlParameter("nro_hasta", Types.VARCHAR)
                )
                .returningResultSet("freeNumbers", new ParameterizedRowMapper<InAudit>() {
                    @Override
                    public InAudit mapRow(ResultSet resultSet, int i) throws SQLException {
                        InAudit inAudit = new InAudit();
                        inAudit.setRow(resultSet.getString(1));
                        return inAudit;
                    }
                });

//        this.reserveNumbersProc = new SimpleJdbcCall(jdbcTemplate)
//        .withProcedureName("SP3_RERSERVANL_NROCUENTACOYLC")
//                .declareParameters(
//                        new SqlParameter("nrocuenta", Types.VARCHAR)
//                );
//
//        this.unBlockeNumbersProc = new SimpleJdbcCall(jdbcTemplate)
//                .withProcedureName("SP2_LNROSLCXSUCURSALNRODESDEHASTAPORC_ACTESTLI")
//                .withoutProcedureColumnMetaDataAccess()
//                .declareParameters(
//                    new SqlParameter("sucursal", Types.NUMERIC),
//                    new SqlParameter("nro_desde", Types.VARCHAR),
//                    new SqlParameter("nro_hasta", Types.VARCHAR)
//                )
//                .returningResultSet("rows",
//                        ParameterizedBeanPropertyRowMapper.newInstance(InAuditMapper.class));
    }

    public List<InAudit> getFrozenNumbers(int city, String from, String to)    {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("sucursal", city)
                .addValue("nro_desde", from)
                .addValue("nro_hasta", to);
        Map out = frozenNumbersProc
                .execute(parameterSource);
        logger.info("getFrozenNumbers::"+out.get("frozenNumbers"));
        return (List<InAudit>)out.get("frozenNumbers");
    }

    public List<InAudit> getFreeNumbers(int city, String from, String to)    {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("sucursal", city)
                .addValue("nro_desde", from)
                .addValue("nro_hasta", to);
        Map out = freeNumbersProc
                .execute(parameterSource);
        logger.info("getFreeNumbers::"+out.get("freeNumbers"));
        return (List<InAudit>)out.get("freeNumbers");
    }

    public String reserveNumber(String number)    {
//        SqlParameterSource parameterSource = new MapSqlParameterSource()
//                .addValue("nrocuenta", number);
//        logger.info("reserveNumbers::"+number);
//        Map out = reserveNumbersProc.execute(parameterSource);
//        return out.toString();
        return "";
    }

    public List<InAudit> unBlockeNumbers(int city, String from, String to)    {
//        SqlParameterSource parameterSource = new MapSqlParameterSource()
//                .addValue("sucursal", city)
//                .addValue("nro_desde", from)
//                .addValue("nro_hasta", to);
//        Map out = unBlockeNumbersProc
//                .execute(parameterSource);
//        logger.info("unBlockeNumbers::"+out.get("rows"));
//        return (List<InAuditMapper>)out.get("rows");
        return null;
    }


}
