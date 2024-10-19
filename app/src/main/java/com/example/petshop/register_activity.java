package com.example.petshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register_activity extends AppCompatActivity {

    EditText edtName, edtContactNo, edtAdd, edtEmail, edtPass;
    Button btnReg;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edtname);
        edtContactNo = findViewById(R.id.edtC_no);
        edtAdd = findViewById(R.id.edtAdd);
        edtEmail = findViewById(R.id.txtEmail);
        edtPass = findViewById(R.id.txtPass);
        btnReg = findViewById(R.id.btn_regis);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String contactNo = edtContactNo.getText().toString().trim();
                String address = edtAdd.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPass.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(contactNo) || TextUtils.isEmpty(address) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(register_activity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = fAuth.getCurrentUser();

                        // Set the display name
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();

                        user.updateProfile(profileUpdates);

                        // Store user data in Firestore
                        DocumentReference df = fStore.collection("Users").document(user.getUid());
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("Name", name);
                        userInfo.put("ContactNo", contactNo);
                        userInfo.put("Address", address);
                        userInfo.put("userEmail", email);
                        userInfo.put("isUser", "1");

                        df.set(userInfo);

                        Toast.makeText(register_activity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), login_activity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(register_activity.this, "Failed to create an account: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}


