package com.lms.SqlCommands;

public class EqualIntSql extends SqlDecorator {
    String element;
    int value;
    public EqualIntSql(SqlCommand sqlCommand, String element, int value) {
        this.element = element;
        this.value = value;
        this.sqlCommand = sqlCommand;
    }
    public String command() {
        return this.sqlCommand.command() + this.element + "=" + this.value;
    }
}
