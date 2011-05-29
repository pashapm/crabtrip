package ru.hackday.crabtrip;

import android.media.AudioManager;
import ru.hackday.crabtrip.model.Direction;
import ru.hackday.crabtrip.model.Model;
import ru.hackday.crabtrip.view.CanvasView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

public class MyActivity extends Activity implements OnTouchListener {
	public static final int TICKS_PER_STEP = 6;
	
	private static final int DIALOG_GAME_OVER = 0;
	
	private Thread mRenderThread;
	private CanvasView mView;
	private SoundManager mSoundManager;
	private Vibrator mVibrator;
	
	private Model mModel;
	
	private int mTicks;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        mView = (CanvasView) findViewById(R.id.gameView);
        mSoundManager = SoundManager.getInstance(getApplicationContext());
        findViewById(R.id.up).setOnTouchListener(this);
        findViewById(R.id.down).setOnTouchListener(this);
//        findViewById(R.id.up).setVisibility(View.INVISIBLE);
//        findViewById(R.id.down).setVisibility(View.INVISIBLE);
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        
        Handler drumHandler = new Handler(new Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				mSoundManager.playDrum();
				if (mTicks++ % TICKS_PER_STEP == 0) {
					mTicks = 1;
                    mView.reset();
					mModel.move(Direction.FORWARD);
					updateSonar();
					checkGameOver();
				} else {
                    mView.step();
                }
				return true;
			}

		});
        EventBus.getInstance().mDrumHandler = drumHandler;
        
        Handler mTapHandler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					mSoundManager.playPata();
					break;
				case 1:
					mSoundManager.playPon();
					break;
				case 2:
					mModel.move(Direction.LEFT);
					mTicks = 1;					
					mVibrator.vibrate(100);

					mSoundManager.playTurnLeft();
					Log.d("CRAAAAAAAAB", "PATA PATA PATA PON!");					
					break;
				case 3:
					mModel.move(Direction.RIGHT);
					mTicks = 1;
					
					mVibrator.vibrate(100);

					mSoundManager.playTurnRight();
					Log.d("CRAAAAAAAAB", "PON PON PATA PON!");
					break;
				default:
					break;
				}
				
				checkGameOver();
				return true;
			}			
		});
        EventBus.getInstance().mTapHandler = mTapHandler;
        
        mModel = new Model();
        mView.setModel(mModel);
    }
    
    private void updateSonar() {
    	Log.d("SONAR", mModel.getNearestStonePosition()+"");
    	mSoundManager.updateDirection(mModel.getNearestStonePosition());
	}
    
    @Override
    protected void onResume() { 
    	mRenderThread =  new Thread(new Runnable() {
            public void run() {
            	
                while (! Thread.interrupted()) {
                	mView.postInvalidate();
                	try { 
						Thread.sleep(50);
					} catch (InterruptedException e) {
						return;
					}
                }
            }
        }) ;
    	mRenderThread.start();
    	EventBus.getInstance().start();
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	mRenderThread.interrupt();
    	mRenderThread = null;
    	EventBus.getInstance().stop();
    	super.onPause();
    }

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		if (arg1.getAction() != MotionEvent.ACTION_DOWN) {
			return true;
		}
		
		switch (arg0.getId()) {
		case R.id.up:
			EventBus.getInstance().postPatapon(0);
//			showForWhile(arg0);
			break;
		case R.id.down:
			EventBus.getInstance().postPatapon(1);
//			showForWhile(arg0);
			break;
		default:
			break;
		}
		return true;
	}
	
	private void showForWhile(final View v) {
		v.setVisibility(View.VISIBLE);
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				v.setVisibility(View.INVISIBLE);
			}
		}, 1000);
	}
	
	private void checkGameOver() {
		if (mModel.isGameOver()) {
			Log.d("CRAAAAAAAAB", "Game Over. Distance = " + mModel.getDistance());
			
			showDialog(DIALOG_GAME_OVER);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		if (DIALOG_GAME_OVER == id) {		
			builder.setMessage("Your distance: " + mModel.getDistance())
			       .setCancelable(false)
			       .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               mModel.reset(); 
			        	   dialog.cancel();
			           }
			       })
			       .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   MyActivity.this.finish();
			           }
			       });
		}
		
		return builder.create();
	}
	
	
}
