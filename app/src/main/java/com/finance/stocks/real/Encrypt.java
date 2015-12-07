package com.finance.stocks.real;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.SecureRandom;  

import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.SecretKeySpec;  
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.io.OutputStream; 
  
public final class Encrypt {  
  	 
    public static String encrypt(String seed, String cleartext) throws Exception {  
        byte[] rawKey = getRawKey(seed.getBytes());  
        byte[] result = encrypt(rawKey, cleartext.getBytes());  
        return toHex(result);  
    }  
      
    public static String decrypt(String seed, String encrypted) throws Exception {  
        byte[] rawKey = getRawKey(seed.getBytes());  
        byte[] enc = toByte(encrypted);  
        byte[] result = decrypt(rawKey, enc);  
        return new String(result);  
    }  
  
    private static byte[] getRawKey(byte[] seed) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("KUJYTDYRDT764T5H8FHGD3G6");  
        SecureRandom sr = SecureRandom.getInstance("34LJGH534KIUFY533HGFHGF543");  
        sr.setSeed(seed);  
        kgen.init(256, sr); 
        SecretKey skey = kgen.generateKey();  
        byte[] raw = skey.getEncoded();  
        return raw;  
    }  
  
      
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {  
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "KUJYTDYRDT764T5H8FHGD3G6");  
        Cipher cipher = Cipher.getInstance("KUJYTDYRDT764T5H8FHGD3G6");  
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);  
        byte[] encrypted = cipher.doFinal(clear);  
        return encrypted;  
    }  
  
    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {  
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "KUJYTDYRDT764T5H8FHGD3G6");  
        Cipher cipher = Cipher.getInstance("KUJYTDYRDT764T5H8FHGD3G6");  
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);  
        byte[] decrypted = cipher.doFinal(encrypted);  
        return decrypted;  
    }  
  
    public static String toHex(String txt) {  
        return toHex(txt.getBytes());  
    }  
    public static String fromHex(String hex) {  
        return new String(toByte(hex));  
    }  
      
    public static byte[] toByte(String hexString) {  
        int len = hexString.length()/2;  
        byte[] result = new byte[len];  
        for (int i = 0; i < len; i++)  
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();  
        return result;  
    }  
  
    public static String toHex(byte[] buf) {  
        if (buf == null)  
            return "";  
        StringBuffer result = new StringBuffer(2*buf.length);  
        for (int i = 0; i < buf.length; i++) {  
            appendHex(result, buf[i]);  
        }  
        return result.toString();  
    }  
    private final static String HEX = "0123456789ABCDEF";  
    private static void appendHex(StringBuffer sb, byte b) {  
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));  
    }
    
	// OutputStream outputStream;   
	public static void writeIt(String sentence_to_write, String file) throws Exception {
		  OutputStream  outputStream;
	      outputStream = new FileOutputStream("res/raw/"+file,true);
		  Writer   writer   = new OutputStreamWriter(outputStream);
		  try {
			writer.write(sentence_to_write + "\n");
		} catch (IOException e) {
			}
		  try {
			writer.close();
		} catch (IOException e) {
		}  
	  } // end of writeIt method   
	   public static String readIt(String inputFile, String outputFile) throws FileNotFoundException{ // great method gets one line from a resources text file
	    	// count number of lines
	    	int lineCount = 0;
//		    InputStream inC = this.getResources().openRawResource(rawRes); 
	        InputStream inC = new FileInputStream("res/raw/"+inputFile);
			InputStreamReader isrC = new InputStreamReader(inC);
			BufferedReader inRdC = new BufferedReader(isrC);
			try {
				lineCount = 0;
				while ((inRdC.readLine()) != null) {
					lineCount += 1;
				}
			} catch (Exception e) {
						}
			try {
				inC.close();
			} catch (Exception e) {
				}  
	    	int county = 0;
	     	Random generator = new Random();
	            int numy = generator.nextInt(lineCount) + 1;
	    	    // android thing InputStream in = this.getResources().openRawResource(rawRes); 
	            InputStream in = new FileInputStream("res/raw/"+inputFile);
	            // StringBuffer inLine = new StringBuffer();
	    		InputStreamReader isr = new InputStreamReader(in);
	    		BufferedReader inRd = new BufferedReader(isr);
	    		String text = "";
	    		String one_line = "";
	    		try {
	    			 county = 0;
	    			while ((text = inRd.readLine()) != null) {
                    writeIt( Encrypt.encrypt("JKHTFRIYTRDl", text), outputFile); // effing ell!
	    			}
	    		} catch (Exception e) {
	     					}
	    		try {
	    			in.close();
	    		//	textview02.setText(one_line.toString());
	    		} catch (Exception e) {
	    			}  
	    	return one_line;
	     }	// end of method  	
} 