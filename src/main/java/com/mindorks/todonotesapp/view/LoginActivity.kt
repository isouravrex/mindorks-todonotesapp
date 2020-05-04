package com.mindorks.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.mindorks.todonotesapp.utils.AppConstant
import com.mindorks.todonotesapp.utils.PrefConstant
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.utils.StotreSession

class LoginActivity : AppCompatActivity() {
    lateinit var editTextFullName: EditText
    lateinit var editTextUserName: EditText
    lateinit var buttonLogin: MaterialButton
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)
        buttonLogin = findViewById(R.id.buttonLogin)
        setupSharedPreferences()
        val clickAction = View.OnClickListener {
            val fullname = editTextFullName.getText().toString()
            val username = editTextUserName.getText().toString()
            if (!TextUtils.isEmpty(fullname) && !TextUtils.isEmpty(username)) {
                val intent = Intent(this@LoginActivity, MyNotes::class.java)
                intent.putExtra(AppConstant.FULL_NAME, fullname)
                startActivity(intent)
                saveLoginStatus()
                saveFullName(fullname)
            } else {
                Toast.makeText(this@LoginActivity, "Fullname and Username Can't be Empty!", Toast.LENGTH_SHORT).show()
            }
        }
        buttonLogin.setOnClickListener(clickAction)
    }

    private fun saveFullName(fullname: String) {
//        editor = sharedPreferences!!.edit()
        StotreSession.write(PrefConstant.FULL_NAME, fullname)
//        editor.apply()
    }

    private fun saveLoginStatus() {
//        editor = sharedPreferences!!.edit()
        StotreSession.write(PrefConstant.IS_LOGGED_IN, true)
//        editor.apply()
    }

    private fun setupSharedPreferences() {
        StotreSession.init(this)
    }
}