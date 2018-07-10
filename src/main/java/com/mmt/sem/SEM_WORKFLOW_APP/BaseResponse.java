package com.mmt.sem.SEM_WORKFLOW_APP;


import java.util.ArrayList;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class BaseResponse {
    private int status = 200;
    private String msg = "ok";
    private ArrayList<String> fileNames = new ArrayList<>();
    private String function;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        if(this.status != 400){
        this.status = status;
        }
    }

    @JsonSerialize
    public String getMessage() {
        return msg;
    }
    public void setMessage(String msg) {
        if(this.msg.equals("ok"))
        this.msg = msg;
    }

    public ArrayList<String> getFileName(){
        return fileNames;
    }

    public void addFileName(String fileName){
        fileNames.add(fileName);
    }

    public String getFunction(){
        return function;
    }

    public void setFunction(String function){
        this.function = function;
    }
}
