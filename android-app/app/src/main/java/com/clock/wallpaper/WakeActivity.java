package com.clock.wallpaper;

import android.app.*;import android.os.*;import android.view.*;import android.graphics.Color;

public class WakeActivity extends Activity {
  @Override public void onCreate(Bundle b){super.onCreate(b);getWindow().setStatusBarColor(Color.TRANSPARENT);getWindow().setNavigationBarColor(Color.TRANSPARENT);getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON); View v=new View(this);v.setBackgroundColor(Color.TRANSPARENT);setContentView(v);new Handler().postDelayed(this::finish,1000);}
  @Override public void onBackPressed(){finish();}
}
