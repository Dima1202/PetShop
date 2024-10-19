package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Customer_Info extends AppCompatActivity {

    EditText edtName, edtContactNo, edtAdd, edtEmail;
    Button btnSave;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        edtName = findViewById(R.id.edtName);
        edtContactNo = findViewById(R.id.edtContactNo);
        edtAdd = findViewById(R.id.edt_Add);
        edtEmail = findViewById(R.id.edtEmail);
        btnSave = findViewById(R.id.btn_up);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        // Fetch and display user information
        fetchUserInfo();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update user information
                updateUserInfo();
            }
        });
    }

    private void fetchUserInfo() {
        FirebaseUser user = fAuth.getCurrentUser();
        if (user != null) {
            DocumentReference userRef = fStore.collection("Users").document(user.getUid());

            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String name = documentSnapshot.getString("Name");
                    String contactNo = documentSnapshot.getString("ContactNo");
                    String address = documentSnapshot.getString("Address");
                    String email = documentSnapshot.getString("userEmail");

                    edtName.setText(name);
                    edtContactNo.setText(contactNo);
                    edtAdd.setText(address);
                    edtEmail.setText(email);
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(Customer_Info.this, "Failed to fetch user information", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void updateUserInfo() {
        FirebaseUser user = fAuth.getCurrentUser();
        if (user != null) {
            DocumentReference userRef = fStore.collection("Users").document(user.getUid());

            String name = edtName.getText().toString().trim();
            String contactNo = edtContactNo.getText().toString().trim();
            String address = edtAdd.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("Name", name);
            userInfo.put("ContactNo", contactNo);
            userInfo.put("Address", address);
            userInfo.put("userEmail", email);

            userRef.update(userInfo).addOnSuccessListener(aVoid -> {
                Toast.makeText(Customer_Info.this, "User information updated successfully", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(Customer_Info.this, "Failed to update user information", Toast.LENGTH_SHORT).show();
            });
        }
    }
}