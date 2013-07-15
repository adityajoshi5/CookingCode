package aditya.android.promptV1;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import aditya.android.promptV1.R;
import java.io.*;
import android.app.Activity;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GetAPrompt extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getaprompt);

		String random = String.valueOf((int) (Math.random() * 15) + 1);
		TextView lineView = (TextView) findViewById(R.id.lineNum);
		TextView promptV = (TextView) findViewById(R.id.promptView);
		lineView.setText(random + "");
		int max = 0;
		try {
			XmlResourceParser xrp = getResources().getXml(R.xml.lines);
			xrp.next();
			boolean isLine = false;
			// int eventType = xrp.getEventType();
			int eventType;
			while ((eventType = xrp.getEventType()) != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					if (xrp.getName().equals("Line"))
						max++;
				}
				eventType = xrp.next();
			}
		} catch (Exception ex) {
		}

		int randomInt = (int) (Math.random() * max);
		// String prompt = "";

		// prompt = getPrompt(randomInt);

		String prompt = "ERR : No Prompt Found";
		int no = 0;
		try {
			XmlResourceParser xrp = getResources().getXml(R.xml.lines);
			xrp.next();
			boolean isLine = false;
			// int eventType = xrp.getEventType();
			int eventType;
			while ((eventType = xrp.getEventType()) != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
				} else if (eventType == XmlPullParser.START_TAG) {
					no++;
				} else if (eventType == XmlPullParser.END_TAG) {
				} else if (eventType == XmlPullParser.TEXT) {
					String text = xrp.getText();
					if (no >= randomInt) {
						prompt = text;
						break;
					}

				}
				eventType = xrp.next();
			}
		} catch (Exception ex) {
		}

		// // // Use this to put in random phrase rather than the full string
		// String trimmed = prompt.trim();
		// int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
		//
		promptV.setText(prompt);

		Button exitBut = (Button) findViewById(R.id.button_exit);
		exitBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GetAPrompt.this.finish();
			}
		});

		Button getPromptBut = (Button) findViewById(R.id.button_getAnother);
		getPromptBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(GetAPrompt.this, GetAPrompt.class);
				startActivity(i);
				GetAPrompt.this.finish();
				// Is this the right approach?
				// How the hell would I know? I just works the way I want it to
				// be
			}
		});
	}

	public String getPrompt(int RandomInt) {
		String prompt = "ERR : No Prompt Found";
		int no = 0;
		try {
			XmlResourceParser xrp = getResources().getXml(R.xml.lines);
			xrp.next();
			boolean isLine = false;
			int eventType = xrp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {

				} else if (eventType == XmlPullParser.START_TAG) {
					if (xrp.getName() == "Line") {
						isLine = true;
						no++;
					}
				} else if (eventType == XmlPullParser.END_TAG) {
				} else if (eventType == XmlPullParser.TEXT) {
					String text = xrp.getText();
					if (no >= RandomInt)
						return text;
				}
				eventType = xrp.next();
			}
		} catch (Exception ex) {
			System.out.println(" Exaception Occured : - " + ex.toString());
			ex.printStackTrace();
			return null;
		}
		return prompt;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
