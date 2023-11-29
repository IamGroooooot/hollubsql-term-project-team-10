package com.holub.database;

import com.holub.text.ParseFailure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("Original Database Test Case")
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

    @DisplayName("Original Database Failure Test Case")
    @Test
    void testDatabaseParseFail() {
        // Test for failure
        Throwable exception = assertThrows(ParseFailure.class, () -> {
            theDatabase.execute("insert garbage SQL");
            fail("Test failed");
        });

        assertEquals(exception.getMessage(), "\"into\" expected.");
    }


    @DisplayName("Primary Key Test Case")
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

    @DisplayName("Primary Key Failure - Null Test Case")
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

    @DisplayName("Primary Key Failure - Duplicated Key Test Case")
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

    @DisplayName("Select * with join Test Case")
    @Test
    void testSelectAllWithJoin() throws IOException, ParseFailure {
        String tableSql = """
                create table address
                (
                    addrId int,
                    street varchar,
                    city   varchar,
                    state  char(2),
                    zip    int,
                    primary key (addrId)
                )
                """;
        theDatabase.execute(tableSql);
        theDatabase.execute("insert into address values (1, 'Dongjak', 'Seoul', 'gu', 12345)");
        theDatabase.execute("insert into address values (2, 'Seocho', 'Seoul', 'gu', 22245)");
        theDatabase.execute("insert into address values (3, 'Gangnam', 'Seoul', 'gu', 33345)");

        String tableSql2 = """
                create table name
                (
                    first  varchar(3),
                    last   varchar(8),
                    addrId integer
                )
                """;
        theDatabase.execute(tableSql2);
        theDatabase.execute("insert into name values ('HoSeong', 'Kim', 1)");
        theDatabase.execute("insert into name values ('JuHyung', 'Ko', 2)");
        theDatabase.execute("insert into name values ('TaekWon', 'Nam', 3)");
        theDatabase.execute("insert into name values ('JuHee', 'Choi', 4)");

        String selectSql = """
                select * from name, address
                where name.addrId = address.addrId
                """;
        String result = theDatabase.execute(selectSql).toString();

        System.out.println(result);

        assertTrue(result.contains("HoSeong	Kim	1	1	Dongjak	Seoul	gu	12345"));
        assertTrue(result.contains("JuHyung	Ko	2	2	Seocho	Seoul	gu	22245"));
        assertTrue(result.contains("TaekWon	Nam	3	3	Gangnam	Seoul	gu	33345"));
        assertFalse(result.contains("JuHee	Choi	4	4"));
    }
}