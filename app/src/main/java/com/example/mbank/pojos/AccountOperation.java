package com.example.mbank.pojos;

public class AccountOperation {
    private int operationImage;
    private String operationName;
    private double operationBalance;

    public AccountOperation(int operationImage,
                            String operationName,
                            double operationBalance) {
        this.operationBalance = operationBalance;
        this.operationName = operationName;
        this.operationImage = operationImage;
    }

    public int getOperationImage() {
        return operationImage;
    }

    public void setOperationImage(int operationImage) {
        this.operationImage = operationImage;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public double getOperationBalance() {
        return operationBalance;
    }

    public void setOperationBalance(double operationBalance) {
        this.operationBalance = operationBalance;
    }



}
