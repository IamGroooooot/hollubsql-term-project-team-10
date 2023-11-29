package com.main.lms.SqlCommands;

public class UpdateSql extends SqlCommand {
    // update table set 형태 제작
    String tableName;
    public UpdateSql(String tableName) {
        this.tableName = tableName;
    }
    public String command() {
        return "update " + this.tableName + " set ";
    }
}
