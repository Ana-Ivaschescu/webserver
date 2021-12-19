package com.code.http;

import com.code.httpserver.config.Configuration;
import com.code.httpserver.config.ConfigurationManager;

public class Code {
    private int code;
    private static Code myCode;

    public static Code getInstance(){
        if(myCode == null)
            myCode = new Code();
        return myCode;
    }
    private Code(){}

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }
}
