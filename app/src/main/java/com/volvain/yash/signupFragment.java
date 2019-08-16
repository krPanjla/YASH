package com.volvain.yash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class signupFragment extends Fragment {
    EditText name;
    EditText phone;
    EditText password;
    EditText confirmPassword;
    Button submit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.fragment_signup,null);
        name=(EditText) v.findViewById(R.id.name);
        phone=(EditText)v.findViewById(R.id.phone);
        password=(EditText)v.findViewById(R.id.password);
        confirmPassword=(EditText)v.findViewById(R.id.confirmPassword);
        submit=(Button)v.findViewById(R.id.register);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
       return v;
    }
    public void signup(){
        String pass=password.getText().toString();
        Long Phone =Long.parseLong(phone.getText().toString());
        if(pass.equals(confirmPassword.getText().toString())){
            Data data = new Data.Builder()
                    .putString("password", pass)
                    .putString("name",name.getText().toString())
                    .putLong("phone",Phone)
                    .build();

            OneTimeWorkRequest Work =
                    new OneTimeWorkRequest.Builder(Signup_Server.class)
                            .setInputData(data)
                            // .setInputData(Name)
                            //.setInputData(Phone)
                            .build();
            WorkManager.getInstance().enqueue(Work);
            WorkManager.getInstance().getWorkInfoByIdLiveData(Work.getId())
                    .observe( this, new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(@Nullable WorkInfo workInfo) {
                            if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                Toast.makeText(signupFragment.this.getContext(),"Signup Sucessful!",Toast.LENGTH_LONG).show();
                            }
                            else if (workInfo != null && workInfo.getState() == WorkInfo.State.FAILED) {
                                Toast.makeText(signupFragment.this.getContext(),"User Already Exists",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else Toast.makeText(this.getContext(),"Password Match Incorrect",Toast.LENGTH_LONG).show();
    }
}
