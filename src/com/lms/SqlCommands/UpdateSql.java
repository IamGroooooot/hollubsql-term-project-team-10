package com.lms.SqlCommands;

public class UpdateSql extends SqlCommand {
    String tableName;
    public UpdateSql(String tableName) {
        this.tableName = tableName;
    }
    public String command() {
        return "update " + this.tableName + " set ";
    }
}
