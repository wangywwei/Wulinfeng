package com.hxwl.common.xsocket.tcp.client.helper.stickpackage;

import com.hxwl.common.xsocket.utils.ExceptionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 最简单的做法，不处理粘包，直接读取返回，最大长度256
 */
public class HeiXiongHotChatStickPackageHelper implements AbsStickPackageHelper {
    private byte[] tail;
    private List<Byte> bytes;
    private int tailLen;

    public HeiXiongHotChatStickPackageHelper(byte[] tail) {
        this.tail = tail;
        if (tail == null) {
            ExceptionUtils.throwException("tail ==null");
        }
        if (tail.length == 0) {
            ExceptionUtils.throwException("tail length==0");
        }
        tailLen = tail.length;
        bytes = new ArrayList<>();
    }

    private boolean endWith(Byte[] src, byte[] target) {
        try {

            String strTarget = new String(target, "UTF-8");
            String strSrc = ByteArrayToString(src);
            if (strSrc.length() < strTarget.length()) {
                return false;
            }
            //取最后一组字符串与目标字符串进行比较
            String str = strSrc.substring(strSrc.length()-strTarget.length());
            if (str.equals(strTarget)) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private byte[] getRangeBytes(List<Byte> list, int start, int end) {
        Byte[] temps = Arrays.copyOfRange(list.toArray(new Byte[0]), start, end);
        byte[] result = new byte[temps.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = temps[i];
        }
        return result;
    }

    private String  ByteArrayToString(Byte[] src){
        try{

            byte[] result = new byte[src.length];
            for(int i=0;i<result.length;i++){
                result[i] = src[i];
            }
            return new String(result,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

//    int k =0;

    @Override
    public byte[] execute(InputStream is) {
        bytes.clear();
//        k++;
//        LogUtils.e(MainActivity.class,k + " execute");
        int len = -1;
        byte temp;
        int startIndex = 0;
        byte[] result = null;
        boolean isFindEnd = false;
        try {
            while ((len = is.read()) != -1) {
                temp = (byte) len;
                bytes.add(temp);
                Byte[] byteArray = bytes.toArray(new Byte[]{});
                if (!isFindEnd) {
                    if (endWith(byteArray, tail)) {
                        if (startIndex <= bytes.size() - tailLen) {
                            isFindEnd = true;
                            result = getRangeBytes(bytes, startIndex, bytes.size());
                            break;
                        }
                    }
                }
            }
            if (len == -1) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
