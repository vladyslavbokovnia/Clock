package com.clock.wallpaper;
import android.content.*;
public class AlarmReceiver extends BroadcastReceiver { @Override public void onReceive(Context c,Intent i){ Intent s=new Intent(c,ClockService.class);s.setAction("com.clock.wallpaper.TIMER");ClockService.start(c,s); } }
