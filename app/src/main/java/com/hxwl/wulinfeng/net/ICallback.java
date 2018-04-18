package com.hxwl.wulinfeng.net;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public interface ICallback {
         public  void error(IOException e);
         public  void success(ResultPacket result);

}
