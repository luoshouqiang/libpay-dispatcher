package com.libpay.dispatcher.service;

public enum PayType {

    WEIXIN(1),ZHIFUBAO(2);
    private int typeId;
    private PayType(int typeId){
        this.typeId=typeId;
    }

    public int getTypeId() {
        return typeId;
    }
}
