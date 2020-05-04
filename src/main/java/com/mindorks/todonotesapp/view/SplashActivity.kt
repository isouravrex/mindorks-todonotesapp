package com.mindorks.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.onboarding.OnBoardingActivity
import com.mindorks.todonotesapp.utils.PrefConstant


class SplashActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPreference()
        checkLoginStatus()
        getFCMToken()
    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("SplashActivity", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result!!.token

                    // Log and toast
                    Log.d("SplashActivity", token)
                    Toast.makeText(baseContext,token, Toast.LENGTH_SHORT).show()
                })
    }

    private fun setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        val isBoardingSuccess = sharedPreferences.getBoolean(PrefConstant.ON_BOARDED_SUCCESFULLY,false)
        val isloggedin = sharedPreferences!!.getBoolean(PrefConstant.IS_LOGGED_IN, false)
        if (isloggedin) {
            val intent = Intent(this@SplashActivity, MyNotes::class.java)
            startActivity(intent)
        } else {
            if(isBoardingSuccess) {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent)
            }
        }
    }
}