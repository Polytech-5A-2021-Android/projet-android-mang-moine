package com.polytech.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.polytech.myapplication.adapter.MyApiListAdapter
import com.polytech.myapplication.databinding.FragmentApiListBinding
import com.polytech.myapplication.viewmodel.ApiListViewModel
import com.polytech.myapplication.viewmodelfactory.ApiListViewModelFactory


class ApiListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentApiListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_api_list, container, false
        )

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ApiListViewModelFactory(application)

        var viewModel = ViewModelProvider(this,viewModelFactory).get(ApiListViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = MyApiListAdapter()
        binding.list.adapter = adapter

        viewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                //adapter.data = it
                adapter.submitList(it)
            }
        })
        return binding.root
    }
}