package com.example.testapprealkit;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.DialogInterface;
import com.apprealkit.*;
import com.example.testapprealkit.R;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private boolean bDebug = true;

	// API information
	private String sHost = "192.168.10.102";
	private int iPort = 7777;
	private String sAppID = "080693ab-7123-4aee-b560-9f6a8a332a43"; //Change by your APP ID
	private String sAppsecret = "VORAI5D6yz"; //Change by your APP SECRET

	// Test data
	private String loginID = "1234";
	private String memberID = "5678";
	
	// List button
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private Button button8;
	private EditText roomText1;
	private EditText roomText2;
	private EditText roomText3;
	private EditText roomText4;

	private void registerAppRealKit() {
		// Setup the balancer
		Balancer.getInstance().setVerbose(bDebug);
		Balancer.getInstance().setProductID(sAppID, sAppsecret);

		// Add KitNode to handle the notification messages
		Balancer.getInstance().setKitNode(new KitNode() {
			@Override
			public void onKitOpen(String arg0, int arg1) {
				Log.d(TAG, "onKitOpen:" + arg0);
			}

			@Override
			public void onKitSent(String arg0, int arg1) {
				Log.d(TAG, "onKitSent:" + arg0);
			}

			@Override
			public void onKitMessage(String arg0, int arg1) {				
				final String sMessage = arg0;
				Log.d(TAG, "onKitMessage received:" + arg0);
				
				// Run UI on main thread								
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//Create Alert dialog
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
						
						// set title
						alertDialogBuilder
								.setTitle("NOTIFY RESPONSE");

						// set dialog message
						alertDialogBuilder
								.setMessage(sMessage)
								.setCancelable(false)
								.setPositiveButton(
										"Yes",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												dialog.cancel();
											}
										})
								.setNegativeButton(
										"No",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												dialog.cancel();
											}
										});
						
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder
								.create();

						// show it
						alertDialog.show();
					}
				});
			}

			@Override
			public void onKitClose(String arg0, int arg1) {
				Log.d(TAG, "onKitClose:" + arg0);
			}
		});

		// Connect to API server
		Balancer.getInstance().connect(sHost, iPort);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Register APPREALKIT
		registerAppRealKit();

		// Authenticate button
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Balancer.getInstance().authenticate(loginID, new KitEvent() {
					@Override
					public void onMessage(String arg0, int arg1) {
						// Run UI on main thread
						final String sMessage = arg0;
						Log.d(TAG, "authenticate response:" + sMessage);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								//Create Alert dialog
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
								
								// set title
								alertDialogBuilder
										.setTitle("AUTHENTICATE RESPONSE");

								// set dialog message
								alertDialogBuilder
										.setMessage(sMessage)
										.setCancelable(false)
										.setPositiveButton(
												"Yes",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														dialog.cancel();
													}
												})
										.setNegativeButton(
												"No",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														dialog.cancel();
													}
												});
								
								// create alert dialog
								AlertDialog alertDialog = alertDialogBuilder.create();

								// show it
								alertDialog.show();
							}
						});
					}
				});
			}
		});

		// Create room button
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				HashMap<String, String> arrProperties = new HashMap<String, String>();
				arrProperties.put("name", "Test ROOM");
				arrProperties.put("desc", "Test ROOM description");
				int iCapacity = 0;
				Balancer.getInstance().createRoom(loginID, arrProperties, iCapacity,
						new KitEvent() {
							@Override
							public void onMessage(String arg0, int arg1) {
								final String sMessage = arg0;
								Log.d(TAG, "createRoom response:" + sMessage);
								
								// Run UI on main thread								
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										//Create Alert dialog
										AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
										
										// set title
										alertDialogBuilder
												.setTitle("CREATE ROOM RESPONSE");

										// set dialog message
										alertDialogBuilder
												.setMessage(sMessage)
												.setCancelable(false)
												.setPositiveButton(
														"Yes",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.cancel();
															}
														})
												.setNegativeButton(
														"No",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.cancel();
															}
														});

										// create alert dialog
										AlertDialog alertDialog = alertDialogBuilder
												.create();

										// show it
										alertDialog.show();
									}
								});
							}
						});
			}
		});
		
		// Get user login button
		button6 = (Button) findViewById(R.id.button6);
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {				
				Balancer.getInstance().getUser(loginID, new KitEvent() {
							@Override
							public void onMessage(String arg0, int arg1) {
								final String sMessage = arg0;
								Log.d(TAG, "getUser response:" + sMessage);
																
								// Run UI on main thread								
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										//Parse JSON
										try {
											JSONObject jsonObject = new JSONObject(sMessage);
											JSONObject jsonContent = jsonObject.getJSONObject("obj");
											JSONArray jsonRooms = jsonContent.getJSONArray("rooms");
											Log.d(TAG, "jsonRooms:" + jsonRooms.toString());
											
											if(jsonRooms.length() > 0) {
												if(jsonRooms.isNull(0) == false) {
													roomText1.setText(jsonRooms.getString(0));
													roomText2.setText(jsonRooms.getString(0));
													roomText3.setText(jsonRooms.getString(0));
													roomText4.setText(jsonRooms.getString(0));
												}
												
												if(jsonRooms.isNull(1) == false) {													
													roomText2.setText(jsonRooms.getString(1));
												}
												
												if(jsonRooms.isNull(2) == false) {
													roomText3.setText(jsonRooms.getString(2));
												}
												
												if(jsonRooms.isNull(3) == false) {
													roomText4.setText(jsonRooms.getString(3));
												}
											}
										} catch (JSONException e) {											
											
										}
										
										//Create Alert dialog
										AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
										
										// set title
										alertDialogBuilder
												.setTitle("GET USER RESPONSE");

										// set dialog message
										alertDialogBuilder
												.setMessage(sMessage)
												.setCancelable(false)
												.setPositiveButton(
														"Yes",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.cancel();
															}
														})
												.setNegativeButton(
														"No",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.cancel();
															}
														});
										
										// create alert dialog
										AlertDialog alertDialog = alertDialogBuilder
												.create();

										// show it
										alertDialog.show();
									}
								});
							}
						});
			}
		});
		
		// Notify users button
		button8 = (Button) findViewById(R.id.button8);
		button8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {				
				String[] arrUserID = new String[2];
				arrUserID[0] = loginID; 
				arrUserID[1] = memberID;
				int iReason = 1;
				String sMessage = "Push broadcast for (" + arrUserID.toString() + ") at " + System.currentTimeMillis();
				boolean ignoreMeFlg = false;
				Balancer.getInstance().notifyListUser(arrUserID, iReason, sMessage, ignoreMeFlg, new KitEvent() {
							@Override
							public void onMessage(String arg0, int arg1) {
								final String sMessage = arg0;
								Log.d(TAG, "notifyListUser response:" + sMessage);								
							}
						});
			}
		});
		
		//Get the text data
		roomText1 = (EditText) findViewById(R.id.editText1);
		roomText2 = (EditText) findViewById(R.id.editText2);
		roomText3 = (EditText) findViewById(R.id.editText3);
		roomText4 = (EditText) findViewById(R.id.editText4);
		
		// Join ROOM button
		button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {		
				final String roomID = roomText1.getText().toString();
				if(roomID.isEmpty() == true) {
					return;
				}
				Balancer.getInstance().joinRoom(loginID, roomID, new KitEvent() {
							@Override
							public void onMessage(String arg0, int arg1) {
								final String sMessage = arg0;
								Log.d(TAG, "joinRoom response:" + sMessage);
								
								// Run UI on main thread								
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										//Create Alert dialog
										AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
										
										// set title
										alertDialogBuilder
												.setTitle("JOIN ROOM RESPONSE");

										// set dialog message
										alertDialogBuilder
												.setMessage(sMessage)
												.setCancelable(false)
												.setPositiveButton(
														"Yes",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.cancel();
															}
														})
												.setNegativeButton(
														"No",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.cancel();
															}
														});

										// create alert dialog
										AlertDialog alertDialog = alertDialogBuilder
												.create();

										// show it
										alertDialog.show();
									}
								});
							}
						});
			}
		});
		
		// Leave ROOM button
		button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {		
				final String roomID = roomText2.getText().toString();
				if(roomID.isEmpty() == true) {
					return;
				}
				Balancer.getInstance().leaveRoom(loginID, roomID, new KitEvent() {
							@Override
							public void onMessage(String arg0, int arg1) {
								final String sMessage = arg0;
								Log.d(TAG, "leaveRoom response:" + sMessage);
								
								// Run UI on main thread								
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										//Create Alert dialog
										AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
										
										// set title
										alertDialogBuilder
												.setTitle("LEAVE ROOM RESPONSE");

										// set dialog message
										alertDialogBuilder
												.setMessage(sMessage)
												.setCancelable(false)
												.setPositiveButton(
														"Yes",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.cancel();
															}
														})
												.setNegativeButton(
														"No",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.cancel();
															}
														});

										// create alert dialog
										AlertDialog alertDialog = alertDialogBuilder
												.create();

										// show it
										alertDialog.show();
									}
								});
							}
						});
			}
		});
		
		// Get ROOM button
		button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {		
				final String roomID = roomText3.getText().toString();
				if(roomID.isEmpty() == true) {
					return;
				}
				Balancer.getInstance().getRoom(roomID, new KitEvent() {
							@Override
							public void onMessage(String arg0, int arg1) {
								final String sMessage = arg0;
								Log.d(TAG, "getRoom response:" + sMessage);
								
								// Run UI on main thread								
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										//Create Alert dialog
										AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
										
										// set title
										alertDialogBuilder
												.setTitle("GET ROOM RESPONSE");

										// set dialog message
										alertDialogBuilder
												.setMessage(sMessage)
												.setCancelable(false)
												.setPositiveButton(
														"Yes",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.cancel();
															}
														})
												.setNegativeButton(
														"No",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.cancel();
															}
														});
										
										// create alert dialog
										AlertDialog alertDialog = alertDialogBuilder
												.create();

										// show it
										alertDialog.show();
									}
								});
							}
						});
			}
		});
		
		// Notify ROOM button
		button7 = (Button) findViewById(R.id.button7);
		button7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {				
				final String roomID = roomText4.getText().toString();
				if(roomID.isEmpty() == true) {
					return;
				}
				int iReason = 1;
				String sMessage = "Push from ROOM " + roomID + " at " + System.currentTimeMillis();
				boolean ignoreMeFlg = false;
				Balancer.getInstance().notifyInRoom(roomID, iReason, sMessage, ignoreMeFlg, new KitEvent() {
							@Override
							public void onMessage(String arg0, int arg1) {
								final String sMessage = arg0;
								Log.d(TAG, "notifyInRoom response:" + sMessage);								
							}
						});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
