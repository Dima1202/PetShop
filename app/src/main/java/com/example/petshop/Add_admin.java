package com.example.petshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add_admin extends AppCompatActivity {

TextView visitLogin;
EditText edtName, edtEmail, edtPass;
Button btnregister;
boolean valid= true;

FirebaseAuth fireAuth;
FirebaseFirestore fireStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        fireAuth=FirebaseAuth.getInstance();
        fireStore=FirebaseFirestore.getInstance();

        visitLogin = findViewById(R.id.txvLogin);
        edtName = findViewById(R.id.edt_txtName);
        edtEmail = findViewById(R.id.edt_txtEmail);
        edtPass = findViewById(R.id.edt_txtPass);
        btnregister = findViewById(R.id.btnReg);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              checkField(edtName);
              checkField(edtEmail);
              checkField(edtPass);

              if (valid){
                  fireAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                      @Override
                      public void onSuccess(AuthResult authResult) {
                          FirebaseUser user = fireAuth.getCurrentUser();
                          Toast.makeText(Add_admin.this, "Admin account is created Successfully", Toast.LENGTH_SHORT).show();
                          DocumentReference df= fireStore.collection("User").document(user.getUid());
                          Map<String, Object>userInfo= new HashMap<>();
                          userInfo.put("Name",edtName.getText().toString());
                          userInfo.put("UserEmail", edtEmail.getText().toString());
                          userInfo.put("Admin","1");
                          df.set(userInfo);
                          startActivity(new Intent(getApplicationContext(), login_activity.class));
                          finish();
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Toast.makeText(Add_admin.this, "Failed to create an account", Toast.LENGTH_SHORT).show();
                      }
                  });
              }
            }
        });
    }

    public  void visitLogin(){
        Intent visitLogin = new Intent(getApplicationContext(), login_activity.class);
        startActivity(visitLogin);
    }



    public boolean checkField(EditText editTextValue){
        if (editTextValue.getText().toString().isEmpty()){
            editTextValue.setError("Error");
            valid=false;
        }else {
            valid=true;
        }
        return valid;
    };



}