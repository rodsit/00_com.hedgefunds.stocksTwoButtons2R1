package com.finance.stocks.real;


import com.finance.stocks.real.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


public class VoiceRecognition extends Activity implements OnClickListener {
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private ListView mList;
    Button speakButton;
    Button backButton;
    String string_from_speaker, CID;   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recognition);
// get stuff from Speaker Activity
        Intent intent = getIntent();
        intent.getExtras();
        string_from_speaker = intent.getStringExtra("key1"); 
        CID = intent.getStringExtra("key2");
        speakButton = (Button) findViewById(R.id.btn_speak);
        backButton = (Button) findViewById(R.id.back_to_speaker);

        mList = (ListView) findViewById(R.id.list);
        // Check to see if a recognition activity is present
        try {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        
        if (activities.size() != 0) {
            speakButton.setOnClickListener(this);
            backButton.setOnClickListener(this);
        } else {
            speakButton.setEnabled(false);
            speakButton.setText("install google speech");
        	Intent intent2 = new Intent(VoiceRecognition.this,EnglishSpeaker.class);
            startActivity(intent2);
            // Toast.makeText(getApplicationContext(), " no google speech recognition installed, sorry", Toast.LENGTH_LONG).show();
            finish(); 
        }
        } catch(Exception e){
           	Intent intent2 = new Intent(VoiceRecognition.this,EnglishSpeaker.class);
            startActivity(intent2);
            Toast.makeText(getApplicationContext(), " no google speech recognition installed, sorry", Toast.LENGTH_LONG).show();
            finish();       	
        }
        try {
     startVoiceRecognitionActivity(); 
    } catch(Exception e){
       	Intent intent2 = new Intent(VoiceRecognition.this,EnglishSpeaker.class);
        startActivity(intent2);
        Toast.makeText(getApplicationContext(), " no google speech recognition installed, sorry", Toast.LENGTH_LONG).show();
        finish();       	
    }
    }
    public void onClick(View v) {
      	    if (v==speakButton){
      	        PackageManager pm = getPackageManager();
      	        List<ResolveInfo> activities = pm.queryIntentActivities(
      	                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
      	        if (activities.size() == 0) {
      	            speakButton.setEnabled(false);
      	            speakButton.setText("Install google speech"); 
    Toast.makeText(getApplicationContext(), "You need google speech recognition switched on or installed",Toast.LENGTH_LONG).show();

      	        } else {
            startVoiceRecognitionActivity(); } }
    	    else if (v==backButton){
               	Intent intent = new Intent(VoiceRecognition.this,EnglishSpeaker.class);
                intent.putExtra("key1", " ");
                intent.putExtra("key2", " ");
                startActivity(intent);
                finish();
  //  	    Intent intent = new Intent(getApplicationContext(),com.english.easy.opposites.EnglishSpeaker.class);  
  //  	    startActivity(intent); 
    	    }    }
     /**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            speakButton.setEnabled(false);
            speakButton.setText("install google speech");   
            Toast.makeText(getApplicationContext(), "You need google speech recognition switched on or installed",Toast.LENGTH_LONG).show();

        } else {
    	
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    } }
    /**
     * Handle the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    matches));
               if(!matches.isEmpty()) {
            	String what_recogniser_heard =  matches.get(0);
            	Intent intent = new Intent(VoiceRecognition.this,EnglishSpeaker.class);
                intent.putExtra("key1", what_recogniser_heard);
                intent.putExtra("key2", string_from_speaker);
                startActivity(intent);
                finish();              
               } else { // if nuttin is heard
                Intent intent = new Intent(VoiceRecognition.this,EnglishSpeaker.class);
                intent.putExtra("key1", "voice match was empty");
                intent.putExtra("key2", string_from_speaker); // sending PID back to where it originated because it will have lost it by now!!
                startActivity(intent);
                finish();           	   
            	       }
         }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
