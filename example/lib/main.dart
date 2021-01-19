import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:vcrtc_plugin/vcrtc_plugin.dart';

import 'meeting.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      routes: {
        '/view': (context) => Meeting(),
      },
      home: Home(),
    );
  }
}

class Home extends StatefulWidget {
  Home({Key key}) : super(key: key);

  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    SystemUiOverlayStyle systemUiOverlayStyle =
        SystemUiOverlayStyle(statusBarColor: Colors.transparent);
    SystemChrome.setSystemUIOverlayStyle(systemUiOverlayStyle);
    initPlatformState();
    login();
    setServerAddress();
  }

  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await VcrtcPlugin.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  login() async {
    var login = await VcrtcPlugin.login(account: "123", password: "123");
    print(login);
  }

  setServerAddress() async {
    var res = await VcrtcPlugin.setServerAddress(
        serverAddress: "cloud.51vmr.cn", httpsPort: "443");
    print(res);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Plugin example app'),
      ),
      body: Column(
        children: [
          Text('Running on: $_platformVersion\n'),
          RaisedButton(
            child: Text('go meeting view'),
            onPressed: enterMeeting,
          ),
        ],
      ),
    );
  }

  void enterMeeting() async {
    if (await Permission.camera.request().isGranted &&
        await Permission.microphone.request().isGranted) {
      VcrtcPlugin.connect();
      Navigator.pushNamed(context, "/view");
    } else {
      print("需要获取音视频权限才能进入");
    }
  }
}
