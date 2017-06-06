package br.com.edenred.base

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.Serializable

/**
 * Created by ticketservices on 5/23/17.
 */
abstract class BaseFragment : Fragment() {

    abstract fun onBuild()
    abstract fun getName(): String
    abstract fun getLayout(): Int
    abstract fun onStarted()

    companion object {
        open val PARCELABLE:String = "parcelable"
        open val SERIALIZABLE:String = "serializable"
        open val PARCELABLE_LIST:String = "parcelable_list"
    }

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

    fun setData(data:Any){
        val bundle:Bundle = Bundle()
        when(data){
            is Serializable -> bundle.putSerializable(SERIALIZABLE,data)
            is Parcelable -> bundle.putParcelable(PARCELABLE,data)
            is ArrayList<*> -> bundle.putParcelableArrayList(PARCELABLE_LIST, data as ArrayList<Parcelable>)
        }
        arguments = bundle
    }

    protected fun <S:Serializable> getSerializable() = arguments.getSerializable(BaseActivity.SERIALIZABLE) as S

    protected fun <P:Parcelable> getParcelable() = arguments.getParcelable<P>(BaseActivity.PARCELABLE)

    protected fun <P:Parcelable> getParcelableList() = arguments.getParcelableArrayList<P>(BaseActivity.PARCELABLE_LIST)
}
