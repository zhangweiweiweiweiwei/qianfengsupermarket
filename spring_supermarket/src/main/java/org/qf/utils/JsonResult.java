package org.qf.utils;

import java.util.HashMap;

public class JsonResult extends HashMap<String,Object>{
    public  JsonResult message(String message){
        this.put("message",message);
        return  this;
    }
    public JsonResult success(Boolean success){
        this.put("success",success);
        return  this;
    }
    public JsonResult result(String result){
        this.put("result",result);
        return this;
    }

}
