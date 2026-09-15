package com.clock.wallpaper;

import android.content.*;import android.graphics.*;import android.view.*;import java.util.*;

public class ScreenHandsOverlay extends View {
  Paint p=new Paint(3); Calendar now=Calendar.getInstance(); boolean upside;
  ScreenHandsOverlay(Context c,boolean u){super(c);upside=u;p.setStrokeCap(Paint.Cap.ROUND);setLayerType(View.LAYER_TYPE_SOFTWARE,null);}
  PointF perimeter(float t,float w,float h){float inset=Math.max(12,Math.min(w,h)*.035f),x0=inset,y0=inset,x1=w-inset,y1=h-inset;float per=2*((x1-x0)+(y1-y0));float d=(t%1f+1f)%1f*per;if(d<(x1-x0))return new PointF(x0+d,y0);d-=x1-x0;if(d<(y1-y0))return new PointF(x1,y0+d);d-=y1-y0;if(d<(x1-x0))return new PointF(x1-d,y1);d-=x1-x0;return new PointF(x0,y1-d);}
  void hand(Canvas c,float cx,float cy,PointF end,float width,int color){p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(width+10);p.setColor(Color.argb(180,20,5,0));c.drawLine(cx,cy,end.x,end.y,p);p.setStrokeWidth(width);p.setColor(color);c.drawLine(cx,cy,end.x,end.y,p);p.setStyle(Paint.Style.FILL);p.setColor(color);c.drawCircle(end.x,end.y,width*1.35f,p);}
  @Override protected void onDraw(Canvas c){super.onDraw(c);float w=getWidth(),h=getHeight(),cx=w/2f,cy=h/2f;now=Calendar.getInstance();float hour=((now.get(Calendar.HOUR)%12)+now.get(Calendar.MINUTE)/60f)/12f;float minute=(now.get(Calendar.MINUTE)+now.get(Calendar.SECOND)/60f)/60f;if(upside){hour=1f-hour;minute=1f-minute;}PointF he=perimeter(hour,w,h),me=perimeter(minute,w,h);hand(c,cx,cy,he,Math.max(10,w*.018f),Color.rgb(255,218,86));hand(c,cx,cy,me,Math.max(7,w*.012f),Color.rgb(255,240,150));p.setColor(Color.rgb(255,218,86));c.drawCircle(cx,cy,Math.max(12,w*.022f),p);invalidateDelayed();}
  void invalidateDelayed(){postInvalidateDelayed(500);}
}
