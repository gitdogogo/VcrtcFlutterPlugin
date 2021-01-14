import 'package:flutter/material.dart';
import 'package:vcrtc_plugin/vcrtc_plugin.dart';

class Meeting extends StatefulWidget {
  Meeting({Key key}) : super(key: key);

  @override
  _MeetingState createState() => _MeetingState();
}

class _MeetingState extends State<Meeting> {
  VcrtcPlugin vcrtcPlugin = VcrtcPlugin();

  Map<String, Widget> screens = {};
  Widget largeWidget;
  int largeViewId;
  dynamic defaultLargeParams;
  @override
  void initState() {
    super.initState();
    screens["0"] = RaisedButton(
      child: Text("退出"),
      onPressed: () => goBack(),
    );
    vcrtcPlugin.addListener((type, params) {
      if (type == VctrcListenerEnum.onLocalVideo) {
        Widget wgt = InkWell(
          onDoubleTap: () => changeLarge(params),
          child: VcrtcPlugin.startMeeting(
            key: ValueKey(params['uuid']),
            creationParams: params,
            created: (int viewId) {},
          ),
        );
        screens[params['uuid']] = wgt;
        defaultLargeParams = params;
        largeWidget = VcrtcPlugin.startMeeting(
            creationParams: {...params, "action": "new"},
            created: (int viewId) {
              largeViewId = viewId;
            });
        setState(() {});
      } else if (type == VctrcListenerEnum.onRemoteStream) {
        screens[params['uuid']] = InkWell(
          onDoubleTap: () => changeLarge(params),
          child: VcrtcPlugin.startMeeting(
            key: ValueKey(params['uuid']),
            creationParams: params,
            created: (int viewId) {},
          ),
        );
        setState(() {});
      } else if (type == VctrcListenerEnum.onRemoveView) {
        changeLarge(defaultLargeParams);
        screens.remove(params['uuid']);
        setState(() {});
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () {
        goBack();
      },
      child: SafeArea(
        child: Scaffold(
          body: Stack(
            children: [
              buildLarge(),
              GridView.builder(
                padding: EdgeInsets.all(10),
                gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 3,
                  mainAxisSpacing: 10.0,
                  crossAxisSpacing: 10.0,
                  childAspectRatio: 1 / 1,
                ),
                itemCount: screens.length,
                itemBuilder: (BuildContext context, int index) {
                  return buildItem(screens.values.toList()[index]);
                },
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget buildItem(Widget widget) {
    var child = widget == null ? Text('') : widget;
    return Container(
      decoration: BoxDecoration(color: Color.fromARGB(80, 255, 255, 255)),
      child: child,
    );
  }

  Widget buildLarge() {
    if (largeWidget == null) {
      return Text("");
    } else {
      return Container(
        child: largeWidget,
      );
    }
  }

  void changeLarge(dynamic params) {
    VcrtcPlugin.setStream(largeViewId, params);
  }

  void goBack() {
    VcrtcPlugin.disconnect();
    Navigator.pop(context);
  }
}
