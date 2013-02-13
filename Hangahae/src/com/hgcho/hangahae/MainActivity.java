package com.hgcho.hangahae;

import java.security.MessageDigest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.LoggingBehavior;

public class MainActivity extends Activity {
	private static final String APP_ID = "550523868299964";
	private static final String TAG = "Hangahae.MainActivity";
    static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/friends?access_token=";
    TextView textInstructionsOrLink;
    Button buttonLoginLogout;
    Session.StatusCallback statusCallback = new SessionStatusCallback();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLoginLogout = (Button)findViewById(R.id.FBLoginButton);
        //textInstructionsOrLink = (TextView)findViewById(R.id.instructionsOrLink);

        //Settings.addLoggingBehavior(com.facebook.LoggingBehaviors.INCLUDE_ACCESS_TOKENS);

        // Add code to print out the key hash 
        /*
        try {
        	PackageInfo info = getPackageManager().getPackageInfo("com.hgcho.hangahae", PackageManager.GET_SIGNATURES);
        	for (Signature signature : info.signatures){
        		MessageDigest md = MessageDigest.getInstance("SHA");
        		md.update(signature.toByteArray());
        		Log.d("KeyHash: ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
        	}
        } catch (Exception e) {} */
        
        
        Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(this);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
            }
        }

        updateView();
    }

    @Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }

    private void updateView() {
        Session session = Session.getActiveSession();
        if (session.isOpened()) {
            //textInstructionsOrLink.setText(URL_PREFIX_FRIENDS + session.getAccessToken());
            buttonLoginLogout.setText("Logout");
            buttonLoginLogout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogout(); }
            });
            
            // make new Activity
        } else {
            //textInstructionsOrLink.setText(R.string.instructions);
            buttonLoginLogout.setText("Login");
            buttonLoginLogout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogin(); }
            });
        }
    }

    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, true, statusCallback);
        }
    }

    private void onClickLogout() {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            updateView();
        }
    }
}
