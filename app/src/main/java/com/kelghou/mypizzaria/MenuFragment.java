package com.kelghou.mypizzaria;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {

    /*
    *
    * variables begin
    *
    * */

    static HashMap<Integer,Integer> buttons= new HashMap<Integer,Integer>();
    static HashMap<Integer,String> buttonNames= new HashMap<Integer,String>();
    Set<Integer> keyButtons = buttons.keySet();
    static HashMap<Integer,HashMap<Integer,Integer>> tablesData = new HashMap<Integer,HashMap<Integer,Integer>>();
    Set<Integer> keytablesData = tablesData.keySet();
    ArrayList<Integer> excludeBtns = new ArrayList<Integer>() ;
    int tableNum = 0;
    View currentView;

    /*
    *
    * variables end
    *
    * */







    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    void buttonsToExclude(){
        excludeBtns.add(R.id.button9);
        excludeBtns.add(R.id.button10);
        excludeBtns.add(R.id.tables);
        //excludeBtns.add(R.id.custom);
    }

    void updateInNewThread(){
        new Handler(getActivity().getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                updateButtons();
            }
        });
    }



    void updateButtons(){
        buttons = getData();
        checkState();
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
    String HandlePizzaSending(String pizza) throws ExecutionException, InterruptedException {
        SendPizza send = new SendPizza();
        return send.execute(pizza).get();
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

    private void CollectExtras() {
        Bundle extras = getActivity().getIntent().getExtras();
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
            if(!buttonNames.containsKey(id)){
                Button buttonInst = ((Button) findViewById(id));
                String output = buttonInst.getText().toString();
                output = output.contains(":") ? output.split(":")[0]:output;
                buttonNames.put(id,output);
            }
        }
    }

    private ArrayList<String> getPizzaFormatBtns(){
        ArrayList<String> command = new ArrayList<>();

        buttons.forEach((k,v) -> {
            if(buttonNames.containsKey(k)){
                String commandString = (String.valueOf(tableNum).length()==1?"0"+String.valueOf(tableNum):String.valueOf(tableNum))+buttonNames.get(k);
                for(int i = 0 ; i < v;i++){
                    command.add(commandString);
                }
            }
        });
        return command;
    }

    private void buttonsHandlers(){
        ((Button)findViewById(R.id.tables)).setOnClickListener(this);
        ((Button)findViewById(R.id.button9)).setOnClickListener(this);
        ((Button)findViewById(R.id.button9)).setEnabled(false);
        ((Button)findViewById(R.id.button10)).setOnClickListener(this);
        ((Button)findViewById(R.id.button10)).setEnabled(false);
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

    private Object findViewById(int tables) {
        return currentView.findViewById(tables);
    }

    public void onClick(View v) {
        keyButtons = buttons.keySet();
        if(v instanceof Button) {
            setData(buttons);
            if(v.getId() == R.id.tables){
                setData(buttons);
                Intent switchActivityIntent = new Intent(getActivity(), ChoixTable.class);
                startActivity(switchActivityIntent);
            }
            if(buttons.containsKey(v.getId()) && !excludeBtns.contains(v.getId()))
            {
                buttons.put(v.getId(), buttons.get(v.getId())+1);
                updateButtonsStateUi();

            }else{
                if(!excludeBtns.contains(v.getId())){
                    buttons.put(v.getId(), 1);
                    updateButtonsStateUi();
                }
            }
            if(!excludeBtns.contains(v.getId())){
                Button buttonInst = ((Button) findViewById(v.getId()));
                String output = buttonInst.getText().toString().contains(":")?buttonInst.getText().toString().split(":")[0]+": "+buttons.get(v.getId()).toString():buttonInst.getText()+": "+buttons.get(v.getId()).toString();
                if(buttons.get(v.getId())>0){
                    buttonInst.setText(output);
                }
            }
            if(v.getId() == R.id.button9){
                handleCommands();
            }
            if(v.getId() == R.id.button10){
                resetButtons();
                updateInNewThread();
            }
        }
    }


    private void handleCommands() {
        ArrayList<String> commandsToSend = getPizzaFormatBtns();
        ArrayList<String> ServerResponse = new ArrayList<>();
        resetButtons();
        updateInNewThread();
        for (String pizza:commandsToSend) {
            try {
                ServerResponse.add(HandlePizzaSending(pizza));
            } catch (ExecutionException e) {
                Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
            } catch (InterruptedException e) {
                Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        loadActivity(PopUp.class);
    }

    private void loadActivity(Class activity){
        Intent popup = new Intent(getActivity(),activity);
        startActivity(popup);
    }

    public void startActivityOutside(Class activity){
        Intent popup = new Intent(getActivity(),activity);
        startActivity(popup);
    }

    void updateButtonsStateUi(){
        if(!(((Button)findViewById(R.id.button9)).isEnabled())){
            ((Button)findViewById(R.id.button9)).setEnabled(true);
        }
        if(!(((Button)findViewById(R.id.button10)).isEnabled())){
            ((Button)findViewById(R.id.button10)).setEnabled(true);
        }
    }

    void resetButtons(){
        buttons.forEach((k,v) -> {
            buttons.put(k,0);
        });
        setData(buttons);
        ((Button)findViewById(R.id.button9)).setEnabled(false);
        ((Button)findViewById(R.id.button10)).setEnabled(false);
    }

    void checkState(){
        keyButtons = buttons.keySet();
        for (int key: keyButtons ) {
            int v = buttons.get(key);
            if(v>0){
                updateButtonsStateUi();
                break;
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_menu, container, false);
        // Inflate the layout for this fragment
        buttonsToExclude();
        updateInNewThread();
        buttonsHandlers();
        CollectExtras();

        OrientationEventListener orientationListener = new OrientationEventListener(getActivity(), SensorManager.SENSOR_DELAY_UI) {
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
        return currentView;
    }
}