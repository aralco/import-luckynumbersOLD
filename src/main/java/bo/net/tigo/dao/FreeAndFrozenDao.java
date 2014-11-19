package bo.net.tigo.dao;

import bo.net.tigo.model.InAuditMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
    private SimpleJdbcCall reserveNumbersProc;//SP3_RERSERVANL_NROCUENTACOYLC
    private SimpleJdbcCall unBlockeNumbersProc;

    private static final Logger logger = LoggerFactory.getLogger(FreeAndFrozenDao.class);

    @Autowired
    public void setDataSource(@Qualifier("db2DataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        this.frozenNumbersProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP1_LNrosCOxSucursalNroDesdeHasta")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("sucursal", Types.NUMERIC),
                        new SqlParameter("nro_desde", Types.VARCHAR),
                        new SqlParameter("nro_hasta", Types.VARCHAR)
                )
                .returningResultSet("rows",
                        ParameterizedBeanPropertyRowMapper.newInstance(InAuditMapper.class));

        this.freeNumbersProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP2_LNrosLIxSucursalNroDesdeHastaPorc_ActEstLC")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                    new SqlParameter("sucursal", Types.NUMERIC),
                    new SqlParameter("nro_desde", Types.VARCHAR),
                    new SqlParameter("nro_hasta", Types.VARCHAR)
                )
                .returningResultSet("rows",
                        ParameterizedBeanPropertyRowMapper.newInstance(InAuditMapper.class));

        this.reserveNumbersProc = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("SP3_RERSERVANL_NROCUENTACOYLC")
                .declareParameters(
                        new SqlParameter("nrocuenta", Types.VARCHAR)
                );

        this.unBlockeNumbersProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP2_LNrosLCxSucursalNroDesdeHastaPorc_ActEstLI")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                    new SqlParameter("sucursal", Types.NUMERIC),
                    new SqlParameter("nro_desde", Types.VARCHAR),
                    new SqlParameter("nro_hasta", Types.VARCHAR)
                )
                .returningResultSet("rows",
                        ParameterizedBeanPropertyRowMapper.newInstance(InAuditMapper.class));
    }

    public List<InAuditMapper> getFrozenNumbers(int city, String from, String to)    {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("sucursal", city)
                .addValue("nro_desde", from)
                .addValue("nro_hasta", to);
        Map out = frozenNumbersProc
                .execute(parameterSource);
        logger.info("getFrozenNumbers::"+out.get("rows"));
        return (List<InAuditMapper>)out.get("rows");
    }

    public List<InAuditMapper> getFreeNumbers(int city, String from, String to)    {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("sucursal", city)
                .addValue("nro_desde", from)
                .addValue("nro_hasta", to);
        Map out = freeNumbersProc
                .execute(parameterSource);
        logger.info("getFreeNumbers::"+out.get("rows"));
        return (List<InAuditMapper>)out.get("rows");
    }

    public String reserveNumber(String number)    {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("nrocuenta", number);
        logger.info("reserveNumbers::"+number);
        Map out = reserveNumbersProc.execute(parameterSource);
        return out.toString();
    }

    public List<InAuditMapper> unBlockeNumbers(int city, String from, String to)    {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("sucursal", city)
                .addValue("nro_desde", from)
                .addValue("nro_hasta", to);
        Map out = unBlockeNumbersProc
                .execute(parameterSource);
        logger.info("unBlockeNumbers::"+out.get("rows"));
        return (List<InAuditMapper>)out.get("rows");
    }


}
