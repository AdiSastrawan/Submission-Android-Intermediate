package com.adisastrawan.storyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.ui.auth.AuthViewModel
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
            R.id.logout->{
                lifecycleScope.launch {
                    authViewModel?.saveAuth("","","")
                }
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }
//    override fun onBackPressed() {
//        val navController = findNavController(R.id.container)
//        // Cek apakah saat ini berada di halaman home
//        authViewModel?.getAuth()?.observe(this){
//            if(it.token.isNotEmpty()){
//                if (navController.currentDestination?.id == R.id.listStoryFragment) {
//                    // Jika berada di halaman home, popBackStack untuk menghapus fragment login dari back stack
//                    finish()
//                } else {
//                    // Jika tidak berada di halaman home, jalankan fungsi onBackPressed() bawaan
//                    super.onBackPressed()
//                }
//            }
//        }
//    }
}