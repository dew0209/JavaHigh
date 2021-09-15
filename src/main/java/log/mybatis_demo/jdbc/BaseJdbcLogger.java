package log.mybatis_demo.jdbc;

import log.mybatis_demo.Log;

public abstract class BaseJdbcLogger {
    protected Log statementLog;

    public BaseJdbcLogger(Log statementLog) {
        this.statementLog = statementLog;
    }
    /**
     * 提供统一打印日志的能力
     * ...
     */
    public void debug(){

    }
}
