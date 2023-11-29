package com.holub.database;

import com.holub.text.ParseFailure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private static Database theDatabase;
    private static final String sqlDirectoryPath = "src/test/com/holub/database/sql/";

    @BeforeEach
    void setUp() {
        theDatabase = new Database();
    }

    @AfterEach
    void tearDown() {
        theDatabase = null;
    }

    @Test
    void testDatabase() throws IOException, ParseFailure {
        BufferedReader sql = new BufferedReader(
                new FileReader(sqlDirectoryPath + "original-test.sql"));

        String test;
        while ((test = sql.readLine()) != null) {
            test = test.trim();
            if (test.length() == 0)
                continue;

            while (test.endsWith("\\")) {
                test = test.substring(0, test.length() - 1);
                test += sql.readLine().trim();
            }

            System.out.println("Parsing: " + test);
            Table result = theDatabase.execute(test);

            if (result != null)    // it was a SELECT of some sort
                System.out.println(result);
        }

        theDatabase.dump(ExporterType.CSV);
        System.out.println("Database PASSED");
    }

    @Test
    void testDatabaseParseFail() {
        // Test for failure
        Throwable exception = assertThrows(ParseFailure.class, () -> {
            theDatabase.execute("insert garbage SQL");
            fail("Test failed");
        });

        assertEquals(exception.getMessage(), "\"into\" expected.");
    }
}