package com.clock.wallpaper;

import android.app.*;import android.app.admin.DevicePolicyManager;import android.content.*;import android.os.*;import android.view.*;import android.widget.*;

public class MainActivity extends Activity {
  static final String PREF="clock"; DevicePolicyManager dpm; ComponentName admin;
  @Override public void onCreate(Bundle b){super.onCreate(b); dpm=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE); admin=new ComponentName(this,ClockAdminReceiver.class); LinearLayout box=new LinearLayout(this); box.setOrientation(LinearLayout.VERTICAL); box.setPadding(32,32,32,24);
    TextView title=new TextView(this); title.setText("Clock Wallpaper\nBigme HiBreak"); title.setTextSize(24); box.addView(title);
    TextView info=new TextView(this); info.setText("Фоновая заставка встроена в приложение. Экран кратко включается каждые 5 минут."); info.setPadding(0,18,0,18); box.addView(info);
    addSwitch(box,"Включить автоматическое пробуждение", "enabled", true); addSwitch(box,"Включать при перевороте портретом вверх ногами", "flip", true); addSwitch(box,"Не включать при датчике приближения", "proximity", true); addSwitch(box,"Не включать, если телефон лежит экраном вниз", "faceDown", true); addSwitch(box,"Работать после перезагрузки", "boot", true); addSwitch(box,"Показывать заставку вверх ногами", "wallpaperUpsideDown", false);
    TextView interval=new TextView(this); interval.setText("Интервал: 5 минут\nДлительность показа: 1 секунда\nНочных ограничений нет"); interval.setPadding(0,18,0,18); box.addView(interval);
    Button adminButton=new Button(this); adminButton.setText(dpm.isAdminActive(admin)?"Выключение экрана разрешено":"Разрешить автоматическое выключение экрана"); adminButton.setOnClickListener(v->{Intent i=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,admin);i.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"Нужно для выключения экрана после краткого обновления заставки.");startActivityForResult(i,42);}); box.addView(adminButton);
    Button start=new Button(this); start.setText("Запустить службу сейчас"); start.setOnClickListener(v->ClockService.start(this)); box.addView(start); setContentView(box); ClockService.start(this);
  }
  void addSwitch(LinearLayout box,String label,String key,boolean def){Switch s=new Switch(this);s.setText(label);s.setChecked(getSharedPreferences(PREF,0).getBoolean(key,def));s.setOnCheckedChangeListener((v,c)->getSharedPreferences(PREF,0).edit().putBoolean(key,c).apply());box.addView(s);}
}
