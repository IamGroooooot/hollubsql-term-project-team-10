package com.holub.database;

import com.main.holub.database.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ConcreteTableTest {
    private static String databaseRootPath = "/dp2023";

    Table people;
    Table address;

    @BeforeAll
    static void setUpAll() {
        String os = System.getProperty("os.name").toLowerCase();
        String homeDirectory = System.getProperty("user.home");
        // Windows: c:/dp2023 Linux: ~/dp2023
        databaseRootPath = os.contains("win") ? "/dp2023" : homeDirectory + "/dp2023";
    }

    @BeforeEach
    void setUp() {
        people = TableFactory.create("people", new String[]{"last", "first", "addrId"});
        address = TableFactory.create("address", new String[]{"addrId", "street", "city", "state", "zip"});
    }

    @DisplayName("Original ConcreteTable Test Case")
    @Test
    void test() {
        try {
            testInsert();
        } catch (Throwable t) {
            report(t, "Insert");
        }
        try {
            testUpdate();
        } catch (Throwable t) {
            report(t, "Update");
        }
        try {
            testDelete();
        } catch (Throwable t) {
            report(t, "Delete");
        }
        try {
            testSelect();
        } catch (Throwable t) {
            report(t, "Select");
        }
        try {
            testStore();
        } catch (Throwable t) {
            report(t, "Store/Load");
        }
        try {
            testJoin();
        } catch (Throwable t) {
            report(t, "Join");
        }
        try {
            testUndo();
        } catch (Throwable t) {
            report(t, "Undo");
        }
    }

     void testInsert() {
        people.insert(new Object[]{"Holub", "Allen", "1"});
        people.insert(new Object[]{"Flintstone", "Wilma", "2"});
        people.insert(new String[]{"addrId", "first", "last"}, new Object[]{"2", "Fred", "Flintstone"});

        address.insert(new Object[]{"1", "123 MyStreet", "Berkeley", "CA", "99999"});

        List l = new ArrayList();
        l.add("2");
        l.add("123 Quarry Ln.");
        l.add("Bedrock ");
        l.add("XX");
        l.add("12345");
        assert (address.insert(l) == 1);

        l.clear();
        l.add("3");
        l.add("Bogus");
        l.add("Bad");
        l.add("XX");
        l.add("12345");

        List c = new ArrayList();
        c.add("addrId");
        c.add("street");
        c.add("city");
        c.add("state");
        c.add("zip");
        assert (address.insert(c, l) == 1);

        System.out.println(people.toString());
        System.out.println(address.toString());

//			try {
//				people.insert(new Object[] { "x" });
//				throw new AssertionError("insert wrong number of fields test failed");
//			} catch (Throwable t) {
//				/* Failed correctly, do nothing */ }
//
//			try {
//				people.insert(new String[] { "?" }, new Object[] { "y" });
//				throw new AssertionError("insert-nonexistent-field test failed");
//			} catch (Exception t) {
//				/* Failed correctly, do nothing */ }
    }

     void testUpdate() {
        System.out.println("update set state='YY' where state='XX'");
        int updated = address.update(new Selector() {
            public boolean approve(Cursor[] tables) {
                return tables[0].column("state").equals("XX");
            }

            public void modify(Cursor current) {
                current.update("state", "YY");
            }
        });
        print(address);
        System.out.println(updated + " rows affected\n");
    }

     void testDelete() {
        System.out.println("delete where street='Bogus'");
        int deleted = address.delete(new Selector.Adapter() {
            public boolean approve(Cursor[] tables) {
                return tables[0].column("street").equals("Bogus");
            }
        });
        print(address);
        System.out.println(deleted + " rows affected\n");
    }

     void testSelect() {
        Selector flintstoneSelector = new Selector.Adapter() {
            public boolean approve(Cursor[] tables) {
                return tables[0].column("last").equals("Flintstone");
            }
        };

        // SELECT first, last FROM people WHERE last = "Flintstone"
        // The collection version chains to the string version, so the
        // following call tests both versions

        List columns = new ArrayList();
        columns.add("first");
        columns.add("last");

        Table result = people.select(flintstoneSelector, columns);
        print(result);

        // SELECT * FROM people WHERE last = "Flintstone"
        result = people.select(flintstoneSelector);
        print(result);

        // Check that the result is indeed unmodifiable

        try {
            result.insert(new Object[]{"x", "y", "z"});
            throw new AssertionError("Insert to Immutable Table test failed");
        } catch (Exception e) {
            /* it failed correctly */
        }

        try {
            result.update(flintstoneSelector);
            throw new AssertionError("Update of Immutable Table test failed");
        } catch (Exception e) {
            /* it failed correctly */
        }

        try {
            result.delete(flintstoneSelector);
            throw new AssertionError("Delete of Immutable Table test failed");
        } catch (Exception e) {
            /* it failed correctly */
        }
    }

     void testStore() throws IOException, ClassNotFoundException { // Flush the table to disk, then reread it.
        // Subsequent tests that use the
        // "people" table will
        // fail if this operation fails.

        Writer out = new FileWriter(databaseRootPath + "/people");
        people.export(new XMLExporter(out));
        out.close();

        Reader in = new FileReader(databaseRootPath + "/people");
        people = new ConcreteTable(new XMLImporter(in));
        in.close();
    }

     void testJoin() {
        // First test a two-way join

        System.out.println("\nSELECT first,last,street,city,state,zip" + " FROM people, address"
                + " WHERE people.addrId = address.addrId");

        // Collection version chains to String[] version,
        // so this code tests both:
        List columns = new ArrayList();
        columns.add("first");
        columns.add("last");
        columns.add("street");
        columns.add("city");
        columns.add("state");
        columns.add("zip");

        List tables = new ArrayList();
        tables.add(address);

        Table result = // WHERE people.addrID = address.addrID
                people.select(new Selector.Adapter() {
                    public boolean approve(Cursor[] tables) {
                        return tables[0].column("addrId").equals(tables[1].column("addrId"));
                    }
                }, columns, tables);

        print(result);
        System.out.println();

        // Now test a three-way join
        //
        System.out.println("\nSELECT first,last,street,city,state,zip,text" + " FROM people, address, third"
                + " WHERE (people.addrId = address.addrId)" + " AND (people.addrId = third.addrId)");

        Table third = TableFactory.create("third", new String[]{"addrId", "text"});
        third.insert(new Object[]{"1", "addrId=1"});
        third.insert(new Object[]{"2", "addrId=2"});

        result = people.select(new Selector.Adapter() {
                                   public boolean approve(Cursor[] tables) {
                                       return (tables[0].column("addrId").equals(tables[1].column("addrId"))
                                               && tables[0].column("addrId").equals(tables[2].column("addrId")));
                                   }
                               },

                new String[]{"last", "first", "state", "text"}, new Table[]{address, third});

        System.out.println(result.toString() + "\n");
    }

     void testUndo() {
        // Verify that commit works properly
        people.begin();
        System.out.println("begin/insert into people (Solo, Han, 5)");

        people.insert(new Object[]{"Solo", "Han", "5"});
        System.out.println(people.toString());

        people.begin();
        System.out.println("begin/insert into people (Lea, Princess, 6)");

        people.insert(new Object[]{"Lea", "Princess", "6"});
        System.out.println(people.toString());

        System.out.println("commit(THIS_LEVEL)\n" + "rollback(Table.THIS_LEVEL)\n");
        people.commit(Table.THIS_LEVEL);
        people.rollback(Table.THIS_LEVEL);
        System.out.println(people.toString());

        // Now test that nested transactions work correctly.

        System.out.println(people.toString());

        System.out.println("begin/insert into people (Vader,Darth, 4)");
        people.begin();
        people.insert(new Object[]{"Vader", "Darth", "4"});
        System.out.println(people.toString());

        System.out.println("begin/update people set last=Skywalker where last=Vader");

        people.begin();
        people.update(new Selector() {
            public boolean approve(Cursor[] tables) {
                return tables[0].column("last").equals("Vader");
            }

            public void modify(Cursor current) {
                current.update("last", "Skywalker");
            }
        });
        System.out.println(people.toString());

        System.out.println("delete from people where last=Skywalker");
        people.delete(new Selector.Adapter() {
            public boolean approve(Cursor[] tables) {
                return tables[0].column("last").equals("Skywalker");
            }
        });
        System.out.println(people.toString());

        System.out.println("rollback(Table.THIS_LEVEL) the delete and update");
        people.rollback(Table.THIS_LEVEL);
        System.out.println(people.toString());

        System.out.println("rollback(Table.THIS_LEVEL) insert");
        people.rollback(Table.THIS_LEVEL);
        System.out.println(people.toString());
    }

     void print(Table t) { // tests the table iterator
        Cursor current = t.rows();
        while (current.advance()) {
            for (Iterator columns = current.columns(); columns.hasNext(); )
                System.out.print(columns.next() + " ");
            System.out.println();
        }
    }

    void report(Throwable t, String message) {
        System.out.println(message + " FAILED with exception toss");
        t.printStackTrace();
        System.exit(1);
    }
}