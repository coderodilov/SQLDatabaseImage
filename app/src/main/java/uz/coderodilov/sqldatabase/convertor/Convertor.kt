package uz.coderodilov.sqldatabase.convertor

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import uz.coderodilov.sqldatabase.interfaces.ConverterTasks
import java.io.ByteArrayOutputStream

class Convertor : ConverterTasks{
    override fun convertBitmapToByteArray(bitmap: Bitmap):ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    override fun convertByteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}