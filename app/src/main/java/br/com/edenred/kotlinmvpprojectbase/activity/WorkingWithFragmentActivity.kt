package br.com.edenred.kotlinmvpprojectbase.activity

import br.com.edenred.base.BaseActivity
import br.com.edenred.kotlinmvpprojectbase.R
import br.com.edenred.kotlinmvpprojectbase.fragment.ValueBetweenFragment
import kotlinx.android.synthetic.main.working_with_fragment_activity.*

/**
 * Created by ticketservices on 5/29/17.
 */
class WorkingWithFragmentActivity : BaseActivity() {

    override fun buildComponents() {
        addFragment(createFragment(ValueBetweenFragment::class.java), container.id)

    }

    override fun getLayout(): Int = R.layout.working_with_fragment_activity
}