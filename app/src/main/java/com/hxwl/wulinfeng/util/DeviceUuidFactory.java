package com.hxwl.wulinfeng.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

public class DeviceUuidFactory
{
  protected static final String PREFS_FILE = "device_id.xml";
  protected static final String PREFS_DEVICE_ID = "device_id";
  protected static UUID uuid;
  
  public DeviceUuidFactory(Context paramContext)
  {
    if (uuid == null) {
      synchronized (DeviceUuidFactory.class)
      {
        if (uuid == null)
        {
          SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("device_id.xml", 0);
          String str1 = localSharedPreferences.getString("device_id", null);
          if (str1 != null)
          {
            uuid = UUID.fromString(str1);
          }
          else
          {
            String str2 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
            try
            {
              if (!"9774d56d682e549c".equals(str2))
              {
                uuid = UUID.nameUUIDFromBytes(str2.getBytes("utf8"));
              }
              else
              {
                String str3 = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
                uuid = str3 != null ? UUID.nameUUIDFromBytes(str3.getBytes("utf8")) : generateDeviceUuid(paramContext);
              }
            }
            catch (UnsupportedEncodingException localUnsupportedEncodingException)
            {
              throw new RuntimeException(localUnsupportedEncodingException);
            }
            localSharedPreferences.edit().putString("device_id", uuid.toString()).commit();
          }
        }
      }
    }
  }
  
  private UUID generateDeviceUuid(Context paramContext)
  {
    String str1 = Build.BOARD + Build.BRAND + Build.CPU_ABI + Build.DEVICE + Build.DISPLAY + Build.FINGERPRINT + Build.HOST + Build.ID + Build.MANUFACTURER + Build.MODEL + Build.PRODUCT + Build.TAGS + Build.TYPE + Build.USER;
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    String str2 = localTelephonyManager.getDeviceId();
    String str3 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
    String str4 = localWifiManager.getConnectionInfo().getMacAddress();
    if ((isEmpty(str2)) && (isEmpty(str3)) && (isEmpty(str4))) {
      return UUID.randomUUID();
    }
    String str5 = str1.toString() + str2 + str3 + str4;
    return UUID.nameUUIDFromBytes(str5.getBytes());
  }
  
  public UUID getDeviceUuid()
  {
    return uuid;
  }
  
  private static boolean isEmpty(Object paramObject)
  {
    if (paramObject == null) {
      return true;
    }
    if (((paramObject instanceof String)) && (((String)paramObject).trim().length() == 0)) {
      return true;
    }
    if ((paramObject instanceof Map)) {
      return ((Map)paramObject).isEmpty();
    }
    return false;
  }
}
