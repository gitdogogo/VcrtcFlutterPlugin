//
//  Param.swift
//  vcrtc_plugin
//
//  Created by apple on 2021/1/15.
//

import Foundation
public class Param{
    public static func get(call:FlutterMethodCall,result:@escaping FlutterResult,param:String) -> Any?{
        let value = (call.arguments as! [String:Any])[param]
        if(value == nil){
            result(FlutterError(code: "5", message: "Missing Parameter", details: "Cannot found parameter \(param) or \(param) is null"))
        }
        return value;
    }
}
