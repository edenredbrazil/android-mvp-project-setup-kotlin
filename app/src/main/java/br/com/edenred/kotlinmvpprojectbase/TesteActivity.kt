package br.com.edenred.kotlinmvpprojectbase

import br.com.edenred.base.BaseActivity

/**
 * Created by ticketservices on 5/29/17.
 */
class TesteActivity : BaseActivity() {

    override fun getLayout(): Int = R.layout.teste_activity

    override fun buildComponents() {
        val data:A = A(name = "Rodrigo", age = 31)
        startActivity(Teste2Activity::class.java, data)
    }
}