package com.clock.wallpaper;

import android.content.*;import android.graphics.*;import android.view.*;import java.util.*;

public class AnalogOverlay extends View {
  Paint p=new Paint(3); Calendar now=Calendar.getInstance(); int battery=100; boolean upside;
  AnalogOverlay(Context c, boolean u){super(c);upside=u;p.setStrokeCap(Paint.Cap.ROUND);setLayerType(View.LAYER_TYPE_SOFTWARE,null);}
  void hand(Canvas c,float cx,float cy,float len,float angle,float width,int color){double a=Math.toRadians(angle-90);float x=cx+(float)Math.cos(a)*len,y=cy+(float)Math.sin(a)*len;p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(width+8);p.setColor(Color.argb(150,35,8,0));c.drawLine(cx,cy,x,y,p);p.setStrokeWidth(width);p.setColor(color);c.drawLine(cx,cy,x,y,p);p.setStyle(Paint.Style.FILL);c.drawCircle(cx,cy,width*0.72f,p);}
  @Override protected void onDraw(Canvas c){super.onDraw(c);float w=getWidth(),h=getHeight(),s=Math.min(w/971f,h/1620f);int gold=Color.rgb(255,218,86), dark=Color.rgb(87,28,5);
    now=Calendar.getInstance();float cx=w*.5f,cy=h*.218f;float hour=(now.get(Calendar.HOUR)%12)*30+now.get(Calendar.MINUTE)*.5f, min=now.get(Calendar.MINUTE)*6+now.get(Calendar.SECOND)*.1f;hand(c,cx,cy,w*.105f,hour,(int)Math.max(9,w*.018f),(int)dark);hand(c,cx,cy,w*.145f,min,(int)Math.max(7,w*.013f),(int)gold);
    float battAngle=360f-(battery/100f)*360f;hand(c,cx,cy,w*.185f,battAngle,(int)Math.max(5,w*.009f),(int)gold);
    float wx=w*.285f,wy=h*.465f;int dow=now.get(Calendar.DAY_OF_WEEK);float weekAngle=(dow-1)*45f;hand(c,wx,wy,w*.105f,weekAngle,(int)Math.max(6,w*.012f),gold);
    float yy=h*.72f;int month=now.get(Calendar.MONTH);float monthAngle=month*30f;hand(c,cx,yy,w*.145f,monthAngle,(int)Math.max(6,w*.012f),gold);
    p.setColor((int)gold);p.setStyle(Paint.Style.FILL);c.drawCircle(cx,cy,Math.max(11,w*.022f),p);p.setColor((int)dark);c.drawCircle(cx,cy,Math.max(5,w*.010f),p);
  }
  public void setBattery(int b){battery=Math.max(0,Math.min(100,b));}
}
