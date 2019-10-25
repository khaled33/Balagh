package org.credif.balagh;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ContactFragment extends Fragment implements View.OnClickListener{

    LinearLayout l2,l3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        l2 = (LinearLayout) view.findViewById(R.id.l2);
        l2.setOnClickListener(this);
        l3 = (LinearLayout) view.findViewById(R.id.l3);
        l3.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.l2 :
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+216 71885322"));
                startActivity(callIntent);
                break;
            case R.id.l3:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new SendcontacteFragment()).commit();
                break;
        }
    }
}
