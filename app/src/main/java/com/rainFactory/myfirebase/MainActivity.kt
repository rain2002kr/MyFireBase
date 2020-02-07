package com.rainFactory.myfirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableMap
import kotlin.collections.mutableMapOf


class MainActivity : AppCompatActivity() {
    private lateinit var mPostReference: DatabaseReference
    var arrayIndex = ArrayList<String>()
    var arrayData = ArrayList<String>()
    var count = 0

    var mutData :MutableMap<String,Any> = mutableMapOf<String,Any>()

    var t: GenericTypeIndicator<MutableMap<String,Any>?> =
        object : GenericTypeIndicator<MutableMap<String,Any>?>() {}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btInsert.setOnClickListener({
            val arrayIndex = ed2.text.toString()
            val subject = ed3.text.toString()
            postFirebaseDatabase(true,arrayIndex,subject)
        })
        btSerch.setOnClickListener({
            val serch = ed4.text.toString()
            getFirebaseDatabase(serch)

        })

    }

    fun postFirebaseDatabase(add: Boolean,data:String, value:Any) {
        mPostReference = FirebaseDatabase.getInstance().reference
        val childUpdates: MutableMap<String, Any?> = HashMap()
        var postValues: Map<String, Any?>? = null
        mutData.put(data,value)

        if (add) {
            val dbIndex = ed1.text.toString()
            mPostReference.child(dbIndex).updateChildren(mutData)

        }
    }

    fun getFirebaseDatabase(serch : String){
        mPostReference = FirebaseDatabase.getInstance().reference
        mPostReference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(db: DataSnapshot) {
                txt1.text = db.value.toString() //same of getValue
                txt2.text = db.getValue().toString()
                txt3.text = db.child(ed1.text.toString()).getValue().toString() //원하는 영역 전체 불러오기
                //검색한 key값에 해당하는 value값 보여주기
                var ts:MutableMap<String,Any>? =  db.child(ed1.text.toString()).getValue(t)
                ts?.forEach({
                    if(it.key.toString() == serch){
                        txt4.text = it.value.toString()
                    }
                })

            }

            override fun onCancelled(db: DatabaseError) {

            }

        })
    }


}
