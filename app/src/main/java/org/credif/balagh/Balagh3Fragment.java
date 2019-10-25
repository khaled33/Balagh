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

public class Balagh3Fragment extends Fragment implements View.OnClickListener {

    SharedPreferences sherd;
    SharedPreferences.Editor editor;
    Button suvi,precd;
    RadioButton rd_bus, rd_metro, rd_train, rd_taxi;
    RadioGroup transport;
    boolean checktrans = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_balagh3, container, false);
        sherd = getContext().getSharedPreferences("file", Context.MODE_PRIVATE);
        editor = sherd.edit();
        editor.putInt("Page", 3);
        editor.apply();

        suvi = (Button) view.findViewById(R.id.suiv);
        precd = (Button) view.findViewById(R.id.prec);

        //declaration des RadiButton
        rd_bus = (RadioButton) view.findViewById(R.id.rd_bus);
        rd_metro = (RadioButton) view.findViewById(R.id.rd_met);
        rd_train = (RadioButton) view.findViewById(R.id.rd_tra);
        rd_taxi = (RadioButton) view.findViewById(R.id.rd_tax);

        //declaration des RadiGroup
        transport = (RadioGroup) view.findViewById(R.id.type);
        if (Locale.getDefault().getLanguage().equals("ar")) {
            suvi.setBackgroundResource(R.drawable.next1);
            precd.setBackgroundResource(R.drawable.next2);
        } else {
            suvi.setBackgroundResource(R.drawable.next2);
            precd.setBackgroundResource(R.drawable.next1);

        }
        int restoredtrans = sherd.getInt("TransportID", 0);

        if (restoredtrans != 0) {
            int trns = sherd.getInt("TransportID", 0);
            transport.check(trns);
            checktrans = true;
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
                transaction.replace(R.id.fragment_container, new Balagh2Fragment()).commit();
            }
        });
        suvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checktrans) {

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    if (Locale.getDefault().getLanguage().equals("ar")) {
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                    }else {
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    }
                    transaction.replace(R.id.fragment_container, new Balagh4Fragment()).commit();
                } else {
                    Toast.makeText(getContext(), R.string.remplir_formulaire, Toast.LENGTH_LONG).show();

                }
            }
        });

        rd_bus.setOnClickListener(this);
        rd_metro.setOnClickListener(this);
        rd_train.setOnClickListener(this);
        rd_taxi.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rd_bus:
                checktrans = true;
                editor.putString("Transport", rd_bus.getText().toString());
                editor.putInt("TransportID", rd_bus.getId());
                editor.commit();
                break;
            case R.id.rd_met:
                checktrans = true;
                editor.putString("Transport", rd_metro.getText().toString());
                editor.putInt("TransportID", rd_metro.getId());
                editor.commit();
                break;
            case R.id.rd_tra:
                checktrans = true;
                editor.putString("Transport", rd_train.getText().toString());
                editor.putInt("TransportID", rd_train.getId());
                editor.apply();
                break;
            case R.id.rd_tax:
                checktrans = true;
                editor.putString("Transport", rd_taxi.getText().toString());
                editor.putInt("TransportID", rd_taxi.getId());
                editor.apply();
                break;
        }
    }
}