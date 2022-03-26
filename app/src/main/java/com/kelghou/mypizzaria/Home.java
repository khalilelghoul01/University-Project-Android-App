package com.kelghou.mypizzaria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.kelghou.mypizzaria.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity implements View.OnClickListener {


    ActivityHomeBinding binding;
    public static Fragment CurrentFragment = null;
    int tableNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(CurrentFragment != null? CurrentFragment : new MenuFragment());
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.defaultMenu:
                    replaceFragment(new MenuFragment());
                    break;
                case R.id.customMenu:
                    replaceFragment(new CustomFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new SettingFragment());
                    break;
            }

            return true;
        });
        CollectExtras();
        Button returnButton = (Button) findViewById(R.id.tables2);

        returnButton.setOnClickListener(this);
    }

    private void CollectExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tableNum = extras.getInt("tables");
        }
        TextView tables = (TextView) findViewById(R.id.tableViewText);
        tables.setText(tables.getText()+" "+tableNum);
    }

    private void replaceFragment(Fragment fragment){
        CurrentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }



    @Override
    public void onClick(View view) {
        if(view instanceof Button){
            if(view.getId() == R.id.tables2){
                Intent switchActivityIntent = new Intent(this, ChoixTable.class);
                startActivity(switchActivityIntent);
            }
        }
    }

}
