package com.ccc.vcrtc_plugin;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ccc.vcrtc_plugin.bean.Stream;
import com.ccc.vcrtc_plugin.listener.MeetingViewListener;
import com.ccc.vcrtc_plugin.platform.MeetingViewListenerCallBack;
import com.ccc.vcrtc_plugin.platform.MeetingFactory;
import com.ccc.vcrtc_plugin.platform.MeetingView;
import com.ccc.vcrtc_plugin.platform.MeetingViewCallBack;
import com.ccc.vcrtc_plugin.platform.ViewCollect;
import com.ccc.vcrtc_plugin.receivers.LoginReceiver;
import com.ccc.vcrtc_plugin.utils.Param;
import com.vcrtc.VCRTC;
import com.vcrtc.VCRTCPreferences;
import com.vcrtc.VCRTCView;
import com.vcrtc.callbacks.CallBack;
import com.vcrtc.listeners.VCRTCListener;
import com.vcrtc.registration.VCRegistrationUtil;
import com.vcrtc.webrtc.RTCManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.platform.PlatformViewRegistry;

import static com.vcrtc.registration.VCService.VC_ACTION;

/** VcrtcPlugin */
public class VcrtcPlugin implements FlutterPlugin, MethodCallHandler {

  final static public Handler mainHandler = new Handler(Looper.getMainLooper());
  private static final String TAG = "VcrtcPlugin";
  private MethodChannel channel;
  private Context context;
  private final LoginReceiver loginReceiver = new LoginReceiver();
  private VCRTCPreferences vcrtcPreferences;
  private VCRTC vcrtc;
  VCRTCListener listener;
  PlatformViewRegistry registry;
  BinaryMessenger messenger;
  MeetingFactory meetingFactory;
  ViewCollect viewCollect;

  public VcrtcPlugin(){}

  private VcrtcPlugin(BinaryMessenger messenger, Context context, MethodChannel channel, PlatformViewRegistry registry){
    this.channel = channel;
    this.context = context;
    this.registry = registry;
    this.messenger = messenger;
    vcrtcPreferences = new VCRTCPreferences(context);
    RTCManager.init(context);
    RTCManager.DEVICE_TYPE = "Android";
    RTCManager.OEM = "";
    IntentFilter filter = new IntentFilter(VC_ACTION);
    context.registerReceiver(this.loginReceiver,filter);
  }


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "vcrtc_plugin");
    channel.setMethodCallHandler(new VcrtcPlugin(flutterPluginBinding.getBinaryMessenger(),flutterPluginBinding.getApplicationContext(),channel,flutterPluginBinding.getPlatformViewRegistry()));
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

  private void connect(MethodCall call, final Result result){
    vcrtc = new VCRTC(context);
    viewCollect = ViewCollect.getInstance();
    meetingFactory = new MeetingFactory(context, messenger, new MeetingViewCallBack() {
      @Override
      public void onLocalViewCreated(int viewId,MeetingView view) {
      }
      @Override
      public void onRemoteViewCreated(int viewId,MeetingView view) {
      }
    }, new MeetingViewListenerCallBack() {
      @Override
      public void onLocalVideo(String uuid, VCRTCView view) {
        viewCollect.setView(uuid,view);
      }

      @Override
      public void onRemoteVideo(String uuid, VCRTCView view) {
        //viewCollect.setView(uuid,view);
      }

      @Override
      public void onAddView(String uuid, VCRTCView view, String viewType) {
        viewCollect.setView(uuid,view);
      }

      @Override
      public void onRemoteStream(String uuid, String streamUrl, String streamType) {
        Stream stream = viewCollect.getStream(uuid);
        if(stream == null){
          stream = new Stream();
        }
        stream.setStream(streamType,streamUrl);
        viewCollect.setStream(uuid,stream);
      }

      @Override
      public void onLocalStream(String uuid, String streamUrl, String streamType) {
        Stream stream = viewCollect.getStream(uuid);
        if(stream == null){
          stream = new Stream();
        }
        stream.setStream(streamType,streamUrl);
        viewCollect.setStream(uuid,stream);
      }

      @Override
      public void onRemoveView(String uuid, VCRTCView vcrtcView) {
        viewCollect.removeView(uuid);
      }
    });
    listener = new MeetingViewListener(meetingFactory,channel);
    vcrtc.setVCRTCListener(listener);
    vcrtc.connect("8089199","","test", new CallBack() {
      @Override
      public void success(String s) {
        mainHandler.post(new Runnable() {
          @Override
          public void run() {
            result.success(null);
          }
        });
      }

      @Override
      public void failure(final String s) {
        mainHandler.post(new Runnable() {
          @Override
          public void run() {
            result.error("1001","失败",s);
          }
        });
      }
    });
    registry.registerViewFactory(MeetingFactory.SIGN,meetingFactory);
  }

  private void disconnect(MethodCall call, Result result){
    vcrtc.disconnect();
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
