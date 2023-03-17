package com.clandestinestudio.arfurniture.Model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SharedPreferencesManager(private val context: Context) {
     private val sharedPreferences = context.getSharedPreferences("FurniturePrefs", Context.MODE_PRIVATE)
    fun saveFavoriteFurniture(favFurniture: FavoriteFurniture) {
        val editor = sharedPreferences.edit()
        val existingFavorites = getFavoriteFurnitureList()
        val newFavorites = mutableListOf<FavoriteFurniture>()
        var isExistingFavorite = false

        // check if the favorite already exists and update it
        for (existingFavorite in existingFavorites) {
            if (existingFavorite.id == favFurniture.id) {
                newFavorites.add(favFurniture)
                isExistingFavorite = true
            } else {
                newFavorites.add(existingFavorite)
            }
        }

        // add the new favorite if it doesn't exist
        if (!isExistingFavorite) {
            newFavorites.add(favFurniture)
        }

        // save the updated list of favorites
        editor.putString("favoriteFurnitureList", Gson().toJson(newFavorites))
        editor.apply()
    }

    fun getFavoriteFurnitureList(): List<FavoriteFurniture> {
        val favoriteFurnitureJson = sharedPreferences.getString("favoriteFurnitureList", null)
        val type = object : TypeToken<List<FavoriteFurniture>>() {}.type
        return Gson().fromJson(favoriteFurnitureJson, type) ?: emptyList()
    }


    fun removeFavoriteFurniture(favFurniture: FavoriteFurniture) {
        val editor = sharedPreferences.edit()
        val existingFavorites = getFavoriteFurnitureList()
        val newFavorites = mutableListOf<FavoriteFurniture>()

        // remove the favorite if it exists
        for (existingFavorite in existingFavorites) {
            if (existingFavorite.id != favFurniture.id) {
                newFavorites.add(existingFavorite)
            }
        }

        // save the updated list of favorites
        editor.putString("favoriteFurnitureList", Gson().toJson(newFavorites))
        editor.apply()
    }

}