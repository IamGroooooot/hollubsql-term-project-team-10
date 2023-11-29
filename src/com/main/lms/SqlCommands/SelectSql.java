package com.lms.SqlCommands;

public class SelectSql extends SqlCommand {
    // select value from table 형태 제작
    String tableName;
    String item;
    public SelectSql (String tableName, String item) {
        this.tableName = tableName;
        this.item = item;
    }
    public String command() {
        return "select " + this.item + " from " + this.tableName;
    }
}
