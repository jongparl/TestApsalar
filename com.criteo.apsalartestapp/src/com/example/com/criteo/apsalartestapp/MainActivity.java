package com.example.com.criteo.apsalartestapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.mobileapptracker.MATEvent;
import com.mobileapptracker.MATEventItem;
import com.mobileapptracker.MobileAppTracker;
import com.apsalar.sdk.Apsalar;
import com.example.com.criteo.apsalartestapp.R;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;

import java.io.IOException;     
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.location.Location;
import android.net.wifi.WifiManager;

/**
 * @author Tester
 * @author Tester
 */
public class MainActivity extends Activity{
	   String m_My_Advertiser_ID = null;
	   String m_My_Conversion_Key = null;
	   
	   Button m_ViewHome = null;
	   Button m_ViewProduct = null;
	   Button m_ViewList = null;
	   Button m_Purchase = null;
	   Button m_ViewCart = null;
	   Button m_Search = null;
	   
	   // Criteo data object
	   CriteoDO m_CriteoDO = null;
	
	   
	   //Make date to YYYY-MM-DD type
	   SimpleDateFormat m_Criteo_Date;
	   Date m_Date1;
	   Date m_Date2;
	   
	   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
             
        //Create Criteo data object
        m_CriteoDO = new CriteoDO();
        
        // Assign Button resources  
        onButtonInit();
        
        // Starts session for Apsalar 
        // Start session means "viewHome" event of Criteo. It needn't implement separate "viewHome"event of Criteo
        Apsalar.startSession(getApplicationContext(), "appsupportus", "EIAagUkz");
        
        boolean isExistingUser = false;
        if (isExistingUser) {
           // mobileAppTracker.setExistingUser(true); 
        } else {
        	
        	//mobileAppTracker.setExistingUser(false);
        }      
        
        // Trigers start measuring
        //mobileAppTracker.measureSession();
        
        // Collect Google Play Advertising ID; REQUIRED for attribution of Android apps distributed via Google Play
        new Thread(new Runnable() {
            @Override public void run() {
                // See sample code at http://developer.android.com/google/play-services/id.html
                try {
                    Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
   
                   // mobileAppTracker.setGoogleAdvertisingId(adInfo.getId(), adInfo.isLimitAdTrackingEnabled());
                    
                    // Just for testing of getting and setting of ANDROID ID
                   // mobileAppTracker.setAndroidId(Secure.getString(getContentResolver(), Secure.ANDROID_ID));
                    
                    // Just for testing of getting and setting of DEVICE ID
                    String deviceId = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    //mobileAppTracker.setDeviceId(deviceId);
                    
                    // WifiManager objects may be null
                    try {
                        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                        //mobileAppTracker.setMacAddress(wm.getConnectionInfo().getMacAddress());
                    } catch (NullPointerException e) {
                    	//Please print log
                    }
                    
                } catch (IOException e) {
                    // Unrecoverable error connecting to Google Play services (e.g.,
                    // the old version of the service doesn't support getting AdvertisingId).
                    //mobileAppTracker.setAndroidId(Secure.getString(getContentResolver(), Secure.ANDROID_ID));
                } catch (GooglePlayServicesNotAvailableException e) {
                    // Google Play services is not available entirely.
                    //mobileAppTracker.setAndroidId(Secure.getString(getContentResolver(), Secure.ANDROID_ID));
                } catch (GooglePlayServicesRepairableException e) {
                    // Encountered a recoverable error connecting to Google Play services.
                    //mobileAppTracker.setAndroidId(Secure.getString(getContentResolver(), Secure.ANDROID_ID));
                } catch (NullPointerException e) {
                    // getId() is sometimes null
                    //mobileAppTracker.setAndroidId(Secure.getString(getContentResolver(), Secure.ANDROID_ID));
                }
            }
        }).start();
        
    }

    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 // Get source of open for app re-engagement
        //mobileAppTracker.setReferralSources(this);
        // MAT will not function unless the measureSession call is included
        //mobileAppTracker.measureSession();
        
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //Not implemented anything here
        
        return true;
    }
   
	 /**
     * This method initializes resources and Criteo retargeting events.
     * @param void
     * @return void
     */
	public void onButtonInit() {
        // Inflate the menu; this adds items to the action bar if it is present.
		
		// Start session is used instead of sending of Criteo ViewHome event
		m_ViewHome =(Button)findViewById(R.id.button1);
		m_ViewHome.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Criteo ViewHome And OPEN Event", "Button ViewHome");
				//Apsalar.event("HomeView");
				
			}
		});
		
		// Apsalar parameter setting for Criteo ViewProduct event
		m_ViewProduct =(Button)findViewById(R.id.button2);
		m_ViewProduct.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Criteo ViewProduct onClick", "Button2");
				Apsalar.event("viewProduct", "product", String.valueOf(166277));			
				
			}
		});
		
		// Apsalar parameters setting for Criteo ViewList event
		m_ViewList =(Button)findViewById(R.id.button3);
		m_ViewList.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Criteo ViewList onClick", "Button3");
				List <String> m_ProductItems = new ArrayList<String> ();
				// int[] height = new int[11];
				//String[] m_ProductItems = new String[3];
				
				// in case of using below then result is {"product":"[166277, 11262072, 6987408]"}
				m_ProductItems.add(String.valueOf(166277));
				m_ProductItems.add(String.valueOf(11262072));
				m_ProductItems.add(String.valueOf(6987408));
				
				// In case of using below then result is {"product":"[10166277, 11262072, 6987408]"}
				//m_ProductItems[0] = "10166277";
				//m_ProductItems[1] = "11262072";
				//m_ProductItems[2] = "6987408";
				String obj = m_ProductItems.toString();
				
				//Apsalar.event("viewListing", "product", m_ProductItems);
			
				//m_ProductItems.add("10166277");
				//m_ProductItems.add("11262072");
				//m_ProductItems.add("6987408");  
				JSONArray list = new JSONArray();
				list.put("12766277");
				list.put("11262072");
				list.put("6987408");
				Apsalar.event("viewListing", "viewListing", list);
				
				/*
				JSONObject args = new JSONObject();
				  try {
					args.put("product", (Object)obj);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Apsalar.eventJSON("viewListing", args);
				*/
			}
		});
		
		// Apsalar parameters setting for Criteo ViewBasket event
		m_ViewCart =(Button)findViewById(R.id.button4);
		m_ViewCart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Criteo ViewCart onClick", "Button4");
				
				//Build JSON objects 
				try {
					  JSONArray contents = new JSONArray();
					  JSONObject item1 = new JSONObject();

					  item1.put("id", "12345678");
					  item1.put("quantity", 1);
					  item1.put("price", 8.99);
					  //item1.put("currency", "USD");
					  contents.put(item1);

					  JSONObject item2 = new JSONObject();
					  item2.put("id", "23456789");
					  item2.put("quantity", 2);
					  item2.put("price", 15.99);
					  //item2.put("currency", "USD");
					  contents.put(item2);

					  JSONObject item3 = new JSONObject();
					  item3.put("id", "34567890");
					  item3.put("quantity", 3);
					  item3.put("price", 15.99);
					  //item3.put("currency", "USD");
					  contents.put(item3);

					  JSONObject args = new JSONObject();
					  args.put("currency", "USD");
					  args.put("product", contents);

					  //Apsalar.eventJSON("viewBasket", "currency", "USD", args);
					  Apsalar.event("viewBasket", args);
					}
					catch(JSONException e) {
					  android.util.Log.e("Now", "JSON Exception in cart");
					}
				
				
				//Apsalar.event("viewBasket");
				
				
			}
		});
		
		// Apsalar parameters setting for Criteo trackTransaction event
		m_Purchase =(Button)findViewById(R.id.button5);
		m_Purchase.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Criteo Purchase onClick", "Button5");
				
				// Event name???
				Apsalar.event("__iap__", "ps", "appsupportus", "pk", "6987408", "pcc", "USD", "pq", String.valueOf(2), "pp", String.valueOf(10.34), "r", String.valueOf(20.68) ); 
				
			}
		});
		
		// Apsalar parameters setting for Criteo Search event
		m_Search =(Button)findViewById(R.id.button6);
		m_Search.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				Log.d("Criteo Search onClick", "Button6");
				
				

			}
		});
		
		// MAT parameters setting for Criteo User Level event
				m_Search =(Button)findViewById(R.id.button7);
				m_Search.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						
						// TODO Auto-generated method stub
						Log.d("Criteo Search onClick", "Button7");
						
						

					}
				});
				
				
				// MAT parameters setting for Criteo User Status event
				m_Search =(Button)findViewById(R.id.button8);
				m_Search.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						
						// TODO Auto-generated method stub
						Log.d("Criteo Search onClick", "Button8");
						

					}
				});
				
				// MAT parameters setting for Criteo Achievement Unlocked event
				m_Search =(Button)findViewById(R.id.button9);
				m_Search.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						
						// TODO Auto-generated method stub
						Log.d("Criteo Search onClick", "Button9");
						

					}
				});
				
        return ;
    }
	

}
