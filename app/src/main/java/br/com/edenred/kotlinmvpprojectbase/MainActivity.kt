package br.com.edenred.kotlinmvpprojectbase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val a:Intent = Intent(applicationContext, TesteActivity::class.java)
        startActivity(a)
    }
}
