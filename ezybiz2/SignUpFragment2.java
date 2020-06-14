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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment2 extends Fragment {
    public SignUpFragment2(){

    }

    private TextView existingUser;
    private FrameLayout parentFrameLayout;
    private EditText et_email;
    private EditText et_name;
    private EditText et_password;
    private Button sign_up_btn;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebasefirestore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up,container,false);
        existingUser = view.findViewById(R.id.existingUser);
        parentFrameLayout = getActivity().findViewById(R.id.reg_frameLayout);
        et_email = view.findViewById(R.id.sign_up_email2);
        et_name = view.findViewById(R.id.sign_up_name2);
        et_password = view.findViewById(R.id.sign_up_password2);
        sign_up_btn = view.findViewById(R.id.sign_up_btn2);
        firebaseAuth = FirebaseAuth.getInstance();
        firebasefirestore = FirebaseFirestore.getInstance();
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.register_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        existingUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        et_name.addTextChangedListener(new TextWatcher() {
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
        et_email.addTextChangedListener(new TextWatcher() {
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
        et_password.addTextChangedListener(new TextWatcher() {
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

        sign_up_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                check_Email_Password();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment).commit();
    }
    private void checkInputs(){
        if(!TextUtils.isEmpty(et_name.getText())){
            if(!TextUtils.isEmpty(et_email.getText())){
                if(!TextUtils.isEmpty(et_password.getText())){
                    if(et_password.length()>=6){
                        sign_up_btn.setEnabled(true);
                        sign_up_btn.setTextColor(Color.rgb(255,255,255));
                    }else{
                        sign_up_btn.setEnabled(false);
                        sign_up_btn.setTextColor(Color.argb(50,255,255,255));
                    }
                }else{
                   sign_up_btn.setEnabled(false);
                   sign_up_btn.setTextColor(Color.argb(50,255,255,255));
                }
            }else{
                    sign_up_btn.setEnabled(false);
                    sign_up_btn.setTextColor(Color.argb(50,255,255,255));

            }
        }else{
                sign_up_btn.setEnabled(false);
                sign_up_btn.setTextColor(Color.argb(50,255,255,255));

        }
    }
    private void check_Email_Password(){
        if(et_email.getText().toString().matches(emailPattern)){
            sign_up_btn.setEnabled(false);
            sign_up_btn.setTextColor(Color.argb(50,255,255,255));
            firebaseAuth.createUserWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Map<String, Object> data = new HashMap<>();
                        data.put("Name",et_name.getText().toString());
                        data.put("Email",et_email.getText().toString());
                        data.put("Password",et_password.getText().toString());

                        firebasefirestore.collection("USERS").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful()){
                                    Intent main_intent = new Intent(getActivity(),MainActivity.class);
                                    startActivity(main_intent);
                                    getActivity().finish();
                                }else{
                                    sign_up_btn.setEnabled(false);
                                    sign_up_btn.setTextColor(Color.rgb(255,255,255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }else{
                        sign_up_btn.setEnabled(false);
                        sign_up_btn.setTextColor(Color.rgb(255,255,255));
                        String error = task.getException().getMessage();
                        Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            et_email.setError("Invalid Email!");
        }
    }
}
