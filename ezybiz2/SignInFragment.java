package com.example.ezybiz2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInFragment extends Fragment {
    public SignInFragment(){

    }

    private TextView newUser;
    private FrameLayout parentFrameLayout;
    private EditText email;
    private EditText password;
    private Button sign_in_btn;
    private TextView forgot_password;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.fragment_sign_in,container,false);
        newUser = view.findViewById(R.id.textview_newUser);
        parentFrameLayout = getActivity().findViewById(R.id.reg_frameLayout);
        email = view.findViewById(R.id.emailAdd);
        password = view.findViewById(R.id.editTextTextPassword);
        sign_in_btn = view.findViewById(R.id.sign_in_btn);
        forgot_password = view.findViewById(R.id.forgot_password);
        firebaseAuth = FirebaseAuth .getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ResetPasswordFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sign_in_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                check_Email_Password();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment).commit();
    }

    private void checkInputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                sign_in_btn.setEnabled(true);
                sign_in_btn.setTextColor(Color.rgb(255,255,255));
            }else{
                sign_in_btn.setEnabled(false);
                sign_in_btn.setTextColor(Color.argb(50,255,255,255));
            }
        }else{
            sign_in_btn.setEnabled(false);
            sign_in_btn.setTextColor(Color.argb(50,255,255,255));
        }
    }
    private void check_Email_Password(){
        if(email.getText().toString().matches(emailPattern)){
            if(password.length()>=6){
                sign_in_btn.setEnabled(false);
                sign_in_btn.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Logging in...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),Customer_Dashboard.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else{
                            sign_in_btn.setEnabled(true);
                            sign_in_btn.setTextColor(Color.rgb(255,255,255));
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getActivity(), "Incorrect Email or Password!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(), "Incorrect Email or Password!", Toast.LENGTH_SHORT).show();
        }
    }

}
