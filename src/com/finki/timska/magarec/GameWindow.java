package com.finki.timska.magarec;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
@SuppressLint("NewApi")
public class GameWindow extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
		View decorView = getWindow().getDecorView();
		
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);

		// Remember that you should never show the action bar if the
		// status bar is hidden, so hide that too if necessary.
		getActionBar().hide();
		
		setContentView(R.layout.activity_game_window);
		
		final Button newGameBtn = (Button) findViewById(R.id.new_game_btn);
		final Button howToBtn = (Button) findViewById(R.id.howto_btn);
		final Button aboutBtn = (Button) findViewById(R.id.about_btn);
		final TextView tvGameName = (TextView) findViewById(R.id.start_name);
		
		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/KaushanScript-Regular.otf");
		tvGameName.setTypeface(custom_font);
		
	}
	
	public void viewAbout(View view) {
		Intent intent = new Intent(GameWindow.this, AboutActivity.class);
		startActivity(intent);
	}
	
	public void viewHowTo(View view) {
		Intent intent = new Intent(GameWindow.this, HowToActivity.class);
		startActivity(intent);
	}
	
	public void newGame(View view) {
		Intent intent = new Intent(GameWindow.this, NewGameActivity.class);
		startActivity(intent);
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
