package com.volvain.yash;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class loginFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View v= inflater.inflate(R.layout.fragment_login,null);;
        Button b=(Button)v.findViewById(R.id.createAccount);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignup();;
            }
        });
        return v;


    }
    public void openSignup(){
        Fragment fragment = new signupFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        //fragmentTransaction.addToBackStack();
        fragmentTransaction.commit();
    }
}
