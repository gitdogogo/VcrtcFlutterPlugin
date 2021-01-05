package com.ccc.vcrtc_plugin;

import com.vcrtc.VCRTCView;
import com.vcrtc.entities.ConferenceStatus;
import com.vcrtc.entities.ErrorCode;
import com.vcrtc.entities.Participant;
import com.vcrtc.entities.Stage;
import com.vcrtc.listeners.VCRTCListener;

import java.util.List;

public class Listener implements VCRTCListener {
    @Override
    public void onAuthorizationStatus(boolean b) {

    }

    @Override
    public void onCameraStream(String s) {

    }

    @Override
    public void onConnected(String s) {

    }

    @Override
    public void onCallConnected() {

    }

    @Override
    public void onCallReconnected() {

    }

    @Override
    public void onLocalVideo(String s, VCRTCView vcrtcView) {

    }

    @Override
    public void onRemoteVideo(String s, VCRTCView vcrtcView) {

    }

    @Override
    public void onAddView(String s, VCRTCView vcrtcView, String s1) {

    }

    @Override
    public void onRemoveView(String s, VCRTCView vcrtcView) {

    }

    @Override
    public void onLocalStream(String s, String s1, String s2) {

    }

    @Override
    public void onRemoteStream(String s, String s1, String s2) {

    }

    @Override
    public void onAddParticipant(Participant participant) {

    }

    @Override
    public void onRemoveParticipant(String s) {

    }

    @Override
    public void onUpdateParticipant(Participant participant) {

    }

    @Override
    public void onStageVoice(List<Stage> list) {

    }

    @Override
    public void onSubtitle(String s) {

    }

    @Override
    public void onChatMessage(String s, String s1) {

    }

    @Override
    public void onDisconnect(String s) {

    }

    @Override
    public void onCallDisconnect(String s) {

    }

    @Override
    public void onMediaDisconnect() {

    }

    @Override
    public void onPresentation(boolean b, String s) {

    }

    @Override
    public void onPresentationReload(String s) {

    }

    @Override
    public void onPresentaionShareState(boolean b, String s) {

    }

    @Override
    public void onScreenShareState(boolean b) {

    }

    @Override
    public void onLayoutUpdate(String s, String s1, String s2) {

    }

    @Override
    public void onLayoutUpdateParticipants(List<String> list) {

    }

    @Override
    public void onRecordState(boolean b) {

    }

    @Override
    public void onLiveState(boolean b) {

    }

    @Override
    public void onRoleUpdate(String s) {

    }

    @Override
    public void onConferenceUpdate(ConferenceStatus conferenceStatus) {

    }

    @Override
    public void onDecline(String s) {

    }

    @Override
    public void onError(ErrorCode errorCode, String s) {

    }
}
