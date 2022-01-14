package com.polytech.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.polytech.myapplication.adapter.MyListAdapter
import com.polytech.myapplication.adapter.UserListener
import com.polytech.myapplication.database.Database
import com.polytech.myapplication.databinding.FragmentListBinding
import com.polytech.myapplication.model.User
import com.polytech.myapplication.viewmodel.IdentityViewModel
import com.polytech.myapplication.viewmodel.ListViewModel
import com.polytech.myapplication.viewmodelfactory.ListViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class ListFragment :  Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = Database.getInstance(application).userDao
        val viewModelFactory = ListViewModelFactory(dataSource, application)

        val viewModel = ViewModelProvider(this,viewModelFactory).get(ListViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //val adapter = MyListAdapter()

        val adapter = MyListAdapter(UserListener { userId ->
            Toast.makeText(this.context,"$userId clicked",Toast.LENGTH_SHORT).show()
        })

        binding.list.adapter = adapter

        viewModel.users.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        @BindingAdapter("userDate")
        fun TextView.setUserDate(item: User) {
            val date = Date(item.birthdayDate)
            val f = SimpleDateFormat("dd/MM/yy")
            val dateText = f.format(date)
            text = dateText
        }


        @BindingAdapter("userImage")
        @JvmStatic
        fun ImageView.setUserImage(item: User) {
            setImageResource(
                when (item.gender) {
                    "Homme" -> R.mipmap.ic_man
                    else -> R.mipmap.ic_woman
                }
            )
        }
    }


}