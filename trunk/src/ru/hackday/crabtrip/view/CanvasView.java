package ru.hackday.crabtrip.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {

    public static final Paint PAINT = new Paint();   

    public CanvasView(Context context) {
        super(context);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        PAINT.setColor(Color.RED);
    }

    private int i = 0;
    private int mPosition = 10;
    
    @Override
    protected void onDraw(Canvas canvas) {
        if (i++>=getWidth()) i = 0;
        canvas.drawRect(i, mPosition, 100 + i, mPosition + 100, PAINT);
    }
    
    public void moveLeft() {
    	if (mPosition > 10) {
    		mPosition -= 100;
    	}
    }
    
    public void moveRight() {
    	if (mPosition < 200) {
    		mPosition += 100;
    	}
    }
}
