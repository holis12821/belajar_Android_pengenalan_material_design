package com.example.belajar_android_pengenalan_material_design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private MaterialEditText userName,emailAddress,password,mobile;
    private RadioGroup radioGroup;
    private CheckBox isExpert, isUser;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fStore;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
       getSupportActionBar().setTitle(R.string.label_Register);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
           }
       });

       /*Define FirebaseAuth*/
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

       /*Define id*/
       userName = findViewById(R.id.username);
       emailAddress = findViewById(R.id.email);
       password = findViewById(R.id.password);
       isExpert = findViewById(R.id.checkExpert);
       isUser = findViewById(R.id.checkUser);
       mobile = findViewById(R.id.mobile);
       radioGroup = findViewById(R.id.radioButton);
       Button registerBtn = findViewById(R.id.register);
       progressBar = findViewById(R.id.progressBar);

       /*Checkbox Logic*/
        isUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    isExpert.setChecked(false);
                }
            }
        });
        isExpert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    isUser.setChecked(false);
                }
            }
        });

       /*Listener Button*/
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_name = userName.getText().toString();
                final String email = emailAddress.getText().toString();
                final String txt_password = password.getText().toString();
                final String txt_mobile = mobile.getText().toString();
                int checkedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selected_gender = radioGroup.findViewById(checkedId);

                /*Conditional If*/
                if (selected_gender == null){
                    Toast.makeText(RegisterActivity.this, "Select gender please", Toast.LENGTH_SHORT).show();
                } else{
                    final String gender  = selected_gender.getText().toString();
                    if (TextUtils.isEmpty(user_name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(txt_password)
                    || TextUtils.isEmpty(txt_mobile)){
                        Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    } else{
                        register(user_name, email, txt_password, txt_mobile, gender);
                    }
                }

            }
        });

        /*Checkbox Validation*/
        if (isExpert.isChecked() || isUser.isChecked()){
            Toast.makeText(this, "Select The Account Type", Toast.LENGTH_SHORT).show();
        }

    }

    private void register(final String user_name, final String email, String txt_password, final String txt_mobile, final String gender) {
        progressBar.setVisibility(View.VISIBLE);
        /*Define FirebaseAuth and Create User with email and password*/
        firebaseAuth.createUserWithEmailAndPassword(email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser rUser = firebaseAuth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    assert rUser != null;
                    String userId = rUser.getUid();
                    DocumentReference df = fStore.collection("Users").document(rUser.getUid());
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("userId", userId);
                    hashMap.put("username", user_name);
                    hashMap.put("email", email);
                    hashMap.put("gender", gender);
                    hashMap.put("mobile", txt_mobile);
                    hashMap.put("imageUrl", "default");
                    /*Specify if the user is admin*/
                    if(isExpert.isChecked()){
                        hashMap.put("isAdmin", "1");
                    }
                    if (isUser.isChecked()){
                        hashMap.put("isUser", "1");
                    }

                    df.set(hashMap);
                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else{
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}