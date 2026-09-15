package com.clock.wallpaper;

import android.app.*;import android.content.*;import android.hardware.*;import android.os.*;import android.provider.Settings;import java.util.*;

public class ClockService extends Service implements SensorEventListener {
  static final int INTERVAL=5*60*1000, WAKE=1*1000; SensorManager sm; Sensor accel, prox; boolean near=false, upside=false, faceDown=false; Handler h=new Handler(Looper.getMainLooper());
  public static void start(Context c){Intent i=new Intent(c,ClockService.class); if(Build.VERSION.SDK_INT>=26)c.startForegroundService(i);else c.startService(i);}
  @Override public void onCreate(){super.onCreate(); sm=(SensorManager)getSystemService(SENSOR_SERVICE); accel=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);prox=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY); if(accel!=null)sm.registerListener(this,accel,SensorManager.SENSOR_DELAY_NORMAL);if(prox!=null)sm.registerListener(this,prox,SensorManager.SENSOR_DELAY_NORMAL); if(Build.VERSION.SDK_INT>=26){NotificationChannel ch=new NotificationChannel("clock","Clock",NotificationManager.IMPORTANCE_MIN);getSystemService(NotificationManager.class).createNotificationChannel(ch);startForeground(7,new Notification.Builder(this,"clock").setContentTitle("Clock wallpaper active").setSmallIcon(android.R.drawable.ic_menu_recent_history).build());} schedule();}
  void schedule(){h.removeCallbacksAndMessages(null);h.postDelayed(()->{wake("timer");schedule();},INTERVAL);}
  void wake(String why){if(!prefs("enabled",true))return;if((near&&prefs("proximity",true))||(faceDown&&prefs("faceDown",true)))return;Intent i=new Intent(this,WakeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_ANIMATION);startActivity(i);}
  boolean prefs(String k,boolean d){return getSharedPreferences("clock",0).getBoolean(k,d);}
  @Override public void onSensorChanged(SensorEvent e){if(e.sensor.getType()==Sensor.TYPE_PROXIMITY)near=e.values[0]<e.sensor.getMaximumRange();if(e.sensor.getType()==Sensor.TYPE_ACCELEROMETER){float y=e.values[1], z=e.values[2];boolean was=upside;upside=y < -7.5f; faceDown=z < -7.5f; if(!was&&upside&&prefs("flip",true))wake("flip");}}
  @Override public void onAccuracyChanged(Sensor s,int a){}
  @Override public int onStartCommand(Intent i,int f,int id){return START_STICKY;}
  @Override public void onDestroy(){sm.unregisterListener(this);h.removeCallbacksAndMessages(null);super.onDestroy();}
  @Override public android.os.IBinder onBind(Intent i){return null;}
}
