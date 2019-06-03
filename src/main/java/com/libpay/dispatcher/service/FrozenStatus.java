package com.libpay.dispatcher.service;

public enum FrozenStatus {
    FROZEN(1),UNFROZEN(2);
    private int statusId;
    private FrozenStatus( int statusId){
        this.statusId=statusId;
    }

    public int getStatusId() {
        return statusId;
    }
}
