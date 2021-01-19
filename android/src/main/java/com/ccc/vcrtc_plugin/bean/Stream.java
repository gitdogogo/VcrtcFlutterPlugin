package com.ccc.vcrtc_plugin.bean;

import java.util.HashMap;
import java.util.Map;

public class Stream {
    Map<String,String> map = new HashMap<String,String>();
    public void setStream(String streamType,String streamUrl){
        map.put(streamType,streamUrl);
    }
    public String getStream(String streamType){
        if(streamType == null){
            return (String) map.values().toArray()[0];
        }else{
            return map.get(streamType);
        }
    }
}
