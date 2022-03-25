package com.kelghou.mypizzaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ChoixTable extends AppCompatActivity implements View.OnClickListener {

    int tables = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_table);
        buttonsHandlers();


    }

    private void buttonsHandlers(){
        Button submit = ((Button) findViewById(R.id.btnValider));
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v instanceof Button){
            EditText input = (EditText) findViewById(R.id.editTextNumber);
            String text = "1";
            if(input != null) {text = input.getText().toString();}
            else {text = "1";}
            tables = !text.isEmpty()? Integer.parseInt(text) : 1;
            tables = tables>99?99:tables;
            tables = tables<0?0:tables;
            Home.CurrentFragment = new MenuFragment();
            Intent switchActivityIntent = new Intent(this, Home.class);
            switchActivityIntent.putExtra("tables",tables);
            startActivity(switchActivityIntent);
        }
    }

    @Override
    protected void onPause() {
        //Toast.makeText(getApplicationContext(),"Pause",Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStart() {
        //Toast.makeText(getApplicationContext(),"Start",Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    @Override
    protected void onStop() {
        //Toast.makeText(getApplicationContext(),"Stop",Toast.LENGTH_SHORT).show();
        super.onStop();
    }
}