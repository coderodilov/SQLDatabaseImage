package uz.coderodilov.sqldatabase.interfaces

import android.graphics.Bitmap

interface ConverterTasks {
    fun convertBitmapToByteArray(bitmap: Bitmap) : ByteArray
    fun convertByteArrayToBitmap(byteArray: ByteArray) : Bitmap
}