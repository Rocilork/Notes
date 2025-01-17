package com.example.note

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.note.Adapter.Adapter_Notes
import com.example.note.Class.Class_Notes
import com.example.note.Object.SB
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.util.Locale.filter
import kotlin.math.exp

class Glavnai : AppCompatActivity() {
    val viewItems = ArrayList<Class_Notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glavnai)

        val buttonProfile: Button = findViewById(R.id.buttonProfile)
        val buttonCreateNote: Button = findViewById(R.id.buttonCreateNote)
        val buttonExite: Button = findViewById(R.id.buttonExite)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        //Получаем список заметок
        try {
            lifecycleScope.launch {
                val userID = SB.getClient().auth.retrieveUserForCurrentSession(updateSession = true).id
                val city = SB.getClient().from("Заметки").select(){
                    filter {
                        eq("id_пользователя", userID)
                    }
                }

                val buf = StringBuilder()
                buf.append(city.data.toString()).append("\n")
                val array = JSONArray(buf.toString())

                Log.e("!!!!!", city.data)

                try {
                    for(i in 0 until array.length()){
                        val item = array.getJSONObject(i)
                        val ID_заметки = item.getInt("ID_заметки")
                        val ДляЧего = item.getString("ДляЧего")
                        val Описание = item.getString("Описание")
                        val id_пользователя = item.getString("id_пользователя")
                        val api = Class_Notes(ID_заметки, ДляЧего, Описание, id_пользователя)
                        viewItems.add(api)
                    }
                }catch (e: JSONException){
                    Log.e("!!!", e.message.toString())
                }
            }
        }catch (e: Exception){
            Log.e("!!!", e.message.toString())
        }

        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long){}

            override fun onFinish() {
                recyclerView.adapter = Adapter_Notes(viewItems)
            }
        }
        timer.start()

        buttonProfile.setOnClickListener {
            val profile = Intent(this, Profile::class.java)
            startActivity(profile)
        }

        buttonExite.setOnClickListener {
            val exit = Intent(this, Avtorizashen::class.java)
            startActivity(exit)
        }

        buttonCreateNote.setOnClickListener {

        }
    }
}