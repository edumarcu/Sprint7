package com.marcuello.sprint7;

import java.io.FileNotFoundException;
import java.lang.annotation.*;
import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;


/**
 * SPRINT 7
 *
 * Goal: The new Extraction
 *
 * As an Architect I would like to have an annotated Java
 * bean able to map to CSV file so we can reuse the existing model class.
 */

public class SomeCoolCsvReader {

    private File CSVFile;
    private FileReader CSVReader;
    private BufferedReader CSVBufferedReader;
    private Class<?> CSVClass;
    private HashMap<Integer, String> CSVcolumns;   //  index of the column and name of the field
    private String CSVSeparator;                  // char separator

    //---------------------------------------------------------------- CONSTRUCTORS
    public SomeCoolCsvReader() {

    }

    public SomeCoolCsvReader(File file, Class<?> c) {
        this.setCSVFile(file);
        try {
            this.setCSVReader(new FileReader(this.getCSVFile()));
        } catch (FileNotFoundException e) {
            //additional: manage exception
        }
        this.setCSVBufferedReader(new BufferedReader(this.getCSVReader()));
        this.setCSVClass(c);
        this.setCSVSeparator();
        this.setCSVcolumns();
    }

    //------------------------------------------------------------- GETTERS & SETTERS
    public File getCSVFile() {
        return CSVFile;
    }

    public void setCSVFile(File CSVFile) {
        this.CSVFile = CSVFile;
    }

    public FileReader getCSVReader() {
        return this.CSVReader;
    }

    public void setCSVReader(FileReader reader) {
        this.CSVReader = reader;
    }

    public BufferedReader getCSVBufferedReader() {
        return this.CSVBufferedReader;
    }

    public void setCSVBufferedReader(BufferedReader bufferedReader) {
        this.CSVBufferedReader = bufferedReader;
    }

    public Class<?> getCSVClass() {
        return CSVClass;
    }

    public void setCSVClass(Class<?> CSVClass) {
        this.CSVClass = CSVClass;
    }


    //---------------------------------------------------------------------------- GET ANNOTATIONS INFO
    public String getCSVcolumn(Integer i) {
        return this.CSVcolumns.get(i);
    }

    // Get the value of the annotations CSVColumn, return pairs <index, fieldname>
    public void setCSVcolumns() {

        this.CSVcolumns = new HashMap<>();
        Field fields[] = CSVClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if(fields[i].isAnnotationPresent(CSVColumn.class)) {
                this.CSVcolumns.put(fields[i].getAnnotation(CSVColumn.class).indx(), fields[i].getName());
                //additional: check indexes starting in 1 and correlative, if not throw exception "indexes not valid"
            } else {
                this.CSVcolumns = null; //additional: throw exception "field not annotated"
                break;
            }
        }
    }

    public String getCSVSeparator() {
        return this.CSVSeparator;
    }

    // Get the value of the annotation FileMetaData
    public void setCSVSeparator() {
        if(CSVClass.isAnnotationPresent(FileMetaData.class)) {
            CSVSeparator = CSVClass.getAnnotation(FileMetaData.class).separator();
        } else {
            CSVSeparator = null;
        }
    }

    //------------------------------------------------------------------------------------- AUX
    public String getCSVFileName() {
        return CSVFile.getName();
    }

    protected String readCSVLine() {
        try {
            return getCSVBufferedReader().readLine();
        } catch (Exception e) {
            // additional: manage exception
            return null;
        }
    }

    //--------------------------------------------------------------------- GET PERSONS
    // additional: getData based on the class passed in the constructor
    public List<Person> getPersons() {
        List<Person> data = new ArrayList<>();
        String line;
        while ((line = readCSVLine()) != null) {
            String tokens[] = line.split(this.getCSVSeparator());
            Person CSVperson = new Person();
            CSVperson.setFirstName(tokens[0]); //additional: use setters based on the class passed to the constructor
            CSVperson.setLastName(tokens[1]);
            data.add(data.size(), CSVperson);
        }
        return data;
    }

    //--------------------------------------------------------------------------- ANNOTATIONS
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface FileMetaData {
        String separator() default ",";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface CSVColumn {
        int indx() default 1;
    }
}