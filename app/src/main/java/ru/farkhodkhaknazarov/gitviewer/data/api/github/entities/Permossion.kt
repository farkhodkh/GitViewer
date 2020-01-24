package ru.farkhodkhaknazarov.gitviewer.data.api.github.entities

import androidx.room.PrimaryKey

class Permossion(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var Permossion: Boolean,
    var push: Boolean,
    var pull: Boolean
){
    constructor():this(0, false, false, false)
}