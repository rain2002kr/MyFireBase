package com.rainFactory.myfirebase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableMap
import kotlin.collections.mutableMapOf


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private lateinit var mPostReference: DatabaseReference
    var arrayIndex = ArrayList<String>()
    var arrayData = ArrayList<String>()
    var count = 0
    var gsonConvert : GsonConvert = GsonConvert()

    var mutData :MutableMap<String,Any> = mutableMapOf<String,Any>()

    var t: GenericTypeIndicator<MutableMap<String,Any>> =
        object : GenericTypeIndicator<MutableMap<String,Any>>() {}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btInsert.setOnClickListener({

            postFirebaseDatabase(true)
        })
        btSerch.setOnClickListener({
            val serch = ed4.text.toString()
            getFirebaseDatabase(serch)

        })

    }
    //TODO PostFireBase function
    fun postFirebaseDatabase(add: Boolean) {
        mPostReference = FirebaseDatabase.getInstance().reference
        val childUpdates: MutableMap<String, Any?> = HashMap()
        var postValues: Map<String, Any?>? = null
        var name = ed2.text.toString()
        var number = ed3.text.toString()
        var person = Person(name, number)
        gsonConvert.put(person)

        var js = gsonConvert.jsGet()
        //var lastindex = gsonConvert.lastIndex()
        childUpdates.put("key".toString(), js)

        if (add) {
            val dbName = ed1.text.toString()
            mPostReference.child(dbName).updateChildren(childUpdates)

        }
    }
    //TODO GetFireBase function
    fun getFirebaseDatabase(serch : String){
        mPostReference = FirebaseDatabase.getInstance().reference
        mPostReference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(db: DataSnapshot) {
                txt1.text = db.value.toString() //same of getValue
                txt2.text = db.getValue().toString()
                txt3.text = db.child(ed1.text.toString()).getValue().toString() //원하는 영역 전체 불러오기
                //검색한 key값에 해당하는 value값 보여주기
                try {
                var gs = mutableListOf<Person>()
                var ts =  db.child(ed1.text.toString()).getValue(t)

                ts?.forEach {
                    gs = gsonConvert.gsGet(it.value.toString())
                    Log.d(TAG, it.value.toString())
                }

                gs.forEach({
                    println(it.toString())
                    txt4.text = it.toString()
                })

                }catch (e: Exception){
                    e.printStackTrace()
                }


            }

            override fun onCancelled(db: DatabaseError) {

            }

        })
    }


}
