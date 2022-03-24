package com.kelghou.mypizzaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static HashMap<Integer,Integer> buttons= new HashMap<Integer,Integer>();
    Set<Integer> keyButtons = buttons.keySet();
    static HashMap<Integer,HashMap<Integer,Integer>> tablesData = new HashMap<Integer,HashMap<Integer,Integer>>();
    Set<Integer> keytablesData = tablesData.keySet();
    ArrayList<Integer> excludeBtns = new ArrayList<Integer>() ;
    int tableNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonsToExclude();
        updateInNewThread();
        buttonsHandlers();
        testHandler();
        try {
            HandlePizzaSending();
        } catch (ExecutionException e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
        OrientationEventListener orientationListener = new OrientationEventListener(MainActivity.this, SensorManager.SENSOR_DELAY_UI) {
            public void onOrientationChanged(int orientation) {
                buttons = getData();
                keyButtons = buttons.keySet();
                for(int key : keyButtons){
                    Button buttonInst = ((Button) findViewById(key));
                    String output = buttonInst.getText().toString().contains(":")?buttonInst.getText().toString().split(":")[0]+": "+ buttons.get(key) :buttonInst.getText()+": "+buttons.get(key);
                    Log.d("log",Integer.toString(key));
                    if(keyButtons.contains(key)){
                        if(buttons.get(key)>0){
                            buttonInst.setText(output);
                        }
                    }

                }
            }
        };
        orientationListener.enable();
    }

    void buttonsToExclude(){
        excludeBtns.add(R.id.button9);
        excludeBtns.add(R.id.button10);
        excludeBtns.add(R.id.tables);
    }

    void updateInNewThread(){
        new Handler(getApplicationContext().getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                updateButtons();
            }
        });
    }

    void updateButtons(){
        buttons = getData();
        keyButtons = buttons.keySet();
        for(int key : keyButtons){
            Button buttonInst = ((Button) findViewById(key));
            String output = buttonInst.getText().toString().contains(":")?buttonInst.getText().toString().split(":")[0]+": "+ buttons.get(key) :buttonInst.getText()+": "+buttons.get(key);
            Log.d("log",Integer.toString(key));
            if(keyButtons.contains(key)){
                int keyTempValue = buttons.get(key);
                if(keyTempValue>0){
                    buttonInst.setText(output);
                }else if(keyTempValue == 0){
                    output = output.contains(":") ? output.split(":")[0]:output;
                    buttonInst.setText(output);
                }
            }

        }
    }
    void HandlePizzaSending() throws ExecutionException, InterruptedException {
        SendPizza send = new SendPizza();
        send.execute("04montagnarde");
        Toast.makeText(getApplicationContext(),"sent",Toast.LENGTH_SHORT).show();
    }

    HashMap<Integer,Integer> getData(){
        if(keytablesData.contains(tableNum)){
            return tablesData.get(tableNum);
        }
        return new HashMap<Integer,Integer>();
    }

    void setData(HashMap<Integer,Integer> data){
        tablesData.put(tableNum,data);
    }

    private void testHandler() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tableNum = extras.getInt("tables");
        }
        TextView tables = (TextView) findViewById(R.id.textView3);
        tables.setText(tables.getText()+" "+tableNum);
    }

    private void addButton(int id){
        keyButtons = buttons.keySet();
        if(!keyButtons.contains(id)){
            buttons.put(id,0);
        }
    }


    private void buttonsHandlers(){
        ((Button)findViewById(R.id.tables)).setOnClickListener(this);
        ((Button)findViewById(R.id.button9)).setOnClickListener(this);
        ((Button)findViewById(R.id.button10)).setOnClickListener(this);
        addButton(R.id.button1);
        addButton(R.id.button2);
        addButton(R.id.button3);
        addButton(R.id.button4);
        addButton(R.id.button5);
        addButton(R.id.button6);
        addButton(R.id.button7);
        addButton(R.id.button8);
        for(int key : keyButtons){
            ((Button) findViewById(key)).setOnClickListener(this);
        }

    }

    public void onClick(View v) {
        keyButtons = buttons.keySet();
        if(v instanceof Button) {
            if(v.getId() == R.id.tables){
                setData(buttons);
                Intent switchActivityIntent = new Intent(this, ChoixTable.class);
                startActivity(switchActivityIntent);
            }
            if(buttons.containsKey(v.getId()) && !excludeBtns.contains(v.getId()))
            {
                buttons.put(v.getId(), buttons.get(v.getId())+1);
            }else{
                if(!excludeBtns.contains(v.getId()))
                    buttons.put(v.getId(), 1);
            }
            if(!excludeBtns.contains(v.getId())){
                Button buttonInst = ((Button) findViewById(v.getId()));
                String output = buttonInst.getText().toString().contains(":")?buttonInst.getText().toString().split(":")[0]+": "+buttons.get(v.getId()).toString():buttonInst.getText()+": "+buttons.get(v.getId()).toString();
                if(buttons.get(v.getId())>0){
                    buttonInst.setText(output);
                }
            }
            if(v.getId() == R.id.button9){

            }
            if(v.getId() == R.id.button10){
                resetButtons();
                updateInNewThread();
            }
        }
    }


    void resetButtons(){
        buttons.forEach((k,v) -> {
            buttons.put(k,0);
        });
        setData(buttons);
    }
    @Override
    protected void onPause() {
        Toast.makeText(getApplicationContext(),"Pause",Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStart() {
        Toast.makeText(getApplicationContext(),"Start",Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    @Override
    protected void onStop() {
        Toast.makeText(getApplicationContext(),"Stop",Toast.LENGTH_SHORT).show();
        super.onStop();
    }
}