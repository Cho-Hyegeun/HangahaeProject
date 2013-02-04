package com.hgcho.hangahae;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

import com.facebook.*;
import com.facebook.widget.*;

public class MainActivity extends FragmentActivity {
	private static final String APP_ID = "550523868299964";
	private static final String TAG = "FBLoginFragment";
	private LoginButton fbLoginButton;
	private FBLoginFragment mainFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		//fbLoginButton = (LoginButton) findViewById(R.id.FBLoginButton);

		if (savedInstanceState == null) {
			// Add the fragment on initial activity setup
			mainFragment = new FBLoginFragment();
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();
		} else {
			// Or set the fragment from restored state info
			mainFragment = (FBLoginFragment)getSupportFragmentManager().findFragmentById(android.R.id.content);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) 
	{
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
		}
	}
	

}
