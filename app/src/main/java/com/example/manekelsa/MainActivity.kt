package com.example.manekelsa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.manekelsa.ui.auth.AuthViewModel
import com.example.manekelsa.ui.auth.LoginScreen
import com.example.manekelsa.ui.auth.OtpScreen
import com.example.manekelsa.ui.auth.SplashScreen
import com.example.manekelsa.ui.user.HomeScreen
import com.example.manekelsa.ui.user.WorkerListScreen
import com.example.manekelsa.ui.worker.WorkerProfileScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            // Shared ViewModel
            val authViewModel = remember {
                AuthViewModel()
            }

            NavHost(
                navController = navController,
                startDestination = "splash"
            ) {

                // Splash Screen
                composable("splash") {
                    SplashScreen(navController)
                }

                // Login Screen
                composable("login") {
                    LoginScreen(
                        navController = navController,
                        viewModel = authViewModel
                    )
                }

                // OTP Screen
                composable("otp") {
                    OtpScreen(
                        navController = navController,
                        viewModel = authViewModel
                    )
                }

                // Home Screen
                composable("home") {
                    HomeScreen(navController)
                }

                // Worker Profile Screen
                composable("worker_profile") {
                    WorkerProfileScreen(navController)
                }

                // Worker List Screen
                composable("worker_list") {
                    WorkerListScreen(navController)
                }
            }
        }
    }
}