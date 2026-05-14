package com.example.manekelsa.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFF3D6),
                        Color(0xFFE8F7FF),
                        Color(0xFFE9FFE8)
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(x = (-25).dp, y = 70.dp)
                .background(Color(0xFFFFC857).copy(alpha = 0.30f), CircleShape)
        )

        Box(
            modifier = Modifier
                .size(90.dp)
                .offset(x = 285.dp, y = 100.dp)
                .background(Color(0xFF7AD3FF).copy(alpha = 0.22f), CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(90.dp))

            Card(
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.75f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .background(
                                    brush = Brush.linearGradient(
                                        listOf(Color(0xFF6A5AE0), Color(0xFF2D9CDB))
                                    ),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(42.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Mane-Kelsa",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF172B4D)
                        )

                        Text(
                            text = "Find Trusted Workers.\nGet Work Done.",
                            fontSize = 16.sp,
                            color = Color(0xFF44546A)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Card(
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome Back! 👋",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5A4FCF)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Choose what you want to do",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    HomeActionButton(
                        title = "Add Worker Profile",
                        Icons.Default.Build,
                        color = Color(0xFF6A5AE0),
                        onClick = { navController.navigate("worker_profile") }
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    HomeActionButton(
                        title = "View Workers",
                        icon = Icons.Default.Search,
                        color = Color(0xFF2FB344),
                        onClick = { navController.navigate("worker_list") }
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    HomeActionButton(
                        title = "Logout",
                        Icons.AutoMirrored.Filled.ExitToApp,
                        color = Color(0xFFFF8C42),
                        onClick = {
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeActionButton(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}