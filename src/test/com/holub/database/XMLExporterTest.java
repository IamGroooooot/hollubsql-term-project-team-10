package com.holub.database;

import com.holub.tools.ArrayIterator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

class XMLExporterTest {
    private Table.Exporter exporter;
    private Writer out;

    private  String tableName;
    private String[] columnNames;
    private LinkedList rowSet;


    @BeforeEach
    void setUp() throws IOException {
        out = new FileWriter("people");
        exporter = new XMLExporter(out);

        tableName = "people";
        columnNames = new String[]{"last", "first", "addrId"};

        rowSet = new LinkedList();
        rowSet.add(new Object[]{"Holub", "Allen", "1"});
        rowSet.add(new Object[]{"Flintstone", "Wilma", "2"});
        rowSet.add(new Object[]{"2", "Fred", null});
    }

    @AfterEach
    void tearDown() throws IOException {
        out.close();
    }

    @Test
    void export() throws IOException {
        exporter.startTable();
        exporter.storeMetadata(tableName, columnNames.length, rowSet.size(), new ArrayIterator(columnNames));
        for (Iterator i = rowSet.iterator(); i.hasNext(); )
            exporter.storeRow(new ArrayIterator((Object[]) i.next()));
        exporter.endTable();
    }
}