package com.polytech.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.polytech.myapplication.adapter.MyListAdapter
import com.polytech.myapplication.database.Database
import com.polytech.myapplication.databinding.FragmentListBinding
import com.polytech.myapplication.viewmodel.ListViewModel
import com.polytech.myapplication.viewmodelfactory.ListViewModelFactory


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

        val viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = MyListAdapter()
        binding.list.adapter = adapter

        viewModel.users.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        return binding.root
    }
}