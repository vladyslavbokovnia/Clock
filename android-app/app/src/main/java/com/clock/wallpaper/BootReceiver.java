package com.clock.wallpaper;
import android.content.*;
public class BootReceiver extends BroadcastReceiver { @Override public void onReceive(Context c,Intent i){ if(c.getSharedPreferences("clock",0).getBoolean("bootHome",true)){try{Intent home=new Intent(Intent.ACTION_MAIN);home.addCategory(Intent.CATEGORY_HOME);home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);c.startActivity(home);}catch(Exception ignored){}} if(c.getSharedPreferences("clock",0).getBoolean("boot",true)) ClockService.start(c); } }
