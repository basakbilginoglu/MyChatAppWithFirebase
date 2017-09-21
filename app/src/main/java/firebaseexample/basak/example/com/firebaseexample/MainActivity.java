package firebaseexample.basak.example.com.firebaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {
    Intent intent;
    private Button btnSave;

    private Button btnLogin;

    private EditText editUserName;

    private EditText editUserPassword;

    //db işlemleri için kullanılacaktır.
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    //auth işlemleri için kullenılacaktır.
    private FirebaseAuth mAuth ;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views

        editUserName= (EditText) findViewById(R.id.editUserName);
        editUserPassword = (EditText) findViewById(R.id.editUserPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin) ;
        btnSave=(Button)findViewById(R.id.btnSave);

        // Buttons
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.btnSave).setOnClickListener(this);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }
    // [END on_start_check_user]

    private void createAccount(String email, String password) {

        if (!validateForm()) {
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {

        if (!validateForm()) {
            return;
        }
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("başarıyla giriş yapıldı");
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(MainActivity.this, MainMenuActivity.class));
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            //  Intent intent=new Intent(MainActivity.this,MainMenuActivity.class);
                            //startActivity(intent);
                        }

                        else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                            updateUI(null);

                        }
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }

                });
        // [END sign_in_with_email]

    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        // Disable button
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(MainActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = editUserName.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editUserName.setError("Required.");
            valid = false;
        } else {
            editUserName.setError(null);
        }

        String password = editUserPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editUserPassword.setError("Required.");
            valid = false;
        } else {
            editUserPassword.setError(null);
        }

        return valid;
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnSave) {
            createAccount(editUserName.getText().toString(), editUserPassword.getText().toString());
        } else if (i == R.id.btnLogin) {
            signIn(editUserName.getText().toString(), editUserPassword.getText().toString());
        }
    }

}
