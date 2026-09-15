package com.clock.wallpaper;

import android.content.*;
public class BootReceiver extends BroadcastReceiver { @Override public void onReceive(Context c,Intent i){ if(c.getSharedPreferences("clock",0).getBoolean("boot",true)) ClockService.start(c); } }
