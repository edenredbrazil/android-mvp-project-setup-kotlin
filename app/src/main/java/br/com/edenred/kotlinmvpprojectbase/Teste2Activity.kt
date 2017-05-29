package br.com.edenred.kotlinmvpprojectbase

import android.util.Log
import br.com.edenred.base.BaseActivity

/**
 * Created by ticketservices on 5/29/17.
 */

class Teste2Activity : BaseActivity() {

    override fun getLayout(): Int = R.layout.teste_activity2

    override fun buildComponents() {
        val a:A?= getSerializable()
        Log.e(Teste2Activity::class.simpleName, "${a?.name} - ${a?.age}")
    }
}