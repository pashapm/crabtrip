package ru.hackday.crabtrip.view;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import ru.hackday.crabtrip.MyActivity;
import ru.hackday.crabtrip.R;
import ru.hackday.crabtrip.model.Model;
import ru.hackday.crabtrip.model.River;
import ru.hackday.crabtrip.model.Ship;

public class TapView extends View {

    private float x;
    private float y;
    private Paint TAP_PAINT = new Paint();

    public TapView(Context context) {
        super(context);
        init();
    }

    public TapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        TAP_PAINT.setColor(Color.argb(128, 255, 0, 0));
    }

    private long lastTap;

    @Override
    protected void onDraw(Canvas canvas) {
        if (lastTap > System.currentTimeMillis() - 500) {
            canvas.drawCircle(x, y, 40, TAP_PAINT);
        }
    }

    public void tap(MotionEvent motionEvent) {
        lastTap = System.currentTimeMillis();
        x = motionEvent.getRawX();
        y = motionEvent.getRawY();
    }
}