package com.clock.wallpaper;

import android.app.*;import android.app.admin.DevicePolicyManager;import android.content.*;import android.graphics.Color;import android.os.*;import android.view.*;import android.widget.*;

public class WakeActivity extends Activity {
  DevicePolicyManager dpm; ComponentName admin; Handler handler=new Handler(Looper.getMainLooper()); Runnable lockTask;
  @Override public void onCreate(Bundle b){super.onCreate(b); dpm=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);admin=new ComponentName(this,ClockAdminReceiver.class);getWindow().setStatusBarColor(Color.BLACK);getWindow().setNavigationBarColor(Color.BLACK);getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON); ImageView image=new ImageView(this);image.setImageResource(com.clock.wallpaper.R.drawable.clock_wallpaper);image.setScaleType(ImageView.ScaleType.FIT_XY);if(getSharedPreferences("clock",0).getBoolean("wallpaperUpsideDown",false))image.setRotation(180);image.setOnClickListener(v->{if(lockTask!=null)handler.removeCallbacks(lockTask);startActivity(new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));finish();});setContentView(image);lockTask=()->{if(dpm.isAdminActive(admin))dpm.lockNow();else finishAndRemoveTask();};handler.postDelayed(lockTask,1000);}
  @Override public void onBackPressed(){if(lockTask!=null)handler.removeCallbacks(lockTask);finish();}
  @Override protected void onDestroy(){if(lockTask!=null)handler.removeCallbacks(lockTask);super.onDestroy();}
}
