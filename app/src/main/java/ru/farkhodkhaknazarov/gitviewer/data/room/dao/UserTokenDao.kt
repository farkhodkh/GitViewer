package ru.farkhodkhaknazarov.gitviewer.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.UserToken

@Dao
interface UserTokenDao {
    @Query("SELECT * FROM usertokens")
    fun allTokens(): List<UserToken>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userToken: UserToken)

    @Query("DELETE FROM usertokens")
    fun deleteAllTokens()

    @Query("SELECT * FROM usertokens LIMIT 1")
    fun firstToken(): UserToken


}