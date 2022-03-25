package com.kelghou.mypizzaria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PizzasAdapter extends BaseAdapter
{
    Context context;
    ArrayList<ArrayList<String>> pizzas;
    LayoutInflater inflater;
    public PizzasAdapter(Context ctx, ArrayList<ArrayList<String>> pizzas){
        this.context = ctx;
        this.pizzas = pizzas;
        inflater = LayoutInflater.from(ctx);
    }

    String getName(int i){
        ArrayList<String> pizza = pizzas.get(i);
        String name = "Pizza:";
        int index = 0;
        for (String elm: pizza) {
            index++;
            if(index == pizza.size()){
                name += elm ;
            }else {
                name += elm + "-";
            }
        }
        return name;
    }

    @Override
    public int getCount() {
        return pizzas.size();
    }

    @Override
    public Object getItem(int i) {
        return pizzas.get(i);
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
        imageView.setImageResource(R.drawable.pizza);
        return view;
    }
}
