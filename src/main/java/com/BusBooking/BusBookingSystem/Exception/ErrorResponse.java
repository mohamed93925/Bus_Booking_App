package com.BusBooking.BusBookingSystem.Exception;

public class ErrorResponse {

    public int status;

    public String message;

    public ErrorResponse(int status, String message){
        this.message=message;
        this.status =status;

    }

    public int getStatus(int status){
        return status;
    }
    public String getMessage(String message){
        return message;
    }
}
