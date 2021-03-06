package com.polytech.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.polytech.myapplication.database.Database
import com.polytech.myapplication.databinding.FragmentConnexionBinding
import com.polytech.myapplication.model.Connexion
import com.polytech.myapplication.service.IotApi
import com.polytech.myapplication.viewmodel.ConnexionViewModel
import com.polytech.myapplication.viewmodelfactory.ConnexionViewModelFactory


class ConnexionFragment : Fragment() {
    private lateinit var binding: FragmentConnexionBinding
    private lateinit var viewModel: ConnexionViewModel
    private lateinit var viewModelFactory: ConnexionViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_connexion, container, false)

        //viewModel = ViewModelProvider(this).get(IdentityViewModel::class.java)
        //viewModel = ViewModelProviders.of(this).get(IdentityViewModel::class.java)

        val application = requireNotNull(this.activity).application
        val dataSource = Database.getInstance(application).utilisateurDao

        viewModelFactory = ConnexionViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ConnexionViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.apply {
            /*
            tvTitle.text = getString(R.string.title)
            tiFirstname.hint = getString(R.string.firstname)
            tiLastname.hint = getString(R.string.lastname)
            btValidate.text = getString(R.string.validate)
            */

            btnValider.text = getString(R.string.validate)
            btnInscription.text = getString(R.string.inscription)

        }

        binding.btnValider.setOnClickListener {
            validate(it)
        }

        binding.btnInscription.setOnClickListener {
            goToInscription(it)
        }

        println(IotApi)

        return binding.root
    }

    private fun goToInscription(view: View) {
        this.findNavController().navigate(ConnexionFragmentDirections.actionConnexionFragmentToInscriptionFragment())
    }

    private fun validate(view: View) {
        val username = this.binding.txtUsername.text
        val mdp = this.binding.txtMdp.text

        if(username.isBlank() || mdp.isBlank()) {
            Toast.makeText(this.context,"Les champs ne doivent pas ??tre vides", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.connexion()

            if(Connexion.connex) {
                view.findNavController().navigate(ConnexionFragmentDirections.actionConnexionFragmentToPrincipalFragment())
                Toast.makeText(this.context,"Connexion effectu??e", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this.context,"Connexion ??chou??e", Toast.LENGTH_SHORT).show()
            }
        }





    }

}