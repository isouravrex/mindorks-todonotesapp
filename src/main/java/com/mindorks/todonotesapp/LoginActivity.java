package com.mindorks.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import static com.mindorks.todonotesapp.PrefConstant.IS_LOGGED_IN;

public class LoginActivity extends AppCompatActivity {

    EditText editTextFullName, editTextUserName;
    MaterialButton buttonLogin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        editTextFullName=findViewById(R.id.editTextFullName);
        editTextUserName=findViewById(R.id.editTextUserName);
        buttonLogin=findViewById(R.id.buttonLogin);
        setupSharedPreferences();



        View.OnClickListener clickAction=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname = editTextFullName.getText().toString();
                String username = editTextUserName.getText().toString();

                if (!TextUtils.isEmpty(fullname) && !TextUtils.isEmpty(username)) {
                    Intent intent = new Intent(LoginActivity.this, MyNotes.class);
                    intent.putExtra(AppConstant.FULL_NAME, fullname);
                    startActivity(intent);
                    saveLoginStatus();
                    saveFullName(fullname);

                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Fullname and Username Can't be Empty!", Toast.LENGTH_SHORT).show();
                }

                }
            };


                buttonLogin.setOnClickListener(clickAction);
    }

    private void saveFullName(String fullname) {

        editor = sharedPreferences.edit();
        editor.putString(PrefConstant.FULL_NAME,fullname);
        editor.apply();

    }

    private void saveLoginStatus() {
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefConstant.IS_LOGGED_IN,true);
        editor.apply();
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
    }
}
