package org.credif.balagh;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class Balagh5Fragment extends Fragment implements View.OnClickListener{

    SharedPreferences sherd;
    SharedPreferences.Editor editor;
    Button suvi,precd;
    boolean  checksex = false, checktran = false;
    RadioButton rd_moin18, rd_entre18, rd_entre35, rd_plus65,rd_home, rd_fame;
    RadioGroup sexe_agress, tranch_agress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_balagh5, container, false);
        sherd = getContext().getSharedPreferences("file", Context.MODE_PRIVATE);
        editor = sherd.edit();
        editor.putInt("Page", 5);
        editor.apply();

        suvi = (Button) view.findViewById(R.id.suiv);
        precd = (Button) view.findViewById(R.id.prec);
        //declaration button radio
        rd_home = (RadioButton) view.findViewById(R.id.homme);
        rd_fame = (RadioButton) view.findViewById(R.id.famme);

        rd_moin18 = (RadioButton) view.findViewById(R.id.mois18);
        rd_entre18 = (RadioButton) view.findViewById(R.id.entre18);
        rd_entre35 = (RadioButton) view.findViewById(R.id.entre35);
        rd_plus65 = (RadioButton) view.findViewById(R.id.plus65);
        //declaration des RadiGroup
        sexe_agress = (RadioGroup) view.findViewById(R.id.sexe_agres);
        tranch_agress = (RadioGroup) view.findViewById(R.id.tranche_agrrs);

        if (Locale.getDefault().getLanguage().equals("ar")) {
            suvi.setBackgroundResource(R.drawable.next1);
            precd.setBackgroundResource(R.drawable.next2);
        } else {
            suvi.setBackgroundResource(R.drawable.next2);
            precd.setBackgroundResource(R.drawable.next1);
        }
        //recupurer les valeur
        int restoredsexe = sherd.getInt("Sexe_agresseurID", 0);
        int restoredtranch = sherd.getInt("Tranche_ageAgrrID", 0);

        if (restoredsexe != 0) {
            int sex = sherd.getInt("Sexe_agresseurID", 0);
            sexe_agress.check(sex);
            checksex = true;
        }
        if (restoredtranch != 0) {
            int tranche = sherd.getInt("Tranche_ageAgrrID", 0);
            tranch_agress.check(tranche);
            checktran = true;
        }
        precd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (Locale.getDefault().getLanguage().equals("ar")) {
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                }else {
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                }
                transaction.replace(R.id.fragment_container, new Balagh4Fragment()).commit();
            }
        });
        suvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( checksex && checktran) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    if (Locale.getDefault().getLanguage().equals("ar")) {
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                    }else {
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    }
                    transaction.replace(R.id.fragment_container, new Balagh6Fragment()).commit();
                } else {
                    Toast.makeText(getContext(), R.string.remplir_formulaire, Toast.LENGTH_LONG).show();
                }
            }
        });
        rd_home.setOnClickListener(this);
        rd_fame.setOnClickListener(this);

        rd_moin18.setOnClickListener(this);
        rd_entre18.setOnClickListener(this);
        rd_entre35.setOnClickListener(this);
        rd_plus65.setOnClickListener(this);
       return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homme:
                checksex = true;
                editor.putString("Sexe_agresseur", rd_home.getText().toString());
                editor.putInt("Sexe_agresseurID", rd_home.getId());
                editor.apply();
                break;
            case R.id.famme:
                checksex = true;
                editor.putString("Sexe_agresseur", rd_fame.getText().toString());
                editor.putInt("Sexe_agresseurID", rd_fame.getId());
                editor.apply();
                break;
            case R.id.mois18:
                checktran = true;
                editor.putString("Tranche_ageAgrr", rd_moin18.getText().toString());
                editor.putInt("Tranche_ageAgrrID", rd_moin18.getId());
                editor.commit();
                break;
            case R.id.entre18:
                checktran = true;
                editor.putString("Tranche_ageAgrr", rd_entre18.getText().toString());
                editor.putInt("Tranche_ageAgrrID", rd_entre18.getId());
                editor.commit();
                break;
            case R.id.entre35:
                checktran = true;
                editor.putString("Tranche_ageAgrr", rd_entre35.getText().toString());
                editor.putInt("Tranche_ageAgrrID", rd_entre35.getId());
                editor.apply();
                break;
            case R.id.plus65:
                checktran = true;
                editor.putString("Tranche_ageAgrr", rd_plus65.getText().toString());
                editor.putInt("Tranche_ageAgrrID", rd_plus65.getId());
                editor.apply();
                break;
        }
    }
}
