package com.example.note

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.note.Object.SB
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch
import org.json.JSONException


class Avtorizashen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avtorizashen)

        val avtEmail: EditText = findViewById(R.id.avtEmail)
        val avtPassword: EditText = findViewById(R.id.avtPassword)
        val avtButton: Button = findViewById(R.id.avtButton)
        val reg: Button = findViewById(R.id.reg)

        //Авторизация
        avtButton.setOnClickListener {
            val emailA = avtEmail.text.toString()
            val passwordA = avtPassword.text.toString()

            val glavnai = Intent(this, Glavnai::class.java)

            try{
                if(emailA == "" || passwordA == ""){
                    Toast.makeText(applicationContext, "Поля не все заполнены!", Toast.LENGTH_LONG).show()
                } else{
                    //Корутина
                    lifecycleScope.launch {
                        try {
                            SB.getClient().auth.signInWith(Email) {
                                email = emailA
                                password = passwordA
                            }

                            Toast.makeText(applicationContext, "Вы авторизовались!", Toast.LENGTH_SHORT).show()
                            startActivity(glavnai)
                        }catch (ex: Exception){
                            Toast.makeText(applicationContext, "Такого пользователя нет!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }catch (ex: JSONException){
                Log.e("!!!", ex.message.toString())
            }
        }

        //Перейти к регистрации
        reg.setOnClickListener {
            val intentReg = Intent(this, Registrashen::class.java)
            startActivity(intentReg)
        }
    }
}