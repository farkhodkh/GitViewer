package ru.farkhodkhaknazarov.gitviewer.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.Repo

@Dao
interface RepoDao {
    @Query("SELECT * FROM repos")
    fun allRepos(): List<Repo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepo(repo: Repo)

    @Query("DELETE FROM repos")
    fun deleteAllRepos()

    @Query("SELECT * FROM repos WHERE id= :id")
    fun getrepoById(id: Int): Repo?

}