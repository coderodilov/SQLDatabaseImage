package uz.coderodilov.sqldatabase.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.coderodilov.sqldatabase.convertor.Convertor
import uz.coderodilov.sqldatabase.databinding.UserItemBinding
import uz.coderodilov.sqldatabase.model.User

class UserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val convertor:Convertor = Convertor()

    inner class ViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(user: User) {
            binding.tvUserFullName.text = "${user.name} ${user.lastName}"
            binding.imageViewUserItem.setImageBitmap(convertor.convertByteArrayToBitmap(user.image!!))

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listUser[position])
    }
}