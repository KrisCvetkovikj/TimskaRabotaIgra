package com.finki.timska.magarec;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class NewGameActivity extends Activity {

	static final int PLAYER_TIME_REQUEST = 1010; // The request code

	final TextView[] tvs = new TextView[4];

	final ImageButton[] imgBtnsP1 = new ImageButton[5];

	ImageView imgViewP2C1;
	ImageView imgViewP3C1;
	ImageView imgViewP4C1;

	ImageView imgViewP2C5;
	ImageView imgViewP3C5;
	ImageView imgViewP4C5;

	Hashtable<Integer, String> htCards = new Hashtable<Integer, String>();

	String[] p1Cards = new String[5];
	String[] p2Cards = new String[5];
	String[] p3Cards = new String[5];
	String[] p4Cards = new String[5];

	Integer p1Letters = 0;
	Integer p2Letters = 0;
	Integer p3Letters = 0;
	Integer p4Letters = 0;

	String[] letters = { "", "M", "MA", "MAG", "MAGA", "MAGAR", "MAGARE",
			"MAGAREC" };

	final Integer[] imgBtnIds = new Integer[5];
	final ArrayList<Integer> cardList = new ArrayList<Integer>();

	// odlucuva koj e delitel t.e. posleden, igracot desno dobiva 5 karti (prv)
	// 0 = prv, 1 = vtor, ..., 3 = cetvrt
	int delitel = new Random().nextInt(4);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_new_game);

		setup();
		getCardList();
		dealCards();
		try {
			// checkCurrentState();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	float playerTime = -1;

	// index of winner, if any
	int indexWinner = -1;

	public void checkCurrentState() throws Exception {

		// check p1 cards; are they equal
		for (int i = 0; i < 3; i++) {

			if (p1Cards[i].charAt(p1Cards[i].length() - 1) != p1Cards[i + 1]
					.charAt(p1Cards[i + 1].length() - 1)) {
				break;
			} else {
				indexWinner = 0;
			}
		}

		// check p2 cards; are they equal
		for (int i = 0; i < 3; i++) {
			if (p2Cards[i].charAt(p2Cards[i].length() - 1) != p2Cards[i + 1]
					.charAt(p2Cards[i + 1].length() - 1)) {
				break;
			} else {
				indexWinner = 1;
			}
		}

		// check p3 cards; are they equal
		for (int i = 0; i < 3; i++) {
			if (p3Cards[i].charAt(p3Cards[i].length() - 1) != p3Cards[i + 1]
					.charAt(p3Cards[i + 1].length() - 1)) {
				break;
			} else {
				indexWinner = 2;
			}
		}

		// check p4 cards; are they equal
		for (int i = 0; i < 3; i++) {
			if (p4Cards[i].charAt(p4Cards[i].length() - 1) != p4Cards[i + 1]
					.charAt(p4Cards[i + 1].length() - 1)) {
				break;
			} else {
				indexWinner = 3;
			}
		}

		indexWinner = 1;
		Random r = new Random();
		if (indexWinner == 0) {

			// 0 == p2, 1 == p3, 2 == p4
			float indexLoser[] = { (r.nextInt(100) + 1) / 10,
					(r.nextInt(100) + 1) / 10, (r.nextInt(100) + 1) / 10 };
			if (indexLoser[0] >= indexLoser[1]
					&& indexLoser[0] >= indexLoser[2]) {
				p2Letters++;
			} else if (indexLoser[1] >= indexLoser[0]
					&& indexLoser[1] >= indexLoser[2]) {
				p3Letters++;
			} else {
				p4Letters++;
			}
			wonRound(indexWinner);

		} else if (indexWinner == 1) {

			Intent intent = new Intent(this, PlayerTimeActivity.class);
			startActivityForResult(intent, PLAYER_TIME_REQUEST);
			Log.d("[playerto,e", String.format("%.2f", playerTime));

			float indexLoser[] = { (r.nextFloat() * 9) + 1,
					(r.nextFloat() * 9) + 1 };
			// indexLoser[0] == p3, indexLoser[1] == p4, indexWinner == p2
			if (indexLoser[0] >= indexLoser[1] && indexLoser[0] >= playerTime) {
				p3Letters++;
			} else if (indexLoser[1] >= indexLoser[0]
					&& indexLoser[1] >= playerTime) {
				p4Letters++;
			} else {
				p1Letters++;
			}
			Log.d("times",
					String.format("%.2f %.2f", indexLoser[0], indexLoser[1]));

			// wonRound(indexWinner);
		} else if (indexWinner == 2) {
			Intent intent = new Intent(this, PlayerTimeActivity.class);
			startActivityForResult(intent, PLAYER_TIME_REQUEST);

			float indexLoser[] = { (r.nextInt(100) + 1) / 10,
					(r.nextInt(100) + 1) / 10 };
			// indexLoser[0] == p2, indexLoser[1] == p4, indexWinner == p3
			if (indexLoser[0] >= indexLoser[1] && indexLoser[0] >= playerTime) {
				p2Letters++;
			} else if (indexLoser[1] >= indexLoser[0]
					&& indexLoser[1] >= playerTime) {
				p4Letters++;
			} else {
				p1Letters++;
			}
			// wonRound(indexWinner);
		} else if (indexWinner == 3) {
			Intent intent = new Intent(this, PlayerTimeActivity.class);
			startActivityForResult(intent, PLAYER_TIME_REQUEST);

			float indexLoser[] = { (r.nextInt(100) + 1) / 10,
					(r.nextInt(100) + 1) / 10 };
			// indexLoser[0] == p2, indexLoser[1] == p3, indexWinner == p4
			if (indexLoser[0] >= indexLoser[1] && indexLoser[0] >= playerTime) {
				p2Letters++;
			} else if (indexLoser[1] >= indexLoser[0]
					&& indexLoser[1] >= playerTime) {
				p3Letters++;
			} else {
				p1Letters++;
			}
			// wonRound(indexWinner);
		} else if (indexWinner == -1) {
			// no one has 4 equal cards, keep playing
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PLAYER_TIME_REQUEST) {
			if (resultCode == RESULT_OK) {
				try {
					playerTime = Float.parseFloat(data.getStringExtra("pTime"));
					Log.d("time", String.format("%.2f", playerTime));
					wonRound(indexWinner);

				} catch (NumberFormatException nfe) {
					Log.d("nfe", nfe.getMessage());
				}
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
				playerTime = 100;
			}
		}
	}// onActivityResult

	public void wonRound(int indexWinner) {
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this,
				android.R.style.Theme_Black_NoTitleBar);

		// Add the buttons
		builder.setPositiveButton(R.string.cont,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User clicked OK button
					}
				});

		// 2. Chain together various setter methods to set the dialog
		// characteristics
		StringBuilder message = new StringBuilder();

		message.append("Current state:\n");
		message.append("Player 1: " + letters[p1Letters] + "\n");
		message.append("Player 2: " + letters[p2Letters] + "\n");
		message.append("Player 3: " + letters[p3Letters] + "\n");
		message.append("Player 4: " + letters[p4Letters] + "\n");

		builder.setMessage(message.toString());

		if (indexWinner == 0) {
			builder.setTitle("You won this round!");
		} else {
			builder.setTitle("Player " + (indexWinner + 1)
					+ " wins this round!");
		}

		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	boolean cardSelected = false;
	int currCardId = -1;

	public void selectCard(View view) {
		int viewId = view.getId();
		if (currCardId == -1) {
			currCardId = viewId;
			selectCardAnimation(view, 0.0f, -20.0f);
		} else {
			if (viewId == currCardId) {
				selectCardAnimation(view, 0.0f, 0.0f);
				currCardId = -1;
			} else {
				for (int i = 0; i < 4; i++) {
					selectCardAnimation(imgBtnsP1[i], 0.0f, 0.0f);
				}
				currCardId = viewId;
				selectCardAnimation(view, 0.0f, -20.0f);
			}
		}
	}

	private void selectCardAnimation(View view, float fromYDelta, float toYDelta) {
		TranslateAnimation anim = new TranslateAnimation(0, 0, fromYDelta,
				toYDelta);
		anim.setDuration(200);
		anim.setFillAfter(true);
		view.startAnimation(anim);
	}

	public void dealCards() {

		//
		// Get a random entry from the hashtable.
		//
		Object[] keys = htCards.keySet().toArray();

		Integer key = -1;

		/*
		 * p2Cards[0] = htCards.get(2130837594); p2Cards[1] =
		 * htCards.get(2130837590); p2Cards[2] = htCards.get(2130837586);
		 * p2Cards[3] = htCards.get(2130837582);
		 * 
		 * htCards.remove(2130837594); htCards.remove(2130837590);
		 * htCards.remove(2130837586); htCards.remove(2130837582);
		 */

		for (int i = 0; i < 4; i++) {

			// p1 cards

			while (!htCards.containsKey(key)) {
				key = (Integer) keys[new Random().nextInt(keys.length)];
				if (htCards.containsKey(key)) {
					imgBtnsP1[i].setBackgroundResource(key);
					p1Cards[i] = htCards.get(key);
					htCards.remove(key);
					break;
				}
			}

			// p2 cards
			while (!htCards.containsKey(key)) {
				key = (Integer) keys[new Random().nextInt(keys.length)];
				if (htCards.containsKey(key)) {
					p2Cards[i] = htCards.get(key);
					htCards.remove(key);
					break;
				}
			}

			// p3 cards
			while (!htCards.containsKey(key)) {
				key = (Integer) keys[new Random().nextInt(keys.length)];
				if (htCards.containsKey(key)) {
					p3Cards[i] = htCards.get(key);
					htCards.remove(key);
					break;
				}
			}

			// p4 cards
			while (!htCards.containsKey(key)) {
				key = (Integer) keys[new Random().nextInt(keys.length)];
				if (htCards.containsKey(key)) {
					p4Cards[i] = htCards.get(key);
					htCards.remove(key);
					break;
				}
			}
		}

		String fifthCard = null;
		while (!htCards.containsKey(key)) {
			key = (Integer) keys[new Random().nextInt(keys.length)];
			if (htCards.containsKey(key)) {
				fifthCard = htCards.get(key);
				htCards.remove(key);
				break;
			}
		}
		switch (delitel) {
		case 3:
			p1Cards[4] = fifthCard;
			imgBtnsP1[4].setBackgroundResource(key);
			imgViewP2C1.setVisibility(View.INVISIBLE);
			imgViewP3C1.setVisibility(View.INVISIBLE);
			imgViewP4C1.setVisibility(View.INVISIBLE);
			break;
		case 1:
			p2Cards[4] = fifthCard;
			imgBtnsP1[4].setEnabled(false);
			imgBtnsP1[4].setVisibility(View.INVISIBLE);
			imgViewP2C1.setVisibility(View.INVISIBLE);
			imgViewP4C1.setVisibility(View.INVISIBLE);
			break;
		case 2:
			p3Cards[4] = fifthCard;
			imgBtnsP1[4].setEnabled(false);
			imgBtnsP1[4].setVisibility(View.INVISIBLE);
			imgViewP2C1.setVisibility(View.INVISIBLE);
			imgViewP3C1.setVisibility(View.INVISIBLE);
			break;
		case 0:
			p4Cards[4] = fifthCard;
			imgBtnsP1[4].setEnabled(false);
			imgBtnsP1[4].setVisibility(View.INVISIBLE);
			imgViewP3C1.setVisibility(View.INVISIBLE);
			imgViewP4C1.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}
		Log.d("delitel", String.format("%d", delitel));

		// find and show card 2

		int found = -1; // flag that tells if 2 is found
		for (int i = 0; i < 4; i++) {
			if (p1Cards[i].contains("cardclubs2")) {
				found = 1;
				break;
			}
			
			if (p2Cards[i].contains("cardclubs2")) {
				imgViewP2C5.setBackgroundResource(R.drawable.cardclubs2);
				found = 1;
				break;
			}
			if (p3Cards[i].contains("cardclubs2")) {
				imgViewP3C5.setBackgroundResource(R.drawable.cardclubs2);
				found = 1;
				break;
			}
			if (p4Cards[i].contains("cardclubs2")) {
				imgViewP4C5.setBackgroundResource(R.drawable.cardclubs2);
				found = 1;
				break;
			}
		}

		// check in fifth card
		if (found == -1) {
			switch (delitel) {
			case 0:
				imgViewP2C5.setBackgroundResource(R.drawable.cardclubs2);
				break;
			case 1:
				imgViewP3C5.setBackgroundResource(R.drawable.cardclubs2);
				break;
			case 2:
				imgViewP4C5.setBackgroundResource(R.drawable.cardclubs2);
				break;

			default:
				break;
			}
		}

	}

	public void setup() {
		tvs[0] = (TextView) findViewById(R.id.textView1);
		tvs[1] = (TextView) findViewById(R.id.textView2);
		tvs[2] = (TextView) findViewById(R.id.textView3);
		tvs[3] = (TextView) findViewById(R.id.textView4);

		Typeface custom_font = Typeface.createFromAsset(getAssets(),
				"fonts/Quicksand-BoldItalic.otf");

		for (int i = 0; i < 4; i++) {
			tvs[i].setTypeface(custom_font);
		}

		imgBtnsP1[0] = (ImageButton) findViewById(R.id.imageButton01);
		imgBtnsP1[1] = (ImageButton) findViewById(R.id.imageButton02);
		imgBtnsP1[2] = (ImageButton) findViewById(R.id.imageButton03);
		imgBtnsP1[3] = (ImageButton) findViewById(R.id.imageButton04);
		imgBtnsP1[4] = (ImageButton) findViewById(R.id.imageButton05);
		for (int i = 0; i < 5; i++) {
			imgBtnIds[i] = imgBtnsP1[i].getId();
		}

		imgViewP2C1 = (ImageView) findViewById(R.id.imageViewP2C1);
		imgViewP3C1 = (ImageView) findViewById(R.id.imageViewP3C1);
		imgViewP4C1 = (ImageView) findViewById(R.id.imageViewP4C5);

		imgViewP2C5 = (ImageView) findViewById(R.id.imageViewP2C5);
		imgViewP3C5 = (ImageView) findViewById(R.id.imageViewP3C5);
		imgViewP4C5 = (ImageView) findViewById(R.id.imageViewP4C1);

	}

	public void getCardList() {
		final R.drawable drawableResources = new R.drawable();
		final Class<R.drawable> c = R.drawable.class;
		final Field[] fields = c.getDeclaredFields();

		for (int i = 0, max = fields.length; i < max; i++) {
			final int resourceId;
			try {
				resourceId = fields[i].getInt(drawableResources);
			} catch (Exception e) {
				continue;
			}

			String s = getResources().getResourceName(resourceId);
			if (s.contains("card") && !s.contains("back")) {
				cardList.add(resourceId);
				htCards.put(resourceId, s.substring(34));
			}
		}
		System.out.println(htCards.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
