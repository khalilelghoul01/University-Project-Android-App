package com.kelghou.mypizzaria;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomFragment extends Fragment {

    /*
     *
     * variables begin
     *
     * */

    static HashMap<String,Integer> ingredients = new HashMap<>();
    public static ArrayList<String> pizza = new ArrayList<>();
    public static ArrayList<ArrayList<String>> pizzas = new ArrayList<>();
    ArrayList<Integer> elementIds = new ArrayList<>();
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

    public CustomFragment() {
        addIngredient("Ananas",R.drawable.ananass);
        addIngredient("Kebab",R.drawable.kebab);
        addIngredient("Olive",R.drawable.olive);
        addIngredient("Poivron",R.drawable.poivron);
        addIngredient("Poulet",R.drawable.poulet);
        addIngredient("Tomates",R.drawable.tomates);
        addIngredient("Pomme De Terre",R.drawable.pommedeterre);
        addIngredient("Viande Hachée",R.drawable.viande);
        addIngredient("Mozzarella",R.drawable.mozzarella);

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomFragment newInstance(String param1, String param2) {
        CustomFragment fragment = new CustomFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom, container, false);

        ListView ingredientsView = (ListView) view.findViewById(R.id.ingredients);
        ListView pizzasView = (ListView) view.findViewById(R.id.pizzas);
        IngredientsAdpter ingredientsAdpter = new IngredientsAdpter(getActivity().getApplicationContext(),ingredients);
        ingredientsView.setAdapter(ingredientsAdpter);
        ingredientsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               if(!elementIds.contains(i)){
                   view.findViewById(R.id.ItemTitle).setEnabled(false);
                   view.setEnabled(false);
                   pizza.add(getName(i));
                   Log.i("pizzas:",pizza.toString());
                   elementIds.add(i);
               }

            }
        });
        // Inflate the layout for this fragment
        return view;
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

    private  void addIngredient(String name,int image){
        if(!ingredients.containsKey(name)){
            ingredients.put(name,image);
        }
    }
}