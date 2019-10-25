package org.credif.balagh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class BalaghFragment extends Fragment implements View.OnClickListener {

    SharedPreferences sherd;
    SharedPreferences.Editor editor;
    boolean checkdec = false, checksex = false, checktran = false;
    Button suvi;
    RadioButton rd_vect, rd_temoin, rd_home, rd_fame;
    RadioButton rd_moin18, rd_entre18, rd_entre35, rd_plus65;
    RadioGroup declarer, sexe_vict, tranch_vict;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_balagh, container, false);

        sherd = getContext().getSharedPreferences("file", Context.MODE_PRIVATE);
        editor = sherd.edit();

        editor.putInt("Page", 1);
        editor.apply();
        suvi = (Button) view.findViewById(R.id.suiv);
        //declaration des RadiButton
        rd_vect = (RadioButton) view.findViewById(R.id.rd_vict);
        rd_temoin = (RadioButton) view.findViewById(R.id.rd_temo);
        rd_home = (RadioButton) view.findViewById(R.id.homme);
        rd_fame = (RadioButton) view.findViewById(R.id.famme);

        rd_moin18 = (RadioButton) view.findViewById(R.id.mois18);
        rd_entre18 = (RadioButton) view.findViewById(R.id.entre18);
        rd_entre35 = (RadioButton) view.findViewById(R.id.entre35);
        rd_plus65 = (RadioButton) view.findViewById(R.id.plus65);
        //declaration des RadiGroup
        declarer = (RadioGroup) view.findViewById(R.id.declare);
        sexe_vict = (RadioGroup) view.findViewById(R.id.sexe);
        tranch_vict = (RadioGroup) view.findViewById(R.id.tranche_vict);

        if (Locale.getDefault().getLanguage().equals("ar")) {
            suvi.setBackgroundResource(R.drawable.next1);
        } else {
            suvi.setBackgroundResource(R.drawable.next2);
        }
        //recupurer les valeur
        int restoreddecl = sherd.getInt("declareID", 0);
        int restoredsexe = sherd.getInt("sexe_victimeID", 0);
        int restoredtranch = sherd.getInt("Tranche_ageVictID", 0);

        if (restoredsexe != 0) {
            int sex = sherd.getInt("sexe_victimeID", 0);
            sexe_vict.check(sex);
            checksex = true;
        }
        if (restoreddecl != 0) {
            int declare = sherd.getInt("declareID", 0);
            declarer.check(declare);
            checkdec = true;
        }
        if (restoredtranch != 0) {
            int tranche = sherd.getInt("Tranche_ageVictID", 0);
            tranch_vict.check(tranche);
            checktran = true;
        }

        suvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkdec && checksex && checktran) {

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    if (Locale.getDefault().getLanguage().equals("ar")) {
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                    }else {
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    }
                    transaction.replace(R.id.fragment_container, new Balagh2Fragment()).commit();
                } else {
                    Toast.makeText(getContext(), R.string.remplir_formulaire, Toast.LENGTH_LONG).show();

                }
            }
        });
        rd_vect.setOnClickListener(this);
        rd_temoin.setOnClickListener(this);
        rd_home.setOnClickListener(this);
        rd_fame.setOnClickListener(this);

        rd_moin18.setOnClickListener(this);
        rd_entre18.setOnClickListener(this);
        rd_entre35.setOnClickListener(this);
        rd_plus65.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rd_vict:
                checkdec = true;
                editor.putString("declare", rd_vect.getText().toString());
                editor.putInt("declareID", rd_vect.getId());
                editor.commit();
                break;
            case R.id.rd_temo:
                checkdec = true;
                editor.putString("declare", rd_temoin.getText().toString());
                editor.putInt("declareID", rd_temoin.getId());
                editor.commit();
                break;
            case R.id.homme:
                checksex = true;
                editor.putString("sexe_victime", rd_home.getText().toString());
                editor.putInt("sexe_victimeID", rd_home.getId());
                editor.apply();
                break;
            case R.id.famme:
                checksex = true;
                editor.putString("sexe_victime", rd_fame.getText().toString());
                editor.putInt("sexe_victimeID", rd_fame.getId());
                editor.apply();
                break;
            case R.id.mois18:
                checktran = true;
                editor.putString("Tranche_ageVict", rd_moin18.getText().toString());
                editor.putInt("Tranche_ageVictID", rd_moin18.getId());
                editor.commit();
                break;
            case R.id.entre18:
                checktran = true;
                editor.putString("Tranche_ageVict", rd_entre18.getText().toString());
                editor.putInt("Tranche_ageVictID", rd_entre18.getId());
                editor.commit();
                break;
            case R.id.entre35:
                checktran = true;
                editor.putString("Tranche_ageVict", rd_entre35.getText().toString());
                editor.putInt("Tranche_ageVictID", rd_entre35.getId());
                editor.apply();
                break;
            case R.id.plus65:
                checktran = true;
                editor.putString("Tranche_ageVict", rd_plus65.getText().toString());
                editor.putInt("Tranche_ageVictID", rd_plus65.getId());
                editor.apply();
                break;
        }
    }
}
