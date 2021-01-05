package com.ccc.vcrtc_plugin;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ccc.vcrtc_plugin.receivers.LoginReceiver;
import com.ccc.vcrtc_plugin.utils.Param;
import com.vcrtc.VCRTC;
import com.vcrtc.VCRTCPreferences;
import com.vcrtc.callbacks.CallBack;
import com.vcrtc.entities.Call;
import com.vcrtc.listeners.VCRTCListener;
import com.vcrtc.registration.VCRegistrationUtil;
import com.vcrtc.webrtc.RTCManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import static com.vcrtc.registration.VCService.VC_ACTION;

/** VcrtcPlugin */
public class VcrtcPlugin implements FlutterPlugin, MethodCallHandler {
  private static final String TAG = "VcrtcPlugin";
  private MethodChannel channel;
  private Context context;
  private final LoginReceiver loginReceiver = new LoginReceiver();
  private VCRTCPreferences vcrtcPreferences;
  private VCRTC vcrtc;
  VCRTCListener listener;

  public VcrtcPlugin(){}

  private VcrtcPlugin(final Context context, MethodChannel channel){
    this.channel = channel;
    this.context = context;
    vcrtcPreferences = new VCRTCPreferences(context);
    listener = new Listener();
    RTCManager.init(context);
    RTCManager.DEVICE_TYPE = "Android";
    RTCManager.OEM = "";
    IntentFilter filter = new IntentFilter(VC_ACTION);
    context.registerReceiver(this.loginReceiver,filter);
  }


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "vcrtc_plugin");
    channel.setMethodCallHandler(new VcrtcPlugin(flutterPluginBinding.getApplicationContext(),channel));
  }


  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    Log.i(TAG, "|method=" + call.method + "|arguments=" + call.arguments);
    try {
      Method method = this.getClass().getDeclaredMethod(call.method, MethodCall.class, Result.class);
      method.setAccessible(true);
      method.invoke(this, call, result);
    } catch (NoSuchMethodException e) {
      result.notImplemented();
    } catch (IllegalAccessException e) {
      result.notImplemented();
    } catch (InvocationTargetException ignored) {
    }
  }

  private void makeCall(){
    vcrtc = new VCRTC(context);
    vcrtc.setVCRTCListener(listener);
    vcrtc.connect("8080199","","test", new CallBack() {
      @Override
      public void success(String s) {

      }

      @Override
      public void failure(String s) {

      }
    });
  }

  private void getPlatformVersion(MethodCall call, Result result){
    result.success(android.os.Build.VERSION.RELEASE);
  }

  private void setServerAddress(MethodCall call, final Result result) {
    String serverAddress = Param.get(call,result,"serverAddress");
    String httpsPort = Param.get(call,result,"httpsPort");
    vcrtcPreferences.setServerAddress(serverAddress, httpsPort, new CallBack() {
      final Handler handler = new Handler(Looper.getMainLooper());
      @Override
      public void success(final String s) {
        handler.post(new Runnable() {
          @Override
          public void run() {
            result.success("设setServerAddress置成功"+s);
          }
        });
      }

      @Override
      public void failure(final String s) {
        handler.post(new Runnable() {
          @Override
          public void run() {
            result.error("10001","设置失败",s);
          }
        });
      }
    });
  }

  private void login(MethodCall call, Result result) {
    String account = Param.get(call,result,"account");
    String password = Param.get(call,result,"password");
    loginReceiver.setResult(result);
    VCRegistrationUtil.login(this.context,account,password);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    this.context.unregisterReceiver(loginReceiver);
  }
}
