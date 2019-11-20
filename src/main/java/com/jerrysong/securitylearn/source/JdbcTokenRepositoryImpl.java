package com.jerrysong.securitylearn.source;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;

/**
 * JDBC based persistent login token repository implementation.
 *
 * @author Luke Taylor
 * @since 2.0
 */
public class JdbcTokenRepositoryImpl extends JdbcDaoSupport implements
        PersistentTokenRepository {
    // ~ Static fields/initializers
    // =====================================================================================

    /** Default SQL for creating the database table to store the tokens */
    public static final String CREATE_TABLE_SQL = "create table persistent_logins (username varchar(64) not null, series varchar(64) primary key, "
            + "token varchar(64) not null, last_used timestamp not null)";
    public static final String DEF_TOKEN_BY_SERIES_SQL = "select username,series,token,last_used from persistent_logins where series = ?";
    public static final String DEF_INSERT_TOKEN_SQL = "insert into persistent_logins (username, series, token, last_used) values(?,?,?,?)";
    public static final String DEF_UPDATE_TOKEN_SQL = "update persistent_logins set token = ?, last_used = ? where series = ?";
    public static final String DEF_REMOVE_USER_TOKENS_SQL = "delete from persistent_logins where username = ?";

    // ~ Instance fields
    // ================================================================================================

    private String tokensBySeriesSql = DEF_TOKEN_BY_SERIES_SQL;
    private String insertTokenSql = DEF_INSERT_TOKEN_SQL;
    private String updateTokenSql = DEF_UPDATE_TOKEN_SQL;
    private String removeUserTokensSql = DEF_REMOVE_USER_TOKENS_SQL;
    private boolean createTableOnStartup;

    protected void initDao() {
        if (createTableOnStartup) {
            getJdbcTemplate().execute(CREATE_TABLE_SQL);
        }
    }

    public void createNewToken(PersistentRememberMeToken token) {
        getJdbcTemplate().update(insertTokenSql, token.getUsername(), token.getSeries(),
                token.getTokenValue(), token.getDate());
    }

    public void updateToken(String series, String tokenValue, Date lastUsed) {
        getJdbcTemplate().update(updateTokenSql, tokenValue, lastUsed, series);
    }

    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            return getJdbcTemplate().queryForObject(tokensBySeriesSql,
                    (rs, rowNum) -> new PersistentRememberMeToken(rs.getString(1), rs
                            .getString(2), rs.getString(3), rs.getTimestamp(4)), seriesId);
        }
        catch (EmptyResultDataAccessException zeroResults) {
            if (logger.isDebugEnabled()) {
                logger.debug("Querying token for series '" + seriesId
                        + "' returned no results.", zeroResults);
            }
        }
        catch (IncorrectResultSizeDataAccessException moreThanOne) {
            logger.error("Querying token for series '" + seriesId
                    + "' returned more than one value. Series" + " should be unique");
        }
        catch (DataAccessException e) {
            logger.error("Failed to load token for series " + seriesId, e);
        }

        return null;
    }

    public void removeUserTokens(String username) {
        getJdbcTemplate().update(removeUserTokensSql, username);
    }

    public void setCreateTableOnStartup(boolean createTableOnStartup) {
        this.createTableOnStartup = createTableOnStartup;
    }
}
