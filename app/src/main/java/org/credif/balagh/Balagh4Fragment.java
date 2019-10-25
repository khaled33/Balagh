package org.credif.balagh;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Balagh4Fragment extends Fragment {

    SharedPreferences sherd;
    SharedPreferences.Editor editor;
    Button suvi,precd;
    ListView list;
    String [] gouvernement;
    ArrayList<String> listA= new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balagh4, container, false);
        sherd = getContext().getSharedPreferences("file", Context.MODE_PRIVATE);
        editor = sherd.edit();
        gouvernement = getResources().getStringArray(R.array.gouvernement);
        editor.putInt("Page", 4);
        editor.apply();

        suvi =  view.findViewById(R.id.suiv);
        precd =  view.findViewById(R.id.prec);
        list = view.findViewById(R.id.list_gouvert);
        if (Locale.getDefault().getLanguage().equals("ar")) {
            suvi.setBackgroundResource(R.drawable.next1);
            precd.setBackgroundResource(R.drawable.next2);
        } else {
            suvi.setBackgroundResource(R.drawable.next2);
            precd.setBackgroundResource(R.drawable.next1);

        }
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.rowlist,R.id.text_gouv,gouvernement);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=((TextView)view).getText().toString();
                if (listA.contains(item)){
                    listA.remove(item);
                }else {
                    listA.add(item);
                }
            }
        });
        precd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (Locale.getDefault().getLanguage().equals("ar")) {
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                }else {
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                }
                transaction.replace(R.id.fragment_container, new Balagh3Fragment()).commit();
            }
        });
        suvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listA.size()!=0){
                    Set<String> itemset = new HashSet<>(listA);
                    editor.putStringSet("Gouvernement", itemset);
                    editor.apply();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    if (Locale.getDefault().getLanguage().equals("ar")) {
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                    }else {
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    }
                    transaction.replace(R.id.fragment_container, new Balagh5Fragment()).commit();
                }else {
                    Toast.makeText(getContext(), R.string.remplir_formulaire, Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
