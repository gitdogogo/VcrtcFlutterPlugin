import Flutter
import UIKit
import VCRTC

public class SwiftVcrtcPlugin: NSObject, FlutterPlugin{
    private let vcrtc = VCRtcModule.sharedInstance()
    let vcrtcListener:MeetingDelegate
    let channel:FlutterMethodChannel
    init(with registrar:FlutterPluginRegistrar,channel:FlutterMethodChannel) {
        let meetingViewFacory = MeetingViewFacory(messenger: registrar.messenger());
        self.channel = channel
        vcrtcListener = MeetingDelegate(meetingViewFacory,channel)
        registrar.register(meetingViewFacory, withId: MeetingViewFacory.SIGN)
    }
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "vcrtc_plugin", binaryMessenger: registrar.messenger())
    let instance = SwiftVcrtcPlugin(with: registrar,channel: channel)
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    switch call.method {
    case "connect":
        self.connect(call, result: result);
        break;
    case "disconnect":
        self.disconnect(call, result: result);
        break;
    case "getPlatformVersion":
        self.getPlatformVersion(call,result:result);
        break;
    case "setServerAddress":
        self.setServerAddress(call,result:result);
        break;
    case "login":
        self.login(call, result: result)
    default:
        result(FlutterMethodNotImplemented);
        break
        
    }
  }
    public func connect(_ call: FlutterMethodCall, result: @escaping FlutterResult){
        vcrtc.connectChannel("8089199", password: "", name: "ios test") { (Any) in
            result(nil);
        } failure: { (Error) in
            result(FlutterError(code: "1", message: "连接失败", details: "链接8089199会议失败"));
        }
        vcrtc.delegate = vcrtcListener
    }
    
    public func disconnect(_ call: FlutterMethodCall, result: @escaping FlutterResult){
        vcrtc.exitChannelSuccess { (Any) in
            result(nil)
        } failure: { (Error) in
            result(nil)
        }

    }
    public func getPlatformVersion(_ call: FlutterMethodCall, result: @escaping FlutterResult){
        result("iOS " + UIDevice.current.systemVersion)
    }
    public func setServerAddress(_ call: FlutterMethodCall, result: @escaping FlutterResult){
        let server = Param.get(call: call,result: result,param: "serverAddress") as! String
        let port = Param.get(call: call,result: result,param: "httpsPort") as! String
        vcrtc.apiServer = server
        vcrtc.https_port = portå
    }
    public func login(_ call: FlutterMethodCall, result: @escaping FlutterResult){
        
    }
}
