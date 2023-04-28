package uz.coderodilov.sqldatabase

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import uz.coderodilov.sqldatabase.adapter.UserAdapter
import uz.coderodilov.sqldatabase.convertor.Convertor
import uz.coderodilov.sqldatabase.database.DatabaseHelper
import uz.coderodilov.sqldatabase.databinding.ActivityMainBinding
import uz.coderodilov.sqldatabase.databinding.CustomDialogAddUserBinding
import uz.coderodilov.sqldatabase.model.User
import java.io.FileNotFoundException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dialogBinding:CustomDialogAddUserBinding

    private var listUser = ArrayList<User>()
    private val convertor by lazy { Convertor() }

    private lateinit var userAdapter: UserAdapter
    private lateinit var dbHelper:DatabaseHelper

    private val requestCodeImage = 100
    private lateinit var bitmap: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        listUser = dbHelper.readAllUser()
        userAdapter = UserAdapter(listUser)

        binding.rviewUsers.adapter = userAdapter
        val dividerItemDecoration = DividerItemDecoration(binding.rviewUsers.context,
            LinearLayoutManager.VERTICAL)
        binding.rviewUsers.addItemDecoration(dividerItemDecoration)

        binding.btnAddUser.setOnClickListener{
            val builder = MaterialAlertDialogBuilder(this)
            dialogBinding = CustomDialogAddUserBinding.inflate(layoutInflater)
            builder.setView(dialogBinding.root)
            val dialog = builder.create()
            dialog.show()

            dialogBinding.imageChooser.setOnClickListener{
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(Intent.createChooser(intent, "SELECT A PICTURE" ), requestCodeImage)
            }

            dialogBinding.btnSaveAndExit.setOnClickListener{
                val name = dialogBinding.edUserName.text.toString()
                val lastName = dialogBinding.edUserLastName.text.toString()

                saveUser(name, lastName, bitmap)
                dialog.dismiss()
            }

        }

    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == requestCodeImage){
            try {
                val inputStream = contentResolver.openInputStream(data?.data!!)
                bitmap = BitmapFactory.decodeStream(inputStream)
                dialogBinding.imageChooser.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException){
                e.printStackTrace()
            }
        }
    }


    private fun saveUser(name:String, lastName:String, image:Bitmap){
        val user = User(0, name, lastName, convertor.convertBitmapToByteArray(image))
        listUser.add(0,user)

        userAdapter.notifyItemInserted(0)
        dbHelper.insertUser(name,lastName,convertor.convertBitmapToByteArray(image))

        Toast.makeText(this, "User crated successfully", Toast.LENGTH_SHORT).show()
    }
}