package com.ccc.vcrtc_plugin.platform;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.ccc.vcrtc_plugin.utils.Param;
import com.vcrtc.VCRTCView;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

public class MeetingView implements PlatformView, MethodChannel.MethodCallHandler{

    final public static String TAG = "MeetingView";
    private Context context;
    private BinaryMessenger messenger;
    private VCRTCView vcrtcView;
    MeetingViewCallBack callBack;
    int viewId;
    public MeetingView(Context context, BinaryMessenger messenger, int viewId, Map<String, Object> params, MeetingViewCallBack callBack) {
        this.context = context;
        this.messenger = messenger;
        this.callBack = callBack;
        this.viewId = viewId;
        ViewCollect viewCollect = ViewCollect.getInstance();
        String uuid = (String)params.get("uuid");
        String type = (String) params.get("type");
        String action = (String) params.get("action");
        String streamUrl = viewCollect.getStream(uuid).getStream(type);
        if("new".equals(action)){
            this.vcrtcView = new VCRTCView(context);
        }else{
            this.vcrtcView = viewCollect.getView(uuid);
        }
        this.vcrtcView.setStreamURL(streamUrl);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        Log.i(TAG, "|method=" + call.method + "|arguments=" + call.arguments);
        switch (call.method){
            case "startLocalView":
                startLocalView(call,result);
                break;
            case "startRemoteView":
                startRemoteView(call,result);
            case "setStream":
                setStream(call,result);
                break;
            default:
                result.notImplemented();
        }
    }

    private void setStream(MethodCall call, MethodChannel.Result result) {
        String uuid = call.argument("uuid");
        String type = call.argument("type");
        String streamUrl = ViewCollect.getInstance().getStream(uuid).getStream(type);
        this.vcrtcView.setStreamURL(streamUrl);
    }

    private void startLocalView(MethodCall call, MethodChannel.Result result) {
        callBack.onLocalViewCreated(viewId,this);
    }
    private void startRemoteView(MethodCall call, MethodChannel.Result result) {
        callBack.onRemoteViewCreated(viewId,this);
    }

    @Override
    public View getView() {
        return vcrtcView;
    }

    @Override
    public void dispose() {

    }
}
