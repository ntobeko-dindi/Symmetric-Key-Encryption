package com.example.cypherus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import soup.neumorphism.NeumorphButton;

public class MainActivity extends AppCompatActivity {

    private EditText input_text;
    private EditText key_input_text;
    private TextView output_Text;
    private Encryption encryption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NeumorphButton submit_btn = findViewById(R.id.submit_btn);
        input_text = findViewById(R.id.input_text);
        key_input_text = findViewById(R.id.key_input_text);
        output_Text = findViewById(R.id.output_txt);
        SwitchMaterial is_encrypting = findViewById(R.id.is_encrypting);
        SwitchMaterial is_decrypting = findViewById(R.id.is_decrypting);

        output_Text.setTextIsSelectable(true);

        is_encrypting.setOnCheckedChangeListener((compoundButton, b) -> {
            if(is_encrypting.isChecked()){
                is_encrypting.setChecked(true);
                is_decrypting.setChecked(false);
            }else{
                is_encrypting.setChecked(false);
                is_decrypting.setChecked(true);
            }
        });

        is_decrypting.setOnCheckedChangeListener((compoundButton, b) -> {
            if(is_decrypting.isChecked()){
                is_decrypting.setChecked(true);
                is_encrypting.setChecked(false);
            }else{
                is_decrypting.setChecked(false);
                is_encrypting.setChecked(true);
            }
        });

        submit_btn.setOnClickListener(view -> {
            String key = key_input_text.getText().toString().trim();
            String text = input_text.getText().toString();
            boolean isEncrypting = is_encrypting.isChecked();
            if(text.isEmpty()){
                Toast.makeText(this, "Please enter the text to " + (isEncrypting ? "encrypt" : "decrypt"), Toast.LENGTH_LONG).show();
            }
            else if(key.isEmpty()){
                Toast.makeText(this, "Please enter the encryption key", Toast.LENGTH_LONG).show();
            }else{
            encryption = new Encryption(key, text, isEncrypting);
            if (isEncrypting)
                output_Text.setText(encryption.encrypt());
            else
                output_Text.setText(encryption.decrypt());
            }
        });
    }
}