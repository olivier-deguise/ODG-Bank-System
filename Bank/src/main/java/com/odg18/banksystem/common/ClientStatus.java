package com.odg18.banksystem.common;

public enum ClientStatus {
    BRONZE(0),
    SILVER(1),
    GOLD(2);

    int level;
    ClientStatus(int level){
        this.level = level;
    }

    public int getLevel(){
        return this.level;
    }

}
