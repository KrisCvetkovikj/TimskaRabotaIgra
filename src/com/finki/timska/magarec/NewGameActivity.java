package com.finki.timska.magarec;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewGameActivity extends Activity {

	final TextView[] tvs = new TextView[4];
	final ImageButton[] imgBtns = new ImageButton[4];
	Hashtable<Integer, String> htCards = new Hashtable<Integer, String>();

	String[] p1Cards = new String[4];
	String[] p2Cards = new String[4];
	String[] p3Cards = new String[4];
	String[] p4Cards = new String[4];

	Integer p1Letters = 0;
	Integer p2Letters = 0;
	Integer p3Letters = 0;
	Integer p4Letters = 0;
	
	String[] letters = { "", "M", "MA", "MAG", "MAGA", "MAGAR", "MAGARE", "MAGAREC" }; 

	final Integer[] imgBtnIds = new Integer[4];
	final ArrayList<Integer> cardList = new ArrayList<Integer>();

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

		setContentView(R.layout.activity_new_game);
		getActionBar().hide();

		setup();
		getCardList();
		dealCards();
		try {
			checkCurrentState();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkCurrentState() throws Exception {

		// index of winner, if any
		int indexWinner = -1;

		// check p1 cards; are they equal
		for (int i = 0; i < 3; i++) {
			
			if (p1Cards[i].charAt(p1Cards[i].length() - 1) != p1Cards[i + 1].charAt(p1Cards[i + 1].length() - 1)) {
				break;
			} else {
				indexWinner = 0;
			}
		}
		
		// check p2 cards; are they equal
		for (int i = 0; i < 3; i++) {
			if (p2Cards[i].charAt(p2Cards[i].length() - 1) != p2Cards[i + 1].charAt(p2Cards[i + 1].length() - 1)) {
				break;
			} else {
				indexWinner = 1;
			}
		}
		
		// check p3 cards; are they equal
		for (int i = 0; i < 3; i++) {
			if (p3Cards[i].charAt(p3Cards[i].length() - 1) != p3Cards[i + 1].charAt(p3Cards[i + 1].length() - 1)) {
				break;
			} else {
				indexWinner = 2;
			}
		}

		// check p4 cards; are they equal
		for (int i = 0; i < 3; i++) {
			if (p4Cards[i].charAt(p4Cards[i].length() - 1) != p4Cards[i + 1].charAt(p4Cards[i + 1].length() - 1)) {
				break;
			} else {
				indexWinner = 3;
			}
		}

		indexWinner = 1;
		
		if (indexWinner == 0) {
			Random r = new Random();
			
			// 0 == p2, 1 == p3, 2 == p4
			int indexLoser[] = { r.nextInt(100), r.nextInt(100), r.nextInt(100) };
			if (indexLoser[0] <= indexLoser[1] && indexLoser[0] <= indexLoser[2]) {
				p2Letters++;
			} else if (indexLoser[1] <= indexLoser[0] && indexLoser[1] <= indexLoser[2]) {
				p3Letters++;
			} else {
				p4Letters++;
			}
			wonRound(indexWinner);
			
		} else if (indexWinner == 1) {
			int playerTime = countPlayerTime();
			Thread.sleep(1000);
			int indexLoser[] = { new Random().nextInt(100), new Random().nextInt(100) };
			// indexLoser[0] == p3, indexLoser[1] == p4, indexWinner == p2
			if (indexLoser[0] <= indexLoser[1] && indexLoser[0] <= playerTime) {
				p3Letters++;
			} else if (indexLoser[1] <= indexLoser[0] && indexLoser[1] <= playerTime) {
				p4Letters++;
			} else {
				p1Letters++;
			}
			wonRound(indexWinner);
		} else if (indexWinner == 2) {
			int playerTime = countPlayerTime();
			Thread.sleep(1000);
			int indexLoser[] = { new Random().nextInt(100), new Random().nextInt(100) };
			// indexLoser[0] == p2, indexLoser[1] == p4, indexWinner == p3
			if (indexLoser[0] <= indexLoser[1] && indexLoser[0] <= playerTime) {
				p2Letters++;
			} else if (indexLoser[1] <= indexLoser[0] && indexLoser[1] <= playerTime) {
				p4Letters++;
			} else {
				p1Letters++;
			}
			wonRound(indexWinner);
		} else if (indexWinner == 3) {
			int playerTime = countPlayerTime();
			Thread.sleep(1000);
			int indexLoser[] = { new Random().nextInt(100), new Random().nextInt(100) };
			// indexLoser[0] == p2, indexLoser[1] == p3, indexWinner == p4
			if (indexLoser[0] <= indexLoser[1] && indexLoser[0] <= playerTime) {
				p2Letters++;
			} else if (indexLoser[1] <= indexLoser[0] && indexLoser[1] <= playerTime) {
				p3Letters++;
			} else {
				p1Letters++;
			}
			wonRound(indexWinner);
		} else if (indexWinner == -1) {
			// no one has 4 equal cards, keep playing
		}
	}
	
	public void wonRound(int indexWinner) {
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

		// Add the buttons
		builder.setPositiveButton(R.string.cont, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User clicked OK button
		           }
		       });
		
		// 2. Chain together various setter methods to set the dialog characteristics
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
			builder.setTitle("Player " + (indexWinner + 1) + " wins this round!");
		}
		
		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.show();
		getActionBar().hide();
	}
	
	public int countPlayerTime() {
		final Button tapBtn = (Button) findViewById(R.id.tapBtn);
		final ProgressBar pBar = (ProgressBar) findViewById(R.id.progBar);
		pBar.setMax(100);
		pBar.setProgress(0);
		tapBtn.setVisibility(1);
		pBar.setVisibility(1);
		
		// random time
		final int pTime = 12;
		
		tapBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (pBar.getProgress() == 100) {
					try {
						Thread.sleep(500);
						tapBtn.setVisibility(0);
						pBar.setVisibility(0);
						pBar.setProgress(9);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				} else {
					pBar.setProgress(pBar.getProgress() + (new Random().nextInt(15) + 5));
				}
			}
		});
		
		return pTime;
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
					selectCardAnimation(imgBtns[i], 0.0f, 0.0f);
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
        
       	p2Cards[0] = htCards.get(2130837588);
       	p2Cards[1] = htCards.get(2130837596);
       	p2Cards[2] = htCards.get(2130837592);
       	p2Cards[3] = htCards.get(2130837584);
       	
       	htCards.remove(2130837588);
       	htCards.remove(2130837596);
       	htCards.remove(2130837592);
       	htCards.remove(2130837584);
        
        for (int i = 0; i < 4; i++) {

        	// p1 cards
        	
        	while (!htCards.containsKey(key)) {
        		key = (Integer) keys[new Random().nextInt(keys.length)];
        		if (htCards.containsKey(key)) {
            		imgBtns[i].setBackgroundResource(key);
            		System.out.println(key + htCards.get(key));
                   	p1Cards[i] = htCards.get(key);
                   	htCards.remove(key);
                   	break;
           		}
        	}
        	
        	// p2 cards
        	/*while (!htCards.containsKey(key)) {
        		key = (Integer) keys[new Random().nextInt(keys.length)];
        		if (htCards.containsKey(key)) {
                   	p2Cards[i] = htCards.get(key);
                   	htCards.remove(key);
                   	break;
           		}
        	}*/
        	
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

		imgBtns[0] = (ImageButton) findViewById(R.id.imageButton01);
		imgBtns[1] = (ImageButton) findViewById(R.id.imageButton02);
		imgBtns[2] = (ImageButton) findViewById(R.id.imageButton03);
		imgBtns[3] = (ImageButton) findViewById(R.id.imageButton04);
		for (int i = 0; i < 4; i++) {
			imgBtnIds[i] = imgBtns[i].getId();
		}
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
