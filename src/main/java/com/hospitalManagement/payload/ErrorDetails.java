package com.hospitalManagement.payload;


import java.util.Date;

public class ErrorDetails {

    private Date timeStamp;  //CURRENT DATE & TIME
    private String message;
    private String details;

    public ErrorDetails(Date timeStamp,String message,String details){
        this.timeStamp=timeStamp;
        this.message=message;
        this.details=details;
    }

    private Date getTimeStamp(){
        return timeStamp;
    }
    private String getMessage(){
        return message;
    }
    private String getDetails(){
        return details;
    }

}
