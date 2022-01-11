package com.polytech.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.polytech.myapplication.database.Database
import com.polytech.myapplication.databinding.FragmentIdentityBinding
import com.polytech.myapplication.model.User
import com.polytech.myapplication.viewmodel.IdentityViewModel
import com.polytech.myapplication.viewmodel.PersonalDataViewModel
import com.polytech.myapplication.viewmodelfactory.IdentityViewModelFactory
import com.polytech.myapplication.viewmodelfactory.PersonalDataViewModelFactory


class IdentityFragment : Fragment() {
    private lateinit var binding: FragmentIdentityBinding
    private lateinit var viewModel: IdentityViewModel
    private lateinit var viewModelFactory: IdentityViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_identity, container, false)

        //viewModel = ViewModelProvider(this).get(IdentityViewModel::class.java)
        //viewModel = ViewModelProviders.of(this).get(IdentityViewModel::class.java)

        val application = requireNotNull(this.activity).application
        val dataSource = Database.getInstance(application).userDao

        viewModelFactory = IdentityViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(IdentityViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.apply {
            tvTitle.text = getString(R.string.title)
            tiFirstname.hint = getString(R.string.firstname)
            tiLastname.hint = getString(R.string.lastname)
            btValidate.text = getString(R.string.validate)
        }
        binding.btValidate.setOnClickListener {
            validate(it)
        }


        return binding.root
    }

    private fun validate(view: View) {
        viewModel.onValidate()

        view.findNavController().navigate(IdentityFragmentDirections.actionIdentityFragmentToPersonalDataFragment(viewModel.user.value?: User()))
    }
}