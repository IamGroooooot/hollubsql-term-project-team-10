package com.holub.database;

import com.holub.tools.ArrayIterator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

class XMLExporterTest {
    private static final String fileName = "people_xml_test";

    private Writer out;
    private Table.Exporter exporter;

    private  String tableName;
    private String[] columnNames;
    private LinkedList rowSet;

    @BeforeEach
    void setUp() throws IOException {
        out = new FileWriter(fileName);

        tableName = "people";
        columnNames = new String[]{"last", "first", "addrId"};

        rowSet = new LinkedList();
        insert(new Object[]{"Holub", "Allen", "1"});
        insert(new Object[]{"Flintstone", "Wilma", "2"});
        insert(new Object[]{"2", "Fred", null});
    }

    @AfterEach
    void tearDown() throws IOException {
        out.close();

        FileReader fileReader = new FileReader(fileName);
        BufferedReader in = new BufferedReader(fileReader);

        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

        in.close();
    }

    void insert(Object[] row) {
        rowSet.add(row);
    }

    @Test
    void testExport() throws IOException {
        exporter = new XMLExporter(out);

        exporter.startTable();
        exporter.storeMetadata(tableName, columnNames.length, rowSet.size(), new ArrayIterator(columnNames));
        for (Iterator i = rowSet.iterator(); i.hasNext(); )
            exporter.storeRow(new ArrayIterator((Object[]) i.next()));
        exporter.endTable();
    }
}