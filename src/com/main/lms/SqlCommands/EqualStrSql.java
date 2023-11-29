package com.main.lms.SqlCommands;

public class EqualStrSql extends SqlDecorator {
    // Attribute=string 형태 추가
    String element;
    String value;
    public EqualStrSql(SqlCommand sqlCommand, String element, String value) {
        this.element = element;
        this.value = value;
        this.sqlCommand = sqlCommand;
    }
    public String command() {
        return this.sqlCommand.command() + this.element + "='" + this.value + "'";
    }
}
