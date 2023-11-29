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
    void setUp() throws IOException, ParseFailure {
        theDatabase = new Database();
        String sql = """
                create database Dbase
                """;
        theDatabase.execute(sql);
    }

    @AfterEach
    void tearDown() {
        theDatabase = null;
    }

    @Test
    void testOriginalDatabaseTestCase() throws IOException, ParseFailure {
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


    @Test
    void testPrimaryKey() throws IOException, ParseFailure {
        String tableSql = """
                create table user
                (
                    id int,
                    name varchar(20),
                    addr varchar(20),
                    primary key (id)
                )
                """;
        theDatabase.execute(tableSql);
        theDatabase.execute("insert into user(id, name, addr) values (1, 'Hoseong', 'Seoul')");
        theDatabase.execute("insert into user(id, name, addr) values (2, 'Juhyeong', 'Seoul')");
        String selectSql = """
                select * from user
                """;
        String result = theDatabase.execute(selectSql).toString();
        System.out.println(result);
        assertTrue(result.contains("1	Hoseong	Seoul"));
        assertTrue(result.contains("2	Juhyeong	Seoul"));
    }

    @Test
    void testPrimaryKeyNullFail() throws IOException, ParseFailure {
        String tableSql = """
                create table user
                (
                    id int,
                    name varchar(20),
                    addr varchar(20),
                    primary key (id)
                )
                """;
        theDatabase.execute(tableSql);
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            theDatabase.execute("insert into user(id, name, addr) values (null, 'Juhyeong', 'Seoul')");
            fail("Test failed");
        });

        assertEquals(exception.getMessage(), "Primary key(id) is null");
    }

    @Test
    void testPrimaryKeyDuplicatedFail() throws IOException, ParseFailure {
        String tableSql = """
                create table user
                (
                    id int,
                    name varchar(20),
                    addr varchar(20),
                    primary key (id)
                )
                """;
        theDatabase.execute(tableSql);
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            theDatabase.execute("insert into user(id, name, addr) values (1, 'Hoseong', 'Seoul')");
            theDatabase.execute("insert into user(id, name, addr) values (1, 'Juhyeong', 'Seoul')");
            fail("Test failed");
        });

        assertEquals(exception.getMessage(), "Primary key(id) is duplicated");
    }
}