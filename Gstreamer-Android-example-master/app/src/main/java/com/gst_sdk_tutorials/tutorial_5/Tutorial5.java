package com.gst_sdk_tutorials.tutorial_5;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.StrictMode;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import java.math.RoundingMode;
import java.net.*;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.lang.*;
import org.freedesktop.gstreamer.GStreamer;


public class Tutorial5 extends Activity implements SurfaceHolder.Callback {


    private native void nativeInit();     // Initialize native code, build pipeline, etc
    private native void nativeFinalize(); // Destroy pipeline and shutdown native code
    private native void nativePlay();     // Set pipeline to PLAYING
    private native void nativePause();    // Set pipeline to PAUSED
    private static native boolean nativeClassInit(); // Initialize native class: cache Method IDs for callbacks
    private native void nativeSurfaceInit(Object surface); // A new surface is available
    private native void nativeSurfaceFinalize(); // Surface about to be destroyed
    private long native_custom_data;      // Native code will use this to keep private data
    private boolean is_playing_desired;   // Whether the user asked to go to PLAYING
    private int position;                 // Current position, reported by native code                  //NEW
    private int duration;                 // Current clip duration, reported by native code             //NEW
    int combination = 2;
    boolean lanedetecton = true;
    private PowerManager.WakeLock wake_lock;
    String ip = "";
    boolean GrayImage = false;
    boolean feature = true;
    String keyboard ="";
    DecimalFormat df = new DecimalFormat("#.##");
    boolean fullscreen = true;
    double hogscale;
    int levelscount;
    int hoggroup;
    double hit;
    boolean graymode = false;
    private Button savenotebutton1;
    private SharedPreferences savednotes;
    private SharedPreferences sharedPref;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private EditText editText7;
    private EditText editText8;
    private EditText editText9;
    private EditText editText10;
    private EditText editText11;
    private EditText editText12;
    private Button clearbutton;
    private Spinner customspinner1;
    private Spinner customspinner2;
    private Spinner customspinner3;
    private Spinner customspinner4;
    private Spinner customspinner5;
    private Spinner customspinner6;
    private Button custombutton;
    private Button custombutton2;
    private Button custombutton3;
    private Button custombutton4;
    private Button custombutton5;
    private Button custombutton6;
    boolean processorgpu = true;
    String ServerIP = "131.155.71.79";
    private EditText hogtxt;
    private EditText levelstxt;
    private EditText hoggrtxt;
    private EditText hittxt;
    private Button IP;
    private Button processor;
    private Button graybut;
    private Spinner grayimage;
    int state = 3;
    private TabHost tabHost;
    private TabHost.TabSpec tabSpec;
    private Spinner inputchoice;
    private EditText foldername;
    private EditText serverip;
    private RadioButton ldfeature;
    private RadioButton fdfeature;
    private Button setip;
    private ImageButton play;
    private ImageButton pause;
    private ImageButton stop;
    private SurfaceView sv;
    private CheckBox HKcomb;
    private CheckBox LaneDetect;
    private CheckBox IPARcomb;
    private EditText yawangle;
    private EditText pitchangle;
    private EditText coefthetamax;
    private ImageButton hogscaleincr;
    private ImageButton levelscountincr;
    private ImageButton hoggrthresholdincr;
    private ImageButton hitincr;
    private ImageButton hogscaledecr;
    private ImageButton levelscountdecr;
    private ImageButton hoggrthresholddecr;
    private ImageButton hitdecr;
    private Button SetCD;
    private Button SetLD;
    private Double yawangledouble;
    private Double pitchangledouble;
    private Double coefthetamaxdouble;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize GStreamer and warn if it fails
        try {
            GStreamer.init(this);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setContentView(R.layout.main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        serverip = (EditText) findViewById(R.id.serverip);
        setip = (Button) findViewById(R.id.setip);
        foldername = (EditText) findViewById(R.id.sourcetxt);
        inputchoice = (Spinner)findViewById(R.id.spinner3);
        hogtxt = (EditText) findViewById(R.id.hogtext);
        levelstxt = (EditText) findViewById(R.id.levelstext);
        hoggrtxt = (EditText) findViewById(R.id.hoggrtext);
        hittxt = (EditText) findViewById(R.id.hittext);
        IP = (Button) findViewById(R.id.ipbutton);
        processor = (Button) findViewById(R.id.processorbut);
        graybut = (Button) findViewById(R.id.graybut);
        grayimage = (Spinner)findViewById(R.id.spinner2);
        savenotebutton1 = (Button) findViewById(R.id.savebutton);
        savednotes = getSharedPreferences("notes",MODE_PRIVATE);
        clearbutton = (Button) findViewById(R.id.clearbutton);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        sv = (SurfaceView) this.findViewById(R.id.surface_video);
        play = (ImageButton) this.findViewById(R.id.button_play);
        pause = (ImageButton) this.findViewById(R.id.button_pause);
        stop = (ImageButton) this.findViewById(R.id.button_stop);
        ldfeature = (RadioButton) findViewById(R.id.ldradio);
        fdfeature = (RadioButton) findViewById(R.id.fdradio);
        HKcomb = (CheckBox)findViewById(R.id.HK);
        LaneDetect = (CheckBox) findViewById(R.id.LaneDetect);
        IPARcomb = (CheckBox) findViewById(R.id.IPAR);
        yawangle = (EditText) findViewById(R.id.yawangle);
        pitchangle = (EditText) findViewById(R.id.pitchangle);
        coefthetamax = (EditText) findViewById(R.id.alphatext);
        hogscaleincr = (ImageButton) findViewById(R.id.hogincr);
        levelscountincr = (ImageButton) findViewById(R.id.levelsincr);
        hoggrthresholdincr = (ImageButton) findViewById(R.id.hoggrincr);
        hitincr = (ImageButton) findViewById(R.id.hitincr);
        hogscaledecr = (ImageButton) findViewById(R.id.hogdecr);
        levelscountdecr = (ImageButton) findViewById(R.id.levelsdecr);
        hoggrthresholddecr = (ImageButton) findViewById(R.id.hoggrdecr);
        hitdecr = (ImageButton) findViewById(R.id.hitdecr);
        SetCD = (Button) this.findViewById(R.id.FCWset);
        SetLD = (Button) this.findViewById(R.id.LDWset);
        editText1 = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);
        editText7 = (EditText) findViewById(R.id.editText7);
        editText8 = (EditText) findViewById(R.id.editText8);
        editText9 = (EditText) findViewById(R.id.editText9);
        editText10 = (EditText) findViewById(R.id.editText10);
        editText11 = (EditText) findViewById(R.id.editText11);
        editText12 = (EditText) findViewById(R.id.editText12);
        editText1.setText(savednotes.getString("tag", "Default Value"));
        editText2.setText(savednotes.getString("tag2", "Default Value"));
        editText3.setText(savednotes.getString("tag3", "Default Value"));
        editText4.setText(savednotes.getString("tag4", "Default Value"));
        editText5.setText(savednotes.getString("tag5", "Default Value"));
        editText6.setText(savednotes.getString("tag6", "Default Value"));
        editText7.setText(savednotes.getString("tag7", "Default Value"));
        editText8.setText(savednotes.getString("tag8", "Default Value"));
        editText9.setText(savednotes.getString("tag9", "Default Value"));
        editText10.setText(savednotes.getString("tag10", "Default Value"));
        editText11.setText(savednotes.getString("tag11", "Default Value"));
        editText12.setText(savednotes.getString("tag12", "Default Value"));
        custombutton = (Button) findViewById(R.id.customset);
        custombutton2 = (Button) findViewById(R.id.customset2);
        custombutton3 = (Button) findViewById(R.id.customset3);
        custombutton4 = (Button) findViewById(R.id.customset4);
        custombutton5 = (Button) findViewById(R.id.customset5);
        custombutton6 = (Button) findViewById(R.id.customset6);

        //Initialize the six customs spinners in the Advanced Tab
        customspinner1 = (Spinner)findViewById(R.id.customsp1);
        String[] customtype1 = new String[]{"Integer", "Double", "String", "Boolean"};
        ArrayAdapter<String> customadapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, customtype1);
        customspinner1.setAdapter(customadapter1);

        SharedPreferences sharedPref = getSharedPreferences("FileName",MODE_PRIVATE);
        int spinnerValue = sharedPref.getInt("userChoiceSpinner",-1);
        if(spinnerValue != -1)
            customspinner1.setSelection(spinnerValue);

        customspinner2 = (Spinner)findViewById(R.id.customsp2);
        String[] customtype2 = new String[]{"Integer", "Double", "String", "Boolean"};
        ArrayAdapter<String> customadapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, customtype2);
        customspinner2.setAdapter(customadapter2);

        SharedPreferences sharedPref2 = getSharedPreferences("FileName",MODE_PRIVATE);
        int spinnerValue2 = sharedPref2.getInt("userChoiceSpinner2",-1);
        if(spinnerValue2 != -1)
            customspinner2.setSelection(spinnerValue2);

        customspinner3 = (Spinner)findViewById(R.id.customsp3);
        String[] customtype3 = new String[]{"Integer", "Double", "String", "Boolean"};
        ArrayAdapter<String> customadapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, customtype3);
        customspinner3.setAdapter(customadapter3);

        SharedPreferences sharedPref3 = getSharedPreferences("FileName",MODE_PRIVATE);
        int spinnerValue3 = sharedPref3.getInt("userChoiceSpinner3",-1);
        if(spinnerValue3 != -1)
            customspinner3.setSelection(spinnerValue3);

        customspinner4 = (Spinner)findViewById(R.id.customsp4);
        String[] customtype4 = new String[]{"Integer", "Double", "String", "Boolean"};
        ArrayAdapter<String> customadapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, customtype4);
        customspinner4.setAdapter(customadapter4);
        SharedPreferences sharedPref4 = getSharedPreferences("FileName",MODE_PRIVATE);
        int spinnerValue4 = sharedPref4.getInt("userChoiceSpinner4",-1);
        if(spinnerValue4 != -1)
            customspinner4.setSelection(spinnerValue4);

        customspinner5 = (Spinner)findViewById(R.id.customsp5);
        String[] customtype5 = new String[]{"Integer", "Double", "String", "Boolean"};
        ArrayAdapter<String> customadapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, customtype5);
        customspinner5.setAdapter(customadapter5);
        SharedPreferences sharedPref5 = getSharedPreferences("FileName",MODE_PRIVATE);
        int spinnerValue5 = sharedPref5.getInt("userChoiceSpinner5",-1);
        if(spinnerValue5 != -1)
            customspinner5.setSelection(spinnerValue5);

        customspinner6 = (Spinner)findViewById(R.id.customsp6);
        String[] customtype6 = new String[]{"Integer", "Double", "String", "Boolean"};
        ArrayAdapter<String> customadapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, customtype6);
        customspinner6.setAdapter(customadapter6);
        SharedPreferences sharedPref6 = getSharedPreferences("FileName",MODE_PRIVATE);
        int spinnerValue6 = sharedPref6.getInt("userChoiceSpinner6",-1);
        if(spinnerValue6 != -1)
            customspinner6.setSelection(spinnerValue6);


        //Save and Clear Button
        savenotebutton1.setOnClickListener(saveButtonListener);
        clearbutton.setOnClickListener(clearButtonListener);


        //Setup of the Tab Host
        tabHost.setup();

        tabSpec = tabHost.newTabSpec("Lane Detector");
        tabSpec.setContent(R.id.LDW);
        tabSpec.setIndicator("Lane Detector");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Car Detector");
        tabSpec.setContent(R.id.FCW);
        tabSpec.setIndicator("Car Detector");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Advanced");
        tabSpec.setContent(R.id.Custom_settings);
        tabSpec.setIndicator("Advanced");
        tabHost.addTab(tabSpec);


        /*Changing the tab sends a message to the system, indicating which tab we chose
        The following two commands handle the communication.

        AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PLAY).setStream(AdasDebugApplication.Stream.LDW).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").build()).build();
        This command constructs a protocol buffers defined message according to the adasdebugapplication.proto file. For this case, we use the Pipeline_Config message.
        Similarly, we can use LDW_Config and FCW_Config messages in the same way.

        CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
        The above command converts the message to byte array and sends it to the CommunicationThread class, which establishes a TCP connection to transmit the message.
        We send three arguments to this method: the message byterray, the IP of the ZeroMQ server and the Connect button (to update the UI after connection attempt).
        */
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                AdasDebugApplication.Message messagetype;
                final String choice = inputchoice.getSelectedItem().toString();

                if (tabHost.getCurrentTab() == 0) {
                    if (state == 1) {
                        if (choice == "Camera") {
                            messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PLAY).setStream(AdasDebugApplication.Stream.LDW).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").build()).build();
                            CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                        } else if (choice == "Folder") {
                            messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PLAY).setStream(AdasDebugApplication.Stream.LDW).setSource(AdasDebugApplication.Source.FOLDER).setSourceFolder(foldername.getText().toString()).build()).build();
                            CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                        }
                    } else if (state == 2) {
                        if (choice == "Camera") {
                            messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PAUSE).setStream(AdasDebugApplication.Stream.LDW).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").build()).build();
                            CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                        } else if (choice == "Folder") {
                            messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PAUSE).setStream(AdasDebugApplication.Stream.LDW).setSource(AdasDebugApplication.Source.FOLDER).setSourceFolder(foldername.getText().toString()).build()).build();
                            CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                        }
                    } else if (state == 3) {
                        if (choice == "Camera") {
                            messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.STOP).setStream(AdasDebugApplication.Stream.LDW).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").build()).build();
                            CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                        } else if (choice == "Folder") {
                            messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.STOP).setStream(AdasDebugApplication.Stream.LDW).setSource(AdasDebugApplication.Source.FOLDER).setSourceFolder(foldername.getText().toString()).build()).build();
                            CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                        }
                    }
                } else if (tabHost.getCurrentTab() == 1) {
                    if (state == 1) {
                        if (choice == "Camera") {
                            messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PLAY).setStream(AdasDebugApplication.Stream.FCW).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").build()).build();
                            CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                        } else if (choice == "Folder") {
                            messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PLAY).setStream(AdasDebugApplication.Stream.FCW).setSource(AdasDebugApplication.Source.FOLDER).setSourceFolder(foldername.getText().toString()).build()).build();
                            CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                        }
                    } else if (state == 2) {
                        if (choice == "Camera") {
                            messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PAUSE).setStream(AdasDebugApplication.Stream.FCW).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").build()).build();
                            CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                        } else if (choice == "Folder") {
                            messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PAUSE).setStream(AdasDebugApplication.Stream.FCW).setSource(AdasDebugApplication.Source.FOLDER).setSourceFolder(foldername.getText().toString()).build()).build();
                            CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                        }
                    } else if (state == 3) {
                        if (choice == "Camera") {
                            messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.STOP).setStream(AdasDebugApplication.Stream.FCW).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").build()).build();
                            CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                        } else if (choice == "Folder") {
                            messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.STOP).setStream(AdasDebugApplication.Stream.FCW).setSource(AdasDebugApplication.Source.FOLDER).setSourceFolder(foldername.getText().toString()).build()).build();
                            CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                        }
                    }
                }
            }
        });


        //Initializing the Input drop down menu
        String[] items3 = new String[]{"Folder", "Camera"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        inputchoice.setAdapter(adapter3);
        String imagesource = "clips/lane_%d.png";
        foldername.setText(imagesource);
        serverip.setText(ServerIP);


        //Lane Detector and Car Detector radiobuttons in Advanced Settings Tab
        ldfeature.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fdfeature.isChecked()) {
                    fdfeature.setChecked(false);
                }
            }
        });

        fdfeature.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ldfeature.isChecked()) {
                    ldfeature.setChecked(false);
                }
            }
        });


        //Disabling the folder name textfield, if camera input is selected
        inputchoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (inputchoice.getSelectedItem().toString() == "Camera") {
                    foldername.setEnabled(false);
                } else {
                    foldername.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wake_lock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GStreamer tutorial 5");
        wake_lock.setReferenceCounted(false);


        //The OnClick listeners of the six custom commands in Advanced Tab.
        custombutton.setOnClickListener(customlistener1);
        custombutton2.setOnClickListener(customlistener2);
        custombutton3.setOnClickListener(customlistener3);
        custombutton4.setOnClickListener(customlistener4);
        custombutton5.setOnClickListener(customlistener5);
        custombutton6.setOnClickListener(customlistener6);

        //Play, pause and stop buttons listener
        play.setOnClickListener(playlistener);
        pause.setOnClickListener(pauselistener);
        stop.setOnClickListener(stoplistener);

        //By tapping the streaming surface, we invoke fullscreen mode.
        sv.setOnClickListener(surfacelistener);

        //Car Detection and Lane Detection Set buttons listener.
        SetCD.setOnClickListener(SetCDlistener);
        SetLD.setOnClickListener(SetLDlistener);

        processor.setText("GPU");
        graybut.setText("Colour");


        //Calculating the IP of the device and storing it to a variable
        IPCalculation();

        //Saving the ZeroMQ server IP
        setip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerIP = serverip.getText().toString();
            }
        });



        SurfaceHolder sh = sv.getHolder();
        sh.addCallback(this);


        String[] items2 = new String[]{"Colour", "Gray"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        grayimage.setAdapter(adapter2);

        yawangle.setText("0");
        pitchangle.setText("0.1");
        coefthetamax.setText("0.5");
        LaneDetect.setChecked(true);
        hogtxt.setText("1.12");
        levelstxt.setText("13");
        hoggrtxt.setText("0");
        hittxt.setText("1.4");
        HKcomb.setChecked(true);
        IPARcomb.setEnabled(false);


        df.setRoundingMode(RoundingMode.CEILING);


        //Functionality of the eight image buttons in Car Detection Tab
        processor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (processor.getText().toString() == "GPU") {
                    processor.setText("CPU");
                    processorgpu = false;
                } else {
                    processor.setText("GPU");
                    processorgpu = true;
                }
                AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setGrayInit(graymode).setProcessor(processorgpu).setHOGscaleInit(Double.parseDouble(hogtxt.getText().toString())).setLevelsCount(Integer.parseInt(levelstxt.getText().toString())).setHOGgroupThresholdInit(Integer.parseInt(hoggrtxt.getText().toString())).setHitThreshold(Double.parseDouble(hittxt.getText().toString())).build()).build();
                CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
            }
        });


        graybut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (graybut.getText().toString() == "Colour") {
                    graybut.setText("Gray");
                    graymode = true;
                } else {
                    graybut.setText("Colour");
                    graymode = false;
                }
                AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setGrayInit(graymode).setProcessor(processorgpu).setHOGscaleInit(Double.parseDouble(hogtxt.getText().toString())).setLevelsCount(Integer.parseInt(levelstxt.getText().toString())).setHOGgroupThresholdInit(Integer.parseInt(hoggrtxt.getText().toString())).setHitThreshold(Double.parseDouble(hittxt.getText().toString())).build()).build();
                CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
            }
        });



        hogscaleincr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hogscale = Double.parseDouble(hogtxt.getText().toString());
                hogscale = hogscale + hogscale * 0.11;
                hogtxt.setText(df.format(hogscale));
                AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setGrayInit(graymode).setProcessor(processorgpu).setHOGscaleInit(hogscale).setLevelsCount(Integer.parseInt(levelstxt.getText().toString())).setHOGgroupThresholdInit(Integer.parseInt(hoggrtxt.getText().toString())).setHitThreshold(Double.parseDouble(hittxt.getText().toString())).build()).build();
                CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);

            }
        });

        hogscaledecr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hogscale = Double.parseDouble(hogtxt.getText().toString());
                hogscale = hogscale - hogscale*0.11;
                hogtxt.setText(df.format(hogscale));
                AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setGrayInit(graymode).setProcessor(processorgpu).setHOGscaleInit(hogscale).setLevelsCount(Integer.parseInt(levelstxt.getText().toString())).setHOGgroupThresholdInit(Integer.parseInt(hoggrtxt.getText().toString())).setHitThreshold(Double.parseDouble(hittxt.getText().toString())).build()).build();
                CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP,IP);
            }
        });

        levelscountincr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                levelscount = Integer.parseInt(levelstxt.getText().toString());
                levelscount = levelscount + 1;
                levelstxt.setText(df.format(levelscount));
                AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setGrayInit(graymode).setProcessor(processorgpu).setHOGscaleInit(Double.parseDouble(hogtxt.getText().toString())).setLevelsCount(levelscount).setHOGgroupThresholdInit(Integer.parseInt(hoggrtxt.getText().toString())).setHitThreshold(Double.parseDouble(hittxt.getText().toString())).build()).build();
                CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP,IP);
            }
        });

        levelscountdecr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                levelscount = Integer.parseInt(levelstxt.getText().toString());
                levelscount = levelscount - 1;
                levelstxt.setText(df.format(levelscount));
                AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setGrayInit(graymode).setProcessor(processorgpu).setHOGscaleInit(Double.parseDouble(hogtxt.getText().toString())).setLevelsCount(levelscount).setHOGgroupThresholdInit(Integer.parseInt(hoggrtxt.getText().toString())).setHitThreshold(Double.parseDouble(hittxt.getText().toString())).build()).build();
                CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP,IP);
            }
        });

        hoggrthresholdincr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hoggroup = Integer.parseInt(hoggrtxt.getText().toString());
                hoggroup = hoggroup + 1;
                hoggrtxt.setText(df.format(hoggroup));
                AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setGrayInit(graymode).setProcessor(processorgpu).setHOGscaleInit(Double.parseDouble(hogtxt.getText().toString())).setLevelsCount(Integer.parseInt(levelstxt.getText().toString())).setHOGgroupThresholdInit(hoggroup).setHitThreshold(Double.parseDouble(hittxt.getText().toString())).build()).build();
                CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP,IP);
            }
        });

        hoggrthresholddecr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hoggroup = Integer.parseInt(hoggrtxt.getText().toString());
                hoggroup = hoggroup - 1;
                hoggrtxt.setText(df.format(hoggroup));
                AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setGrayInit(graymode).setProcessor(processorgpu).setHOGscaleInit(Double.parseDouble(hogtxt.getText().toString())).setLevelsCount(Integer.parseInt(levelstxt.getText().toString())).setHOGgroupThresholdInit(hoggroup).setHitThreshold(Double.parseDouble(hittxt.getText().toString())).build()).build();
                CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP,IP);
            }
        });

        hitincr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hit = Double.parseDouble(hittxt.getText().toString());
                hit = hit + 0.05;
                hittxt.setText(df.format(hit));
                AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setGrayInit(graymode).setProcessor(processorgpu).setHOGscaleInit(Double.parseDouble(hogtxt.getText().toString())).setLevelsCount(Integer.parseInt(levelstxt.getText().toString())).setHOGgroupThresholdInit(Integer.parseInt(hoggrtxt.getText().toString())).setHitThreshold(hit).build()).build();
                CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP,IP);
            }
        });

        hitdecr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hit = Double.parseDouble(hittxt.getText().toString());
                hit = hit - 0.05;
                hittxt.setText(df.format(hit));
                AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setGrayInit(graymode).setProcessor(processorgpu).setHOGscaleInit(Double.parseDouble(hogtxt.getText().toString())).setLevelsCount(Integer.parseInt(levelstxt.getText().toString())).setHOGgroupThresholdInit(Integer.parseInt(hoggrtxt.getText().toString())).setHitThreshold(hit).build()).build();
                CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP,IP);
            }
        });



        //Functionality of the two combination checkboxes in Lane Detection Tab: Hough + Kalman and IPM + Particle
        HKcomb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HKcomb.isChecked()) {
                    combination = 2;
                    IPARcomb.setEnabled(false);
                }else {
                    combination = 2;
                    IPARcomb.setEnabled(true);
                }
                System.out.println("Comb is:" + combination);
            }
        });


        IPARcomb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IPARcomb.isChecked()) {
                    combination = 1;
                    HKcomb.setEnabled(false);
                }else {
                    combination = 2;
                    HKcomb.setEnabled(true);
                }
                System.out.println("Comb is:" + combination);
            }
        });

        //Lane Detector checkbox in Lane Detector tab
        LaneDetect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LaneDetect.isChecked()) {
                    lanedetecton = true;
                }else {
                    lanedetecton = false;
                }
            }
        });


        //Connect button functionality
        IP.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("IP", "Address " + ip);

                AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.STOP).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").setStream(AdasDebugApplication.Stream.LDW).build()).build();
                CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP,IP);

            }
        });


        // Retrieve our previous state, or initialize it to default values - Heavily Modified
        if (savedInstanceState != null) {
            is_playing_desired = savedInstanceState.getBoolean("playing");
            position = savedInstanceState.getInt("position");
            duration = savedInstanceState.getInt("duration");
            Log.i("GStreamer", "Activity created with saved state:");
        } else {
            is_playing_desired = false;
            Log.i("GStreamer", "Activity created with no saved state:");
        }
        Log.i("GStreamer", "  playing:" + is_playing_desired + " position:" + position +
                " duration: " + duration);

        // Start with disabled buttons, until native code is initialized
        this.findViewById(R.id.button_play).setEnabled(false);
        this.findViewById(R.id.button_pause).setEnabled(false);

        nativeInit();


    }


    //Tag for saving the 12 text fields in Advanced Tab
    private void makeTag(String tag, int n){
        String or = savednotes.getString(tag, null);
        SharedPreferences.Editor preferencesEditor = savednotes.edit();
        if (n == 1) {
            preferencesEditor.putString("tag", tag);
        }else if (n == 2) {
            preferencesEditor.putString("tag2", tag);
        }else if (n == 3) {
            preferencesEditor.putString("tag3", tag);
        }else if (n == 4) {
            preferencesEditor.putString("tag4", tag);
        }else if (n == 5) {
            preferencesEditor.putString("tag5", tag);
        }else if (n == 6) {
            preferencesEditor.putString("tag6", tag);
        }else if (n == 7) {
            preferencesEditor.putString("tag7", tag);
        }else if (n == 8) {
            preferencesEditor.putString("tag8", tag);
        }else if (n == 9) {
            preferencesEditor.putString("tag9", tag);
        }else if (n == 10) {
            preferencesEditor.putString("tag10", tag);
        }else if (n == 11) {
            preferencesEditor.putString("tag11", tag);
        }else if (n == 12) {
            preferencesEditor.putString("tag12", tag);
        }
        preferencesEditor.commit();
    }


    //Tag for saving the 6 dropdown menus in Advanced Tab
    private void makeTag2(int userChoice, int n ){
        int userChoices = customspinner1.getSelectedItemPosition();
        SharedPreferences sharedPref = getSharedPreferences("FileName", 0);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        if (n == 1) {
            prefEditor.putInt("userChoiceSpinner", userChoice);
        }else if (n == 2) {
            prefEditor.putInt("userChoiceSpinner2", userChoice);
        }else if (n == 3) {
            prefEditor.putInt("userChoiceSpinner3", userChoice);
        }else if (n == 4) {
            prefEditor.putInt("userChoiceSpinner4", userChoice);
        }else if (n == 5) {
            prefEditor.putInt("userChoiceSpinner5", userChoice);
        }else if (n == 6) {
            prefEditor.putInt("userChoiceSpinner6", userChoice);
        }
        prefEditor.commit();
    }


    //Functionality of the Car Detector and Lane Detector Set buttons
    public OnClickListener SetCDlistener = new OnClickListener() {
        @Override
        public void onClick(View view) {
                    try {

                        hogscale = Double.parseDouble(hogtxt.getText().toString());
                        levelscount = Integer.parseInt(levelstxt.getText().toString());
                        hoggroup = Integer.parseInt(hoggrtxt.getText().toString());
                        hit = Double.parseDouble(hittxt.getText().toString());

                        String imagechoice = grayimage.getSelectedItem().toString();

                        if (imagechoice == "Colour") {
                            GrayImage = false;
                        }else if (imagechoice == "Gray" ) {
                            GrayImage = true;
                        }
                        hogscale = Double.parseDouble(hogtxt.getText().toString());
                        levelscount = Integer.parseInt(levelstxt.getText().toString());
                        hoggroup = Integer.parseInt(hoggrtxt.getText().toString());
                        hit = Double.parseDouble(hittxt.getText().toString());
                        graymode = GrayImage;

                        AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setGrayInit(GrayImage).setProcessor(processorgpu).setHOGscaleInit(hogscale).setLevelsCount(levelscount).setHOGgroupThresholdInit(hoggroup).setHitThreshold(hit).build()).build();
                        CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP,IP);
                        feature = false;



                    }catch (java.lang.Exception e) {
                        Log.i("Java.lang.Exception", "Occured " + e.toString());
                        setException(e.toString());
                    }
        }
    };

    public OnClickListener SetLDlistener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            try {

                yawangledouble = Double.parseDouble(yawangle.getText().toString());
                pitchangledouble = Double.parseDouble(pitchangle.getText().toString());
                coefthetamaxdouble = Double.parseDouble(coefthetamax.getText().toString());


                AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setDetectionCombination(combination).setPitchAngle(pitchangledouble).setYawAngle(yawangledouble).setCoefThetaMax(coefthetamaxdouble).build()).build();
                CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP,IP);

                feature = true;


            } catch (java.lang.Exception e) {
                Log.i("Java.lang.Exception", "Occured " + e.toString());
                setException(e.toString());
            }
        }
    };

    //Functionality of the 6 custom commands in Advanced Tab
    public OnClickListener customlistener1 = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ldfeature.isChecked()) {
                if (customspinner1.getSelectedItem().toString() == "Integer") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText1.getText().toString()).setType(AdasDebugApplication.Type.Int).setValue(editText2.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("LDW + Integer ");
                }else if (customspinner1.getSelectedItem().toString() == "Double") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText1.getText().toString()).setType(AdasDebugApplication.Type.Double).setValue(editText2.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("LDW + Double ");
                }else if (customspinner1.getSelectedItem().toString() == "String") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText1.getText().toString()).setType(AdasDebugApplication.Type.String).setValue(editText2.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("LDW + String ");
                }else if (customspinner1.getSelectedItem().toString() == "Boolean") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText1.getText().toString()).setType(AdasDebugApplication.Type.Boolean).setValue(editText2.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("LDW + Boolean");
                }
            }else if (fdfeature.isChecked()) {
                if (customspinner1.getSelectedItem().toString() == "Integer") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText1.getText().toString()).setType(AdasDebugApplication.Type.Int).setValue(editText2.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("FCW + Integer ");
                }else if (customspinner1.getSelectedItem().toString() == "Double") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText1.getText().toString()).setType(AdasDebugApplication.Type.Double).setValue(editText2.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("FCW + Double ");
                }else if (customspinner1.getSelectedItem().toString() == "String") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText1.getText().toString()).setType(AdasDebugApplication.Type.String).setValue(editText2.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("FCW + String ");
                }else if (customspinner1.getSelectedItem().toString() == "Boolean") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText1.getText().toString()).setType(AdasDebugApplication.Type.Boolean).setValue(editText2.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("FCW + Boolean");
                }
            }
        }
    };

    public OnClickListener customlistener2 = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ldfeature.isChecked()) {
                if (customspinner2.getSelectedItem().toString() == "Integer") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText3.getText().toString()).setType(AdasDebugApplication.Type.Int).setValue(editText4.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner2.getSelectedItem().toString() == "Double") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText3.getText().toString()).setType(AdasDebugApplication.Type.Double).setValue(editText4.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner2.getSelectedItem().toString() == "String") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText3.getText().toString()).setType(AdasDebugApplication.Type.String).setValue(editText4.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner2.getSelectedItem().toString() == "Boolean") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText3.getText().toString()).setType(AdasDebugApplication.Type.Boolean).setValue(editText4.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }
            }else if (fdfeature.isChecked()) {
                if (customspinner2.getSelectedItem().toString() == "Integer") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText3.getText().toString()).setType(AdasDebugApplication.Type.Int).setValue(editText4.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner2.getSelectedItem().toString() == "Double") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText3.getText().toString()).setType(AdasDebugApplication.Type.Double).setValue(editText4.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner2.getSelectedItem().toString() == "String") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText3.getText().toString()).setType(AdasDebugApplication.Type.String).setValue(editText4.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner2.getSelectedItem().toString() == "Boolean") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText3.getText().toString()).setType(AdasDebugApplication.Type.Boolean).setValue(editText4.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }
            }
        }
    };

    public OnClickListener customlistener3= new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ldfeature.isChecked()) {
                if (customspinner3.getSelectedItem().toString() == "Integer") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText5.getText().toString()).setType(AdasDebugApplication.Type.Int).setValue(editText6.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner3.getSelectedItem().toString() == "Double") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText5.getText().toString()).setType(AdasDebugApplication.Type.Double).setValue(editText6.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner3.getSelectedItem().toString() == "String") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText5.getText().toString()).setType(AdasDebugApplication.Type.String).setValue(editText6.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner3.getSelectedItem().toString() == "Boolean") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText5.getText().toString()).setType(AdasDebugApplication.Type.Boolean).setValue(editText6.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }
            }else if (fdfeature.isChecked()) {
                if (customspinner3.getSelectedItem().toString() == "Integer") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText5.getText().toString()).setType(AdasDebugApplication.Type.Int).setValue(editText6.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner3.getSelectedItem().toString() == "Double") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText5.getText().toString()).setType(AdasDebugApplication.Type.Double).setValue(editText6.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner3.getSelectedItem().toString() == "String") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText5.getText().toString()).setType(AdasDebugApplication.Type.String).setValue(editText6.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner3.getSelectedItem().toString() == "Boolean") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText5.getText().toString()).setType(AdasDebugApplication.Type.Boolean).setValue(editText6.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }
            }
        }
    };

    public OnClickListener customlistener4= new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ldfeature.isChecked()) {
                if (customspinner4.getSelectedItem().toString() == "Integer") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText7.getText().toString()).setType(AdasDebugApplication.Type.Int).setValue(editText8.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner4.getSelectedItem().toString() == "Double") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText7.getText().toString()).setType(AdasDebugApplication.Type.Double).setValue(editText8.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner4.getSelectedItem().toString() == "String") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText7.getText().toString()).setType(AdasDebugApplication.Type.String).setValue(editText8.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner4.getSelectedItem().toString() == "Boolean") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText7.getText().toString()).setType(AdasDebugApplication.Type.Boolean).setValue(editText8.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }
            }else if (fdfeature.isChecked()) {
                if (customspinner4.getSelectedItem().toString() == "Integer") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText7.getText().toString()).setType(AdasDebugApplication.Type.Int).setValue(editText8.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner4.getSelectedItem().toString() == "Double") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText7.getText().toString()).setType(AdasDebugApplication.Type.Double).setValue(editText8.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner4.getSelectedItem().toString() == "String") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText7.getText().toString()).setType(AdasDebugApplication.Type.String).setValue(editText8.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner4.getSelectedItem().toString() == "Boolean") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText7.getText().toString()).setType(AdasDebugApplication.Type.Boolean).setValue(editText8.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }
            }
        }
    };

    public OnClickListener customlistener5 = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ldfeature.isChecked()) {
                if (customspinner5.getSelectedItem().toString() == "Integer") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText9.getText().toString()).setType(AdasDebugApplication.Type.Int).setValue(editText10.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner5.getSelectedItem().toString() == "Double") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText9.getText().toString()).setType(AdasDebugApplication.Type.Double).setValue(editText10.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner5.getSelectedItem().toString() == "String") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText9.getText().toString()).setType(AdasDebugApplication.Type.String).setValue(editText10.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner5.getSelectedItem().toString() == "Boolean") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText9.getText().toString()).setType(AdasDebugApplication.Type.Boolean).setValue(editText10.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }
            }else if (fdfeature.isChecked()) {
                if (customspinner5.getSelectedItem().toString() == "Integer") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText9.getText().toString()).setType(AdasDebugApplication.Type.Int).setValue(editText10.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner5.getSelectedItem().toString() == "Double") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText9.getText().toString()).setType(AdasDebugApplication.Type.Double).setValue(editText10.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner5.getSelectedItem().toString() == "String") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText9.getText().toString()).setType(AdasDebugApplication.Type.String).setValue(editText10.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner5.getSelectedItem().toString() == "Boolean") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText9.getText().toString()).setType(AdasDebugApplication.Type.Boolean).setValue(editText10.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }
            }
        }
    };

    public OnClickListener customlistener6 = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ldfeature.isChecked()) {
                if (customspinner6.getSelectedItem().toString() == "Integer") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText11.getText().toString()).setType(AdasDebugApplication.Type.Int).setValue(editText12.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner6.getSelectedItem().toString() == "Double") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText11.getText().toString()).setType(AdasDebugApplication.Type.Double).setValue(editText12.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner6.getSelectedItem().toString() == "String") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText11.getText().toString()).setType(AdasDebugApplication.Type.String).setValue(editText12.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner6.getSelectedItem().toString() == "Boolean") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.LDW_Config).setLdwConfig(AdasDebugApplication.Message.LDWConfig.newBuilder().setLaneDetector(lanedetecton).setPitchAngle(0.0).setDetectionCombination(combination).setYawAngle(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText11.getText().toString()).setType(AdasDebugApplication.Type.Boolean).setValue(editText12.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }
            }else if (fdfeature.isChecked()) {
                if (customspinner6.getSelectedItem().toString() == "Integer") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText11.getText().toString()).setType(AdasDebugApplication.Type.Int).setValue(editText12.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner6.getSelectedItem().toString() == "Double") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText11.getText().toString()).setType(AdasDebugApplication.Type.Double).setValue(editText12.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner6.getSelectedItem().toString() == "String") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText11.getText().toString()).setType(AdasDebugApplication.Type.String).setValue(editText12.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }else if (customspinner6.getSelectedItem().toString() == "Boolean") {
                    AdasDebugApplication.Message messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.FCW_Config).setFcwConfig(AdasDebugApplication.Message.FCWConfig.newBuilder().setProcessor(processorgpu).setGrayInit(graymode).setHOGscaleInit(0.0).setLevelsCount(13).setHOGgroupThresholdInit(0).setHitThreshold(0.0).setCustom(AdasDebugApplication.Message.CustomField.newBuilder().setName(editText11.getText().toString()).setType(AdasDebugApplication.Type.Boolean).setValue(editText12.getText().toString()).build()).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                }
            }
        }
    };

    //Functionality of the play,pause,stop buttons
    public OnClickListener playlistener = new OnClickListener() {
        public void onClick(View v) {
            is_playing_desired = true;
            wake_lock.acquire();
            nativePlay();

            state = 1;

            inputchoice.setEnabled(false);
            foldername.setEnabled(false);

            final String choice = inputchoice.getSelectedItem().toString();

            AdasDebugApplication.Message messagetype;


            if (choice == "Camera") {
                if (feature) {
                    messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PLAY).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").setStream(AdasDebugApplication.Stream.LDW).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("Camera+LDW ");
                } else {
                    messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PLAY).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").setStream(AdasDebugApplication.Stream.FCW).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("Camera+FCW ");
                }
            } else if (choice == "Folder") {
                if (feature) {
                    messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PLAY).setSource(AdasDebugApplication.Source.FOLDER).setSourceFolder(foldername.getText().toString()).setStream(AdasDebugApplication.Stream.LDW).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("Folder+LDW ");
                } else {
                    messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PLAY).setSource(AdasDebugApplication.Source.FOLDER).setSourceFolder(foldername.getText().toString()).setStream(AdasDebugApplication.Stream.FCW).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("Folder+FCW ");
                }
            }
        }
    };

    public OnClickListener pauselistener = new OnClickListener() {
        public void onClick(View v) {
            is_playing_desired = false;
            wake_lock.release();

            state = 2;

            AdasDebugApplication.Message messagetype;

            final String choice = inputchoice.getSelectedItem().toString();

            if (choice == "Camera") {
                if (feature) {
                    messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PAUSE).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").setStream(AdasDebugApplication.Stream.LDW).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("Camera+LDW ");
                } else {
                    messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PAUSE).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").setStream(AdasDebugApplication.Stream.FCW).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("Camera+FCW ");
                }
            } else if (choice == "Folder") {
                if (feature) {
                    messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PAUSE).setSource(AdasDebugApplication.Source.FOLDER).setSourceFolder(foldername.getText().toString()).setStream(AdasDebugApplication.Stream.LDW).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("Folder+LDW ");
                } else {
                    messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.PAUSE).setSource(AdasDebugApplication.Source.FOLDER).setSourceFolder(foldername.getText().toString()).setStream(AdasDebugApplication.Stream.FCW).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("Folder+FCW ");
                }
            }
        }
    };

    public OnClickListener stoplistener = new OnClickListener() {
        public void onClick(View v) {
            is_playing_desired = false;
            wake_lock.release();

            state = 3;

            inputchoice.setEnabled(true);
            foldername.setEnabled(true);

            AdasDebugApplication.Message messagetype;

            final String choice = inputchoice.getSelectedItem().toString();

            if (choice == "Camera") {
                if (feature) {
                    messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.STOP).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").setStream(AdasDebugApplication.Stream.LDW).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP,IP);
                    System.out.println("Camera+LDW ");
                } else {
                    messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.STOP).setSource(AdasDebugApplication.Source.CAMERA).setSourceFolder("0").setStream(AdasDebugApplication.Stream.FCW).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("Camera+FCW ");
                }
            } else if (choice == "Folder") {
                if (feature) {
                    messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.STOP).setSource(AdasDebugApplication.Source.FOLDER).setSourceFolder(foldername.getText().toString()).setStream(AdasDebugApplication.Stream.LDW).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("Folder+LDW ");
                } else {
                    messagetype = AdasDebugApplication.Message.newBuilder().setMessagetype(AdasDebugApplication.MessageType.PipeLine_Config).setPipelineConfig(AdasDebugApplication.Message.PipelineConfig.newBuilder().setIP(ip).setState(AdasDebugApplication.State.STOP).setSource(AdasDebugApplication.Source.FOLDER).setSourceFolder(foldername.getText().toString()).setStream(AdasDebugApplication.Stream.FCW).build()).build();
                    CommunicationThread stuff = new CommunicationThread(messagetype.toByteArray(), ServerIP, IP);
                    System.out.println("Folder+FCW ");
                }
            }
        }
    };

    //Functionality of the fullscreen mode
    public OnClickListener surfacelistener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (fullscreen) {
                android.widget.RelativeLayout.LayoutParams params = new android.widget.RelativeLayout.LayoutParams(1024, 472);
                sv.setLayoutParams(params);
                fullscreen = false;
                tabHost.setVisibility(View.INVISIBLE);
                onMediaSizeChanged(1024, 472);
            } else {
                android.widget.RelativeLayout.LayoutParams params = new android.widget.RelativeLayout.LayoutParams(676, 374);
                sv.setLayoutParams(params);
                tabHost.setVisibility(View.VISIBLE);
                fullscreen = true;
                onMediaSizeChanged(676, 374);
            }
        }
    };


    //Saving the configuration in Advanced Tab
    public OnClickListener saveButtonListener = new OnClickListener(){

        @Override
        public void onClick(View v) {


            makeTag2(customspinner1.getSelectedItemPosition(),1);
            makeTag2(customspinner2.getSelectedItemPosition(),2);
            makeTag2(customspinner3.getSelectedItemPosition(),3);
            makeTag2(customspinner4.getSelectedItemPosition(),4);
            makeTag2(customspinner5.getSelectedItemPosition(),5);
            makeTag2(customspinner6.getSelectedItemPosition(),6);


            makeTag(editText1.getText().toString(),1);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText1.getWindowToken(),0);
            makeTag(editText2.getText().toString(), 2);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText2.getWindowToken(), 0);
            makeTag(editText3.getText().toString(), 3);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText3.getWindowToken(), 0);
            makeTag(editText4.getText().toString(), 4);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText4.getWindowToken(), 0);
            makeTag(editText5.getText().toString(), 5);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText5.getWindowToken(), 0);
            makeTag(editText6.getText().toString(), 6);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText6.getWindowToken(), 0);
            makeTag(editText7.getText().toString(), 7);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText7.getWindowToken(), 0);
            makeTag(editText8.getText().toString(), 8);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText8.getWindowToken(), 0);
            makeTag(editText9.getText().toString(), 9);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText9.getWindowToken(), 0);
            makeTag(editText10.getText().toString(), 10);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText10.getWindowToken(), 0);
            makeTag(editText11.getText().toString(), 11);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText11.getWindowToken(), 0);
            makeTag(editText12.getText().toString(), 12);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText12.getWindowToken(),0);

        }
    };

    //Clearing the saved configuration on Advanced Tab
    public OnClickListener clearButtonListener = new OnClickListener() {
        @Override
        public void onClick(View view) {

            editText1.setText("");
            editText2.setText("");
            editText3.setText("");
            editText4.setText("");
            editText5.setText("");
            editText6.setText("");
            editText7.setText("");
            editText8.setText("");
            editText9.setText("");
            editText10.setText("");
            editText11.setText("");
            editText12.setText("");
            customspinner1.setSelection(0);
            customspinner2.setSelection(0);
            customspinner3.setSelection(0);
            customspinner4.setSelection(0);
            customspinner5.setSelection(0);
            customspinner6.setSelection(0);

            makeTag2(0, 1);
            makeTag2(0,2);
            makeTag2(0, 3);
            makeTag2(0, 4);
            makeTag2(0, 5);
            makeTag2(0, 6);

            makeTag("", 1);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText1.getWindowToken(), 0);
            makeTag("", 2);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText2.getWindowToken(), 0);
            makeTag("", 3);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText3.getWindowToken(), 0);
            makeTag("", 4);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText4.getWindowToken(), 0);
            makeTag("", 5);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText5.getWindowToken(), 0);
            makeTag("", 6);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText6.getWindowToken(), 0);
            makeTag("", 7);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText7.getWindowToken(), 0);
            makeTag("", 8);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText8.getWindowToken(), 0);
            makeTag("", 9);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText9.getWindowToken(), 0);
            makeTag("", 10);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText10.getWindowToken(), 0);
            makeTag("", 11);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText11.getWindowToken(), 0);
            makeTag("", 12);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText12.getWindowToken(), 0);


        }
    };

    protected void onSaveInstanceState(Bundle outState) {
        Log.d("GStreamer", "Saving state, playing:" + is_playing_desired + " position:" + position +
                " duration: " + duration);
        outState.putBoolean("playing", is_playing_desired);
        outState.putInt("position", position);
        outState.putInt("duration", duration);
    }


    protected void onDestroy() {
        nativeFinalize();
        if (wake_lock.isHeld())   //new
            wake_lock.release();
        super.onDestroy();

    }

    // Called from native code. This sets the content of the TextView from the UI thread.
    private void setMessage(final String message) {
        final TextView tv = (TextView) this.findViewById(R.id.textview_message);
        tv.setVisibility(View.INVISIBLE);
        runOnUiThread(new Runnable() {
            public void run() {
                tv.setText(message);
            }
        });
    }

    private void setException(final String message) {
        final TextView tv = (TextView) this.findViewById(R.id.textview_message);
        runOnUiThread(new Runnable() {
            public void run() {
                tv.setText(message);
            }
        });
    }

    // Called from native code. Native code calls this once it has created its pipeline and
    // the main loop is running, so it is ready to accept commands.
    private void onGStreamerInitialized() {
        Log.i("GStreamer", "GStreamer initialized:");
        Log.i("GStreamer", "  playing:" + is_playing_desired + " position:" + position);

        // Restore previous playing state
        if (is_playing_desired) {
            nativePlay();
            wake_lock.acquire();
        } else {
            nativePause();
            wake_lock.release();
        }

        // Re-enable buttons, now that GStreamer is initialized
        final Activity activity = this;
        runOnUiThread(new Runnable() {
            public void run() {
                activity.findViewById(R.id.button_play).setEnabled(true);
                activity.findViewById(R.id.button_pause).setEnabled(true);
            }
        });
    }

    private void setCurrentPosition(final int position, final int duration) {
        this.position = position;
        this.duration = duration;
    }

    static {
        System.loadLibrary("gstreamer_android");
        System.loadLibrary("tutorial-5");
        nativeClassInit();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.d("GStreamer", "Surface changed to format " + format + " width "
                + width + " height " + height);
        nativeSurfaceInit(holder.getSurface());
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("GStreamer", "Surface created: " + holder.getSurface());
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("GStreamer", "Surface destroyed");
        nativeSurfaceFinalize();
    }

    // Called from native code when the size of the media changes or is first detected.
    // Inform the video surface about the new size and recalculate the layout.
    private void onMediaSizeChanged(int width, int height) {
        Log.i("GStreamer", "Media size changed to " + width + "x" + height);
        final GStreamerSurfaceView gsv = (GStreamerSurfaceView) this.findViewById(R.id.surface_video);
        gsv.media_width = width;
        gsv.media_height = height;
        runOnUiThread(new Runnable() {
            public void run() {
                gsv.requestLayout();
            }
        });
    }

    private String IPCalculation() {

        try{
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inet4Address = enumIpAddr.nextElement();
                    if (!inet4Address.isLoopbackAddress()) {
                        ip = inet4Address.getHostAddress().toString();
                    }
                }
            }
        }catch (java.lang.Exception e) {
            Log.i("Java.lang.Exception", "Occured " + e.toString());
            setException(e.toString());
        }
        return ip;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}