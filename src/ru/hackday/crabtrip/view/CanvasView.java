package ru.hackday.crabtrip.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Gallery;
import ru.hackday.crabtrip.model.Model;
import ru.hackday.crabtrip.model.River;
import ru.hackday.crabtrip.model.Ship;

public class CanvasView extends View {

    public static final Paint STONE_PAINT = new Paint();
    public static final Paint SHIP_PAINT = new Paint();

    public static final Paint GAME_OVER_PAINT = new Paint();
    public static final Paint SCORES_PAINT = new Paint();

    private Model model;
    private static final int STEPS_IN_MOVE = 6;

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

        GAME_OVER_PAINT.setColor(Color.RED);
        GAME_OVER_PAINT.setTextSize(40);

        SCORES_PAINT.setColor(Color.GREEN);
        SCORES_PAINT.setTextSize(30);
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int length = River.LENGTH;
        int width = River.WIDTH;
        int scaleWidth = getWidth() / length;
        int scaleHeight = getHeight() / width;
        int delta = scaleHeight * step / STEPS_IN_MOVE;

        River river = model.getmRiver();
        for (int i = 0; i < length; i++) {
            int stone = river.getStone(i);
            if (stone == 0) continue;
            //canvas.drawRect(i * scaleWidth, (stone - 1) * scaleHeight, (i + 1) * scaleWidth, stone * scaleHeight, STONE_PAINT);
            canvas.drawRect((stone - 1) * scaleWidth, delta + (length - i - 1) * scaleHeight, stone * scaleWidth, delta + (length - i) * scaleHeight, STONE_PAINT);
        }

        Ship ship = model.getmShip();
        int shipPosition = ship.getPosition();
//        System.out.println("shipPosition = " + shipPosition);
        //canvas.drawRect(0, (shipPosition - 1) * scaleHeight, scaleWidth, shipPosition * scaleHeight, SHIP_PAINT);
        canvas.drawRect((shipPosition - 1) * scaleWidth, scaleHeight * (width - 1), shipPosition * scaleWidth, getHeight(), SHIP_PAINT);

        if (model.isGameOver()) {
            drawGameOver(canvas);
        }
    }

    private void drawGameOver(final Canvas canvas) {
        canvas.drawText("Game Over", 10, 10, GAME_OVER_PAINT);
        canvas.drawText("Distance: " + model.getDistance(), 10, 50, SCORES_PAINT);
    }

    private int step = 0;

    public void step() {
        step++;
    }

    public void reset() {
        step = 0;
    }
}