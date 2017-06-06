package br.com.edenred.kotlinmvpprojectbase.activity

import br.com.edenred.kotlinmvpprojectbase.model.A
import kotlinx.android.synthetic.main.teste_activity2.*

/**
 * Created by ticketservices on 5/29/17.
 */

class ValueBetweenActivity2 : br.com.edenred.base.BaseActivity() {

    override fun getLayout(): Int = br.com.edenred.kotlinmvpprojectbase.R.layout.teste_activity2

    override fun buildComponents() {
        val a: A?= getSerializable()
        val value:String = "${a?.name} - ${a?.age}"
        lblTitle.text = value
        android.util.Log.e(br.com.edenred.kotlinmvpprojectbase.activity.ValueBetweenActivity2::class.simpleName, value)
    }
}