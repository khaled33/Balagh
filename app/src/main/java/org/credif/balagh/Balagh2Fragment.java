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

import java.util.Locale;


public class Balagh2Fragment extends Fragment implements View.OnClickListener {

    SharedPreferences sherd;
    SharedPreferences.Editor editor;
    boolean checktyp = false, checkheur = false;
    RadioButton rd_verb, rd_psy, rd_phy, rd_sex, rd_matin, rd_midi,rd_soir;
    RadioGroup types, heurs;
    Button suvi,prec;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balagh2, container, false);

        sherd = getContext().getSharedPreferences("file", Context.MODE_PRIVATE);
        editor = sherd.edit();
        editor.putInt("Page", 2);
        editor.apply();

        suvi = (Button) view.findViewById(R.id.suiv);
        prec = (Button) view.findViewById(R.id.prec);

        //declaration des RadiButton
        rd_verb = (RadioButton) view.findViewById(R.id.rd_vv);
        rd_psy = (RadioButton) view.findViewById(R.id.rd_vps);
        rd_phy = (RadioButton) view.findViewById(R.id.rd_vph);
        rd_sex = (RadioButton) view.findViewById(R.id.rd_vs);

        rd_matin = (RadioButton) view.findViewById(R.id.rd_mt);
        rd_midi = (RadioButton) view.findViewById(R.id.rd_ap);
        rd_soir = (RadioButton) view.findViewById(R.id.rd_sr);
        //declaration des RadiGroup
        heurs = (RadioGroup) view.findViewById(R.id.heure);
        types = (RadioGroup) view.findViewById(R.id.type);

        if (Locale.getDefault().getLanguage().equals("ar")) {
            suvi.setBackgroundResource(R.drawable.next1);
            prec.setBackgroundResource(R.drawable.next2);
        } else {
            suvi.setBackgroundResource(R.drawable.next2);
            prec.setBackgroundResource(R.drawable.next1);

        }
        int restoredtype = sherd.getInt("TypeID", 0);
        int restoredhoraire = sherd.getInt("HoraireID", 0);

        if (restoredtype != 0) {
            int type = sherd.getInt("TypeID", 0);
            types.check(type);
            checktyp = true;
        }
        if (restoredhoraire != 0) {
            int heur = sherd.getInt("HoraireID", 0);
            heurs.check(heur);
            checkheur = true;
        }
        prec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (Locale.getDefault().getLanguage().equals("ar")) {
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                }else {
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                }
                transaction.replace(R.id.fragment_container, new BalaghFragment()).commit();
            }
        });

        suvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkheur && checktyp ) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    if (Locale.getDefault().getLanguage().equals("ar")) {
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                    }else {
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    }
                    transaction.replace(R.id.fragment_container, new Balagh3Fragment()).commit();
                } else {
                    Toast.makeText(getContext(), R.string.remplir_formulaire, Toast.LENGTH_LONG).show();

                }
            }
        });
        rd_verb.setOnClickListener(this);
        rd_psy.setOnClickListener(this);
        rd_phy.setOnClickListener(this);
        rd_sex.setOnClickListener(this);

        rd_matin.setOnClickListener(this);
        rd_midi.setOnClickListener(this);
        rd_soir.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rd_vv:
                checktyp = true;
                editor.putString("Type", rd_verb.getText().toString());
                editor.putInt("TypeID", rd_verb.getId());
                editor.commit();
                break;
            case R.id.rd_vps:
                checktyp = true;
                editor.putString("Type", rd_psy.getText().toString());
                editor.putInt("TypeID", rd_psy.getId());
                editor.commit();
                break;
            case R.id.rd_vph:
                checktyp = true;
                editor.putString("Type", rd_phy.getText().toString());
                editor.putInt("TypeID", rd_phy.getId());
                editor.apply();
                break;
            case R.id.rd_vs:
                checktyp = true;
                editor.putString("Type", rd_sex.getText().toString());
                editor.putInt("TypeID", rd_sex.getId());
                editor.apply();
                break;
            case R.id.rd_mt:
                checkheur = true;
                editor.putString("Horaire", rd_matin.getText().toString());
                editor.putInt("HoraireID", rd_matin.getId());
                editor.commit();
                break;
            case R.id.rd_ap:
                checkheur = true;
                editor.putString("Horaire", rd_midi.getText().toString());
                editor.putInt("HoraireID", rd_midi.getId());
                editor.commit();
                break;
            case R.id.rd_sr:
                checkheur = true;
                editor.putString("Horaire", rd_soir.getText().toString());
                editor.putInt("HoraireID", rd_soir.getId());
                editor.apply();
                break;
        }
    }
}