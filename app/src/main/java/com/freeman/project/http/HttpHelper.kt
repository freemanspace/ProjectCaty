package com.freeman.project.http

import android.webkit.CookieManager
import com.freeman.project.application.MyApplication
import com.freeman.project.utils.MyLog
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.*
import javax.security.cert.CertificateException

/**
 * 使用kotlin的協程來控制
 * 協程介紹
 * https://kknews.cc/zh-tw/tech/v52z6n2.html
 */
object HttpHelper {

    private var cookieManager:CookieManager = CookieManager.getInstance().apply {
        setAcceptCookie(true)
    }
    private var okHttpClient: OkHttpClient
    init {
        //http client
        val (sslSocketFactory, x509TrustManager) = createSSLSocketFactory()  //create ssl unsafe pass
        val builder = OkHttpClient.Builder().apply {
            //ssl驗證,正式環境建議關閉
            sslSocketFactory(sslSocketFactory, x509TrustManager)
            //host check pass,正式環境建議關閉
            hostnameVerifier(hostnameVerifier = { _, _ -> true })
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(90, TimeUnit.SECONDS)
            //綁憑證正式環境建議使用
            //bindSSLCert(builder)
        }
        okHttpClient = builder.build()
    }

    /**
     * 直接呼叫，方便多個api組合使用
     */
    @Synchronized
    fun httpRequest(request: Request): HttpResponse {
        //request cookie
        val host = "${request.url.scheme}://${request.url.host}"
        val newRequest = createCookieRequest(request,host)

        val call = okHttpClient.newCall(newRequest)
        val httpResponse = HttpResponse()
        try {
            val response = call.execute()
            storeCookie(response,host)

            httpResponse.httpCode = response.code
            httpResponse.response = response.body?.string()
            var httpSession = ""
            val headers = response.headers
            val cookies = headers.values("Set-Cookie")
            if (cookies.isNotEmpty()) {
                httpSession = cookies[0]
                httpSession = httpSession.substring(0, httpSession.indexOf(";"))
            }
            httpResponse.session = httpSession
            logRequestResponse(call,httpResponse.response)
            return httpResponse
        } catch (e:IOException){
            logRequestError(call,e)
            httpResponse.httpCode = -1
            return httpResponse
        }
    }

    //產生cookie request
    @Synchronized
    private fun createCookieRequest(request: Request,host:String):Request{
        //request cookie
        val hostCookie = cookieManager.getCookie(host)?: ""
        if(hostCookie.isNotEmpty()){
            return request.newBuilder().addHeader("Cookie",hostCookie).build()
        }
        return request
    }

    //儲存cookie
    @Synchronized
    private fun storeCookie(response:Response,host:String){
        for (setCookie in response.headers.values("Set-Cookie")) {
            cookieManager.setCookie(host, setCookie)
        }
        cookieManager.flush()
    }
    
    //log request error
    @Synchronized
    private fun logRequestError(call: Call?, e: IOException) {
        try {
            MyLog.log("test", "request =>" + call!!.request().body.toString())
            MyLog.log("test", "headers =>" + call.request().headers.toString())
            val log = Buffer()
            if (call.request().body != null) {
                call.request().body!!.writeTo(log)
            }
            MyLog.log("test", "params =>" + log.readUtf8())
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    //log request
    @Synchronized
    private fun logRequestResponse(call: Call?, response: String?) {
        MyLog.log("test", "request =>" + call!!.request().toString())
        MyLog.log("test", "headers =>" + call.request().headers.toString())
        try {
            val log = Buffer()
            if (call.request().body != null) {
                call.request().body!!.writeTo(log)
            }
            MyLog.log("test", "params =>" + log.readUtf8())
        } catch (e1: java.lang.Exception) {
            e1.printStackTrace()
        }
        try {
            MyLog.log("test", "response =>$response")
        } catch (e: java.lang.Exception) {
        }
    }

    //產生ssl socket factory
    @Synchronized
    private fun createSSLSocketFactory(): Pair<SSLSocketFactory, X509TrustManager> {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })

        val sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory = sslContext.socketFactory
        val x509TrustManager = trustAllCerts[0] as X509TrustManager
        return Pair(sslSocketFactory, x509TrustManager)
    }

    //綁訂憑證
    @Synchronized    
    private fun bindSSLCert(builder: OkHttpClient.Builder){
        try{
            val certFileName = "xxx.cert"
            val keyStorePassword = "sslPassWord".toCharArray()
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
                load(null, keyStorePassword)
            }
            val certificateStream = MyApplication.instance.assets.open(certFileName)
            val certificate = CertificateFactory.getInstance("X.509").generateCertificate(certificateStream)
            keyStore.setCertificateEntry(certFileName, certificate)
            certificateStream.close()
            val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()).apply {
                init(keyStore, keyStorePassword)
            }
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
                init(keyStore)
            }
            val sslContext = SSLContext.getInstance("TLSv1.2").apply {
                init(
                        keyManagerFactory.keyManagers,
                        trustManagerFactory.trustManagers,
                        SecureRandom()
                )
            }
            trustManagerFactory.trustManagers[0].apply {
                if (this is X509TrustManager) {
                    builder.sslSocketFactory(sslContext.socketFactory, this)
                }
            }
        } catch (e:Exception){
        }
    }

}