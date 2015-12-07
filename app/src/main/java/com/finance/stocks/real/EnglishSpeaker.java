package com.finance.stocks.real; 

import java.io.BufferedReader;   
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;

import com.finance.stocks.real.VoiceRecognition;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class EnglishSpeaker extends Activity implements OnInitListener  {
	public EnglishSpeaker(){ 
	}
ArrayList<String> nu = new ArrayList<String>();
HashSet hs = new HashSet();

long TimeStart; 
long TimeEnd;
int countQs, countAs;
int secondcountQs, secondcountAs;
int toggler;
Button button02, button03, button05, button06, button17 ;	
ImageButton button01, secondbutton;
TextView textview01,textview02,textview03,textview04,textview05,textview06 ;
TextToSpeech _tts; 
String theLastPartofSentence, secondtheLastPartofSentence;
boolean _ttsActive = false;  
String what_recogniser_heard = "";
String CID = "";
String newRecognisedString;
String newTestString;
String tWord; 
String theWord, theWord2; 
String secondtheWord, secondtheWord2; 
boolean sound_count = false ;
boolean langCount = true;
ArrayList<String> ar = new ArrayList<String>();
ArrayList<String> secondar = new ArrayList<String>();
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TimeStart = System.currentTimeMillis();
        textview01 = (TextView) findViewById(R.id.TextView01);   
       textview02 = (TextView) findViewById(R.id.TextView02);  
       textview03 = (TextView) findViewById(R.id.TextView03);  
       textview04 = (TextView) findViewById(R.id.TextView04);  
  //     textview05 = (TextView) findViewById(R.id.TextView05);  
       textview06 = (TextView) findViewById(R.id.TextView06); 
//to get the message back from recogniser - yes, I checked, we do.
       Intent intent = getIntent(); 
       intent.getExtras();
       what_recogniser_heard = intent.getStringExtra("key1"); // voice_match from recogniser
       CID = intent.getStringExtra("key2"); // PID (what you said)
 // here is the answers main output***********************************      
       if (what_recogniser_heard != null) {
       textview02.setText("You said: "+what_recogniser_heard); // this displays what the recogniser heard
       String answer = testOccurencesOfOneSubStringInAnother(what_recogniser_heard, CID);
       textview04.setText(answer);
       if (CID.length()>1){
       textview03.setText("Correct is: " + CID);
       }
                        } 
       button01 =  (ImageButton) findViewById(R.id.Button01);  
       button02 =  (Button) findViewById(R.id.Button02);  
       button03 =  (Button) findViewById(R.id.Button03);
       button05 =  (Button) findViewById(R.id.Button05);
       button06 =  (Button) findViewById(R.id.Button06);
       button17 =  (Button) findViewById(R.id.Button17);
       secondbutton = (ImageButton) findViewById(R.id.SecondButton);
       secondbutton.setOnClickListener(new View.OnClickListener() {// this button gets next word
           public void onClick(View view) {
        	   toggler += 1;
        	   if (toggler % 2 == 0) {
        		   secondbutt01();
        		 } else {
        			 secondbutt02();
        		 }
        	   }
       });  // end button01 listener  
       button01.setOnClickListener(new View.OnClickListener() {// this button gets next word
           public void onClick(View view) {
        	   toggler += 1;
        	   if (toggler % 2 == 0) {
        		   butt01();
        		 } else {
        			 butt02();
        		 }
        	   }
       });  // end button01 listener
       button02.setOnClickListener(new View.OnClickListener() { // this gets the answer and reads it
           public void onClick(View view) {
        	   countAs += 1;
        	   if (theLastPartofSentence!=null){
               textview03.setText(theLastPartofSentence); 
               if (sound_count){
               _tts.speak(" " + theLastPartofSentence,TextToSpeech.QUEUE_ADD,null);
               }
        	   } else {
        		   textview02.setText(" "); 
     		 //    textview02.setText("  You are clicking away for nothing.");   
           }}  
       });  // end button02 listener
 // end button01 listener
    button03.setOnClickListener(new View.OnClickListener() { // this goes off to voice recogniser
        public void onClick(View view) { // go to voice recognition
        	if (theLastPartofSentence!=null){
        	Intent intent = new Intent(EnglishSpeaker.this,VoiceRecognition.class);
            intent.putExtra("key1", theLastPartofSentence);
            intent.putExtra("key2", "from EnglishSpeaker");
            startActivity(intent);
            finish(); 
        	} else {
     		   textview02.setText(" ");
 //    		   textview02.setText(" No point in clicking yet");
        	}
     	   }
    });  // end button02 listener
    button17.setOnClickListener(new View.OnClickListener() { // this goes off look at market apps
   	    public void onClick(View view) { 
   	   	 try { // collect simple phone / screen / OS, - nothing personal analyse if I get any spare time.
   	   		  TimeEnd = System.currentTimeMillis();
   	   	  	Display display = getWindowManager().getDefaultDisplay(); 
   	   		  String TimeElapsed = "Time"+String.valueOf(Math.round((TimeEnd - TimeStart)/1000));
   	   		  String buts = "Q"+String.valueOf(countQs)+"A"+String.valueOf(countAs);
   	   	  	String str1 = "BRAND" + Build.BRAND;	 // Build.MODEL
   	   	  	String str2 = "MODEL" + Build.MODEL; 
   	   	  	String str3 = "PROCESSOR" + Build.CPU_ABI; 
   	   	  	String str4 = "LOCALE" + Locale.getDefault().toString(); 
   	   	  	String str5 = "VERSION" + Build.VERSION.RELEASE;
   	   	  	int widthint = display.getWidth(); 
   	   	  	String str7 = "Width" + String.valueOf(widthint);
   	   	  	int heightint = display.getHeight(); 
   	   	  	String str8 = "Height" + String.valueOf(heightint); 
   	   	  	String str10 = "Business English real"; 
   	   	   	String infos = URLEncoder.encode(str10,"UTF-8");
   	   	  	String ids = URLEncoder.encode(str1+str2+str3+str4+str5+str7+str8, "UTF-8"); 
   	    	  String timer = URLEncoder.encode(TimeElapsed+buts, "UTF-8"); 
   	 //   	   	String ids = str1+str2+str3+str4+str5+str7+str8;
   	 //   	   	String infos = str10;
   	 //   	   	String timer = TimeElapsed+buts; 

   	System.out.println("http://besteverapps.appspot.com/?owner="+ids+"&infos="+infos+"&timer="+timer); 
//   	    	  Toast.makeText(getApplicationContext(), "http://besteverapps.appspot.com/?owner="+ids+"&infos="+infos+"&timer="+timer,Toast.LENGTH_LONG).show(); 
   	    	  Intent intent = new Intent(Intent.ACTION_VIEW); intent.setData 
   	   	  	(Uri.parse("http://besteverapps.appspot.com/?owner="+ids+"&infos="+infos+"&timer="+timer));
   	    	  startActivity(intent); 
   	   	 } catch(Exception e){
   	 Toast.makeText(getApplicationContext(), "could not get to market",Toast.LENGTH_LONG).show();    		 
   	    	 }
   	     	  }
   	  }); // end of button 17 listener

    button05.setOnClickListener(new View.OnClickListener() {// this button sets the sound
        public void onClick(View view) {
     	if(!sound_count){
     	   sound_count = true; 
  Toast.makeText(getApplicationContext(), "sound on",Toast.LENGTH_LONG).show();

     	  _tts.speak(" sound on ",TextToSpeech.QUEUE_FLUSH,null);
     	} else {
     	     Toast.makeText(getApplicationContext(), "sound off",Toast.LENGTH_LONG).show();
     		sound_count = false;	
     		_tts.stop();
     	}

            }
        });
    button06.setOnClickListener(new View.OnClickListener() {// this button gets whole list in strings
        public void onClick(View view) {
          	Intent intent = new Intent(EnglishSpeaker.this,TextViews.class);
             startActivity(intent);
            }
        }); 
    _tts = new TextToSpeech(getApplicationContext(), this); // speech init: this does not get called till the end of onCreate regardless of where it is
  butt01(); 
   } // end of on create

  //************************************************************************************


    String[] recognisedSplitValues;
    String[] testSplitValues;
    String sameWords, result =   " ";
    int countOfAnswerWords;
    int countOfMatches = 0;
    void butt02() {
    	   countAs += 1;
    	   if (theLastPartofSentence!=null){
           textview03.setText(theLastPartofSentence); 
           if (sound_count){
           _tts.speak(theLastPartofSentence,TextToSpeech.QUEUE_ADD,null);
           }
    	   } else {
    		   textview02.setText(" "); 
 		 //    textview02.setText("  You are clicking away for nothing.");   
       }
    }
    void secondbutt02() {
 	   secondcountAs += 1;
 	   if (secondtheLastPartofSentence!=null){
        textview03.setText(secondtheLastPartofSentence); 
        if (sound_count){
        _tts.speak(secondtheLastPartofSentence,TextToSpeech.QUEUE_ADD,null);
        }
 	   } else {
 		   textview02.setText(" "); 
		 //    textview02.setText("  You are clicking away for nothing.");   
    }
 }

    void butt01() {
    	try {
    	   countQs += 1;
    	   theWord2 = getWord();
    	    if(ar.contains(theWord2)){
    	    theWord2 = getWord();  
    	                   }
    	    if(ar.contains(theWord2)){
    	        theWord2 = getWord();  
     	                       }
    	    if(ar.contains(theWord2)){
    	        theWord2 = getWord();  
    	                       }
    	        if(ar.contains(theWord2)){
   	            theWord2 = getWord();  
    	                           }
    	        if(ar.contains(theWord2)){
    	            theWord2 = getWord();  
    	                           }
        	    if(ar.contains(theWord2)){
        	        theWord2 = getWord();  
         	                       }
        	    if(ar.contains(theWord2)){
        	        theWord2 = getWord();  
        	                       }
        	        if(ar.contains(theWord2)){
       	            theWord2 = getWord();  
        	                           }
        	        if(ar.contains(theWord2)){
        	            theWord2 = getWord();  
        	                           }
            	    if(ar.contains(theWord2)){
            	        theWord2 = getWord();  
             	                       }
            	    if(ar.contains(theWord2)){
            	        theWord2 = getWord();  
            	                       }
            	        if(ar.contains(theWord2)){
           	            theWord2 = getWord();  
            	                           }
            	        if(ar.contains(theWord2)){
            	            theWord2 = getWord();  
            	                           }
    	        if(ar.contains(theWord2)){
    	            theWord2 = getWord();  
    	                           }
 if(ar.contains(theWord2)){
//Toast.makeText(getApplicationContext(), "get another" + theWord2,Toast.LENGTH_SHORT).show();
theWord2 = getWord();  
 //Toast.makeText(getApplicationContext(), "got another" + theWord2,Toast.LENGTH_SHORT).show();
                }    
theWord = theWord2;
theWord = theWord.replace("q1","a").replace("q2","e").replace("q3","i")
.replace("q4","o").replace("q5","u").replace("q6","!").replace("q7","A")
.replace("q8","E").replace("q9","I").replace("q10","O").replace("q11","U");  
           int theIndex = theWord.indexOf("!");
           theLastPartofSentence = theWord.substring(theIndex+1, theWord.length()); 
           String theFirstPart = theWord.substring(0, theIndex);
           textview02.setText(theFirstPart);
           if (sound_count){
              _tts.speak(theFirstPart,TextToSpeech.QUEUE_FLUSH,null); 
           }
              textview03.setText(" ");
              textview04.setText(" ");    
              ar.add(theWord2);
    	} catch(Exception e){
    		textview02.setText(" moving on ");	
    	}
       }
    void secondbutt01() {
    	try {
    	   secondcountQs += 1;
    	   secondtheWord2 = secondgetWord();
    	    if(secondar.contains(secondtheWord2)){
    	    secondtheWord2 = secondgetWord();  
    	                   }
    	    if(secondar.contains(secondtheWord2)){
    	        secondtheWord2 = secondgetWord();  
     	                       }
    	    if(secondar.contains(secondtheWord2)){
    	        secondtheWord2 = secondgetWord();  
    	                       }
    	        if(secondar.contains(secondtheWord2)){
   	            secondtheWord2 = secondgetWord();  
    	                           }
    	        if(secondar.contains(secondtheWord2)){
    	            secondtheWord2 = secondgetWord();  
    	                           }
        	    if(secondar.contains(secondtheWord2)){
        	        secondtheWord2 = secondgetWord();  
         	                       }
        	    if(secondar.contains(secondtheWord2)){
        	        secondtheWord2 = secondgetWord();  
        	                       }
        	        if(secondar.contains(secondtheWord2)){
       	            secondtheWord2 = secondgetWord();  
        	                           }
        	        if(secondar.contains(secondtheWord2)){
        	            secondtheWord2 = secondgetWord();  
        	                           }
            	    if(secondar.contains(secondtheWord2)){
            	        secondtheWord2 = secondgetWord();  
             	                       }
            	    if(secondar.contains(secondtheWord2)){
            	        secondtheWord2 = secondgetWord();  
            	                       }
            	        if(secondar.contains(secondtheWord2)){
           	            secondtheWord2 = secondgetWord();  
            	                           }
            	        if(secondar.contains(secondtheWord2)){
            	            secondtheWord2 = secondgetWord();  
            	                           }
    	        if(secondar.contains(secondtheWord2)){
    	            secondtheWord2 = secondgetWord();  
    	                           }
 if(secondar.contains(secondtheWord2)){
//Toast.makeText(getApplicationContext(), "get another" + secondtheWord2,Toast.LENGTH_SHORT).show();
secondtheWord2 = secondgetWord();  
 //Toast.makeText(getApplicationContext(), "got another" + secondtheWord2,Toast.LENGTH_SHORT).show();
                }    
secondtheWord = secondtheWord2;
secondtheWord = secondtheWord.replace("q1","a").replace("q2","e").replace("q3","i")
.replace("q4","o").replace("q5","u").replace("q6","!").replace("q7","A")
.replace("q8","E").replace("q9","I").replace("q10","O").replace("q11","U");  
           int theIndex = secondtheWord.indexOf("!");
           secondtheLastPartofSentence = secondtheWord.substring(theIndex+1, secondtheWord.length()); 
           String theFirstPart = secondtheWord.substring(0, theIndex);
           textview02.setText(theFirstPart);
           if (sound_count){
              _tts.speak(theFirstPart,TextToSpeech.QUEUE_FLUSH,null); 
           }
              textview03.setText(" ");
              textview04.setText(" ");    
              ar.add(secondtheWord2);
    	} catch(Exception e){
    		textview02.setText(" moving on ");	
    	}
       }
    
    
    protected String testOccurencesOfOneSubStringInAnother(String recognisedString, String testString){
    	// sticking try catch round the whole thing
    	try {
    		if(!recognisedString.equals(" ")){ // to capture when just a space sent from recogniser
    	newRecognisedString = recognisedString.replaceAll("[,.;]","").replaceAll("[-]"," ");
    	newTestString = testString.replaceAll("[,.;]","").replaceAll("[-]"," ");
    	try {
    	 recognisedSplitValues = newRecognisedString.toLowerCase().split(" ");   
    	 testSplitValues = newTestString.toLowerCase().split(" ");   
    	 countOfAnswerWords = testSplitValues.length;
    	} catch(Exception e){
    	}
    	 if (recognisedSplitValues.length!=0 || recognisedSplitValues!=null || testSplitValues.length!=0 || testSplitValues!=null  )
    	//	System.out.println("recognisedSplitValues " +recognisedSplitValues[0] + " "  +recognisedSplitValues[1]);
    	//	System.out.println("recognisedSplitValues " +testSplitValues[0] + " " + testSplitValues[1]);

    		for (int i = 0;i<testSplitValues.length;i++){
    			for (int j = 0;j<recognisedSplitValues.length;j++){
    			    if (testSplitValues[i].equals(recognisedSplitValues[j])){
    			//    	System.out.println("testSplitValues " + testSplitValues[i]);
    			//    	sameWords += testSplitValues[i];
    					countOfMatches += 1;
    		//			System.out.println(countOfMatches + " out of " + countOfAnswerWords + " correct words were: " + sameWords);       
    			    }
    			} // end of inner for loop
    		} // end of outer for loop

    	  result = String.valueOf(countOfMatches) + " out of " + String.valueOf(countOfAnswerWords);
    		} // from if at start that looks for a space sent from recogniser
    		} catch(Exception e) {
          return "try again";  // should not get here
    	} 
    	 return result;
    }  // end of method testOccurencesOfOneSubStringInAnother
//******************************************************************************************
    @Override
	protected void onStart() {
		super.onStart();
	}
 // file i/o stuff  
    int testy = 0;
    int numy = 0; 
    int numberLines = 2758; // needs to be one less than no of lines in file // TODO
    int numberPlusOne = numberLines + 1 ;
    String secondgetWord(){
    	String one_line = "";
    	try {
 	// testy += 1;
    	Random generator = new Random(); 
  //      int numy = generator.nextInt(numberLines) + 1;
  	 int numy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);
   // 	Log.d("grapherrrrrrrr", "grapher is "+numy + " "+nu.size());
  	 // numy = testy ;
    	  if(nu.contains(String.valueOf(numy))){
    		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
    		  numy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);   
    		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
    		                   } 
    	  if(nu.contains(String.valueOf(numy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  numy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(numy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  numy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(numy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  numy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(numy))){
    		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
    		  numy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);   
    		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
    		                   } 
    	  if(nu.contains(String.valueOf(numy))){
    		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
    		  numy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);   
    		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
    		                   } 
    	  if(nu.contains(String.valueOf(numy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  secondnumy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(numy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  numy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(numy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  numy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(numy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  numy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(numy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  numy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(numy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  numy = numberPlusOne -(numberLines - (generator.nextInt(numberLines) + 1)*(generator.nextInt(numberLines) + 1)/numberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
        nu.add(String.valueOf(numy)); // remove duplicates
        hs.addAll(nu);
        nu.clear();
        nu.addAll(hs);
	    InputStream in = this.getResources().openRawResource(R.raw.secondwordlist); 
		StringBuffer inLine = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader inRd = new BufferedReader(isr);
		String text = "";
	//	String one_line = "";
		try {
			int county = 0;
			while ((text = inRd.readLine()) != null) {
				county += 1;
//				inLine.append(county + " " + text);
//				inLine.append("\n");
				if (county==numy){
				one_line = text;}
			}
		} catch (IOException e) {
					}
		try {
			in.close();
	//		textview02.setText(one_line.toString());
		} catch (IOException e) {

			}  
    	} catch(Exception e) {

    	}
		return one_line.trim();

    }
// secondgetword statr
    int secondtesty = 0;
    int secondnumy = 0;
    int secondnumberLines = 71; // needs to be one less than no of lines in file // TODO
    int secondnumberPlusOne = secondnumberLines + 1 ;
    String getWord(){
    	String one_line = "";
    	try {
 	// secondtesty += 1;
    	Random generator = new Random(); 
  //      int secondnumy = generator.nextInt(secondnumberLines) + 1;
  	 int secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);
   // 	Log.d("grapherrrrrrrr", "grapher is "+secondnumy + " "+nu.size());
  	 // secondnumy = secondtesty ;
    	  if(nu.contains(String.valueOf(secondnumy))){
    		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
    		  secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);   
    		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
    		                   } 
    	  if(nu.contains(String.valueOf(secondnumy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(secondnumy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(secondnumy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(secondnumy))){
    		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
    		  secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);   
    		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
    		                   } 
    	  if(nu.contains(String.valueOf(secondnumy))){
    		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
    		  secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);   
    		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
    		                   } 
    	  if(nu.contains(String.valueOf(secondnumy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(secondnumy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(secondnumy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(secondnumy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(secondnumy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
    	  if(nu.contains(String.valueOf(secondnumy))){
  		    //Toast.makeText(getApplicationContext(), "get another" + ranNum,Toast.LENGTH_SHORT).show();
  		  secondnumy = secondnumberPlusOne -(secondnumberLines - (generator.nextInt(secondnumberLines) + 1)*(generator.nextInt(secondnumberLines) + 1)/secondnumberLines);   
  		    //Toast.makeText(getApplicationContext(), "got another" + ranNum,Toast.LENGTH_SHORT).show();
  		                   } 
        nu.add(String.valueOf(secondnumy)); // remove duplicates
        hs.addAll(nu);
        nu.clear();
        nu.addAll(hs);
	    InputStream in = this.getResources().openRawResource(R.raw.wordlist); 
		StringBuffer inLine = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader inRd = new BufferedReader(isr);
		String text = "";
	//	String one_line = "";
		try {
			int county = 0;
			while ((text = inRd.readLine()) != null) {
				county += 1;
//				inLine.append(county + " " + text);
//				inLine.append("\n");
				if (county==secondnumy){
				one_line = text;}
			}
		} catch (IOException e) {
					}
		try {
			in.close();
	//		textview02.setText(one_line.toString());
		} catch (IOException e) {

			}  
    	} catch(Exception e) {

    	}
		return one_line.trim();

    }

    public void onInit(int status) { 
        if (status == TextToSpeech.SUCCESS) {
         _tts.setLanguage(Locale.US);
 } else {
  Toast.makeText(getApplicationContext(), "You need text-to-speech switched on or installed",Toast.LENGTH_LONG).show();
 }
        // this gets said when returning from recogniser, it always fires from onCreate
        if (what_recogniser_heard!=null){ // this gets the what_recogniser_heard from recogniser, if there is one, or CID (the answer)
        	   if (sound_count){
        	_tts.speak("You said: " + what_recogniser_heard + " , the answer was: " + CID ,TextToSpeech.QUEUE_FLUSH,null);
        	   }
        	   }
        } 
    @Override 
    public void onPause(){ 
            super.onPause(); 
    } 
    @Override 
    public void onResume(){ 
            super.onResume();
            
           // Create our text to speech object. 
      //      _tts = new TextToSpeech(getApplicationContext(), this); 
    } 
    @Override 
    public void onDestroy(){ 
            super.onDestroy(); 
             
                  } 
    @Override 
    public void onStop(){ 
            super.onStop();  

                  }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
       MenuInflater inflater = getMenuInflater();  
       inflater.inflate(R.menu.menu, menu);
        return true;
    }
    /**
     * Called right before your activity's option menu is displayed.
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {  
  
        return true;  
    } 
    /**
     * Called when a menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){  
        switch (item.getItemId()) {  
         case R.id.exit:    
    		finish();
            break;
        case R.id.email:
      	Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:rodsit@gmail.com"));
      	intent.putExtra("subject", "Hello Bill, Android developer");
      	intent.putExtra("body", "Dear Bill, I like finance and.... " );
      	startActivity(intent); 
    		break;
        case R.id.accent:
        if(langCount==true){
        _tts.setLanguage(Locale.UK);
        langCount = false;
               }
        else {
        _tts.setLanguage(Locale.US);
        langCount = true;
               }  
         break;

    }  
        return true;  
    } 
} // end class