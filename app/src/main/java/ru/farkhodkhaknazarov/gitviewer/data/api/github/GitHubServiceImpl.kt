package ru.farkhodkhaknazarov.gitviewer.data.api.github

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.farkhodkhaknazarov.gitviewer.activities.LoginActivity
import ru.farkhodkhaknazarov.gitviewer.common.Constants
import ru.farkhodkhaknazarov.gitviewer.modules.LoginActivityModule
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

class GitHubServiceImpl {

    companion object {
        fun getApiService(): GitHubService {
            var gson: Gson = GsonBuilder().setLenient().create()
            var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.githubUrl)
//                .client(getUnsafeOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val request = retrofit.create<GitHubService>(GitHubService::class.java)
            return request

        }

        fun getUnsafeOkHttpClient(): OkHttpClient {
            return try { // Create a trust manager that does not validate certificate chains
                val trustAllCerts =
                    arrayOf<TrustManager>(
                        object : X509TrustManager {
                            @Throws(CertificateException::class)
                            override fun checkClientTrusted(
                                chain: Array<X509Certificate>,
                                authType: String
                            ) {
                            }

                            @Throws(CertificateException::class)
                            override fun checkServerTrusted(
                                chain: Array<X509Certificate>,
                                authType: String
                            ) {
                            }

                            override fun getAcceptedIssuers(): Array<X509Certificate> {
                                return arrayOf()
                            }
                        }
                    )
                // Install the all-trusting trust manager
                val sslContext =
                    SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(
                    sslSocketFactory,
                    (trustAllCerts[0] as X509TrustManager)
                )
                builder.hostnameVerifier(object : HostnameVerifier {
                    override fun verify(
                        hostname: String?,
                        session: SSLSession?
                    ): Boolean {
                        return true
                    }
                })
                builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }

    fun getGitHubToken(authCode: String?, state: String?) {

        if (authCode == null || state == null) {
            return
        }

        val formBody: RequestBody = FormBody.Builder()

            .build()

        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url(
                Constants.tokenUrl +
                        "?client_id=" + Constants.client_id +
                        "&client_secret=" + Constants.client_secret +
                        "&code=" + authCode
            )
            .post(formBody)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build()

        try {
            val response: Response = client.newCall(request).execute()

            var strInputStream: String? = null

            if (response.code == 200 && response.message == "OK") {

                val input = response.body?.byteStream() // (2)

                strInputStream = input?.bufferedReader().use { it?.readText() }

            }

            if (strInputStream != null) {

                val token = getTokenFromScope(strInputStream)

                if (token != null) {
                    LoginActivityModule().saveToken(token)

                    LoginActivity.startMainActivity()
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getTokenFromScope(strInputStream: String): String? {

        fun getToken(inputStreamArray: List<String>): String? {
            for (line in inputStreamArray) {
                if (line.contains("access_token=")) {
                    return line.replace("access_token=", "")
                }
            }
            return null
        }

        return getToken(strInputStream.split("&"))
    }
}