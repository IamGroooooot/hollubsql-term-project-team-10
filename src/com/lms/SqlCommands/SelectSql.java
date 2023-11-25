package com.lms.SqlCommands;

public class SelectSql extends SqlCommand {
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
