package com.example.amicodingpari;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import java.util.stream.IntStream;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class KhojActivity extends AppCompatActivity {
    private EditText inputNumberEditText, khojNumberEditText;
    private Button  khojButton;
    private TextView resultText;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoj);
        inputNumberEditText = findViewById(R.id.inputNumberEditTextId);
        khojNumberEditText = findViewById(R.id.khojEditTextId);
        khojButton = findViewById(R.id.khojButtonId);
        resultText = findViewById(R.id.resultTextViewId);
        databaseReference = FirebaseDatabase.getInstance().getReference("number");

        khojButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();

            }

            private void saveData() {
                String number = inputNumberEditText.getText().toString().trim();
                String[] parts = number.split(",");
                int[] ints = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    ints[i] = Integer.parseInt(parts[i]);
                }
                Arrays.sort(parts);

                String key = databaseReference.push().getKey();
                Number number1 = new Number(number);

                databaseReference.child(key).setValue(number1);
                Toast.makeText(getApplicationContext(),"Data is saved",Toast.LENGTH_SHORT).show();

                String searchnum = khojNumberEditText.getText().toString().trim();
                int scnum = Integer.parseInt(searchnum);
                boolean found = false;


                for(int x : ints){
                    if(x == scnum){
                        found = true;
                        break;

                    }


                }
                if(found){
                    resultText.setText("Result:"+found);
                    // Toast.makeText(getApplicationContext(),"True",Toast.LENGTH_SHORT).show();
                }

                else
                    resultText.setText("Result:"+found);
                // Toast.makeText(getApplicationContext(),"False",Toast.LENGTH_SHORT).show();



            }
        });
    }
}