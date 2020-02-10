package com.rainFactory.myfirebase

import com.google.firebase.database.GenericTypeIndicator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

data class Person(val name:String, val number:String)

class GsonConvert(){
    var gson : Gson
    var listType : TypeToken<MutableList<Person>> = object : TypeToken<MutableList<Person>>() {}
    var persons = mutableListOf<Person>()
    var js :String = ""
    var gs = mutableListOf<Person>()
    init{
        gson = GsonBuilder().create()
    }
    companion object{
        private var lastindex = 0   //lastindex
        var contacts = mutableListOf<Person>()
    }
    //Contact 객체 추가
    fun put(contact : Person) {
        persons.add(contact)
        lastindex = persons.lastIndex
    }
    //Contacts List 객체 돌려주기
    fun get():MutableList<Person>{
        return contacts
    }
    //Json 객체 돌려주기
    fun jsGet():String{
        js  = gson.toJson(persons,listType.type)
        return js
    }
    //Gson 객체 돌려주기
    fun gsGet():MutableList<Person> {
        gs  = gson.fromJson(js,listType.type)
        return gs
    }
    //Gson 객체 돌려주기
    fun gsGet(js:String?):MutableList<Person> {
        gs  = gson.fromJson(js,listType.type)
        return gs
    }
    //Contacts List lastIndex 값 돌려주기
    fun lastIndex():Int{
        lastindex = persons.lastIndex
        return lastindex
    }
}

