package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.AppDatabase
import com.example.data.PackingRepository
import com.example.ui.PackingApp
import com.example.ui.PackingViewModel
import com.example.ui.PackingViewModelFactory
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Room Database, DAO and Repository
        val database = AppDatabase.getDatabase(this)
        val repository = PackingRepository(database.packingDao())
        
        // Instantiate ViewModel with proper Factory injection
        val viewModel: PackingViewModel by viewModels {
            PackingViewModelFactory(repository, applicationContext)
        }

        enableEdgeToEdge()
        setContent {
            val isDark by viewModel.isDarkTheme.collectAsStateWithLifecycle()
            MyApplicationTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    PackingApp(viewModel = viewModel)
                }
            }
        }
    }
}

