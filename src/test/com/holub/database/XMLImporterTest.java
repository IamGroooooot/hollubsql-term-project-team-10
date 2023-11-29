package com.holub.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class XMLImporterTest {
    private static final String fileName = "people_xml_test";

    private Reader in;
    private Table.Importer importer;

    private Table table;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        in = new FileReader(fileName);
    }

    @AfterEach
    void tearDown() throws IOException {
        in.close();

        System.out.println(table.toString());
    }

    @Test
    void testImport() throws IOException {
        importer = new XMLImporter(in);

        importer.startTable();

        String tableName = importer.loadTableName();
        int width = importer.loadWidth();
        Iterator columns = importer.loadColumnNames();

        String[] columnNames = new String[width];
        for (int i = 0; columns.hasNext(); )
            columnNames[i++] = (String) columns.next();

        table = TableFactory.create(tableName, columnNames);

        while ((columns = importer.loadRow()) != null) {
            Object[] current = new Object[width];
            for (int i = 0; columns.hasNext(); )
                current[i++] = columns.next();
            table.insert(current);
        }
        importer.endTable();
    }
}