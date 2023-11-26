package com.lms.SqlCommands;

public class ConjunctionSql extends SqlDecorator {
    // where, and 같은 접속사?를 추가하는 클래스
    String conjunction;
    public ConjunctionSql(SqlCommand sqlCommand, String conjunction) {
        this.sqlCommand = sqlCommand;
        this.conjunction = conjunction;
    }
    public String command() {
        return this.sqlCommand.command() + " " + this.conjunction + " ";
    }
}
