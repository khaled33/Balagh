package org.credif.balagh;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Balagh6Fragment extends Fragment {

    final static String URL="http://www.ballagh.tn/ws/upload.php";
    SharedPreferences sherd;
    SharedPreferences.Editor editor;
    Button modif, valid;
    String declareur,sexe_victime,tranche_age_victime,type_violence,horaire,transport,sexe_agresseur,tranche_age_agresseur,gouvert="";
    String email = null;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_balagh6, container, false);

        sherd = getContext().getSharedPreferences("file", Context.MODE_PRIVATE);
        editor = sherd.edit();
        editor.putInt("Page", 6);
        editor.apply();

        modif = (Button) view.findViewById(R.id.button_modifi);
        valid = (Button) view.findViewById(R.id.button_valider);

        Pattern gmailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(getContext()).getAccounts();
        for (Account account : accounts) {
            if (gmailPattern.matcher(account.name).matches()) {
                email = account.name;
            }
        }
        declareur=sherd.getString("declare","empty");
        sexe_victime=sherd.getString("sexe_victime","empty");
        tranche_age_victime=sherd.getString("Tranche_ageVict","empty");
        type_violence=sherd.getString("Type","empty");
        horaire=sherd.getString("Horaire","empty");
        transport=sherd.getString("Transport","empty");
        sexe_agresseur=sherd.getString("Sexe_agresseur","empty");
        tranche_age_agresseur=sherd.getString("Tranche_ageAgrr","empty");
        final Set<String> gouvernements= sherd.getStringSet("Gouvernement", null);
        ArrayList<String> items= null;
        if (gouvernements != null) {
            items = new ArrayList<String>(gouvernements);
            for (String id : items) {
                gouvert +="-"+id+"\n";
            }
        }
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
;
                                if (!response.isEmpty()) {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getContext(), getString(R.string.l_op_ration_balagh_est_termin_avec_succes), Toast.LENGTH_LONG).show();
                                    resetvalue();
                                    editor.putInt("Page", 0);
                                    editor.apply();
                                    modif.setVisibility(View.INVISIBLE);
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.fragment_container, new ContactFragment()).commit();
                                } else {
                                    Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                                Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new Hashtable<String, String>();

                        params.put("declareur", declareur);
                        params.put("sex_vict", sexe_victime);
                        params.put("age_vict", tranche_age_victime);
                        params.put("type_transport", transport);
                        params.put("type_violence", type_violence);
                        params.put("gouvernorat", gouvert);
                        params.put("date", horaire);
                        params.put("email", email);
                        params.put("sex_agress", sexe_agresseur);
                        params.put("age_agress", tranche_age_agresseur);

                        return params;
                    }
                };
                //Creating a Request Queue
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }
        });
        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (Locale.getDefault().getLanguage().equals("ar")) {
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                } else {
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                }
                transaction.replace(R.id.fragment_container, new Balagh5Fragment()).commit();
            }
        });
        return view;
    }
    public void resetvalue() {
        editor.remove("declare");
        editor.remove("sexe_victime");
        editor.remove("Tranche_ageVict");
        editor.remove("Type");
        editor.remove("Horaire");
        editor.remove("Transport");
        editor.remove("Sexe_agresseur");
        editor.remove("Tranche_ageAgrr");
        editor.remove("Gouvernement");
        editor.remove("Sexe_agresseurID");
        editor.remove("Tranche_ageAgrrID");
        editor.remove("TransportID");
        editor.remove("TypeID");
        editor.remove("declareID");
        editor.remove("declareID");
        editor.remove("Tranche_ageVictID");
        editor.apply();
    }
}
