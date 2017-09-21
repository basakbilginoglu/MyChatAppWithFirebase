package firebaseexample.basak.example.com.firebaseexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import firebaseexample.basak.example.com.firebaseexample.entity.ChatObject;

public class ChatActivity extends AppCompatActivity {

    private ListView listAllMessages;

    private Button btnSendMessage;

    private EditText editMessage;

    private ArrayAdapter arrayAdapter;

    private DatabaseReference databaseReference;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private List<ChatObject> messageDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        listAllMessages = (ListView) findViewById(R.id.listAllMessages);

        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);

        editMessage = (EditText) findViewById(R.id.editMessage);

        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<String>());

        databaseReference = firebaseDatabase.getReference().child("Messages");

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ChatObject chatObject = new ChatObject(firebaseUser.getEmail().substring(0, firebaseUser.getEmail().indexOf("@")),
                        editMessage.getText().toString().trim());

                databaseReference.push().setValue(chatObject);

                editMessage.setText("");

            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                arrayAdapter.add(dataSnapshot.child("sender").getValue().toString() + " : " +
                        dataSnapshot.child("message").getValue().toString());

                messageDatas.add(new ChatObject(dataSnapshot.getKey(),dataSnapshot.child("sender").getValue().toString(),
                        dataSnapshot.child("message").getValue().toString()));

                listAllMessages.setAdapter(arrayAdapter);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e("Changed", "Changed");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("Removed", "Removed");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.e("Movec", "Moves");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", databaseError.getMessage());
            }
        });

        listAllMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {

                new AlertDialog.Builder(ChatActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Güvenli Çıkış")
                        .setMessage("Mesajı Silmek İstediğinize Emin Misiniz? ")
                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                databaseReference.child(messageDatas.get(position).getId())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                dataSnapshot.getRef().removeValue();
                                                arrayAdapter.notifyDataSetChanged();
                                                Toast.makeText(getApplicationContext(), "İŞLEM TAMAMLANDI!", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.e("ERROR",databaseError.getMessage());
                                            }
                                        });

                                messageDatas.remove(position);
                                arrayAdapter.remove(arrayAdapter.getItem(position));


                            }
                        }).setNegativeButton("İptal", null).show();
            }
        });

    }
}
