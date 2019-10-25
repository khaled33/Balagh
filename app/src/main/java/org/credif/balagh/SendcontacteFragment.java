package org.credif.balagh;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SendcontacteFragment extends Fragment {

    final static String EMAIL_ADMIN="contact@credif.org.tn";
    EditText email,message,sujet;
    Button envoyer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sendcontacte, container, false);

        email = (EditText) view.findViewById(R.id.email);
        message = (EditText) view.findViewById(R.id.descri);
        sujet = (EditText) view.findViewById(R.id.sujet);

        envoyer = (Button) view.findViewById(R.id.bt_envo);

        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOnline()) {
                    if (valider()) {
                        SendMail sm = new SendMail(getContext(), EMAIL_ADMIN, sujet.getText().toString(),
                                "Email de l'utilisateur : "+email.getText().toString()+ "\n"+"Message: "
                                        +message.getText().toString());
                        sm.execute();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.chek_internet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private boolean valider() {
        String messag=message.getText().toString().trim();
        String mail=email.getText().toString().trim();
        String suj=sujet.getText().toString().trim();
        boolean valide = true;
        if(mail.isEmpty() ) {
            email.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if(!mail.isEmpty() && (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches())) {
            email.setError(getString(R.string.email_invalide));
            valide = false;
        }
        if(suj.isEmpty() ) {
            sujet.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if(messag.isEmpty() ) {
            message.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        return valide;
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
