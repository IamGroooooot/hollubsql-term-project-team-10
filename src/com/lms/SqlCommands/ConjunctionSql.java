package com.lms.SqlCommands;

public class ConjunctionSql extends SqlDecorator {
    String conjunction;
    public ConjunctionSql(SqlCommand sqlCommand, String conjunction) {
        this.sqlCommand = sqlCommand;
        this.conjunction = conjunction;
    }
    public String command() {
        return this.command() + " " + this.conjunction + " ";
    }
}
