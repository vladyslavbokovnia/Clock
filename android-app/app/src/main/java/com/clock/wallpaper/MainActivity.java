package com.clock.wallpaper;

import android.app.*;import android.content.*;import android.os.*;import android.view.*;import android.widget.*;

public class MainActivity extends Activity {
  static final String PREF="clock";
  @Override public void onCreate(Bundle b){super.onCreate(b); LinearLayout box=new LinearLayout(this); box.setOrientation(LinearLayout.VERTICAL); box.setPadding(32,32,32,24);
    TextView title=new TextView(this); title.setText("Clock Wallpaper\nBigme HiBreak"); title.setTextSize(24); box.addView(title);
    TextView info=new TextView(this); info.setText("Приложение будит экран на короткий момент каждые 5 минут. Сенсор заблокирован — настройки работают в фоне."); info.setPadding(0,18,0,18); box.addView(info);
    addSwitch(box,"Включить автоматическое пробуждение", "enabled", true);
    addSwitch(box,"Включать при перевороте экраном вверх (портрет вверх ногами)", "flip", true);
    addSwitch(box,"Не включать при срабатывании датчика приближения", "proximity", true);
    addSwitch(box,"Не включать, если телефон лежит экраном вниз", "faceDown", true);
    addSwitch(box,"Работать после перезагрузки", "boot", true);
    TextView interval=new TextView(this); interval.setText("Интервал: 5 минут\nДлительность включения: 1 секунда\nНочных ограничений нет"); interval.setPadding(0,18,0,18); box.addView(interval);
    Button start=new Button(this); start.setText("Запустить службу сейчас"); start.setOnClickListener(v->ClockService.start(this)); box.addView(start);
    setContentView(box); ClockService.start(this);
  }
  void addSwitch(LinearLayout box,String label,String key,boolean def){Switch s=new Switch(this);s.setText(label);s.setChecked(getPreferences(0).getBoolean(key,def));s.setOnCheckedChangeListener((v,c)->{getPreferences(0).edit().putBoolean(key,c).apply();getSharedPreferences(PREF,0).edit().putBoolean(key,c).apply();});box.addView(s);}
}
