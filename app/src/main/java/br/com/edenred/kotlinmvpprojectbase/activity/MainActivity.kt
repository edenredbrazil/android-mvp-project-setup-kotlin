package br.com.edenred.kotlinmvpprojectbase.activity

import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : android.support.v7.app.AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(br.com.edenred.kotlinmvpprojectbase.R.layout.activity_main)

        btnValueBetweenActivity.setOnClickListener { v: View? -> showValueBetweenActivitys() }
        btnFragment.setOnClickListener { v: View? -> showFragment() }
    }

    private fun showFragment() {
        startActivity(Intent(applicationContext, WorkingWithFragmentActivity::class.java))
    }

    fun showValueBetweenActivitys(){
        startActivity(Intent(applicationContext, ValueBetweenActivity::class.java))
    }
}
