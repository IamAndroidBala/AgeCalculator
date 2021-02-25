package com.androidbala.agecalculator.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.androidbala.agecalculator.R
import com.androidbala.agecalculator.databinding.FragmentBiodataBinding
import com.androidbala.agecalculator.ui.activity.MainActivity
import com.androidbala.agecalculator.ui.models.BioDataModel
import com.androidbala.agecalculator.ui.viewmodel.BioDataViewModel
import com.androidbala.agecalculator.utils.TAG
import com.androidbala.agecalculator.utils.dateFormatter
import com.androidbala.agecalculator.utils.replaceFragment
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog
import java.util.*

class GetBioDataFragment : Fragment() {

    var isValidationCompleted = false
    private lateinit var mBinding : FragmentBiodataBinding
    private lateinit var viewModel: BioDataViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_biodata, container, false)
        val mRootView = mBinding.root

        viewModel = ViewModelProvider(this).get(BioDataViewModel::class.java)

        mBinding.btnNext.setOnClickListener {
            validateDataAndGoToNextScreen()
        }

        mBinding.edDateOfBirth.setOnClickListener { showPicker() }

        mBinding.edFirstName.doAfterTextChanged { text -> viewModel.firstName = (text?.toString() ?: "" )}
        mBinding.edLastName.doAfterTextChanged { text -> viewModel.lastName = (text?.toString() ?: "")}
        mBinding.edDateOfBirth.doAfterTextChanged { text -> viewModel.dateOfBirth = (text?.toString() ?: "") }

        viewModel.isFormValid.observe(activity as MainActivity, Observer { valid ->
            isValidationCompleted = valid
        })

        return mRootView

    }

    /**
     * check first name and last name , dob either null or not
     * if null, show error text and force user to enter data
     */
    private fun validateDataAndGoToNextScreen(){

        if(isValidationCompleted) {
            addShowBioDataFragment()
        }  else {
            when {
                viewModel.firstName.isBlank() -> mBinding.edFirstName.error = resources.getString(R.string.first_name)

                viewModel.lastName.isBlank() -> mBinding.edLastName.error = resources.getString(R.string.last_name)

                viewModel.dateOfBirth.isBlank() -> mBinding.edDateOfBirth.error = resources.getString(R.string.date_of_birth)
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        removeBackButton()
    }

    /**
     * Go to next screen
     */
    private fun addShowBioDataFragment() {
        (activity as MainActivity).replaceFragment(
                ShowBioDataFragment.newInstance(
                        BioDataModel(viewModel.firstName, viewModel.lastName, viewModel.dateOfBirth)),
                 R.id.fragment_container,
                "showDetails"
        )
    }

    /**
     * show date picker
     */
    private fun showPicker(){

        val dialog = DatePickerFragmentDialog.newInstance({ _, year, monthOfYear, dayOfMonth ->
            val selectedDob = Calendar.getInstance()
            selectedDob.set(year, monthOfYear, dayOfMonth)
            val birthDate = dateFormatter().format(selectedDob.time)
            mBinding.edDateOfBirth.setText(birthDate)
        }, 1989, 10, 22)

        activity?.supportFragmentManager?.let { dialog.show(it, "tag") }

    }

    /**
     * hide toolbar
     */
    private fun removeBackButton() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(false)
    }

}