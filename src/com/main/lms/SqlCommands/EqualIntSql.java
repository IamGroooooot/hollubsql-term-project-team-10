package com.main.lms.SqlCommands;

public class EqualIntSql extends SqlDecorator {
    // Attribute=int 형태 추가
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
