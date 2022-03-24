package com.kelghou.mypizzaria;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class SendPizza extends AsyncTask<String, Void, Void> {

    private Socket client;
    private PrintWriter printwriter;
    private BufferedReader in_bufferedReader;
    @Override
    protected Void doInBackground(String... strings) {
        String message = strings[0];
        try {
            client = new Socket("chadok.info", 9874);// connect to the server
            printwriter = new PrintWriter(client.getOutputStream(), true);
            printwriter.println(message); // write the message to output stream
            printwriter.close();
            printwriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
