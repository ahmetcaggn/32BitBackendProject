package com.toyota.Sales.exception;

public class CampaignNotFoundException extends RuntimeException{
    public CampaignNotFoundException(String message){
        super(message);
    }
}
