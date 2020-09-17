package com.gabrielnwogu.elevatorSystem.dto;

public class ComplaintDto {

    private String orderNumber;

    private String problem;

    public ComplaintDto() {
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}
