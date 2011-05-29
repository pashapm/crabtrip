package ru.hackday.crabtrip.view;

import ru.hackday.crabtrip.MyActivity;
import ru.hackday.crabtrip.R;
import ru.hackday.crabtrip.model.Model;
import ru.hackday.crabtrip.model.River;
import ru.hackday.crabtrip.model.Ship;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {

    public static final Paint STONE_PAINT = new Paint();
    public static final Paint SHIP_PAINT = new Paint();
    
    public static final Paint SCORES_PAINT = new Paint();

    private Model model;
    private static final int STEPS_IN_MOVE = MyActivity.TICKS_PER_STEP;

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
    
    BitmapDrawable rockd;
    BitmapDrawable shipd;

    private void init() {
        STONE_PAINT.setColor(Color.RED);
        SHIP_PAINT.setColor(Color.BLUE);
        SCORES_PAINT.setColor(Color.BLACK);
        SCORES_PAINT.setTextSize(25);
        
        Bitmap r = BitmapFactory.decodeResource(getResources(), R.drawable.rock_2);
        Bitmap s = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
        rockd = new BitmapDrawable(r);
        shipd = new BitmapDrawable(s);
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int length = River.LENGTH;
        int width = River.WIDTH;
        float scaleWidth = 1.0f * getWidth() / length;
        float scaleHeight = 1.0f * getHeight() / width;
        int delta = (int) (scaleHeight * step / STEPS_IN_MOVE);

        River river = model.getmRiver();
        for (int i = 0; i < length; i++) {
            int stone = river.getStone(i);
            if (stone == 0) continue;
            
            Rect r = new Rect((int)((stone - 1) * scaleWidth), 
            		(int)(delta + (length - i - 1) * scaleHeight),
            		(int)(stone * scaleWidth),
            		(int)(delta + (length - i) * scaleHeight)
    				);
            rockd.setBounds(r);
            rockd.draw(canvas);
//            canvas.drawRect((stone - 1) * scaleWidth, delta + (length - i - 1) * scaleHeight, stone * scaleWidth, delta + (length - i) * scaleHeight, STONE_PAINT);
        }

        Ship ship = model.getmShip();
        int shipPosition = ship.getPosition();
//        System.out.println("shipPosition = " + shipPosition);
        //canvas.drawRect(0, (shipPosition - 1) * scaleHeight, scaleWidth, shipPosition * scaleHeight, SHIP_PAINT);
		
        Rect r = new Rect((int)((shipPosition - 1) * scaleWidth), 
        		(int)(scaleHeight * (width - 1)),
        		(int)(shipPosition * scaleWidth),
        		(int)(getHeight())
				);
        
        shipd.setBounds(r);
        shipd.draw(canvas);
        
//        canvas.drawRect((shipPosition - 1) * scaleWidth, 
//				scaleHeight * (width - 1), 
//				shipPosition * scaleWidth, 
//				getHeight(), SHIP_PAINT);
		
		canvas.drawText(model.getDistance()+"", 10, 30, SCORES_PAINT);
		canvas.drawText(ship.getLife()+"", 10, 60, SCORES_PAINT);
    }


    private int step = 0;

    public void step() {
        step++;
    }

    public void reset() {
        step = 0;
    }
}