package com.stallar.vms.model;

/**
 * Created by lucie on 11/21/2017.
 */

public class NationalIID {
    String id;
    String otherNames;
    String surname;
    String dateOfBirth;
    String serialNo;
    String sex;
    String dateOfIssue;

//    public NationalIID(String id, String otherNames, String surname,
//                       String dateOfBirth, String serialNo, String sex, String dateOfIssue){
//        this.id = id;
//        this.otherNames = otherNames;
//        this.surname = surname;
//        this.dateOfBirth = dateOfBirth;
//        this.serialNo = serialNo;
//        this.sex = sex;
//        this.dateOfIssue = dateOfIssue;
//    }

    public NationalIID(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }
}
