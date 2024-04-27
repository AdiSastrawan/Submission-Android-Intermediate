package com.adisastrawan.storyapp.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.ui.auth.AuthViewModel
import com.adisastrawan.storyapp.ui.maps.MapsFragment
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var authViewModel : AuthViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val factory = ViewModelFactory.getInstance(this)
        authViewModel = ViewModelProvider(this,factory)[AuthViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_logout->{
                lifecycleScope.launch {
                    authViewModel?.saveAuth("","","")
                }
                true
            }
            R.id.setting_language->{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }

            R.id.action_map->{
                val fragment = MapsFragment()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction= fragmentManager.beginTransaction()

                fragmentTransaction.replace(R.id.container, fragment)

                fragmentTransaction.addToBackStack(null)

                fragmentTransaction.commit()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}