package com.ccc.vcrtc_plugin.platform;

import com.vcrtc.VCRTCView;

public interface MeetingViewListenerCallBack {
    void onLocalVideo(String uuid,VCRTCView view);
    void onRemoteVideo(String uuid,VCRTCView view);
    void onAddView(String uuid,VCRTCView view,String viewType);
    void onRemoteStream(String uuid, String streamUrl, String streamType);
    void onLocalStream(String uuid, String streamUrl, String streamType);
    void onRemoveView(String uuid, VCRTCView vcrtcView);
}
