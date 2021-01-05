package com.ccc.vcrtc_plugin.platformview;

import android.content.Context;
import android.widget.TextView;

import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class Meeting extends PlatformViewFactory {
    /**
     * @param createArgsCodec the codec used to decode the args parameter of {@link #create}.
     */
    public Meeting(MessageCodec<Object> createArgsCodec) {
        super(createArgsCodec);
    }

    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        return null;
    }
}
