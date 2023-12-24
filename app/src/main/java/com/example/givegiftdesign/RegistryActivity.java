package com.example.givegiftdesign;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
// import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.givegiftdesign.registryfirebase.SaltPassword;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.givegiftdesign.registryfirebase.HelperClass;

public class RegistryActivity extends AppCompatActivity {

    EditText signupName, signupUsername, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_registry);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(view -> {

            database = FirebaseDatabase.getInstance("https://givegift-241db-default-rtdb.europe-west1.firebasedatabase.app/");
            reference = database.getReference("users");

            String name = signupName.getText().toString();
            String email = signupEmail.getText().toString();
            String username = signupUsername.getText().toString();
            String password = signupPassword.getText().toString();

            SaltPassword sp;
            try {
                sp = new SaltPassword();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            String encrypted_password=sp.encrypt(password);

            HelperClass helperClass = new HelperClass(name, email, username, encrypted_password);
            reference.child(username).setValue(helperClass);

            Toast.makeText(RegistryActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegistryActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        loginRedirectText.setOnClickListener(view -> {
            Intent intent = new Intent(RegistryActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}

