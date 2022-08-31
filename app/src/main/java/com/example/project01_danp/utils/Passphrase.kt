package com.example.project01_danp.utils

import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import kotlin.random.Random

class Passphrase constructor(
    private val sharedPreferences: SharedPreferences,
) {

    companion object {
        private const val ALIAS = "aliaskeystore"
        private const val TRANSFORMATION = "AES/CBC/NoPadding"
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
        private val CHARSET = Charsets.ISO_8859_1
        private const val IV_KEY = "ivkey"
        private const val DB_KEY = "dbkey"
    }

    private var passphrase: ByteArray? = null

    @RequiresApi(Build.VERSION_CODES.M)
    fun getPassphrase(): ByteArray {
        this.passphrase?.let {
            return it
        } ?: kotlin.run {
            return if (sharedPreferences.contains(DB_KEY) && sharedPreferences.contains(IV_KEY)) {
                val encryptedData = sharedPreferences.getString(DB_KEY, null)
                val iv = Base64.decode(sharedPreferences.getString(IV_KEY, null), Base64.DEFAULT)
                decryptData(encryptedData!!.toByteArray(CHARSET), iv)
            } else {
                this.passphrase = Random.nextBytes(128)
                val encryptedData = encrypt(this.passphrase!!)
                sharedPreferences.edit().putString(DB_KEY, encryptedData.toString(CHARSET)).apply()
                return this.passphrase!!
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun encrypt(byteArray: ByteArray): ByteArray {
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        sharedPreferences.edit().remove(IV_KEY).apply()
        sharedPreferences.edit().putString(IV_KEY, Base64.encodeToString(cipher.iv, Base64.DEFAULT)).apply()
        return cipher.doFinal(byteArray)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun decryptData(encryptedData: ByteArray, encryptionIv: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = IvParameterSpec(encryptionIv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
        return cipher.doFinal(encryptedData)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getSecretKey(): SecretKey {

        val ks: KeyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply {
            load(null)
        }

        return if (ks.containsAlias(ALIAS)) {
            val secretKey = ks.getEntry(ALIAS, null) as KeyStore.SecretKeyEntry
            secretKey.secretKey
        } else {
            val keyGenerator: KeyGenerator = KeyGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)

            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
            )
            return keyGenerator.generateKey()
        }
    }

}