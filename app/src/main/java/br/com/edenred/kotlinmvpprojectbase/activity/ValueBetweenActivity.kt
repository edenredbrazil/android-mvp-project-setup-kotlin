package br.com.edenred.kotlinmvpprojectbase.activity

import br.com.edenred.base.BaseActivity
import br.com.edenred.kotlinmvpprojectbase.R
import br.com.edenred.kotlinmvpprojectbase.model.A
import kotlinx.android.synthetic.main.value_between_activity.*

/**
 * Created by ticketservices on 5/29/17.
 */
class ValueBetweenActivity : BaseActivity() {

    override fun getLayout(): Int = R.layout.value_between_activity

    override fun buildComponents() {
        btnOpenNewActivityWithNewValue.setOnClickListener { v -> showNewActivityWithNewValue()  }
    }

    private fun showNewActivityWithNewValue() {
        if(txtName.text.isNotEmpty() && txtName.text.isNotBlank() && txtAge.text.isNotEmpty() && txtAge.text.isNotEmpty()){
            val data: A = A(txtName.text.toString(), txtAge.text.toString().toInt())

            startActivity(ValueBetweenActivity2::class.java, data)

            txtName.text.clear()
            txtAge.text.clear()
            txtName.requestFocus()
        }
    }
}