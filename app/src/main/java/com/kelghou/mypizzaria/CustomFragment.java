package com.kelghou.mypizzaria;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomFragment extends Fragment implements View.OnClickListener {

    /*
     *
     * variables begin
     *
     * */

    static HashMap<String,Integer> ingredients = new HashMap<>();
    public ArrayList<String> pizza = new ArrayList<>();
    public ArrayList<ArrayList<String>> pizzas = new ArrayList<>();
    ArrayList<Integer> elementIds = new ArrayList<>();
    Button add;
    Button envoyer;
    PizzasAdapter pizzasAdapter;
    IngredientsAdpter ingredientsAdpter;
    private int tableNum;
    /*
     *
     * variables end
     *
     * */

    private void CollectExtras() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            tableNum = extras.getInt("tables");
        }
    }

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
        CollectExtras();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom, container, false);
        add = (Button) view.findViewById(R.id.ajouter);
        Button effacer = (Button) view.findViewById(R.id.effacer);
        envoyer = (Button) view.findViewById(R.id.envoyer);
        add.setEnabled(false);
        ListView ingredientsView = (ListView) view.findViewById(R.id.ingredients);
        ListView pizzasView = (ListView) view.findViewById(R.id.pizzas);
        ingredientsAdpter = new IngredientsAdpter(getActivity().getApplicationContext(),ingredients);
        ingredientsView.setAdapter(ingredientsAdpter);
        pizzasAdapter = new PizzasAdapter(getActivity().getApplicationContext(),pizzas);
        pizzasView.setAdapter(pizzasAdapter);
        ingredientsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               if(!elementIds.contains(i)){
                   view.findViewById(R.id.ItemTitle).setEnabled(false);
                   view.setEnabled(false);
                   add.setEnabled(true);
                   pizza.add(getName(i));
                   Log.i("pizzas:",pizza.toString());
                   elementIds.add(i);
                   pizzasAdapter.notifyDataSetChanged();
               }

            }
        });
        add.setOnClickListener(this);
        effacer.setOnClickListener(this);
        envoyer.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    private void checkPizza(){
        if(pizzas.size() > 0){
            envoyer.setEnabled(true);
        }else {
            envoyer.setEnabled(false);
        }
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

    @Override
    public void onClick(View view) {
        if(view instanceof Button){
            if(view.getId() == R.id.ajouter){
                checkPizza();
                pizzas.add(0,(ArrayList<String>) pizza.clone());
                pizza.clear();
                elementIds.clear();
                ingredientsAdpter.notifyDataSetChanged();
                pizzasAdapter.notifyDataSetChanged();
                add.setEnabled(false);
            }
            if(view.getId() == R.id.effacer){
                resetButtons();
            }
            if(view.getId() == R.id.envoyer){
                handleCommands();
                envoyer.setEnabled(false);
            }
        }
    }

    private void handleCommands() {
        ArrayList<String> commandsToSend = getPizzaFormatBtns();
        ArrayList<String> ServerResponse = new ArrayList<>();
        for (String pizza:commandsToSend) {
            try {
                ServerResponse.add(HandlePizzaSending(pizza));
            } catch (ExecutionException e) {
                Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
            } catch (InterruptedException e) {
                Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        resetButtons();
        loadActivity(PopUp.class);
    }

    private void loadActivity(Class activity) {
        Intent popup = new Intent(getActivity(),activity);
        startActivity(popup);

    }

    @SuppressLint("NewApi")
    private ArrayList<String> getPizzaFormatBtns() {
        ArrayList<String> pizzasFormat = new ArrayList<>();
        for(ArrayList<String> pizzaTemp : pizzas){
            pizzasFormat.add(0,(String.valueOf(tableNum).length()==1?"0"+String.valueOf(tableNum):String.valueOf(tableNum))+String.join(" + ",pizzaTemp));
        }
        return pizzasFormat;
    }

    private void resetButtons() {
        pizza.clear();
        elementIds.clear();
        pizzas.clear();
        ingredientsAdpter.notifyDataSetChanged();
        pizzasAdapter.notifyDataSetChanged();
        add.setEnabled(false);
        envoyer.setEnabled(false);
    }

    String HandlePizzaSending(String pizza) throws ExecutionException, InterruptedException {
        SendPizza send = new SendPizza();
        return send.execute(pizza).get();
    }
}