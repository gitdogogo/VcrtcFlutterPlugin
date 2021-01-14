package com.ccc.vcrtc_plugin.platform;

public interface MeetingViewCallBack {
    void onLocalViewCreated(int viewId,MeetingView view);
    void onRemoteViewCreated(int viewId,MeetingView view);
}
