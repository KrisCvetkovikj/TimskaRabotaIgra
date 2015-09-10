package com.finki.timska.magarec;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class NewGameActivity extends Activity {

	static final int PLAYER_TIME_REQUEST = 1010; // The request code

	final TextView[] tvs = new TextView[4];

	final ImageView[] imgViewsP1 = new ImageView[5];

	ImageView imgViewP2C1;
	ImageView imgViewP3C1;
	ImageView imgViewP4C1;

	ImageView imgViewP2C5;
	ImageView imgViewP3C5;
	ImageView imgViewP4C5;

	Hashtable<Integer, String> htCards;

	String[] p1Cards = new String[5];
	String[] p2Cards = new String[5];
	String[] p3Cards = new String[5];
	String[] p4Cards = new String[5];

	Integer p1Letters = 0;
	Integer p2Letters = 0;
	Integer p3Letters = 0;
	Integer p4Letters = 0;

	MediaPlayer mp;

	String[] letters = { "", "M", "MA", "MAG", "MAGA", "MAGAR", "MAGARE",
			"MAGAREC" };

	Integer[] imgBtnIds = new Integer[5];

	final DecimalFormat df = new DecimalFormat("0.00");

	// odlucuva koj e delitel t.e. posleden, igracot desno dobiva 5 karti (prv)
	// 0 = prv, 1 = vtor, ..., 3 = cetvrt

	int delitel = new Random().nextInt(4);

	// int delitel = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_new_game);

		setup();
		getCardList();
		try {
			dealCards();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		swiping();
		/*
		 * try { checkCurrentState(); } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

	Double playerTime = -1.0;

	// index of winner, if any
	int indexWinner = -1;

	public void checkCurrentState() throws Exception {

		// check p1 cards; are they equal

		int numQ = 0, numK = 0, numJ = 0, numA = 0;

		for (int i = 0; i < 4 + ((delitel == 3) ? 1 : 0); i++) {

			switch (p1Cards[i].charAt(p1Cards[i].length() - 1)) {
			case 'q':
				numQ++;
				break;
			case 'a':
				numA++;
				break;
			case 'j':
				numJ++;
				break;
			case 'k':
				numK++;
				break;
			default:
				break;
			}

		}
		if (numA == 4 || numQ == 4 || numK == 4 || numJ == 4)
			indexWinner = 0;

		numA = numQ = numK = numJ = 0;

		System.out.println("p2");

		// check p2 cards; are they equal
		for (int i = 0; i < 4 + ((delitel == 0) ? 1 : 0); i++) {

			switch (p2Cards[i].charAt(p2Cards[i].length() - 1
					- ((delitel != 0) ? 1 : 0))) {
			case 'q':
				numQ++;
				break;
			case 'a':
				numA++;
				break;
			case 'j':
				numJ++;
				break;
			case 'k':
				numK++;
				break;
			default:
				break;
			}
		}
		if (numA == 4 || numQ == 4 || numK == 4 || numJ == 4)
			indexWinner = 1;
		numA = numQ = numK = numJ = 0;

		System.out.println("p3");

		// check p3 cards; are they equal
		for (int i = 0; i < 4 + ((delitel == 1) ? 1 : 0); i++) {

			switch (p3Cards[i].charAt(p3Cards[i].length() - 1
					- ((delitel != 1) ? 1 : 0))) {
			case 'q':
				numQ++;
				break;
			case 'a':
				numA++;
				break;
			case 'j':
				numJ++;
				break;
			case 'k':
				numK++;
				break;
			default:
				break;
			}
		}
		if (numA == 4 || numQ == 4 || numK == 4 || numJ == 4)
			indexWinner = 2;
		numA = numQ = numK = numJ = 0;

		System.out.println("p4");

		// check p4 cards; are they equal
		for (int i = 0; i < 4 + ((delitel == 2) ? 1 : 0); i++) {

			switch (p4Cards[i].charAt(p4Cards[i].length() - 1
					- ((delitel != 2) ? 1 : 0))) {
			case 'q':
				numQ++;
				break;
			case 'a':
				numA++;
				break;
			case 'j':
				numJ++;
				break;
			case 'k':
				numK++;
				break;
			default:
				break;
			}

			/*
			 * if (p4Cards[i].charAt(p4Cards[i].length() - 1) != p4Cards[i + 1]
			 * .charAt(p4Cards[i + 1].length() - 1)) { indexWinner = -1; break;
			 * } else { indexWinner = 3; }
			 */
		}
		// System.out.println("p4" + numA + " " + numQ + " " + numJ + " " + numK
		// + " ");
		if (numA == 4 || numQ == 4 || numK == 4 || numJ == 4)
			indexWinner = 3;
		numA = numQ = numK = numJ = 0;

		System.out.println("indexW " + indexWinner);

		if (indexWinner == 0) {

			// 0 == p2, 1 == p3, 2 == p4
			for (int i = 0; i < 3; i++) {
				playersTimes.put("p" + (i + 2), (r.nextInt(100) + 1) / 10.0);
			}

			String losingPlayer = findMaxPlayerTime();
			addLetter(losingPlayer);
			wonRound(indexWinner);

		} else if (indexWinner > 0) {
			Double iPlayerTime = 0.00;
			for (int i = 2; i < 5; i++) {
				iPlayerTime = (r.nextInt(100) + 1) / 10.00;
				playersTimes.put("p" + i, iPlayerTime);
			}

			playersTimes.remove("p" + (indexWinner + 1));

			Log.d("playersTime", playersTimes.toString());

			Intent intent = new Intent(this, PlayerTimeActivity.class);
			startActivityForResult(intent, PLAYER_TIME_REQUEST);

		} else if (indexWinner == -1) {
			// no one has 4 equal cards, keep playing
		}
	}

	public void addLetter(String losingPlayer) {
		switch (losingPlayer) {
		case "p1":
			p1Letters++;
			break;
		case "p2":
			p2Letters++;
			break;
		case "p3":
			p3Letters++;
			break;
		case "p4":
			p4Letters++;
			break;
		default:
			break;
		}
	}

	//
	Random r = new Random();
	Map<String, Double> playersTimes = new HashMap<String, Double>();

	public String findMaxPlayerTime() {
		String maxTimedPlayer = "";
		Double maxTime = (Collections.max(playersTimes.values()));
		for (Entry<String, Double> entry : playersTimes.entrySet()) {
			if (entry.getValue() == maxTime) {
				maxTimedPlayer = entry.getKey();
				break;
			}
		}
		return maxTimedPlayer;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PLAYER_TIME_REQUEST) {
			if (resultCode == RESULT_OK) {
				try {
					playerTime = Double.parseDouble(data
							.getStringExtra("pTime"));
					Log.d("time", df.format(playerTime));

					playersTimes.put("p1", playerTime);
					String losingPlayer = findMaxPlayerTime();

					addLetter(losingPlayer);
					wonRound(indexWinner);
					playerTime = -1.0;
				} catch (NumberFormatException nfe) {
					Log.d("nfe", nfe.getMessage());
				}
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
				playerTime = -1.0;
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
						try {
							if (p1Letters == 7 || p2Letters == 7
									|| p3Letters == 7 || p1Letters == 7) {
								finish();
							} else {
								nextRound();
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		// 2. Chain together various setter methods to set the dialog
		// characteristics
		StringBuilder message = new StringBuilder();

		message.append("Score:\n");
		message.append("Player 1: " + letters[p1Letters] + "\n");
		message.append("Player 2: " + letters[p2Letters] + "\n");
		message.append("Player 3: " + letters[p3Letters] + "\n");
		message.append("Player 4: " + letters[p4Letters] + "\n");

		builder.setMessage(message.toString());

		if (p1Letters == 7 || p2Letters == 7 || p3Letters == 7
				|| p1Letters == 7) {
			switch (indexWinner) {
			case 0:
				builder.setTitle("Congratulations! You won!");
				break;
			case 1:
				builder.setTitle("Player 2 is the winner!");
				break;
			case 2:
				builder.setTitle("Player 3 is the winner!");
				break;
			case 3:
				builder.setTitle("Player 4 is the winner!");
				break;

			default:
				break;
			}
		} else {

			if (indexWinner == 0) {
				builder.setTitle("You won this round!");
			} else {
				builder.setTitle("Player " + (indexWinner + 1)
						+ " wins this round!");
			}
		}

		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void nextRound() throws InterruptedException {
		// recreate();

		imgViewP2C5.setBackgroundResource(R.drawable.cardback_red2);
		imgViewP3C5.setBackgroundResource(R.drawable.cardback_green2);
		imgViewP4C5.setBackgroundResource(R.drawable.cardback_blue2);
		numTurns2 = 0;
		if (delitel == 3)
			delitel = 0;
		else
			delitel++;

		getCardList();
		dealCards();
		indexWinner = -1;
		Log.d("p1letters", String.format("%d", p1Letters));
		Log.d("p2letters", String.format("%d", p2Letters));
		Log.d("p3letters", String.format("%d", p3Letters));
		Log.d("p4letters", String.format("%d", p4Letters));

		swiping();
		/*
		 * try { checkCurrentState(); } catch (Exception e) { // TODO
		 * Auto-generated catch block e.getLocalizedMessage(); }
		 */
	}

	public void dealCards() throws InterruptedException {

		//
		// Get a random entry from the hashtable.
		//
		Object[] keys = htCards.keySet().toArray();
		List<Object> toShuffle = new ArrayList<Object>();

		for (int i = 0; i < keys.length; i++) {
			toShuffle.add(keys[i]);
		}

		Collections.shuffle(toShuffle);

		keys = toShuffle.toArray();

		Integer key = -1;

		/*
		 * p3Cards[0] = htCards.get(2130837588); p3Cards[1] =
		 * htCards.get(2130837592); p3Cards[2] = htCards.get(2130837580);
		 * p3Cards[3] = htCards.get(2130837584);
		 * 
		 * htCards.remove(2130837588); htCards.remove(2130837592);
		 * htCards.remove(2130837580); htCards.remove(2130837584);
		 */

		for (int i = 0; i < 4; i++) {

			// p1 cards

			while (!htCards.containsKey(key)) {
				key = (Integer) keys[new Random().nextInt(keys.length)];
				if (htCards.containsKey(key)) {
					// imgViewsP1[i].setBackgroundResource(key);
					Thread.sleep(50);
					animCardPlacement(imgViewsP1[i], key);
					Thread.sleep(50);
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

		while (!htCards.containsKey(key)) {
			key = (Integer) keys[new Random().nextInt(keys.length)];
			if (htCards.containsKey(key)) {

				switch (delitel) {
				case 0:
					p2Cards[4] = htCards.get(key);
					imgViewP2C1.setVisibility(View.VISIBLE);
					imgViewsP1[4].setVisibility(View.INVISIBLE);
					imgViewP3C1.setVisibility(View.INVISIBLE);
					imgViewP4C1.setVisibility(View.INVISIBLE);
					break;
				case 1:
					p3Cards[4] = htCards.get(key);
					imgViewP3C1.setVisibility(View.VISIBLE);
					imgViewsP1[4].setVisibility(View.INVISIBLE);
					imgViewP2C1.setVisibility(View.INVISIBLE);
					imgViewP4C1.setVisibility(View.INVISIBLE);
					break;
				case 2:
					p4Cards[4] = htCards.get(key);
					imgViewP4C1.setVisibility(View.VISIBLE);
					imgViewsP1[4].setVisibility(View.INVISIBLE);
					imgViewP2C1.setVisibility(View.INVISIBLE);
					imgViewP3C1.setVisibility(View.INVISIBLE);
					break;
				case 3:
					p1Cards[4] = htCards.get(key);
					imgViewsP1[4].setBackgroundResource(key);
					imgViewP2C1.setVisibility(View.INVISIBLE);
					imgViewP3C1.setVisibility(View.INVISIBLE);
					imgViewP4C1.setVisibility(View.INVISIBLE);
					break;

				default:
					break;
				}

				htCards.remove(key);
				break;
			}
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

		try {
			for (int i = 0; i < 4; i++) {
				Log.d("p1cards", p1Cards[i]);
			}
			for (int i = 0; i < 4; i++) {
				Log.d("p2cards", p2Cards[i]);
			}
			for (int i = 0; i < 5; i++) {
				Log.d("p3cards", p3Cards[i]);
			}
			for (int i = 0; i < 4; i++) {
				Log.d("p4cards", p4Cards[i]);
			}
		} catch (NullPointerException e) {
			e.getLocalizedMessage();
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

		imgViewsP1[0] = (ImageView) findViewById(R.id.imageView01);
		imgViewsP1[1] = (ImageView) findViewById(R.id.imageView02);
		imgViewsP1[2] = (ImageView) findViewById(R.id.imageView03);
		imgViewsP1[3] = (ImageView) findViewById(R.id.imageView04);
		imgViewsP1[4] = (ImageView) findViewById(R.id.imageView05);

		imgViewP2C1 = (ImageView) findViewById(R.id.imageViewP2C1);
		imgViewP3C1 = (ImageView) findViewById(R.id.imageViewP3C1);
		imgViewP4C1 = (ImageView) findViewById(R.id.imageViewP4C5);

		imgViewP2C5 = (ImageView) findViewById(R.id.imageViewP2C5);
		imgViewP3C5 = (ImageView) findViewById(R.id.imageViewP3C5);
		imgViewP4C5 = (ImageView) findViewById(R.id.imageViewP4C1);

		mp = MediaPlayer.create(getApplicationContext(),
				R.raw.cardswipe);
	}

	int numTurns2 = 0;

	public void swiping() {
		for (int i = 0; i < 4 + ((delitel == 3) ? 1 : 0); i++) {
			final int temp = i;
			imgViewsP1[temp].setOnTouchListener(new OnSwipeTouchListener() {

				public void onSwipeRight() {

					int id = getResources().getIdentifier(p1Cards[temp],
							"drawable", getPackageName());
					String cardName = getResources().getResourceEntryName(id);

					if (numTurns2 < 3) {

						if ("cardclubs2".equals(cardName)) {
							try {
								checkCurrentState();
							} catch (Exception e) {

							}
						} else {
							numTurns2++;
							swipeCardAnimation(imgViewsP1[temp]);
							switchCards(temp);
							passedCardAnimation(imgViewsP1[temp], temp);
							try {
								checkCurrentState();
							} catch (Exception e) {

							}
						}
					} else {
						if ("cardclubs2".equals(cardName)) {
							numTurns2 = 0;
							swipeCardAnimation(imgViewsP1[temp]);
							switchCards(temp);
							passedCardAnimation(imgViewsP1[temp], temp);
							try {
								checkCurrentState();
							} catch (Exception e) {

							}
						} else {
							numTurns2++;
							swipeCardAnimation(imgViewsP1[temp]);
							switchCards(temp);
							passedCardAnimation(imgViewsP1[temp], temp);
							try {
								checkCurrentState();
							} catch (Exception e) {

							}
						}
					}

					mp.start();
				}

				public boolean onTouch(View v, MotionEvent event) {
					return gestureDetector.onTouchEvent(event);
				}
			});
		}
	}

	private void swipeCardAnimation(View view) {
		Animation test = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.test);
		test.setFillAfter(true);
		view.startAnimation(test);
	}

	public void switchCards(Integer orderOfCard) {
		Random rCard = new Random();

		Integer p2RCard = rCard.nextInt(4 + ((delitel == 0) ? 1 : 0));
		while (p2RCard == null) {
			p2RCard = rCard.nextInt(4 + ((delitel == 0) ? 1 : 0));
		}
		while ("cardclubs2".equals(p2Cards[p2RCard])) {
			if (numTurns2 > 3) {
				imgViewP2C5.setBackgroundResource(R.drawable.cardback_red2);
				numTurns2 = 0;
				break;
			} else {
				numTurns2++;
				p2RCard = rCard.nextInt(4 + ((delitel == 0) ? 1 : 0));
			}
		}
		String p2Card = p2Cards[p2RCard];
		p2Cards[p2RCard] = p1Cards[orderOfCard];
		if (p2Cards[p2RCard].equals("cardclubs2")) {
			imgViewP2C5.setBackgroundResource(R.drawable.cardclubs2);
		}

		Integer p3RCard = rCard.nextInt(4 + ((delitel == 1) ? 1 : 0));
		while (p3RCard == null) {
			p3RCard = rCard.nextInt(4 + ((delitel == 1) ? 1 : 0));
		}
		while ("cardclubs2".equals(p3Cards[p3RCard])) {
			if (numTurns2 > 3) {
				imgViewP3C5.setBackgroundResource(R.drawable.cardback_green2);
				numTurns2 = 0;
				break;
			} else {
				numTurns2++;
				p3RCard = rCard.nextInt(4 + ((delitel == 1) ? 1 : 0));
			}
		}
		String p3Card = p3Cards[p3RCard];
		p3Cards[p3RCard] = p2Card;
		if (p3Cards[p3RCard].equals("cardclubs2")) {
			imgViewP3C5.setBackgroundResource(R.drawable.cardclubs2);
		}

		Integer p4RCard = rCard.nextInt(4 + ((delitel == 2) ? 1 : 0));
		while (p4RCard == null) {
			p4RCard = rCard.nextInt(4 + ((delitel == 2) ? 1 : 0));
		}
		while ("cardclubs2".equals(p4Cards[p4RCard])) {
			if (numTurns2 > 3) {
				imgViewP4C5.setBackgroundResource(R.drawable.cardback_blue2);
				numTurns2 = 0;
				break;
			} else {
				numTurns2++;
				p4RCard = rCard.nextInt(4 + ((delitel == 2) ? 1 : 0));
			}
		}
		String p4Card = p4Cards[p4RCard];
		p4Cards[p4RCard] = p3Card;
		if (p4Cards[p4RCard].equals("cardclubs2")) {
			imgViewP4C5.setBackgroundResource(R.drawable.cardclubs2);
		}

		p1Cards[orderOfCard] = p4Card;
	}

	public void passedCardAnimation(View view, Integer whichCard) {
		int id = getResources().getIdentifier(p1Cards[whichCard], "drawable",
				getPackageName());
		view.setBackgroundResource(id);
		Animation test = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_in);
		test.setFillAfter(true);
		view.startAnimation(test);
	}

	public void animCardPlacement(View view, Integer key) {
		view.setBackgroundResource(key);
		Animation test = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_in);
		test.setFillAfter(true);
		view.startAnimation(test);
	}

	public void getCardList() {
		final R.drawable drawableResources = new R.drawable();
		final Class<R.drawable> c = R.drawable.class;
		final Field[] fields = c.getDeclaredFields();

		htCards = new Hashtable<Integer, String>();

		for (int i = 0, max = fields.length; i < max; i++) {
			final int resourceId;
			try {
				resourceId = fields[i].getInt(drawableResources);
			} catch (Exception e) {
				continue;
			}

			String s = getResources().getResourceName(resourceId);
			if (s.contains("card") && !s.contains("back")) {
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
