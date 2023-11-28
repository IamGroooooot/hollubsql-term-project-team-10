package com.lms.service;

import com.lms.repository.DatabaseManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SearchDatabaseTest {
    private SearchDatabase searchDatabase;
    private DatabaseManager databaseManager;
    private UpdateDatabase updateDatabase;
    private static final String databaseName = "test";

    @BeforeEach
    void Setup() throws Exception {
        databaseManager = new DatabaseManager();
        databaseManager.openDatabase(databaseName);
        updateDatabase = new UpdateDatabase(databaseManager);
        searchDatabase = new SearchDatabase(databaseManager);
        updateDatabase.AddData("users", "1", "1");
        updateDatabase.AddData("books", "2", "2");
        updateDatabase.AddData("1", "2");
    }

    @DisplayName("Test item exists in users table")
    @Test
    void TestItemExists() throws Exception {
        Assertions.assertTrue(searchDatabase.CheckItemExist("users", "1"));
        Assertions.assertFalse(searchDatabase.CheckItemExist("users", "2"));
    }

    @DisplayName("Test index error in database")
    @Test
    void TestItemExistsIndexError() throws Exception {
        Assertions.assertFalse(searchDatabase.CheckItemExist("test", "2"));
    }

    @DisplayName("Get users id with name")
    @Test
    void TestGetIdByName() throws Exception {
        Assertions.assertEquals("1", searchDatabase.GetItemString("users", "Id", "Name", "1"));
    }

    @DisplayName("Test errors when column not in table")
    @Test
    void TestGetIdByNameError() throws Exception {
        Assertions.assertNull(searchDatabase.GetItemString("users", "Id", "test", "1"));
    }

    @DisplayName("Test item exists in rents table")
    @Test
    void TestItemExistsInRents() throws Exception {
        Assertions.assertTrue(searchDatabase.CheckRentExist("1", "2"));
        Assertions.assertFalse(searchDatabase.CheckRentExist("2", "1"));
    }

    

}
