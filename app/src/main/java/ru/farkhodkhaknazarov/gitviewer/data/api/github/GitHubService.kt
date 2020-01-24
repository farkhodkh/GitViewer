package ru.farkhodkhaknazarov.gitviewer.data.api.github

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url
import ru.farkhodkhaknazarov.elegion.data.entities.Commit
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.Repo


interface GitHubService {
    @GET("/user/repos")
    fun getUserRepos(
        @Header("Authorization") token: String,
        @Query("page") pageNumber:Int,
        @Query("per_page") perPage:Int = 20,
        @Query("sort") sortType:String = "updated"): Observable<ArrayList<Repo>>

    @GET
    fun getRepoCommits(@Url url: String): Observable<ArrayList<Commit>>

}