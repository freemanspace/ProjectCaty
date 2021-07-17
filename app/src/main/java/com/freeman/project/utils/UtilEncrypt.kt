package com.freeman.project.utils

import android.security.keystore.KeyProperties
import android.util.Base64
import java.net.URLDecoder
import java.net.URLEncoder
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 加解密使用
 */
object UtilEncrypt {
    private val utf8 = Charsets.UTF_8
    private val RSATransformation = "${KeyProperties.KEY_ALGORITHM_RSA}/${KeyProperties.BLOCK_MODE_ECB}/${KeyProperties.ENCRYPTION_PADDING_RSA_OAEP}"
    private val AESTransformation = "AES/CBC/PKCS7Padding"

    //-----------------------------------AES------------------------------------------
    /**
     * aes 加密
     */
    @Synchronized
    fun encryptAes(iv: String, key: String, str: String):String{
        return try{
            val iv = IvParameterSpec(iv.toByteArray(utf8))
            val key = SecretKeySpec(key.toByteArray(utf8), "AES")
            val cipher = Cipher.getInstance(AESTransformation).apply {
                init(Cipher.ENCRYPT_MODE, key, iv)
            }
            Base64.encodeToString(
                cipher.doFinal(str.toByteArray(utf8)),
                Base64.NO_WRAP
            )
        } catch (e: Exception){
            ""
        }
    }

    /**
     * aes 解密
     */
    @Synchronized
    fun decryptAES(iv: String, key: String, str: String):String{
        return try{
            val iv = IvParameterSpec(iv.toByteArray(utf8))
            val key = SecretKeySpec(key.toByteArray(utf8), "AES")
            val cipher = Cipher.getInstance(AESTransformation).apply {
                init(Cipher.DECRYPT_MODE, key, iv)
            }
            String(
                cipher.doFinal(Base64.decode(str.toByteArray(utf8), Base64.NO_WRAP)),
                utf8
            )
        } catch (e:Exception){
            ""
        }
    }

    //----------------------------------------RSA------------------------------------
    /**
     * rsa加密使用public key
     */
    @Synchronized
    fun encryptRSA(strPublicKey:String,str:String):String{
        return try {
            val publicKey = createPublicKey(strPublicKey)
            val cipher = Cipher.getInstance(RSATransformation).apply {
                init(Cipher.ENCRYPT_MODE, publicKey)
            }
            Base64.encodeToString(
                cipher.doFinal(str.toByteArray(utf8)),
                Base64.NO_WRAP
            )
        } catch (e:Exception){
            ""
        }
    }

    /**
     * rsa解密使用private key
     */
    @Synchronized
    fun decryptRSA(strPrivateKey: String,str:String):String{
        return try{
            val privateKey = createPrivateKey(strPrivateKey)
            val cipher = Cipher.getInstance(RSATransformation).apply {
                init(Cipher.DECRYPT_MODE, privateKey)
            }
            String(
                cipher.doFinal(Base64.decode(str.toByteArray(utf8), Base64.NO_WRAP)),
                utf8
            )
        } catch (e:Exception){
            ""
        }
    }

    /**
     * 產生public key
     */
    private fun createPublicKey(publicKey: String): PublicKey? {
        return try {
            val keyFactory = KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_RSA)
            val publicKeyByteArray = Base64.decode(publicKey, Base64.NO_WRAP)
            val x509EncodedKeySpec = X509EncodedKeySpec(publicKeyByteArray)
            keyFactory.generatePublic(x509EncodedKeySpec)
        } catch (e:Exception){
            null
        }
    }

    /**
     * 產生private key
     */
    private fun createPrivateKey(privateKey: String): PrivateKey? {
        return try {
            val keyFactory = KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_RSA)
            val privateKeyByteArray = Base64.decode(privateKey, Base64.NO_WRAP)
            val privateCrtKeySpec = PKCS8EncodedKeySpec(privateKeyByteArray)
            keyFactory.generatePrivate(privateCrtKeySpec)
        } catch (e:Exception){
            null
        }
    }


    //--------------------------------------------url---------------------------
    /**
     * url encode
     */
    fun String.urlEncode():String{
        return try{
            URLEncoder.encode(this,"UTF-8").replace("+","%20")
        } catch (e:Exception){
            ""
        }
    }

    fun String.urlDecode():String{
        return try{
            URLDecoder.decode(this,"UTF-8")
        } catch (e:Exception){
            ""
        }
    }

    /**
     * byte to hex String
     */
    fun bytetoHex(byteArray: ByteArray):String{
        if(byteArray.isEmpty()){
            return ""
        }
        val strBuilder = StringBuilder(byteArray.size*2)
        var hexNumber = ""
        for (i in byteArray.indices) {
            hexNumber = "0${Integer.toHexString(0xff and byteArray.get(i).toInt())}"
            strBuilder.append(hexNumber.substring(hexNumber.length - 2))
        }
        return strBuilder.toString()
    }

    /**
     * md5+hex
     */
    fun encodeMD5Hex(str:String):String{
        return try{
            var messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.toByteArray(utf8))
            val digest = messageDigest.digest()
            bytetoHex(digest)
        } catch (e:Exception){
            ""
        }
    }


}