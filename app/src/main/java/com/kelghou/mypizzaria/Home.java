package com.kelghou.mypizzaria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.kelghou.mypizzaria.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity {


    ActivityHomeBinding binding;
    public static Fragment CurrentFragment = null;


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
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        CurrentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }
}