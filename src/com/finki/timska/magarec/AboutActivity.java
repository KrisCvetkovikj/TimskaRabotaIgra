package com.finki.timska.magarec;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

@SuppressLint("NewApi")
public class AboutActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		// Remember that you should never show the action bar if the
		// status bar is hidden, so hide that too if necessary.
		
		setContentView(R.layout.activity_about);
		getActionBar().hide();


		TextView tvAbout = (TextView) findViewById(R.id.aboutInfo);
		StringBuilder sb = new StringBuilder("Автори:\n");
		sb.append("Кристијан Цветковиќ\n");
		sb.append("Томче Киров\n");
		sb.append("Никола Николовски\n");
		sb.append("Дарко Ѓорѓиев\n");
		sb.append("Љупче Ѓорѓиев\n");
		tvAbout.setText(sb.toString());

		Animation animation = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.fade_in);
		tvAbout.startAnimation(animation);
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
