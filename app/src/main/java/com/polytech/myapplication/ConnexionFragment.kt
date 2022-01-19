package com.polytech.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.polytech.myapplication.database.Database
import com.polytech.myapplication.databinding.FragmentConnexionBinding
import com.polytech.myapplication.databinding.FragmentIdentityBinding
import com.polytech.myapplication.model.User
import com.polytech.myapplication.model.Utilisateur
import com.polytech.myapplication.viewmodel.ConnexionViewModel
import com.polytech.myapplication.viewmodel.IdentityViewModel
import com.polytech.myapplication.viewmodelfactory.ConnexionViewModelFactory
import com.polytech.myapplication.viewmodelfactory.IdentityViewModelFactory


class ConnexionFragment : Fragment() {
    private lateinit var binding: FragmentConnexionBinding
    private lateinit var viewModel: ConnexionViewModel
    private lateinit var viewModelFactory: ConnexionViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_identity, container, false)

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


        return binding.root
    }

    private fun validate(view: View) {
        viewModel.onValidate()

        //view.findNavController().navigate(IdentityFragmentDirections.actionIdentityFragmentToPersonalDataFragment(viewModel.utilisateur.value?: Utilisateur()))
    }

}