package com.clock.wallpaper;

import android.content.*;import android.graphics.*;import android.view.*;import java.util.*;

public class ScreenHandsOverlay extends View {
  Paint p=new Paint(3); Calendar now=Calendar.getInstance(); boolean upside; int battery=100;
  ScreenHandsOverlay(Context c,boolean u){super(c);upside=u;p.setStrokeCap(Paint.Cap.ROUND);setLayerType(View.LAYER_TYPE_SOFTWARE,null);}
  void hand(Canvas c,float cx,float cy,float len,float angle,float width,int color){double a=Math.toRadians(angle-90);float x=cx+(float)Math.cos(a)*len,y=cy+(float)Math.sin(a)*len;p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(width+10);p.setColor(Color.argb(190,20,5,0));c.drawLine(cx,cy,x,y,p);p.setStrokeWidth(width);p.setColor(color);c.drawLine(cx,cy,x,y,p);p.setStyle(Paint.Style.FILL);p.setColor(color);c.drawCircle(x,y,width*1.25f,p);}
  @Override protected void onDraw(Canvas c){super.onDraw(c);float w=getWidth(),h=getHeight(),cx=w/2f,cy=h/2f;now=Calendar.getInstance();int gold=Color.rgb(255,218,86),light=Color.rgb(255,240,150);float hour=((now.get(Calendar.HOUR_OF_DAY)%12)+now.get(Calendar.MINUTE)/60f)/12f*360f;float minute=(now.get(Calendar.MINUTE)+now.get(Calendar.SECOND)/60f)/60f*360f;hand(c,cx,cy,Math.min(w,h)*.18f,hour,Math.max(10,w*.018f),gold);hand(c,cx,cy,Math.min(w,h)*.30f,minute,Math.max(7,w*.012f),light);
    float wx=w*.291f,wy=h*.454f;float week=((now.get(Calendar.DAY_OF_WEEK)-1)/7f)*360f;hand(c,wx,wy,Math.min(w,h)*.075f,week,Math.max(5,w*.009f),Color.rgb(255,225,105));
    float topX=w*.5f,topY=h*.218f;float batteryAngle=(battery/100f)*360f;hand(c,topX,topY,Math.min(w,h)*.115f,batteryAngle,Math.max(5,w*.009f),gold);
    float bottomY=h*.72f;float month=now.get(Calendar.MONTH)/12f*360f;float day=(now.get(Calendar.DAY_OF_MONTH)-1)/31f*360f;hand(c,cx,bottomY,Math.min(w,h)*.115f,month,Math.max(5,w*.009f),gold);hand(c,cx,bottomY,Math.min(w,h)*.155f,day,Math.max(5,w*.009f),light);
    p.setStyle(Paint.Style.FILL);p.setColor(gold);c.drawCircle(cx,cy,Math.max(12,w*.022f),p);p.setColor(Color.rgb(75,20,4));c.drawCircle(cx,cy,Math.max(5,w*.010f),p);}
  public void setBattery(int value){battery=Math.max(0,Math.min(100,value));invalidate();}
}
