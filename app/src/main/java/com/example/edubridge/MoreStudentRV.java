package com.example.edubridge;

public class MoreStudentRV {
    String descriptionHome, imgHome, rasiedAmount, studentName, totalAmount;

    public MoreStudentRV() {
    }

    public MoreStudentRV(String descriptionHome, String imgHome, String rasiedAmount, String studentName, String totalAmount) {
        this.descriptionHome = descriptionHome;
        this.imgHome = imgHome;
        this.rasiedAmount = rasiedAmount;
        this.studentName = studentName;
        this.totalAmount = totalAmount;
    }

    public String getDescriptionHome() {
        return descriptionHome;
    }

    public void setDescriptionHome(String descriptionHome) {
        this.descriptionHome = descriptionHome;
    }

    public String getImgHome() {
        return imgHome;
    }

    public void setImgHome(String imgHome) {
        this.imgHome = imgHome;
    }

    public String getRasiedAmount() {
        return rasiedAmount;
    }

    public void setRasiedAmount(String rasiedAmount) {
        this.rasiedAmount = rasiedAmount;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }


}
