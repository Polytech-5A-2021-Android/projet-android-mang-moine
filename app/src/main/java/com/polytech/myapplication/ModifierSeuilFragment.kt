package com.polytech.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.polytech.myapplication.database.Database
import com.polytech.myapplication.databinding.FragmentModifierSeuilBinding
import com.polytech.myapplication.databinding.FragmentPrincipalBinding
import com.polytech.myapplication.utils.FloatConverter
import com.polytech.myapplication.viewmodel.SeuilViewModel
import com.polytech.myapplication.viewmodelfactory.SeuilViewModelFactory


class ModifierSeuilFragment : Fragment() {
    private lateinit var binding: FragmentModifierSeuilBinding
    private lateinit var viewModel: SeuilViewModel
    private lateinit var viewModelFactory: SeuilViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modifier_seuil, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = Database.getInstance(application).seuilDao

        viewModelFactory = SeuilViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SeuilViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.apply {

        }


        binding.btnModifierSeuil.setOnClickListener {
            val nouvSeuil = FloatConverter.stringToFloat(binding.editTextSeuil.text.toString())
            retourPrincipal(it,nouvSeuil)
        }


        return binding.root
    }


    private fun retourPrincipal(view: View, valeur: Float) {
        viewModel.modifier(valeur)
        this.findNavController().navigate(ModifierSeuilFragmentDirections.actionModifierSeuilFragmentToPrincipalFragment())
    }

    private fun afficherMesures(view: View) {

    }

}