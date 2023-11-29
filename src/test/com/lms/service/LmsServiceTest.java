package com.lms.service;

import com.lms.repository.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LmsServiceTest {
    private SearchDatabase searchDatabase;
    private DatabaseManager databaseManager;
    private UpdateDatabase updateDatabase;
    private LmsService lmsService;
    private static final String databaseName = "test";

    @BeforeEach
    void Setup() throws Exception {
        databaseManager = new DatabaseManager();
        databaseManager.openDatabase(databaseName);
        updateDatabase = new UpdateDatabase(databaseManager);
        searchDatabase = new SearchDatabase(databaseManager);
        lmsService = new LmsService(updateDatabase, searchDatabase);
        updateDatabase.AddData("users", "1", "1");
        updateDatabase.AddData("books", "2", "2");
        updateDatabase.AddData("1", "2");
    }

    @DisplayName("Set label values")
    @Test
    void SetLabelValuesTest() throws Exception {
        lmsService.SetLabelValues(0);
        lmsService.SetLabelValues(1);
        Assertions.assertEquals("ID", lmsService.GetLabel1());
        Assertions.assertEquals("이름", lmsService.GetLabel2());
    }

    @DisplayName("Add user success")
    @Test
    void AddUserTest() throws Exception {
        lmsService.AddUser("3", "3");
        Assertions.assertEquals("Inserted", lmsService.GetResult());
    }

    @DisplayName("Add user fail (id already exist)")
    @Test
    void AddUserFailTest() throws Exception {
        lmsService.AddUser("1", "3");
        Assertions.assertEquals("database error", lmsService.GetResult());
    }

    @DisplayName("Add book success")
    @Test
    void AddBookTest() throws Exception {
        lmsService.AddBook("3", "3");
        Assertions.assertEquals("Inserted", lmsService.GetResult());
    }

    @DisplayName("Add book fail (id already exist)")
    @Test
    void AddBookFailTest() throws Exception {
        lmsService.AddBook("2", "3");
        Assertions.assertEquals("database error", lmsService.GetResult());
    }

    @DisplayName("Borrow book success")
    @Test
    void BorrowBookTest() throws Exception {
        updateDatabase.AddData("users", "3", "3");
        updateDatabase.AddData("books", "4", "4");
        lmsService.BorrowBook("4", "3");
        Assertions.assertEquals("Completed", lmsService.GetResult());
    }

    @DisplayName("Borrow book failed by no id")
    @Test
    void BorrowBookFailTest() throws Exception {
        lmsService.BorrowBook("5", "5");
        Assertions.assertEquals("Id Does Not Exist", lmsService.GetResult());
    }

    @DisplayName("Return book success")
    @Test
    void ReturnBookTest() throws Exception {
        lmsService.ReturnBook("2", "1");
        Assertions.assertEquals("Completed", lmsService.GetResult());
    }

    @DisplayName("Return book failed by no id")
    @Test
    void ReturnBookFailTest() throws Exception {
        lmsService.ReturnBook("5", "5");
        Assertions.assertEquals("Id Does Not Exist", lmsService.GetResult());
    }

    @DisplayName("Search item")
    @Test
    void SearchTest() throws Exception {
        lmsService.Search("users", "1");
        String[][] compareStr = {{"1", "1"}};
        Assertions.assertTrue(Arrays.deepEquals(compareStr, lmsService.ReturnDataList()));
    }

    @DisplayName("Search rent result")
    @Test
    void SearchRentTest() throws Exception {
        lmsService.SearchRent("1");
        String[][] compareStr = {{"2", "rent"}};
        System.out.println(lmsService.ReturnDataList()[0][1]);
        Assertions.assertTrue(Arrays.deepEquals(compareStr, lmsService.ReturnDataList()));
    }

    @AfterEach
    void End() throws Exception {
        databaseManager.closeDatabase();
    }

}