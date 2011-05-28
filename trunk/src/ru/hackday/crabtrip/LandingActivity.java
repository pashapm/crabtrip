package ru.hackday.crabtrip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LandingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Button b = new Button(this);
		setContentView(b);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(LandingActivity.this, MyActivity.class);
				startActivity(i);
			}
		});
		
		SoundManager.getInstance(this).loadSounds();
	}
	
}
