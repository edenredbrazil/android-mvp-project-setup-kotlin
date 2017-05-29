package br.com.edenred.base

import android.app.Activity
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import java.io.Serializable


abstract class BaseActivity : AppCompatActivity(){

    companion object {
        val PARCELABLE:String = "parcelable"
        val SERIALIZABLE:String = "serializable"
        val PARCELABLE_LIST:String = "parcelable_list"
    }

    abstract fun getLayout(): Int
    abstract fun buildComponents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        buildComponents()
    }

    protected fun <Fragment:BaseFragment> addFragment(fragment:Fragment, container:Int,backStack:Boolean = false, animations:Array<Int>? = null){
        val ft:FragmentTransaction = fragmentManager.beginTransaction()
        if(fragmentManager.backStackEntryCount == 0){
            if(animations != null && animations.size == 4){
                ft.setCustomAnimations(animations[0],animations[1],animations[2],animations[3])
            }
            ft.add(container,fragment)
            if(backStack) ft.addToBackStack(fragment.getName())
            ft.commit()
        }else{
            replaceFragment(fragment,container,backStack,animations)
        }
    }

    protected fun <Fragment:BaseFragment> replaceFragment(fragment: Fragment, container: Int, backStack: Boolean = false, animations: Array<Int>? = null){
        val ft:FragmentTransaction = fragmentManager.beginTransaction()
        if(animations != null && animations.size == 4){
            ft.setCustomAnimations(animations[0],animations[1],animations[2],animations[3])
        }
        ft.replace(container,fragment)
        if(backStack) ft.addToBackStack(fragment.getName())
        ft.commit()
    }

    protected fun <Fragment:BaseFragment> removeFragment(fragment: Fragment,animations: Array<Int>? = null){
        val ft:FragmentTransaction = fragmentManager.beginTransaction()
        if(animations != null && animations.size == 4){
            ft.setCustomAnimations(animations[0],animations[1],animations[2],animations[3])
        }
        ft.remove(fragment)
    }

    fun <A:Activity> clearBackStack(activity:A? = null){
        val fm:FragmentManager
        if(activity != null) fm = activity.fragmentManager
        else fm = fragmentManager
        for(i in 0 .. fm.backStackEntryCount) fm.popBackStack()
    }

    protected fun <A: AppCompatActivity> startActivity(classActivity: Class<A>, data:Any?){
        var intent:Intent?
        if(data == null){
            intent = Intent(applicationContext,classActivity)
        }else{
            when(data){
                is ArrayList<*> -> intent = getIntentWithParcelableList(classActivity, data as ArrayList<Parcelable>)
                else -> intent = getIntentWithData(classActivity, data)
            }
        }
        startActivity(intent)
    }

    protected fun <A: AppCompatActivity> startActivityForResult(classActivity:Class<A>, requestCode:Int, data:Any? = null) {
        var intent:Intent?
        if(data == null){
            intent = Intent(applicationContext, classActivity)
        }else{
            when(data){
                is ArrayList<*> -> intent = getIntentWithParcelableList(classActivity, data as ArrayList<Parcelable>)
                else -> intent = getIntentWithData(classActivity, data)
            }
        }
        startActivityForResult(intent, requestCode)
    }

    protected fun <S:Serializable> getSerializable() = intent.getSerializableExtra(SERIALIZABLE) as S

    protected fun <P:Parcelable> getParcelable() = intent.getParcelableExtra<P>(PARCELABLE)

    protected fun <P:Parcelable> getParcelableList() = intent.getParcelableArrayListExtra<P>(PARCELABLE_LIST)
    
    protected fun <A: AppCompatActivity, P: Parcelable> getIntentWithParcelableList(classActivity:Class<A>, parcelables: ArrayList<P>):Intent{
        val intent:Intent = Intent(applicationContext,classActivity)
        intent.putParcelableArrayListExtra(PARCELABLE_LIST, parcelables)
        return intent
    }

    protected fun <A: AppCompatActivity> getIntentWithData(classActivity:Class<A>, data:Any): Intent {
        val intent:Intent = Intent(applicationContext,classActivity)
        when(data){
            is Parcelable -> intent.putExtra(PARCELABLE, data)
            is Serializable -> intent.putExtra(SERIALIZABLE, data)
            is Bundle -> intent.putExtras(data)
        }
        return intent
    }
}