package br.com.edenred.base

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by ticketservices on 5/23/17.
 */
abstract class BaseFragment : Fragment() {

    abstract fun onBuild()
    abstract fun getName(): String
    abstract fun getLayout(): Int
    abstract fun onStarted()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBuild()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view:View = inflater!!.inflate(getLayout(), container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onStarted()
    }

    fun <Fragment:BaseFragment> addFragment(fragment:Fragment, container:Int, backStack:Boolean = false, animations: Array<Int>? = null){
        val ft:FragmentTransaction = childFragmentManager.beginTransaction()
        if(fragmentManager.backStackEntryCount == 0){
            if(animations != null && animations.size == 4){
                ft.setCustomAnimations(animations[0],animations[1],animations[2],animations[3])
            }
            ft.add(container, fragment)
            if(backStack) ft.addToBackStack(fragment.getName())
            ft.commit()
        }else{
            replaceFragment(fragment, container, backStack, animations)
        }
    }

    fun <Fragment:BaseFragment> replaceFragment(fragment:Fragment, container:Int, backStack:Boolean = false, animations:Array<Int>? = null) {
        val ft:FragmentTransaction = childFragmentManager.beginTransaction()
        if(animations != null && animations.size == 4){
            ft.setCustomAnimations(animations[0],animations[1],animations[2],animations[3])
        }
        ft.replace(container,fragment)
        if(backStack) ft.addToBackStack(fragment.getName())
        ft.commit()
    }

    fun <Activity:BaseActivity> getBaseActivity():Activity{
        return activity as Activity
    }
}
