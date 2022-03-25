package com.kelghou.mypizzaria;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class SendPizza extends AsyncTask<BundleServer, Void, String> {

    private Socket client;
    private PrintWriter printwriter;
    private BufferedReader in;
    @Override
    protected String doInBackground(BundleServer... bunles) {
        String response = "";
        BundleServer bundle = bunles[0];
        String message = bundle.message;
        try {
            client = new Socket("chadok.info", 9874);// connect to the server
            printwriter = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            printwriter.println(message); // write the message to output stream
            response += in.readLine();
            printwriter.close();
            printwriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("response",response);
        return response;
    }
}
