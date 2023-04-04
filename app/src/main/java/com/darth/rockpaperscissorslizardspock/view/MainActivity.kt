package com.darth.rockpaperscissorslizardspock.view

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.darth.rockpaperscissorslizardspock.R
import com.darth.rockpaperscissorslizardspock.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_game, GameFragment())
            .add(R.id.fragment_list, ListFragment())
            .commit();
    }
}