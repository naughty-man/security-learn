package com.jerrysong.securitylearn.source;

import java.util.Date;

/**
 * 使用本类表明一个验证实体
 *
 * @Author Jerry.Song
 * @Date 2019/11/20 10:17
 */
public class PersistentRememberMeToken {

    /*
    正常用户A用密码登录，产生seriesA和tokenA
    非法用户B用密码登录，产生seriesA和tokenB，这个seriesA是只要第一次登录就永远是这个值？？？
    正常用户A选择自动登录：因为seriesA对应的token已经从A变成了B，
    series是同一个用户使用密码第二次登录？
    */


    private final String username;
    //eries仅在用户使用密码重新登录时更新，
    private final String series;
    //而token会在每一个新的session中都重新生成
    private final String tokenValue;
    private final Date date;

    public PersistentRememberMeToken(String username, String series, String tokenValue,
                                     Date date) {
        this.username = username;
        this.series = series;
        this.tokenValue = tokenValue;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getSeries() {
        return series;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public Date getDate() {
        return date;
    }
}
