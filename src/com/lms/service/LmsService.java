package com.lms.service;

public class LmsService {
    private String label1;
    private String label2;
    private String result = "";
    private UpdateDatabase updateDatabase;
    private SearchDatabase searchDatabase;
    private String[] columnNames;
    private Object[][] dataList;

    public LmsService(UpdateDatabase updateDatabase, SearchDatabase searchDatabase) {
        SetLabelValues(0);
        this.searchDatabase = searchDatabase;
        this.updateDatabase = updateDatabase;
    }

    public String GetLabel1() {
        return label1;
    }
    public String GetLabel2() {
        return label2;
    }
    public String GetResult() {
        return result;
    }

    public void SetLabelValues(int selectedNum) {
        switch (selectedNum) {
            case 1:
                label1 = "ID";
                label2 = "이름";
                break;
            case 0, 2:
                label1 = "도서 ID";
                label2 = "유저 ID";
                break;
            case 3:
                label1 = "도서 ID";
                label2 = "도서 이름";
                break;
            case 4:
                label1 = null;
                label2 = "도서 이름";
                break;
            case 5, 6:
                label1 = null;
                label2 = "이름";
                break;
        }
    }

    public void BorrowBook(String bookId, String userId) {
        if (!searchDatabase.CheckItemExist("users", userId) ||
                !searchDatabase.CheckItemExist("books", bookId) ) {
            SetResultWhenRent(-7026);
            return;
        }
        int resultNum = updateDatabase.AddData(userId, bookId);
        SetResultWhenRent(resultNum);
    }

    public void ReturnBook(String bookId, String userId) {
        if (!searchDatabase.CheckRentExist(userId, bookId)) {
            SetResultWhenRent(-7026);
            return;
        }
        int resultNum = updateDatabase.ReturnUpdate(userId, bookId);
        SetResultWhenRent(resultNum);
    }

    public void AddUser(String userId, String userName) {
        if (searchDatabase.CheckItemExist("users", userId)) {
            SetResultWhenAdd(-7026);
            return;
        }
        int resultNum = updateDatabase.AddData("users", userId, userName);
        SetResultWhenAdd(resultNum);
    }

    public void AddBook(String bookId, String bookName) {
        if (searchDatabase.CheckItemExist("books", bookId)) {
            SetResultWhenAdd(-7026);
            return;
        }
        int resultNum = updateDatabase.AddData("books", bookId, bookName);
        SetResultWhenAdd(resultNum);
    }

    private void SetResultWhenAdd(int resultNum) {
        if (resultNum == -7026) result = "Id Already Exists";
        else if (resultNum == -1 || resultNum == 0) result = "database error";
        else result = "Inserted";
    }

    private void SetResultWhenRent(int resultNum) {
        if (resultNum == -7026) result = "Id Does Not Exist";
        else if (resultNum == -1 || resultNum == 0) result = "database error";
        else result = "Completed";
    }

    public void Search(String tableName, String name) {
        dataList = searchDatabase.SearchData(tableName, name);
        columnNames = new String[]{"Id", "Name"};
    }

    public void SearchRent(String name) {
        columnNames = new String[] {"Book Id", "Rent Status"};
        String userId = searchDatabase.GetItemString("users", "Id",
                "Name", name);
        if (userId == null) {
            dataList = new String[0][];
            return;
        }
        dataList = searchDatabase.SearchRentData(userId);
    }

    public String[] ReturnColumnNames() {
        return columnNames;
    }

    public Object[][] ReturnDataList() {
        return dataList;
    }


    /** DB 쿼리 예시 코드
    public void dbTestCode() {
        DatabaseManager databaseManager = new DatabaseManager();
        try {
            databaseManager.openDatabase("temp");
            databaseManager.executeUpdate(
                    "create table test (" +
                            "  Entry      INTEGER      NOT NULL" +
                            ", Customer   VARCHAR (20) NOT NULL" +
                            ", DOW        VARCHAR (3)  NOT NULL" +
                            ", Cups       INTEGER      NOT NULL" +
                            ", Type       VARCHAR (10) NOT NULL" +
                            ", PRIMARY KEY( Entry )" +
                            ")"
            );

            databaseManager.executeUpdate(
                    "insert into test VALUES (1,  'John',   'Mon', 1, 'JustJoe')");

            ResultSet result = databaseManager.executeQuery("select * from test");

            while (result.next()) {
                System.out.println
                        (result.getInt("Entry") + ", "
                                + result.getString("Customer") + ", "
                                + result.getString("DOW") + ", "
                                + result.getInt("Cups") + ", "
                                + result.getString("Type")
                        );
            }
            databaseManager.closeDatabase();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
     */


}
