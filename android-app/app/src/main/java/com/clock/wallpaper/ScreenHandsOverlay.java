package com.clock.wallpaper;

import android.content.*;import android.graphics.*;import android.view.*;import java.util.*;

public class ScreenHandsOverlay extends View {
  Paint p=new Paint(3); Calendar now=Calendar.getInstance(); boolean upside;
  ScreenHandsOverlay(Context c,boolean u){super(c);upside=u;p.setStrokeCap(Paint.Cap.ROUND);setLayerType(View.LAYER_TYPE_SOFTWARE,null);}
  void hand(Canvas c,float cx,float cy,float len,float angle,float width,int color){double a=Math.toRadians(angle-90);float x=cx+(float)Math.cos(a)*len,y=cy+(float)Math.sin(a)*len;p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(width+10);p.setColor(Color.argb(190,20,5,0));c.drawLine(cx,cy,x,y,p);p.setStrokeWidth(width);p.setColor(color);c.drawLine(cx,cy,x,y,p);p.setStyle(Paint.Style.FILL);p.setColor(color);c.drawCircle(x,y,width*1.25f,p);}
  @Override protected void onDraw(Canvas c){super.onDraw(c);float w=getWidth(),h=getHeight(),cx=w/2f,cy=h/2f;now=Calendar.getInstance();float hour=((now.get(Calendar.HOUR)%12)+now.get(Calendar.MINUTE)/60f)/12f*360f;float minute=(now.get(Calendar.MINUTE)+now.get(Calendar.SECOND)/60f)/60f*360f;if(upside){hour=360f-hour;minute=360f-minute;}hand(c,cx,cy,Math.min(w,h)*.18f,hour,Math.max(10,w*.018f),Color.rgb(255,218,86));hand(c,cx,cy,Math.min(w,h)*.30f,minute,Math.max(7,w*.012f),Color.rgb(255,240,150));p.setStyle(Paint.Style.FILL);p.setColor(Color.rgb(255,218,86));c.drawCircle(cx,cy,Math.max(12,w*.022f),p);p.setColor(Color.rgb(75,20,4));c.drawCircle(cx,cy,Math.max(5,w*.010f),p);}
}
