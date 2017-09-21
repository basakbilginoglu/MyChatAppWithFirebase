package firebaseexample.basak.example.com.firebaseexample;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenuActivity extends AppCompatActivity {

    private Button btnSignInChat;

    private Button btnUpdateUserInfo;

    private Button btnDeleteUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnSignInChat = (Button) findViewById(R.id.btnSignInChat);

        btnUpdateUserInfo = (Button) findViewById(R.id.btnUpdateUserInfo);

        btnDeleteUser = (Button) findViewById(R.id.btnDeleteUser);


        btnSignInChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainMenuActivity.this, ChatActivity.class));

            }
        });

        btnUpdateUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainMenuActivity.this, UpdateMenuActivity.class));

            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                if (firebaseUser != null) {

                    firebaseUser.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainMenuActivity.this, "Kullanıcı Silindi", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("ERROR", "Kullanıcı Silinemedi.");
                            }
                        }
                    });
                } else {
                    Log.e("ERROR", "User is NULL");
                }
            }
        });


    }
}
