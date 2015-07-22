package com.marcuello.sprint7;

import com.marcuello.sprint7.SomeCoolCsvReader.FileMetaData;
import com.marcuello.sprint7.SomeCoolCsvReader.CSVColumn;

import java.lang.reflect.Field;

/**
 * Person: class already exiting
 */

@FileMetaData(separator = ",")
public class Person {
    @CSVColumn(indx = 1) private String firstName;
    @CSVColumn(indx = 2) private String lastName;

    public String getFirstName() {return this.firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return this.lastName;}

    public void setLastName (String lastName) {this.lastName = lastName;}
}
