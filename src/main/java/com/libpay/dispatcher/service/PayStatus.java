package com.libpay.dispatcher.service;

public enum PayStatus {
    INPROGESS(3),SUCCESS(1),FAILD(2);
    private int statusId;
    private PayStatus( int statusId){
        this.statusId=statusId;
    }

    public int getStatusId() {
        return statusId;
    }
}

