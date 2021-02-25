package com.androidbala.agecalculator.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androidbala.agecalculator.R
import com.androidbala.agecalculator.ui.models.BioDataModel
import com.androidbala.agecalculator.utils.*
import java.util.*

class ShowBioDataFragment: Fragment() {

    lateinit var  bioData: BioDataModel
    lateinit var tvDob : TextView

    companion object {
        @JvmStatic
        fun newInstance(bioData: BioDataModel) = ShowBioDataFragment().apply {
            arguments = Bundle().apply {
                putParcelable("BioData", bioData)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        /**
         * get the bundle data
         */
        bioData =  arguments?.getParcelable("BioData")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_show_data, container, false)

        val tvName = view.findViewById<TextView>(R.id.tvFirstName)
        val tvLastName = view.findViewById<TextView>(R.id.tvLastName)
        tvDob = view.findViewById<TextView>(R.id.tvDob)

        bioData.firstName?.let {
            val firstName = "$FIRST_NAME : $it"
            tvName.text = firstName
        }

        bioData.lastName?.let {
            val firstName = "$LAST_NAME : $it"
            tvLastName.text = firstName
        }

        calculateAge()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        enableBackButton()
    }

    /**
     * enable the back button in toolbar
     */
    private fun enableBackButton() {
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(true)
    }

    /**
     * function to calculate the age
     */
    private fun calculateAge() {

        var currentDay      : Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        var currentMonth    : Int = Calendar.getInstance().get(Calendar.MONTH);
        var currentYear     : Int = Calendar.getInstance().get(Calendar.YEAR)

        val calendarNow = Calendar.getInstance()
        val dateNow = calendarNow.time

        val birthDay    : Int = bioData.dateOfBirth!!.split("/")[0].toInt()
        val birthMonth  : Int = bioData.dateOfBirth!!.split("/")[1].toInt()
        val birthYear   : Int = bioData.dateOfBirth!!.split("/")[2].toInt()

        val calendarDOB = Calendar.getInstance()
        calendarDOB.set(birthYear, birthMonth, birthDay)

        val dob = calendarDOB.time

        if (dob.after(dateNow)) {
            return
        }
        // days of every month
        val month = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

        // if birth date is greater then current birth
        // month then do not count this month and add 30
        // to the date so as to subtract the date and
        // get the remaining days
        if (calendarDOB.get(Calendar.DAY_OF_MONTH) > calendarNow.get(Calendar.DAY_OF_MONTH)) {
            currentDay += month[birthMonth - 1]
            currentMonth -= 1
        }

        // if birth month exceeds current month, then do
        // not count this year and add 12 to the month so
        // that we can subtract and find out the difference
        if (calendarDOB.get(Calendar.MONTH) > calendarNow.get(Calendar.MONTH)) {
            currentYear -= 1
            currentMonth += 12
        }

        // calculate date, month, year
        val days   = currentDay - birthDay
        val months = currentMonth - birthMonth
        val years  = currentYear - birthYear

        val age     = "$DATE_OF_BIRTH : $years $YEARS $months $MONTHS $days $DAYS"
        tvDob.text  = age

    }

}