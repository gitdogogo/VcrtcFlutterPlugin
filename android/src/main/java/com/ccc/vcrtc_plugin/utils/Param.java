package com.ccc.vcrtc_plugin.utils;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class Param {
    public static <T> T get(MethodCall call, MethodChannel.Result result, String param) {
        T par = call.argument(param);
        if (par == null) {
            result.error("Missing parameter", "Cannot find parameter `" + param + "` or `" + param + "` is null!", 5);
            throw new RuntimeException("Cannot find parameter `" + param + "` or `" + param + "` is null!");
        }
        return par;
    }
}
