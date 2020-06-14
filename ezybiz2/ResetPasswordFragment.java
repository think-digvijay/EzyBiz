package com.example.ezybiz2;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordFragment extends Fragment {

    private EditText email;
    private Button reset_btn;
    private TextView back;
    private FrameLayout parentFrameLayout;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password,container,false);
        email = view.findViewById(R.id.email_address);
        reset_btn = view.findViewById(R.id.reset_btn);
        back = view.findViewById(R.id.footer);
        parentFrameLayout = getActivity().findViewById(R.id.reg_frameLayout);
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                reset_btn.setEnabled(false);
                reset_btn.setTextColor(Color.argb(50,255,255,255));
                   firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>(){
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       Toast.makeText(getActivity(), "Email Sent Succe  ssfully!", Toast.LENGTH_SHORT).show();
                                   }else{
                                       String error = task.getException().getMessage();
                                       Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                   }
                                   reset_btn.setEnabled(true);
                                   reset_btn.setTextColor(Color.rgb(255,255,255));
                               }
                           });
            }
            });
        }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment).commit();
    }
    private void checkInput(){
        if(!TextUtils.isEmpty(email.getText() )){
            reset_btn.setEnabled(true);
            reset_btn.setTextColor(Color.rgb(255,255,255));
        }else{
            reset_btn.setEnabled(false);
            reset_btn.setTextColor(Color.argb(50,255,255,255));
        }
    }
}