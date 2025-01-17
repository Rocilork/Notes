package com.example.note.Object

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime

object SB {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://ywdubozlpsklibawwpsm.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inl3ZHVib3pscHNrbGliYXd3cHNtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzcxMDg1MDYsImV4cCI6MjA1MjY4NDUwNn0.eXZ1nqa_la7IW-_UvR_B18dolBpZGofpXnaWXHiz0OA"
    ) {
        install(Auth)
        install(Postgrest)
        install(Realtime)
        //install other modules
    }
    public fun getClient(): SupabaseClient {
        return supabase
    }
}