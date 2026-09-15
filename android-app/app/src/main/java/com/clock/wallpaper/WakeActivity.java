package com.clock.wallpaper;

import android.app.*;import android.app.admin.DevicePolicyManager;import android.content.*;import android.graphics.Color;import android.os.*;import android.view.*;import android.widget.*;

public class WakeActivity extends Activity {
  DevicePolicyManager dpm;ComponentName admin;Handler handler=new Handler(Looper.getMainLooper());Runnable lockTask;ScreenHandsOverlay hands;
  @Override public void onCreate(Bundle b){super.onCreate(b);dpm=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);admin=new ComponentName(this,ClockAdminReceiver.class);if(Build.VERSION.SDK_INT>=27)setTurnScreenOn(true);if(Build.VERSION.SDK_INT>=26)setShowWhenLocked(true);getWindow().setStatusBarColor(Color.BLACK);getWindow().setNavigationBarColor(Color.BLACK);getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);boolean upside=getSharedPreferences("clock",0).getBoolean("wallpaperUpsideDown",false);FrameLayout frame=new FrameLayout(this);ImageView image=new ImageView(this);image.setImageResource(R.drawable.clock_wallpaper);image.setScaleType(ImageView.ScaleType.FIT_XY);if(upside)image.setRotation(180);frame.addView(image);hands=new ScreenHandsOverlay(this,upside);frame.addView(hands);image.setOnClickListener(v->{cancelLock();startActivity(new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));finish();});setContentView(frame);scheduleLock();}
  @Override protected void onNewIntent(Intent intent){super.onNewIntent(intent);setIntent(intent);if(hands!=null)hands.invalidate();scheduleLock();}
  void scheduleLock(){if(lockTask!=null)handler.removeCallbacks(lockTask);lockTask=()->{sendBroadcast(new Intent("com.clock.wallpaper.WAKE_DONE"));if(dpm.isAdminActive(admin))dpm.lockNow();else finishAndRemoveTask();};handler.postDelayed(lockTask,1000);}
  void cancelLock(){if(lockTask!=null)handler.removeCallbacks(lockTask);sendBroadcast(new Intent("com.clock.wallpaper.WAKE_DONE"));}
  @Override public void onBackPressed(){cancelLock();finish();}
  @Override protected void onDestroy(){if(lockTask!=null)handler.removeCallbacks(lockTask);sendBroadcast(new Intent("com.clock.wallpaper.WAKE_DONE"));super.onDestroy();}
}
