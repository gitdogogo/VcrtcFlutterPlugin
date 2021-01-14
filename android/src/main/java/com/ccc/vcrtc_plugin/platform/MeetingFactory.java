package com.ccc.vcrtc_plugin.platform;

import android.content.Context;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.JSONMessageCodec;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class MeetingFactory extends PlatformViewFactory {
    final public static String SIGN = "vcrtvChannelMeetingView";
    private Context context;
    private BinaryMessenger messenger;
    MeetingViewListenerCallBack meetingViewListenerCallBack;
    MeetingViewCallBack meetingViewCallBack;
    public MeetingFactory(Context context, BinaryMessenger messenger, MeetingViewCallBack meetingViewCallBack, MeetingViewListenerCallBack meetingViewListenerCallBack){
        super(StandardMessageCodec.INSTANCE);
        this.context = context;
        this.messenger = messenger;
        this.meetingViewListenerCallBack = meetingViewListenerCallBack;
        this.meetingViewCallBack = meetingViewCallBack;
    }

    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        MeetingView view = new MeetingView(context,messenger,viewId,params, meetingViewCallBack);
        new MethodChannel(messenger,SIGN+viewId).setMethodCallHandler(view);
        return view;
    }

    public MeetingViewListenerCallBack getMeetingViewListenerCallBackCallBack() {
        return meetingViewListenerCallBack;
    }

    public MeetingViewCallBack getMeetingViewCallBack() {
        return meetingViewCallBack;
    }
}
