package com.finance.stocks.real;

import android.app.Activity;  
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TextViews extends Activity {
	TextView textview01;
	Button button01;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrolllist);
        textview01 = (TextView) findViewById(R.id.textview01);  
        
      //  textview01.  FastScrollEnabled(true);

    }
    public void onClick(View v) {
    	
    	textview01.setTextColor(Color.RED);
    	textview01.setText(Html.fromHtml("<b>" + "hhh" + "</b>" +  "<br />" + 
                "<large> is this large? should be a new line next </large>" + "<br>" + 
                "<font color=\"blue\">This is some text!</font>"));
      }     
    
}