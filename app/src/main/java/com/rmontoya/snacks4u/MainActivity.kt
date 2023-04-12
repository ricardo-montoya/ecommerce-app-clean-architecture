package com.rmontoya.snacks4u

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.rmontoya.snacks4u.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navigationFragment =
            supportFragmentManager.findFragmentById(R.id.fMain) as NavHostFragment?
        val navHostController = navigationFragment?.navController

        binding.bnvMainMenu.apply {
            navHostController?.let { navController ->
                NavigationUI.setupWithNavController(
                    this,
                    navController
                )
                setOnItemSelectedListener { item ->
                    NavigationUI.onNavDestinationSelected(item, navController)
                    true
                }
                setOnItemReselectedListener { item ->
                    val currentNode : NavGraph = navController.graph.findNode(item.itemId) as NavGraph
                    currentNode.let { navGraph ->
                        navController.popBackStack(
                            destinationId = navGraph.startDestinationId,
                            inclusive = false
                        )
                    }
                }
            }
        }
    }

}