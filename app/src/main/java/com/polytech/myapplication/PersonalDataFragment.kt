package com.polytech.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseMethod
import androidx.lifecycle.ViewModelProvider
import com.polytech.myapplication.databinding.FragmentPersonalDataBinding
import com.polytech.myapplication.viewmodel.IdentityViewModel
import com.polytech.myapplication.viewmodel.PersonalDataViewModel
import com.polytech.myapplication.viewmodelfactory.PersonalDataViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


object LongConverter {
    @JvmStatic
    @InverseMethod("stringToDate")
    fun dateToString(
        value: Long
    ): String {
        val date = Date(value)
        val f = SimpleDateFormat("dd/MM/yy")
        val dateText = f.format(date)
        return dateText
    }
    @JvmStatic
    fun stringToDate( value: String
    ): Long {
        val f = SimpleDateFormat("dd/MM/yy")
        val d = f.parse(value)
        return d.time
    }
}


interface PersonalDateEventListener {
    fun onGender(gender: String)
}

class PersonalDataFragment : Fragment() { //, PersonalDateEventListener {
    private lateinit var binding: FragmentPersonalDataBinding
    private lateinit var viewModel: PersonalDataViewModel
    private lateinit var viewModelFactory: PersonalDataViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_personal_data, container, false)
// binding.eventListener = this
        val args = PersonalDataFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = PersonalDataViewModelFactory(args.user) // initialisation du Factory
        viewModel = ViewModelProvider(this,viewModelFactory).get(PersonalDataViewModel::class.java)

        binding.viewModel = viewModel
        //binding.viewModel = ViewModelProvider(this).get(IdentityViewModel::class.java)


        binding.apply {
            evBirthday.hint = getString(R.string.birthdayDate)
            btValidate.text = getString(R.string.validate)
        }
        binding.btValidate.setOnClickListener {
            validate(it)
        }

        binding.lifecycleOwner = this
        return binding.root
    }


    private fun validate(view: View) {

        val message = viewModel.user?.gender + " " +
                LongConverter.dateToString(viewModel.user?.birthdayDate?:0)
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()

    }
}