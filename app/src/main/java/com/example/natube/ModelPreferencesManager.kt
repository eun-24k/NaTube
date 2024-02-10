package com.example.natube

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.natube.model.UnifiedItem
import com.google.gson.GsonBuilder

/**
 * Singleton class for managing preferences for POJO or model class's object.
 * @author Malwinder Singh
 * medium.com/@dev.malwinder
 */
object ModelPreferencesManager {

    //Shared Preference field used to save and retrieve JSON string
    lateinit var preferences: SharedPreferences

    //Name of Shared Preference file
    private const val PREFERENCES_FILE_NAME = "PREFERENCES_FILE_NAME"

    /**
     * Call this first before retrieving or saving object.
     *
     * @param activity Instance of activity class
     */
    fun with(activity: AppCompatActivity) {
        preferences = activity.getSharedPreferences(
            PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Saves object into the Preferences.
     *
     * @param `object` Object of model class (of type [T]) to save
     * @param key Key with which Shared preferences to
     **/
    fun <T> put(`object`: T, key: String) {
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(`object`)
        //Save that String in SharedPreferences
        preferences.edit().putString(key, jsonString).apply()
    }

    /**
     * Used to retrieve object from the Preferences.
     *
     * @param key Shared Preference key with which object was saved.
     **/
    inline fun <reified T> get(key: String): T? {
        //We read JSON String which was saved.
        val value = preferences.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type Class < T >" is used to cast.
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    inline fun <reified  T> getAll() : ArrayList<T?> {
        //We read JSON String which was saved
        val keys = preferences.all.keys.toMutableList()
        val list : ArrayList<T?> = arrayListOf()
        keys.forEach{
            val value = preferences.getString(it, null)
            list.add(GsonBuilder().create().fromJson(value, T::class.java))
        }
        return list
    }

}