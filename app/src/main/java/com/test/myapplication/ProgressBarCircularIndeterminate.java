package com.test.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.material.internal.ViewUtils;

public class ProgressBarCircularIndeterminate extends CustomView{

    final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";

    int backgroundColor = Color.parseColor("#1E88E5");
    Context mContext;
    public static Canvas draw;

    public ProgressBarCircularIndeterminate(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setAttributes(attrs);
    }

    // Set attributtes of XML to View
    protected void setAttributes(AttributeSet attrs) {
        setMinimumHeight(Utils.dpToPx(32, getResources()));
        setMinimumWidth(Utils.dpToPx(32, getResources()));

        // Set background Color
        // Color by resource
        int backgroundColor = attrs.getAttributeResourceValue(ANDROIDXML, "background",-1);
        if (backgroundColor != -1) {
            setBackgroundColor(getResources().getColor(backgroundColor));
        } else {
            // Color by hexadecimal
            int background = attrs.getAttributeIntValue(ANDROIDXML, "background",-1);
            if (background != -1) {
                setBackgroundColor(background);
            } else {
                setBackgroundColor(Color.parseColor("#1E88E5"));
            }
        }
        setMinimumHeight(Utils.dpToPx(3,getResources()));
    }

    protected int makePressColor() {
        int r = (this.backgroundColor >> 16) & 0xFF;
        int g = (this.backgroundColor >> 8) & 0xFF;
        int b = (this.backgroundColor >> 0) & 0xFF;

        return Color.argb(128,r,g,b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (firstAnimationOver == false) {
            drawFirstAnimation(canvas);
        }
        if (cont > 0) {
            drawSecondAnimation(canvas);
        }
        invalidate();   // invalidate()를 호출 하면 onDraw가 호출된다.
    }

    float pxTodp(int px) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int dpi = displayMetrics.densityDpi;
        float density = displayMetrics.density;

        return (float)Math.round((px - 0.5)/density);
    }

    float radius1 = 0;
    float radius2 = 0;
    int cont = 0;
    boolean firstAnimationOver = false;
    private void drawFirstAnimation(Canvas canvas) {
//        Log.e("getwidth",pxTodp(getWidth())+"");
//        Log.e("getwidth",getWidth()+"");
        if (radius1 < getWidth()/2) {   // 하늘색 원이 커지는것.
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(makePressColor());
            radius1 = (radius1 >= getWidth()/2) ? (float)getWidth()/2 : (float)(radius1 + 1);
            canvas.drawCircle(getWidth()/2, getHeight()/2, radius1, paint);
        } else {    // 하늘색원의 가운데를 흰색으로 지우면서 원모양으로 커지는것.
            Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas temp = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(makePressColor());
            temp.drawCircle(getWidth()/2, getHeight()/2, getHeight()/2, paint);
            Paint transparentPaint = new Paint();
            transparentPaint.setAntiAlias(true);
            transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            if (cont >= 50) {
                radius2 = (radius2 >= getWidth()/2) ? (float)getWidth()/2 : radius2 + 1;
            } else {
                radius2 = (radius2 >= getWidth()/2 - Utils.dpToPx(4, getResources())) ? (float)getWidth()/2 - Utils.dpToPx(4, getResources()) : radius2 + 1;
            }
            temp.drawCircle(getWidth()/2, getHeight()/2, radius2, transparentPaint);
            canvas.drawBitmap(bitmap, 0, 0, new Paint());
            if (radius2 >= getWidth()/2 - Utils.dpToPx(4, getResources())) cont++;
            if (radius2 >= getWidth()/2) {
                firstAnimationOver = true;

            }
        }
    }
    void click() {
        arcD = 0;
        arcO = 0;
        power = 6;
        rotateAngle = 0;
        limite = 0;
        radius1 = 0;
        radius2 = 0;
        cont = 0;
        firstAnimationOver = false;
    }

    int arcD = 0;
    int arcO = 0;
    int power = 6;
    float rotateAngle = 0;
    int limite = 0;
    public void drawSecondAnimation(Canvas canvas) {
        if (arcO == limite) {
            arcD += power;
        }
        if (arcD >= 290 || arcO > limite) {
            arcO += power;
            arcD -= power;  // arcO 기준점이 앞으로 나오게 되니까 기준점으로부터 돌아간 각도인 arcD에 arcO증가만큼 빼줘야 arcD가 멈춰 있는걸로 보인다.
        }
        if (arcO > limite + 290) {  // 초기화
            limite = arcO;  // arcD와 arcO가 만나서 없어지는 그 부분부터 초기화 되어 똑같이 반복
            arcD = 0;
        }
        rotateAngle += 4;
        canvas.rotate(rotateAngle, getWidth()/2, getHeight()/2);

        Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas temp = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(backgroundColor);
        temp.drawArc(new RectF(0,0,getWidth(),getHeight()), arcO, arcD, true, paint);   // arcO기준점부터 arcD 각도를 그린다.
        Paint transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        temp.drawCircle(getWidth()/2, getHeight()/2, (getWidth()/2)-Utils.dpToPx(4, getResources()), transparentPaint);

        canvas.drawBitmap(bitmap, 0, 0, new Paint());
    }

    public void setBackgroundColor(int color) {
        super.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        if (isEnabled()) beforeBackground = backgroundColor;
        this.backgroundColor = color;
    }




}
