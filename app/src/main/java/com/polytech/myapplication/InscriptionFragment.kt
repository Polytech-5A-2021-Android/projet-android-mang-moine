package com.polytech.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.polytech.myapplication.database.Database
import com.polytech.myapplication.databinding.FragmentConnexionBinding
import com.polytech.myapplication.databinding.FragmentInscriptionBinding
import com.polytech.myapplication.viewmodel.ConnexionViewModel
import com.polytech.myapplication.viewmodelfactory.ConnexionViewModelFactory


class InscriptionFragment : Fragment() {

    private lateinit var binding: FragmentInscriptionBinding
    private lateinit var viewModel: ConnexionViewModel
    private lateinit var viewModelFactory: ConnexionViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inscription, container, false)

        //viewModel = ViewModelProvider(this).get(IdentityViewModel::class.java)
        //viewModel = ViewModelProviders.of(this).get(IdentityViewModel::class.java)

        val application = requireNotNull(this.activity).application
        val dataSource = Database.getInstance(application).utilisateurDao

        viewModelFactory = ConnexionViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ConnexionViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.apply {

            btnValider.text = getString(R.string.validate)

        }

        binding.btnValider.setOnClickListener {
            validate(it)
        }


        return binding.root
    }

    private fun validate(view: View) {
        viewModel.onValidate(1)

        view.findNavController().navigate(InscriptionFragmentDirections.actionInscriptionFragmentToConnexionFragment())

        //view.findNavController().navigate(IdentityFragmentDirections.actionIdentityFragmentToPersonalDataFragment(viewModel.utilisateur.value?: Utilisateur()))
    }

}