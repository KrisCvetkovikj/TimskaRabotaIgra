package com.finki.timska.magarec;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class PlayerTimeActivity extends Activity {

	Handler handler = new Handler();
	float playerTime = 0;
	int progressStatus = 0;

	Button tapButton;
	ProgressBar progBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_time);

		tapButton = (Button) findViewById(R.id.tapBtn);
		progBar = (ProgressBar) findViewById(R.id.progBar);

		final long playerStartTime = System.nanoTime();
		Log.d("pTimeStart", String.format("%d", playerStartTime));

		// Start long running operation in a background thread
		new Thread(new Runnable() {
			public void run() {
				while (progressStatus < 100) {
					progressStatus = currentProgress;

					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// Update the progress bar
					handler.post(new Runnable() {
						public void run() {
							progBar.setProgress(currentProgress);
							Log.d("pbarProg",
									String.format("%d", progBar.getProgress()));
						}
					});

					if (progBar.getProgress() >= 100) {
						playerTime = ((float) (System.nanoTime()  - playerStartTime)) / 1000000000;
						Log.d("pTimeEnd", String.format("%.2f", playerTime));

						Intent returnIntent = new Intent();
						returnIntent.putExtra("pTime", Float.toString(playerTime));
						setResult(RESULT_OK, returnIntent);
						finish();
					}
				}
			}
		}).start();
	}

	int currentProgress = 0;

	public void updateProgress(View view) {
		currentProgress += new Random().nextInt(15) + 5;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// TODO: If Settings has multiple levels, Up should navigate up
			// that hierarchy.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
