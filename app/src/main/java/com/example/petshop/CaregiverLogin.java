package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CaregiverLogin extends AppCompatActivity {

    EditText edtEmail, edtPass;
    Button Login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_login);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edttxtEmail);
        edtPass = findViewById(R.id.edtxtPass);
        Login = findViewById(R.id.btn_Login1);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPass.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(CaregiverLogin.this, "Login successful", Toast.LENGTH_SHORT).show();

                        // Start the caregiver dashboard activity
                        startCaregiverDashboardActivity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(CaregiverLogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void startCaregiverDashboardActivity() {
        Intent intent = new Intent(this, Caregiver_Menu.class);
        startActivity(intent);
        finish();
    }
}