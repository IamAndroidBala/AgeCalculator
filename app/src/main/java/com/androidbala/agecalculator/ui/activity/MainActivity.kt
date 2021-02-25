package com.androidbala.agecalculator.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.androidbala.agecalculator.R
import com.androidbala.agecalculator.databinding.ActivityMainBinding
import com.androidbala.agecalculator.ui.fragments.GetBioDataFragment
import com.androidbala.agecalculator.utils.replaceFragment
import com.androidbala.agecalculator.utils.viewBinding

class MainActivity : AppCompatActivity() {

    private val _viewBinding by viewBinding (ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_viewBinding.root)

        setToolbar()
        addBioDataFragment()

    }

    /**
     * set toolbar in activity which will show in all fragment
     */
    private fun setToolbar() {
        setSupportActionBar(_viewBinding.toolbar)
        _viewBinding.toolbar.title = resources.getString(R.string.app_name)
    }

    /**
     * show fragment as default page
     */
    private fun addBioDataFragment() {
        replaceFragment(GetBioDataFragment(), R.id.fragment_container)
    }

    /**
     * check back button pressed either from fragment or activty
     */
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}