package com.kelghou.mypizzaria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class IngredientsAdpter extends BaseAdapter {

    Context context;
    HashMap <String,Integer> ingredients;
    LayoutInflater inflater;

    public IngredientsAdpter(Context ctx, HashMap<String,Integer> ingredients){
        this.context = ctx;
        this.ingredients = ingredients;
        inflater = LayoutInflater.from(ctx);
    }

    private String getName(int i){
        String[] keys = ingredients.keySet().toArray(new String[0]);
        int index = 0 ;
        for(String key : keys){
            if(i == index){
                return key;
            }
            index++;
        }
        return "";
    }

    private int getImage(int i){
        String[] keys = ingredients.keySet().toArray(new String[0]);
        int index = 0 ;
        for(String key : keys){
            if(i == index){
                return ingredients.get(key);
            }
            index++;
        }
        return 0;
    }


    @Override
    public int getCount() {
        return this.ingredients.size();
    }

    @Override
    public Object getItem(int i) {
        String[] keys = ingredients.keySet().toArray(new String[0]);
        int index = 0 ;
        for(String key : keys){
            if(i == index){
                return ingredients.get(key);
            }
            index++;
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_custom_list_view,null);
        TextView textView = view.findViewById(R.id.ItemTitle);
        ImageView imageView = view.findViewById(R.id.ItemImage);
        textView.setText(getName(i));
        imageView.setImageResource(getImage(i));
        return view;
    }
}
