package com.example.edubridge;

public class MoreStudentHomeRV {
    String descriptionHome, imgHome, rasiedAmount, studentName, totalAmount;

    public MoreStudentHomeRV() {
        // Default constructor required for calls to DataSnapshot.getValue(MoreStudentHomeRV.class)
    }

    public MoreStudentHomeRV(String descriptionHome, String imgHome, String rasiedAmount, String studentName, String totalAmount) {
        this.descriptionHome = descriptionHome;
        this.imgHome = imgHome;
        this.rasiedAmount = rasiedAmount;
        this.studentName = studentName;
        this.totalAmount = totalAmount;
    }

    public String getDescriptionHome() {
        return descriptionHome;
    }

    public String getImgHome() {
        return imgHome;
    }

    public String getRasiedAmount() {
        return rasiedAmount;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setRasiedAmount(String raisedAmount) {
        this.rasiedAmount = raisedAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getRasiedAmountInt() {
        return Integer.parseInt(rasiedAmount);
    }

    public int getTotalAmountInt() {
        return Integer.parseInt(totalAmount);
    }
}
