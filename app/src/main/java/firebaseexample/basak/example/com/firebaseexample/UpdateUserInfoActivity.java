package firebaseexample.basak.example.com.firebaseexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateUserInfoActivity extends AppCompatActivity {

    private EditText editOldInfo;

    private EditText editNewInfo;

    private Button btnUpdateFromOldToNew;

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private boolean emailOrPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        editOldInfo = (EditText) findViewById(R.id.editOldInfo);

        editNewInfo = (EditText) findViewById(R.id.editNewInfo);

        btnUpdateFromOldToNew = (Button) findViewById(R.id.btnUpdateFromOldToNew);

        String type = getIntent().getExtras().get("type").toString();


        switch (type) {
            case "E":

                editOldInfo.setEnabled(false);
                editOldInfo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                editOldInfo.setText(firebaseUser.getEmail());

                editNewInfo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                emailOrPassword = true;
                break;
            case "P":

                editOldInfo.setEnabled(false);

                editNewInfo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                emailOrPassword = false;
                break;
        }

        btnUpdateFromOldToNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!editNewInfo.getText().toString().trim().equals("")) {

                    if (emailOrPassword) {
                        if (firebaseUser != null) {
                            firebaseUser.updateEmail(editNewInfo.getText().toString().trim())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(UpdateUserInfoActivity.this, "Email Güncellenmiştir.", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UpdateUserInfoActivity.this, "Email Güncellenememiştir, Lütfen Email Adresini Kontrol Ediniz.",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            Log.e("ERROR", "User is NULL");
                        }

                    } else {

                        if (firebaseUser != null) {
                            firebaseUser.updatePassword(editNewInfo.getText().toString().trim())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(UpdateUserInfoActivity.this, "Şifre Güncellenmiştir.", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UpdateUserInfoActivity.this, "Şifre Güncellenememiştir, Lütfen Şifre Adresini Kontrol Ediniz.",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            Log.e("ERROR", "User is NULL");
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Lütfen Eski ve Yeni Değerleri Girin", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
