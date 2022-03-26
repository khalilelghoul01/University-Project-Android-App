package com.kelghou.mypizzaria;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int tableNum = 1;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        CollectExtras();
    }

    RadioButton radioButton;
    RadioGroup radioGroup;
    View currentview;
    Button btnApply;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        currentview = view;
        btnApply = view.findViewById(R.id.btnApply);
        btnApply.setOnClickListener(this);
        return view;
    }

    public void switchLocal(Context context, String lcode) {
        if (lcode.equalsIgnoreCase(""))
            return;
        Resources resources = context.getResources();
        Locale locale = new Locale(lcode);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new
                android.content.res.Configuration();
        config.locale = locale;
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        //restart base activity
        getActivity().finish();
        Intent refresh = new Intent(getContext(), Home.class);
        refresh.putExtra("tables",tableNum);
        getActivity().startActivity(refresh);
    }


    private void CollectExtras() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            tableNum = extras.getInt("tables");
        }
    }
    void getCkeckedButton(){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = currentview.findViewById(radioId);
        if(radioButton != null){
            String lang = radioButton.getText().toString().trim();
            Toast.makeText(getContext(), lang, Toast.LENGTH_SHORT).show();
            if(lang.contains("English")){
                switchLocal(getContext(),"en");
            }else if ( lang.contains("Français")){
                switchLocal(getContext(),"en");

            }else if ( lang.contains("عربي")){
                switchLocal(getContext(),"ar");

            }else if ( lang.contains("日本")){
                switchLocal(getContext(),"ja");

            }

        }

    }

    @Override
    public void onClick(View view) {
        if(view instanceof Button){
            if(view.getId() == R.id.btnApply){
                getCkeckedButton();
            }
        }
    }
}