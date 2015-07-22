package com.marcuello.sprint7;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;

import java.util.List;

/**
 * Unit test for CSVPersons.
 */
public class SomeCoolCsvReaderTest {

    @Test
    public void testGetCSVSeparator() {
        SomeCoolCsvReader csv = new SomeCoolCsvReader(new File("persons.csv"), Person.class);
        assertEquals(csv.getCSVSeparator(), ",");
    }

    @Test
    public void testGetCSVColumns() {
        SomeCoolCsvReader csv = new SomeCoolCsvReader(new File("persons.csv"), Person.class);
        assertEquals(csv.getCSVcolumn(1), "firstName");
        assertEquals(csv.getCSVcolumn(2), "lastName");
        assertEquals(csv.getCSVcolumn(3), null);
    }

    @Test
    public void testConstructor() {
        SomeCoolCsvReader csv = new SomeCoolCsvReader(new File("persons.csv"), Person.class);
        assertEquals(csv.getCSVcolumn(1), "firstName");
        assertEquals(csv.getCSVcolumn(2), "lastName");
        assertEquals(csv.getCSVSeparator(), ",");
        assertFalse(csv.getCSVBufferedReader() == null);
        assertEquals(csv.getCSVFileName(), "persons.csv");
        assertEquals(csv.getCSVClass(), Person.class);
    }

    @Test
    public void testReadCSVLine() {
        String CSVLine = new SomeCoolCsvReader(new File("persons.csv"), Person.class).readCSVLine();
        assertEquals(CSVLine, "John,Doe");
    }

    @Test
    public void testGetPersons() {
        SomeCoolCsvReader csv = new SomeCoolCsvReader(new File("persons.csv"), Person.class);
        List<Person> persons = csv.getPersons();
        assertEquals(persons.get(0).getFirstName(), "John");
        assertEquals(persons.get(2).getLastName(), "Parker");
    }
}
