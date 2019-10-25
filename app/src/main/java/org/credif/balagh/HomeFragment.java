package org.credif.balagh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class HomeFragment extends Fragment {

    ImageView txt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        txt = view.findViewById(R.id.txt_acueil);
        if (Locale.getDefault().getLanguage().equals("ar")) {
            txt.setImageResource(R.drawable.img_home_arabe);
        } else {
            txt.setImageResource(R.drawable.img_home);
        }

        return view;
    }
}
