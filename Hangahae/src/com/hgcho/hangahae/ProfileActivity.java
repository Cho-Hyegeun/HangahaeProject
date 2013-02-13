package com.hgcho.hangahae;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	
	Button btnProfileHangaOnOff;
	ImageView imgProfileImage;
	TextView textProfileName;
	EditText textProfileGreeting;
	
	@Override
	public void onCreate(Bundle savedInstanceState)  
	{
		super.onCreate(savedInstanceState);
		
		// TO DO : get FB profile data form MainActivity
		
		// TO DO : set activity properties
		
		// TO DO : set listeners to Button and EditText
		updateProfileView();
	}
	
	// life cycles
	/*
	 * onStart
	 * onResume
	 * onPause
	 * onStop
	 *  
	 */

	
	public void updateProfileView()
	{
 
	}

}
