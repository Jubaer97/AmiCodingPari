package com.example.amicodingpari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText signInEmailEditText, signInPasswordEditText;
    private Button signInButton;
    private TextView signUpTextView;
    private ProgressBar progressBar;
    public ImageView imageView;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        signInEmailEditText = findViewById(R.id.signInEmailTextId);
        signInPasswordEditText = findViewById(R.id.signInPasswordEditTextId);
        signInButton = findViewById(R.id.signInButtonId);
        signUpTextView = findViewById(R.id.signUpTextViewId);
        progressBar = findViewById(R.id.progressbarId);
        //imageView = findViewById(R.id.imageViewId);

        signInButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.signInButtonId:

                userLogin();
                break;

            case R.id.signUpTextViewId:
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void checkEmailVerification() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = user.isEmailVerified();

        if(emailflag){

            startActivity(new Intent(MainActivity.this,KhojActivity.class));
        }
        else{

            Toast.makeText(getApplicationContext(),"verify your email",Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
        }

    }

    private void userLogin() {
        final String email = signInEmailEditText.getText().toString().trim();
        final String password = signInPasswordEditText.getText().toString().trim();


        if(email.isEmpty())
        {
            signInEmailEditText.setError("Enter an email address");
            signInEmailEditText.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signInEmailEditText.setError("Enter a valid email address");
            signInEmailEditText.requestFocus();
            return;
        }


        if(password.isEmpty())
        {
            signInPasswordEditText.setError("Enter a password");
            signInPasswordEditText.requestFocus();
            return;
        }

        if(password.length()<6)
        {

            signInPasswordEditText.setError("Length should be minimum 6");
            signInPasswordEditText.requestFocus();
            return;

        }


        else
            progressBar.setVisibility(View.GONE);



        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if(task.isSuccessful()){

                checkEmailVerification();
            }

            else{
                Toast.makeText(getApplicationContext(),"Login UnSuccessful",Toast.LENGTH_SHORT).show();

            }

        });
    }
}

