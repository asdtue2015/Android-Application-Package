package com.gst_sdk_tutorials.tutorial_5;

import org.zeromq.ZMQ;
import java.lang.*;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Button;




public class CommunicationThread extends Activity implements Runnable {

    final Button b;
    byte[] userInput;
    String ip;
    Thread t;


    public CommunicationThread (byte[] userInput, String IP, Button b) {

        //Starting a separate thread
        this.ip = IP;
        this.b = b;
        this.userInput = userInput;
        t = new Thread(this,"Send Thread");
        t.start();


    }


    //Transmits the desired message to the specified IP and updates the Connect Button
    public void run() {

            System.out.println(Thread.currentThread().getName());
            ZMQ.Context context = ZMQ.context(1);

            //  Socket to talk to server
            System.out.println("Connecting to hello world server…");
            ZMQ.Socket requester = context.socket(ZMQ.REQ);
            String ipaddress = String.format("tcp://%s:5555", ip);

            requester.connect(ipaddress);

            runOnUiThread(new Runnable() {

            @Override
            public void run() {
                b.setTextColor(Color.RED);
            }
            });

            System.out.println("Sending: " + userInput.toString());
            requester.send(userInput, 0);

            byte[] reply = requester.recv(0);
            System.out.println("Received " + new String(reply));

            requester.close();
            context.term();

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    b.setTextColor(Color.GREEN);
                }
            });


    }

}