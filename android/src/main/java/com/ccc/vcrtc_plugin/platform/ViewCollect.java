package com.ccc.vcrtc_plugin.platform;


import com.ccc.vcrtc_plugin.bean.Stream;
import com.vcrtc.VCRTCView;

import java.util.HashMap;
import java.util.Map;

public class ViewCollect {
    Map<String,VCRTCView> vcrtcViewMap;
    Map<String, Stream> vcrtcStreamMap;
    private static class SingletonClassInstance{
        private static final ViewCollect instance=new ViewCollect();
    }
    private ViewCollect(){
        vcrtcViewMap = new HashMap<String,VCRTCView>();
        vcrtcStreamMap = new HashMap<String,Stream>();
    }
    public static ViewCollect getInstance(){
        return SingletonClassInstance.instance;
    }
    public void setView(String uuid,VCRTCView view){
        this.vcrtcViewMap.put(uuid,view);
    }
    public Stream getStream(String uuid){
        return this.vcrtcStreamMap.get(uuid);
    }
    public void setStream(String uuid,Stream stream){
        this.vcrtcStreamMap.put(uuid,stream);
    }
    public VCRTCView getView(String uuid){
        return this.vcrtcViewMap.get(uuid);
    }
    public void removeView(String uuid){
        this.vcrtcViewMap.remove(uuid);
        this.vcrtcStreamMap.remove(uuid);
    }
}
