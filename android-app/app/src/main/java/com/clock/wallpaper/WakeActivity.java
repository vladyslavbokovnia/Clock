package com.clock.wallpaper;

import android.app.*;import android.app.admin.DevicePolicyManager;import android.content.*;import android.graphics.Color;import android.os.*;import android.view.*;import android.widget.*;

public class WakeActivity extends Activity {
  DevicePolicyManager dpm;ComponentName admin;Handler handler=new Handler(Looper.getMainLooper());Runnable lockTask;AnalogOverlay overlay;BroadcastReceiver batteryReceiver;
  @Override public void onCreate(Bundle b){super.onCreate(b);dpm=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);admin=new ComponentName(this,ClockAdminReceiver.class);getWindow().setStatusBarColor(Color.BLACK);getWindow().setNavigationBarColor(Color.BLACK);getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);boolean upside=getSharedPreferences("clock",0).getBoolean("wallpaperUpsideDown",false);FrameLayout frame=new FrameLayout(this);ImageView image=new ImageView(this);image.setImageResource(R.drawable.clock_wallpaper);image.setScaleType(ImageView.ScaleType.FIT_XY);if(upside)image.setRotation(180);frame.addView(image);overlay=new AnalogOverlay(this,upside);if(upside)overlay.setRotation(180);frame.addView(overlay);image.setOnClickListener(v->{cancelLock();startActivity(new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));finish();});setContentView(frame);batteryReceiver=new BroadcastReceiver(){public void onReceive(Context c,Intent i){int level=i.getIntExtra("level",100);overlay.setBattery(level);}};registerReceiver(batteryReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));scheduleLock();}
  @Override protected void onNewIntent(Intent intent){super.onNewIntent(intent);setIntent(intent);scheduleLock();}
  void scheduleLock(){if(lockTask!=null)handler.removeCallbacks(lockTask);lockTask=()->{sendBroadcast(new Intent("com.clock.wallpaper.WAKE_DONE"));if(dpm.isAdminActive(admin))dpm.lockNow();else finishAndRemoveTask();};handler.postDelayed(lockTask,1000);}
  void cancelLock(){if(lockTask!=null)handler.removeCallbacks(lockTask);sendBroadcast(new Intent("com.clock.wallpaper.WAKE_DONE"));}
  @Override public void onBackPressed(){cancelLock();finish();}
  @Override protected void onDestroy(){if(lockTask!=null)handler.removeCallbacks(lockTask);if(batteryReceiver!=null)unregisterReceiver(batteryReceiver);sendBroadcast(new Intent("com.clock.wallpaper.WAKE_DONE"));super.onDestroy();}
}
