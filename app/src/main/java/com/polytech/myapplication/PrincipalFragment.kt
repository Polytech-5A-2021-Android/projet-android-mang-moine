package com.polytech.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.polytech.myapplication.database.Database
import com.polytech.myapplication.databinding.FragmentPrincipalBinding
import com.polytech.myapplication.utils.Converter
import com.polytech.myapplication.viewmodel.ConnexionViewModel
import com.polytech.myapplication.viewmodel.SeuilViewModel
import com.polytech.myapplication.viewmodelfactory.ConnexionViewModelFactory
import com.polytech.myapplication.viewmodelfactory.SeuilViewModelFactory


class PrincipalFragment : Fragment() {
    private lateinit var binding: FragmentPrincipalBinding
    private lateinit var viewModel: SeuilViewModel
    private lateinit var viewModelFactory: SeuilViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_principal, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = Database.getInstance(application).seuilDao

        viewModelFactory = SeuilViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SeuilViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.apply {

        }


        binding.btnModifierSeuil.setOnClickListener {
            modifierSeuil(it)
        }

        binding.btnDeconnexion.setOnClickListener {
            deconnexion(it)
        }

        binding.btnAfficherMesures.setOnClickListener {
            afficherMesures(it)
        }
        binding.btnActiverVentilateur.setOnClickListener{
            val action = false;
            desactiverVenti(it,action)
        }
        viewModel.seuil.observe(viewLifecycleOwner, Observer { seuil ->
            binding.txtSeuil.text=seuil.valeur.toString()
        })
        return binding.root
    }

    private fun deconnexion(view: View) {
        this.findNavController().navigate(PrincipalFragmentDirections.actionPrincipalFragmentToConnexionFragment())
    }

    private fun modifierSeuil(view: View) {
        this.findNavController().navigate(PrincipalFragmentDirections.actionPrincipalFragmentToModifierSeuilFragment())
    }

    private fun afficherMesures(view: View) {
        this.findNavController().navigate(PrincipalFragmentDirections.actionPrincipalFragmentToListMesuresFragment())
    }

    private fun desactiverVenti(view: View, bol: Boolean) {
        viewModel.desactivateVenti(bol)
    }

}