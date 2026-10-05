package com.clock.wallpaper;
import android.accessibilityservice.AccessibilityService;import android.content.*;import android.graphics.PixelFormat;import android.view.*;
public class ClockAccessibilityService extends AccessibilityService {
 WindowManager wm; View overlay; BroadcastReceiver receiver;
 @Override public void onServiceConnected(){super.onServiceConnected();wm=(WindowManager)getSystemService(WINDOW_SERVICE);receiver=new BroadcastReceiver(){public void onReceive(Context c,Intent i){String cmd=i.getStringExtra("cmd");if("SHOW".equals(cmd))show();else if("HIDE".equals(cmd))hide();}};registerReceiver(receiver,new IntentFilter("com.clock.wallpaper.ACCESSIBILITY"));}
 // Каждый SHOW пересоздаёт окно: новое окно TYPE_ACCESSIBILITY_OVERLAY оказывается поверх уже существующих наложений других служб
 void show(){hide();boolean upside=getSharedPreferences("clock",0).getBoolean("wallpaperUpsideDown",false)&&!getSharedPreferences("clock",0).getBoolean("normalOrientation",false);ScreenHandsOverlay v=new ScreenHandsOverlay(this,upside);if(upside)v.setRotation(180);v.setClickable(true);v.setOnTouchListener((view,event)->true);overlay=v;WindowManager.LayoutParams lp=new WindowManager.LayoutParams(-1,-1,WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN|WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,PixelFormat.TRANSLUCENT);try{wm.addView(overlay,lp);}catch(Exception e){overlay=null;}}
 void hide(){if(overlay!=null){try{wm.removeView(overlay);}catch(Exception ignored){}overlay=null;}}
 @Override public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent e){}@Override public void onInterrupt(){}
 @Override public void onDestroy(){hide();if(receiver!=null)unregisterReceiver(receiver);super.onDestroy();}
}
