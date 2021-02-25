package com.androidbala.agecalculator.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BioDataModel(var firstName : String? = null,
                        var lastName: String? = null,
                        var dateOfBirth : String? = null): Parcelable {

}
