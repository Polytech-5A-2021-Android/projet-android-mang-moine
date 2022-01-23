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
import androidx.lifecycle.ViewModelProvider
import com.polytech.myapplication.adapter.MyListAdapter
import com.polytech.myapplication.adapter.UserListener
import com.polytech.myapplication.database.Database
import com.polytech.myapplication.databinding.FragmentListBinding
import com.polytech.myapplication.databinding.FragmentListMesuresBinding
import com.polytech.myapplication.model.User
import com.polytech.myapplication.viewmodel.ListMesuresViewModel
import com.polytech.myapplication.viewmodel.ListViewModel
import com.polytech.myapplication.viewmodelfactory.ListMesuresViewModelFactory
import com.polytech.myapplication.viewmodelfactory.ListViewModelFactory
import java.text.SimpleDateFormat
import androidx.lifecycle.Observer
import com.polytech.myapplication.adapter.MesureListAdapter
import com.polytech.myapplication.adapter.MesureListener
import com.polytech.myapplication.model.Mesure
import java.util.*


class ListMesuresFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentListMesuresBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_mesures, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = Database.getInstance(application).mesureDao

        val viewModelFactory = ListMesuresViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(ListMesuresViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //val adapter = MyListAdapter()

        val adapter = MesureListAdapter(MesureListener { mesureId ->
            Toast.makeText(this.context,"$mesureId clicked", Toast.LENGTH_SHORT).show()
        })

        binding.list.adapter = adapter

        viewModel.mesures.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }


    companion object {
        @JvmStatic
        @BindingAdapter("mesureDate")
        fun TextView.setMesureDate(item: Mesure) {
            val date = Date(item.date_mesure)
            val f = SimpleDateFormat("dd/MM/yy hh:mm:ss")
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