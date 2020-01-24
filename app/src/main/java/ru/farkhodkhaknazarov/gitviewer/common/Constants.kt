package ru.farkhodkhaknazarov.gitviewer.common

import java.util.*

class Constants {
    companion object{
        val githubUrl: String = "https://api.github.com"
        val loginUrl: String = "https://github.com/login/oauth/authorize"
        val tokenUrl: String = "https://github.com/login/oauth/access_token"
        val client_id: String = "7e83e77f51c11c75595d"
        val client_secret: String = "6f072e4f9dffbedb6e0e02a364a19ec32abd0cce"
        val state: String = UUID.randomUUID().toString()
    }
}