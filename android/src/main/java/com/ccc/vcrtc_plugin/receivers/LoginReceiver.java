package com.ccc.vcrtc_plugin.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.ccc.vcrtc_plugin.VcrtcPlugin;
import com.vcrtc.registration.VCService;


import io.flutter.plugin.common.MethodChannel;

public class LoginReceiver extends BroadcastReceiver {
    private MethodChannel.Result result;
    private String TAG = "LoginReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(VCService.MSG);
        Log.i(TAG,message);
        switch (message) {
            case VCService.MSG_LOGIN_SUCCESS:
                VcrtcPlugin.mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        result.success("登录成功");
                    }
                });
                break;
            case VCService.MSG_LOGIN_FAILED:
                VcrtcPlugin.mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        result.success("登录失败");
                    }
                });
                break;
            case VCService.MSG_USER_INFO:
                //用户信息
                final String userJson = intent.getStringExtra(VCService.DATA_BROADCAST);
                VcrtcPlugin.mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        result.success(userJson);
                    }
                });
                break;
            default:
        }
    }

    public void setResult(MethodChannel.Result result) {
        this.result = result;
    }
}
