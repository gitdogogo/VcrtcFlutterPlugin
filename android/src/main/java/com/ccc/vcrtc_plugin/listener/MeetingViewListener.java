package com.ccc.vcrtc_plugin.listener;

import android.graphics.Bitmap;
import android.util.Log;

import com.ccc.vcrtc_plugin.VcrtcPlugin;
import com.ccc.vcrtc_plugin.platform.MeetingFactory;
import com.google.gson.Gson;
import com.vcrtc.VCRTCView;
import com.vcrtc.entities.ConferenceStatus;
import com.vcrtc.entities.ErrorCode;
import com.vcrtc.entities.Participant;
import com.vcrtc.entities.Stage;
import com.vcrtc.entities.WhiteboardPayload;
import com.vcrtc.listeners.VCRTCListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class MeetingViewListener implements VCRTCListener {
    final private static String TAG = "MeetingViewListener";

    /**
     * 监听器回调的方法名
     */
    private static final String LISTENER_FUNC_NAME = "onListener";
    private MeetingFactory meetingFactory;
    private  MethodChannel channel;
    Gson gson = new Gson();
    public MeetingViewListener(MeetingFactory meetingFactory, MethodChannel channel){
        this.meetingFactory = meetingFactory;
        this.channel = channel;
    }

    /**
     * 调用监听器
     *
     * @param type   类型
     * @param params 参数
     */
    private void invokeListener(String type, Object params) {
        final Map<String, Object> resultParams = new HashMap<>(2, 1);
        resultParams.put("type", type);
        if (params != null) {
            resultParams.put("params", params);
        }
        VcrtcPlugin.mainHandler.post(new Runnable() {
            @Override
            public void run() {
                channel.invokeMethod(LISTENER_FUNC_NAME, gson.toJson(resultParams));
            }
        });
    }

    @Override
    public void onAuthorizationStatus(boolean b) {
        Log.i(TAG,"onAuthorizationStatus==="+b);
        invokeListener("onAuthorizationStatus",b);
    }

    @Override
    public void onCameraStream(String streamURL) {
        Log.i(TAG,"onCameraStream==="+streamURL);
        invokeListener("onCameraStream",streamURL);
    }

    @Override
    public void onConnected(String uuid) {
        Log.i(TAG,"onConnected==="+uuid);
        invokeListener("onConnected",uuid);
    }

    @Override
    public void onCallConnected() {
        Log.i(TAG,"onCallConnected===");
        invokeListener("onCallConnected",null);
    }

    @Override
    public void onCallReconnected() {
        Log.i(TAG,"onCallReconnected===");
        invokeListener("onCallReconnected",null);
    }

    @Override
    public void onLocalVideo(String uuid, VCRTCView vcrtcView) {
        Log.i(TAG,"onLocalVideo==="+uuid);
        meetingFactory.getMeetingViewListenerCallBackCallBack().onLocalVideo(uuid,vcrtcView);
        Map map = new HashMap();
        map.put("uuid",uuid);
        invokeListener("onLocalVideo",map);
    }

    @Override
    public void onRemoteVideo(String uuid, VCRTCView vcrtcView) {
        Log.i(TAG,"onRemoteVideo==="+uuid);
        meetingFactory.getMeetingViewListenerCallBackCallBack().onRemoteVideo(uuid,vcrtcView);
        Map map = new HashMap();
        map.put("uuid",uuid);
        invokeListener("onRemoteVideo",map);
    }

    @Override
    public void onAddView(String uuid, VCRTCView vcrtcView, String viewType) {
        Log.i(TAG,"onAddView==="+uuid);
        meetingFactory.getMeetingViewListenerCallBackCallBack().onAddView(uuid,vcrtcView,viewType);
//        Map map = new HashMap();
//        map.put("uuid",uuid);
//        map.put("type",viewType);
//        invokeListener("onAddView",map);
    }

    @Override
    public void onRemoveView(String uuid, VCRTCView vcrtcView) {
        Log.i(TAG,"onRemoveView==="+uuid);
        meetingFactory.getMeetingViewListenerCallBackCallBack().onRemoveView(uuid,vcrtcView);
        Map map = new HashMap();
        map.put("uuid",uuid);
        invokeListener("onRemoveView",map);
    }

    @Override
    public void onLocalStream(String uuid, String streamUrl, String streamType) {
        Log.i(TAG,"onLocalStream==="+uuid+"=="+streamUrl+"=="+streamType);
        meetingFactory.getMeetingViewListenerCallBackCallBack().onLocalStream(uuid,streamUrl,streamType);
        //invokeListener("onLocalStream", new String[]{uuid, streamUrl, streamType});
    }

    @Override
    public void onRemoteStream(String uuid, String streamUrl, String streamType) {
        Log.i(TAG,"onRemoteStream==="+uuid+"=="+streamUrl+"=="+streamType);
        meetingFactory.getMeetingViewListenerCallBackCallBack().onRemoteStream(uuid,streamUrl,streamType);
        Map map = new HashMap();
        map.put("uuid",uuid);
        map.put("type",streamType);
        invokeListener("onRemoteStream", map);
    }

    @Override
    public void onParticipantList(List<Participant> list) {
        Log.i(TAG,"onParticipantList==="+list.toString());
        invokeListener("onParticipantList", list);
    }

    @Override
    public void onAddParticipant(Participant participant) {
        Log.i(TAG,"onAddParticipant==="+ gson.toJson(participant));
        invokeListener("onParticipantList", participant);
    }

    @Override
    public void onRemoveParticipant(String uuid) {
        Log.i(TAG,"onRemoveParticipant==="+ uuid);
        invokeListener("onRemoveParticipant", uuid);
    }

    @Override
    public void onUpdateParticipant(Participant participant) {
        Log.i(TAG,"onUpdateParticipant==="+ participant);
        invokeListener("onUpdateParticipant", participant);
    }

    @Override
    public void onStageVoice(List<Stage> stages) {
        Log.i(TAG,"onStageVoice==="+ stages);
        invokeListener("onStageVoice", stages);
    }

    @Override
    public void onSubtitle(String text) {
        Log.i(TAG,"onSubtitle==="+ text);
        invokeListener("onSubtitle", text);
    }

    @Override
    public void onChatMessage(String message) {
        Log.i(TAG,"onChatMessage==="+ message);
        invokeListener("onChatMessage", message);
    }

    @Override
    public void onDisconnect(String reason) {
        Log.i(TAG,"onDisconnect==="+ reason);
        invokeListener("onDisconnect", reason);
    }

    @Override
    public void onCallDisconnect(String reason) {
        Log.i(TAG,"onCallDisconnect==="+ reason);
        invokeListener("onCallDisconnect",reason);
    }

    @Override
    public void onMediaDisconnect() {
        Log.i(TAG,"onMediaDisconnect===");
        invokeListener("onMediaDisconnect", null);
    }

    @Override
    public void onPresentation(boolean isActive, String uuid) {
        Log.i(TAG,"onPresentation===");
        invokeListener("onPresentation", new String[]{String.valueOf(isActive),uuid});
    }

    @Override
    public void onPresentationReload(String url) {
        Log.i(TAG,"onPresentationReload==="+url);
        invokeListener("onPresentationReload", url);
    }

    @Override
    public void onPresentaionShareState(boolean isSuccess, String reason) {
        Log.i(TAG,"onPresentaionShareState==="+isSuccess+"=="+reason);
        invokeListener("onPresentaionShareState", new String[]{String.valueOf(isSuccess),reason});
    }

    @Override
    public void onScreenShareState(boolean isActive) {
        Log.i(TAG,"onScreenShareState==="+isActive);
        invokeListener("onScreenShareState", isActive);
    }

    @Override
    public void onLayoutUpdate(String layout, String hostLayout, String guestLayout) {
        Log.i(TAG,"onLayoutUpdate==="+layout+"=="+hostLayout+"=="+guestLayout);
        invokeListener("onLayoutUpdate", new String[]{layout,hostLayout,guestLayout});
    }

    @Override
    public void onLayoutUpdateParticipants(List<String> participants) {
        Log.i(TAG,"onScreenShareState==="+participants);
        invokeListener("onLayoutUpdateParticipants", participants);
    }

    @Override
    public void onEnableOverlay(boolean b) {
        Log.i(TAG,"onEnableOverlay==="+b);
        invokeListener("onEnableOverlay", b);
    }

    @Override
    public void onRecordState(boolean isActive) {
        Log.i(TAG,"onRecordState==="+isActive);
        invokeListener("onRecordState", isActive);
    }

    @Override
    public void onLiveState(boolean isActive) {
        Log.i(TAG,"onLiveState==="+isActive);
        invokeListener("onLiveState", isActive);
    }

    @Override
    public void onRoleUpdate(String role) {
        Log.i(TAG,"onRoleUpdate==="+role);
        invokeListener("onRoleUpdate", role);
    }

    @Override
    public void onConferenceUpdate(ConferenceStatus conferenceStatus) {
        Log.i(TAG,"onConferenceUpdate==="+conferenceStatus);
        invokeListener("onConferenceUpdate", conferenceStatus);
    }

    @Override
    public void onWhiteboardStart(String s, boolean b) {
        Log.i(TAG,"onWhiteboardStart==="+s+"=="+b);
        invokeListener("onConferenceUpdate", new String[]{String.valueOf(b),s});
    }

    @Override
    public void onWhiteboardStop() {
        Log.i(TAG,"onRoleUpdate===");
        invokeListener("onWhiteboardStop", null);
    }

    @Override
    public void onWhiteboardImageUpdate(Bitmap bitmap) {
        Log.i(TAG,"onWhiteboardImageUpdate==="+bitmap);
        invokeListener("onWhiteboardImageUpdate", bitmap);
    }

    @Override
    public void onWhiteboardAddPayload(int i, WhiteboardPayload whiteboardPayload) {
        Log.i(TAG,"onWhiteboardImageUpdate==="+i+"=="+whiteboardPayload);
        invokeListener("onWhiteboardImageUpdate", i);
    }

    @Override
    public void onWhiteboardDeletePayload(int i) {
        Log.i(TAG,"onWhiteboardDeletePayload==="+i);
        invokeListener("onWhiteboardDeletePayload", i);
    }

    @Override
    public void onWhiteboardClearPayload() {
        Log.i(TAG,"onWhiteboardClearPayload===");
        invokeListener("onWhiteboardDeletePayload", null);
    }

    @Override
    public void onDecline(String s) {
        Log.i(TAG,"onDecline==="+s);
        invokeListener("onDecline", s);
    }

    @Override
    public void onUnmutePls(String s) {
        Log.i(TAG,"onUnmutePls==="+s);
        invokeListener("onUnmutePls", s);
    }

    @Override
        public void onError(ErrorCode errorCode, String s) {
        Log.i(TAG,"onError==="+errorCode.toString()+"=="+s);
        invokeListener("onError", new String[]{errorCode.toString(),s});
    }
}
