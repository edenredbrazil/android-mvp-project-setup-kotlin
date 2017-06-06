package br.com.edenred.kotlinmvpprojectbase.fragment

import android.view.View
import android.widget.ArrayAdapter
import br.com.edenred.base.BaseFragment
import br.com.edenred.kotlinmvpprojectbase.R
import br.com.edenred.kotlinmvpprojectbase.activity.WorkingWithFragmentActivity
import kotlinx.android.synthetic.main.value_between_fragment.*

/**
 * Created by ticketservices on 5/30/17.
 */
class ValueBetweenFragment: BaseFragment() {

    override fun onBuild() {}

    override fun getName(): String = ValueBetweenFragment::class.java.simpleName

    override fun getLayout(): Int = R.layout.value_between_fragment

    override fun onStarted() {
        val activity:WorkingWithFragmentActivity = getBaseActivity()
        val countries = arrayListOf("Brazil", "United States", "United Kingdom", "France", "Germany")
        spinnerCountry.adapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, countries)
        btnOpenNewFragmentWithNewValue.setOnClickListener { v: View? -> showNewFragmentWithvalue() }
    }

    fun showNewFragmentWithvalue(){
        if(txtName.text.isNotEmpty() && txtName.text.isNotBlank() && txtAge.text.isNotEmpty() && txtAge.text.isNotBlank()){
            val countrie:String = spinnerCountry.selectedItem as String

        }else{
            getBaseActivity<WorkingWithFragmentActivity>().showAlert(android.R.drawable.ic_dialog_alert, getString(R.string.alert), getString(R.string.field_empty),
                    fun (dialog, which){
                        txtName.text.clear()
                        txtAge.text.clear()
                        dialog.dismiss()
                    }
            )
        }
    }
}