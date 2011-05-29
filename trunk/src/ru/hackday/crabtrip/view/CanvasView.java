package ru.hackday.crabtrip.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import ru.hackday.crabtrip.model.Model;
import ru.hackday.crabtrip.model.River;
import ru.hackday.crabtrip.model.Ship;

public class CanvasView extends View {

    public static final Paint STONE_PAINT = new Paint();
    public static final Paint SHIP_PAINT = new Paint();

    private Model model;

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
        STONE_PAINT.setColor(Color.RED);
        SHIP_PAINT.setColor(Color.BLUE);
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = River.LENGTH;
        int length = River.WIDTH;
        int scaleWidth = getWidth() / width;
        int scaleHeight = getHeight() / length;

        River river = model.getmRiver();
        for (int i = 0; i < width; i++) {
            int stone = river.getStone(i);
            if (stone == 0) continue;
            canvas.drawRect(i * scaleWidth, (stone - 1) * scaleHeight, (i + 1) * scaleWidth, stone * scaleHeight, STONE_PAINT);
        }

        Ship ship = model.getmShip();
        int shipPosition = ship.getPosition();
//        System.out.println("shipPosition = " + shipPosition);
        canvas.drawRect(0, (shipPosition - 1) * scaleHeight, scaleWidth, shipPosition * scaleHeight, SHIP_PAINT);
    }
}