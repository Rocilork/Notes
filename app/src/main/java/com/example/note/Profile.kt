package com.example.note

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.note.Class.User
import com.example.note.Object.SB
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import org.json.JSONException

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val buttonExitProfile: Button = findViewById(R.id.buttonExitProfile)
        val saveProfile: Button = findViewById(R.id.saveProfile)
        val yourFIO: EditText = findViewById(R.id.yourFIO)
        val yourEmail: TextView = findViewById(R.id.yourEmail)

        //Корутина
        lifecycleScope.launch {
            //Используем сессию авторизованного пользователя
            val session_user = SB.getClient().auth.retrieveUserForCurrentSession(updateSession = true)
            val user = SB.getClient().from("Пользователь").select(){
                filter {
                    eq("ID_пользователя", session_user.id)
                }
            }.decodeSingle<User>()
            //Получаем данные авторизованного пользователя
            yourFIO.setText(user.ФИО)
            yourEmail.setText(session_user.email)
        }

        //Кнопка сохранения
        saveProfile.setOnClickListener {
            val FIO = yourFIO.text.toString()
            val EMAIL = yourEmail.text.toString()

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Вы уверены, что хотите изменить?")
            builder.setTitle(android.R.string.dialog_alert_title)
            builder.setIcon(R.drawable.zam)
            //Да
            builder.setPositiveButton("Да",
                DialogInterface.OnClickListener {dialog, id -> this.lifecycleScope.launch {
                    try{
                        //Используем сессию авторизованного пользователя
                        val session_user = SB.getClient().auth.retrieveUserForCurrentSession(updateSession = true)
                        //Обновляем ФИО
                        SB.getClient().from("Пользователь").update(
                            {
                                set("ФИО", FIO)
                            }
                        ) {
                            filter {
                                eq("ID_пользователя", session_user.id)
                            }
                        }
                        //Обновляем почту
//                        val user = SB.getClient().auth.updateUser {
//                            email = EMAIL
//                        }
//                        yourEmail.setText(user.email)

                        Toast.makeText(applicationContext, "Изменения сохранены!", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }catch (ex: JSONException){
                        Log.e("!!!", ex.message.toString())
                    }
                }})
            //Нет
            builder.setNegativeButton("Нет",
                DialogInterface.OnClickListener {dialog, id -> dialog.cancel()})
            builder.setCancelable(false)
            builder.create()
            builder.show()
        }

        //Выход
        buttonExitProfile.setOnClickListener {
            val exit = Intent(this, Glavnai::class.java)
            startActivity(exit)
        }
    }
}