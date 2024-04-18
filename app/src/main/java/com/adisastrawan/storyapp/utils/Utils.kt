package com.adisastrawan.storyapp.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.adisastrawan.storyapp.BuildConfig
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

private const val MAXIMAL_SIZE = 1000000

fun isValidEmail(email: String): Boolean {
    val emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}\$".toRegex()
    return emailPattern.matches(email)
}

fun getImageUri(context: Context): Uri {
    var uri:Uri? = null
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
        val contentValues =  ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME,"${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH,"Pictures/MyCamera/")
        }
        uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)

    }
    return uri ?: getImageUriPreQ(context)
}

fun getImageUriPreQ(context: Context): Uri {
    val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File(filesDir,"/MyCamera/${System.currentTimeMillis()}.jpg")
    if(imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
    return FileProvider.getUriForFile(
        context,  "${BuildConfig.APPLICATION_ID}.fileprovider",imageFile
    )
}

fun uriToFile(imageUri:Uri,context: Context):File{
    val myFile = createCustomTempFile(context)
    val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length : Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer,0,length)
    outputStream.close()
    inputStream.close()
    return myFile
}

fun File.reduceFileSize():File{
    val file = this
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality =100
    var streamLength : Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,compressQuality,bmpStream)
        val bmpPictByteArray = bmpStream.toByteArray()
        streamLength = bmpPictByteArray.size
        compressQuality -= 5
    }while (streamLength> MAXIMAL_SIZE)
    bitmap.compress(Bitmap.CompressFormat.JPEG,compressQuality,FileOutputStream(file))
    return file
}
fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile("${System.currentTimeMillis()}", ".jpg", filesDir)
}
