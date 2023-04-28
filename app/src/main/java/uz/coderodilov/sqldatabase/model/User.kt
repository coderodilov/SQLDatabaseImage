package uz.coderodilov.sqldatabase.model

class User(
    val id:Int,
    val name: String,
    val lastName: String,
    val image: ByteArray?
    ) {
    constructor(): this(0,"", "", null)
}
