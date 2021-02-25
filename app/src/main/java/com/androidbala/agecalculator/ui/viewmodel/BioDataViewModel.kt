package com.androidbala.agecalculator.ui.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbala.agecalculator.utils.TAG

class BioDataViewModel : ViewModel() {

    private val _isFormValid = MutableLiveData<Boolean>()

    val isFormValid: LiveData<Boolean>
        get() = _isFormValid

    var firstName = ""
        set(value) {
            field = value
            validateForm()
        }

    var lastName = ""
        set(value) {
            field = value
            validateForm()
        }

    var dateOfBirth = ""
        set(value) {
            field = value
            validateForm()
        }

    private fun validateForm() {
        if (firstName.length > 1 && lastName.length > 1 && dateOfBirth.isNotBlank()) {
            _isFormValid.postValue(true)
        } else {
            _isFormValid.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "Done")
    }

}