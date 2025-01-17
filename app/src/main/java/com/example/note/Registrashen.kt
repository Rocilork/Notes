package com.example.note

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.note.Class.User
import com.example.note.Object.SB
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.Realtime
import kotlinx.coroutines.launch
import org.json.JSONException

class Registrashen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrashen)

        val regFIO: EditText = findViewById(R.id.regFIO)
        val regEmail: EditText = findViewById(R.id.regEmail)
        val regPassword: EditText = findViewById(R.id.regPassword)
        val regButton: Button = findViewById(R.id.regButton)
        val avt: Button = findViewById(R.id.avt)

        //Регистрация
        regButton.setOnClickListener {
            val fioR = regFIO.text.toString()
            val emailR = regEmail.text.toString()
            val passwordR = regPassword.text.toString()

            val avtorizashen = Intent(this, Avtorizashen::class.java)


            try {
                if(fioR == "" || emailR == "" || passwordR == ""){
                    Toast.makeText(applicationContext, "Поля не все заполнены!", Toast.LENGTH_LONG).show()
                } else if(emailR.toBoolean() == emailR.isEmailValid()) {
                    Toast.makeText(applicationContext, "Почта некорректна!", Toast.LENGTH_LONG).show()
                } else if(passwordR.length <= 5){
                    Toast.makeText(applicationContext, "Пароль не меньше шести символов!", Toast.LENGTH_SHORT).show()
                } else if(passwordR.length >= 6){
                    //Корутина
                    lifecycleScope.launch {
                        try {
                            val user = SB.getClient().auth.signUpWith(Email) {
                                email = emailR
                                password = passwordR
                            }

                            val userID = SB.getClient().auth.retrieveUserForCurrentSession(updateSession = true).id

                            val city = User(ID_пользователя = userID, ФИО = fioR)
                            SB.getClient().from("Пользователь").insert(city)

                            Toast.makeText(applicationContext, "Вы зарегистрированы!", Toast.LENGTH_LONG).show()
                            startActivity(avtorizashen)
                        }catch (ex: JSONException){
                            Log.e("!!!", ex.message.toString())
                        }
                    }
                }
            }catch (ex: JSONException){
                Log.e("!!!", ex.message.toString())
            }
        }

        //Перейти к авторизации
        avt.setOnClickListener{
            val intentAvt = Intent(this, Avtorizashen::class.java)
            startActivity(intentAvt)
        }
    }
    //Проверяем правильно ли указана почта
    fun String.isEmailValid(): Boolean{
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}