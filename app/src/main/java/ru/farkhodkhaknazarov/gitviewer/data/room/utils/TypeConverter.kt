package ru.farkhodkhaknazarov.elegion.data.room.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.Owner
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.Permossion

class TypeConverter {

    @TypeConverter
    fun convertOwnerToString(owner: Owner): String = Gson().toJson(owner)

    @TypeConverter
    fun convertStringToOwner(value: String): Owner = Gson().fromJson(value, Owner::class.java)

    @TypeConverter
    fun convertPermossionToString(owner: Permossion): String = Gson().toJson(owner)

    @TypeConverter
    fun convertStringToPermossion(value: String): Permossion = Gson().fromJson(value, Permossion::class.java)

}