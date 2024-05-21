package com.example.pervasivecomputingproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    Button googleauth;
    FirebaseAuth auth;
    FirebaseDatabase db ;
    GoogleSignInOptions gso ;
    GoogleSignInClient mSignInClient;
    private static final String ANONYMOUS = "Anonymous";
    private static final int RC_SIGN_IN = 9001;
    private DBhelper dbHelper = new DBhelper(SignIn.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        Button login = findViewById(R.id.sign_in_login_btn);
        Button signUp = findViewById(R.id.sign_in_sign_up_btn);
        Button forgetPassword = findViewById(R.id.sign_in_forget_password);
        //CheckBox rememberMe = findViewById(R.id.sign_up_remember_me);
        //SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        // boolean isRememberMeEnabled = preferences.getBoolean("rememberMe", false);



        EditText editTextEmail = findViewById(R.id.sign_in_email);
        EditText editTextPassword = findViewById(R.id.sign_in_password);
        googleauth = findViewById(R.id.googleee);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mSignInClient = GoogleSignIn.getClient(this, gso);
        CheckBox chkBoxRememberMe = findViewById(R.id.sign_up_remember_me);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

// Use the same key for "Remember Me" checkbox and email/password storage
        chkBoxRememberMe.setChecked(preferences.getBoolean("remember", false)); // Set checkbox state

        // Set last entered email and password if "Remember Me" is checked
        if (chkBoxRememberMe.isChecked()) {
            String lastEmail = preferences.getString("email", "");
            String lastPassword = preferences.getString("password", "");
            editTextEmail.setText(lastEmail);
            editTextPassword.setText(lastPassword);
        }

        chkBoxRememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("remember", isChecked);
            if (!isChecked) {
                // Clear saved email and password if "Remember Me" is unchecked
                editor.remove("email");
                editor.remove("password");
            } else {
                // Save last entered email and password
                editor.putString("email", editTextEmail.getText().toString());
                editor.putString("password", editTextPassword.getText().toString());
            }
            editor.apply();
        });






        editTextPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (editTextPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_icon, 0);
                        } else {
                            editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_icon, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
/*
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("rememberMe", isChecked);
                if (!isChecked) {
                    // Clear saved email and password if "Remember Me" is unchecked
                    editor.remove("email");
                    editor.remove("password");
                } else {
                    // Save last entered email and password
                    editor.putString("email", editTextEmail.getText().toString());
                    editor.putString("password", editTextPassword.getText().toString());
                }
                editor.apply();
            });


});
*/

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignIn.this, Regestiration.class);
                startActivity(i);
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignIn.this, Forgetpass.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();



                if (email.equals("")) {
                    // Show toast message
                    Toast.makeText(getApplicationContext(), "Missing email.", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    // Show toast message
                    Toast.makeText(getApplicationContext(), "Missing password.", Toast.LENGTH_SHORT).show();
                } else {
                    // Firebase Authentication
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Login successful, navigate to home activity
                                        Intent intent = new Intent(SignIn.this, home.class);
                                        startActivity(intent);
                                        finish(); // Optional: Clear activity stack
                                    } else {
                                        // Login failed, show error message
                                        // Toast.makeText(getApplicationContext(), "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        boolean isValid = dbHelper.checkCredentials(email, password);
                                        if (isValid) {
                                            // Login successful, navigate to home activity
                                            Intent intent = new Intent(SignIn.this, home.class);
                                            startActivity(intent);
                                            finish(); }
                                        else {
                                            // Login failed, show error message
                                            Toast.makeText(getApplicationContext(), "Invalid email or password.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });

                    // Check credentials in SQLite database


//

                }
            }
        });

        googleauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }


    private void signIn(){

        Intent intee = mSignInClient.getSignInIntent();
        startActivityForResult(intee,RC_SIGN_IN);
    }
    /*
    private void attemptAutoLogin() {
        // Check if the user is already signed in
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // User is already signed in, navigate to the main activity
            startActivity(new Intent(this, home.class));
            finish();
        } else {
            // Attempt to sign in the user automatically (e.g., using saved credentials)
            // This part depends on how you're handling user authentication
            startActivity(new Intent(this, SignIn.class));
            finish();
        }

     */

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount acc = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(acc);
            } catch (ApiException e) {
                Log.w(TAG,"Google sign in failed",e);
            }
        }
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount acc){

        FirebaseUser currentUser = auth.getCurrentUser();
        /*
        if (currentUser != null) {
            // User is already signed in, proceed to home activity
            Toast.makeText(SignIn.this, "Already logged in as " + currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignIn.this, home.class));
            finish();
            return; // Exit the method to avoid unnecessary sign-in attempt
        }
*/
        AuthCredential credential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            Toast.makeText(SignIn.this, "Successfully logged in as " + user.getDisplayName(), Toast.LENGTH_SHORT).show();

                            String username = getusername();
                            String userpicURL = getUserpicURL();
                            Intent intent = new Intent(SignIn.this, home.class);
                            intent.putExtra("username", username);
                            intent.putExtra("userpicURL", userpicURL);
                            startActivity(new Intent(SignIn.this, home.class));
                            finish();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(SignIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                    }
                });
    }
    private String getusername() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }
        return ANONYMOUS;
    }

    private String getUserpicURL(){
        FirebaseUser user = auth.getCurrentUser();
        if (user!=null && user.getPhotoUrl() != null) {
            return user.getPhotoUrl().toString();
        }
        return null ;
    }
}