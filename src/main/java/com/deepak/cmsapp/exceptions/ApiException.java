package com.deepak.cmsapp.exceptions;

public class ApiException  extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
    public ApiException(){
        super();
    }
    
}
