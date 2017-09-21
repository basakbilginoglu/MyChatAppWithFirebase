package firebaseexample.basak.example.com.firebaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UpdateMenuActivity extends AppCompatActivity {

    private Button btnUpdateUserEmail;

    private Button btnUpdateUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);

        btnUpdateUserEmail = (Button) findViewById(R.id.btnUpdateUserEmail);

        btnUpdateUserPassword = (Button) findViewById(R.id.btnUpdateUserPassword);

        btnUpdateUserEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateMenuActivity.this, UpdateUserInfoActivity.class);
                intent.putExtra("type", "E");
                startActivity(intent);
            }
        });

        btnUpdateUserPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateMenuActivity.this, UpdateUserInfoActivity.class);
                intent.putExtra("type", "P");
                startActivity(intent);
            }
        });
    }
}
