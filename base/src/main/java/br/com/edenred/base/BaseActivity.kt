package br.com.edenred.base

import android.app.*
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.io.Serializable


/**
 * Created by rodrigosimoesrosa
 */
abstract class BaseActivity : AppCompatActivity(){

    companion object {
        val PARCELABLE:String = "parcelable"
        val SERIALIZABLE:String = "serializable"
        val PARCELABLE_LIST:String = "parcelable_list"

        open fun <T: Fragment> createFragment(entityClass:Class<T>):T = entityClass.newInstance()
    }

    abstract fun getLayout(): Int
    abstract fun buildComponents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        buildComponents()
    }

    fun <Fragment:BaseFragment> addFragment(fragment:Fragment, container:Int,backStack:Boolean = false, animations:Array<Int>? = null){
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

    fun <Fragment:BaseFragment> replaceFragment(fragment: Fragment, container: Int, backStack: Boolean = false, animations: Array<Int>? = null){
        val ft:FragmentTransaction = fragmentManager.beginTransaction()
        if(animations != null && animations.size == 4){
            ft.setCustomAnimations(animations[0],animations[1],animations[2],animations[3])
        }
        ft.replace(container,fragment)
        if(backStack) ft.addToBackStack(fragment.getName())
        ft.commit()
    }

    fun <Fragment:BaseFragment> removeFragment(fragment: Fragment, animations: Array<Int>? = null){
        val ft:FragmentTransaction = fragmentManager.beginTransaction()
        if(animations != null && animations.size == 4){
            ft.setCustomAnimations(animations[0],animations[1],animations[2],animations[3])
        }
        ft.remove(fragment)
    }

    fun <Activity:AppCompatActivity> clearBackStack(activity:Activity? = null){
        val fm:FragmentManager
        if(activity != null) fm = activity.fragmentManager
        else fm = fragmentManager
        for(i in 0 .. fm.backStackEntryCount) fm.popBackStack()
    }

    fun <Activity: AppCompatActivity> startActivity(classActivity: Class<Activity>, data:Any?){
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

    fun <Actvity: AppCompatActivity> startActivityForResult(classActivity:Class<Actvity>, requestCode:Int, data:Any? = null) {
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

    fun <S:Serializable> getSerializable() = intent.getSerializableExtra(SERIALIZABLE) as S

    fun <P:Parcelable> getParcelable() = intent.getParcelableExtra<P>(PARCELABLE)

    fun <P:Parcelable> getParcelableList() = intent.getParcelableArrayListExtra<P>(PARCELABLE_LIST)
    
    fun <Activity: AppCompatActivity, P: Parcelable> getIntentWithParcelableList(classActivity:Class<Activity>, parcelables: ArrayList<P>):Intent{
        val intent:Intent = Intent(applicationContext,classActivity)
        intent.putParcelableArrayListExtra(PARCELABLE_LIST, parcelables)
        return intent
    }

    fun <Activity: AppCompatActivity> getIntentWithData(classActivity:Class<Activity>, data:Any): Intent {
        val intent:Intent = Intent(applicationContext,classActivity)
        when(data){
            is Parcelable -> intent.putExtra(PARCELABLE, data)
            is Serializable -> intent.putExtra(SERIALIZABLE, data)
            is Bundle -> intent.putExtras(data)
        }
        return intent
    }

    fun showAlert(icon:Int, title:String, message:String,
                  positive: (dialog:DialogInterface, which: Int) -> Unit? = null!!,
                  negative: (dialog:DialogInterface, which:Int) -> Unit? = null!!) {

        val dialog:AlertDialog.Builder = AlertDialog.Builder(this)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(message)

        if(positive != null) dialog.setPositiveButton(R.string.ok) { dialog, which ->  positive.invoke(dialog,which) }
        else dialog.setPositiveButton(R.string.ok) { dialog, which -> dialog.dismiss() }

        if(negative != null) dialog.setNegativeButton(R.string.cancel) { dialog, which -> negative.invoke(dialog,which) }
    }

    fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
    }
}