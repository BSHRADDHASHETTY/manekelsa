package com.example.manekelsa.ui.user

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.manekelsa.models.Worker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import androidx.navigation.NavController
import coil.compose.AsyncImage
@Composable
fun WorkerListScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    var workerList by remember {
        mutableStateOf<List<Worker>>(emptyList())
    }

    var searchText by remember {
        mutableStateOf("")
    }

    // 🔥 Fetch data from Firestore
    LaunchedEffect(Unit) {
        db.collection("workers")
            .addSnapshotListener { value, error ->

                if (error != null) return@addSnapshotListener

                val list = mutableListOf<Worker>()

                value?.documents?.forEach { document ->

                    val worker = document.toObject(Worker::class.java)

                    if (worker != null) {

                        // 🔥 AUTO-FIX (handles old + new field)
                        val availableOld = document.getBoolean("available") ?: false
                        val availableNew = worker.isAvailable

                        val finalAvailability = availableNew || availableOld

                        val updatedWorker = worker.copy(
                            isAvailable = finalAvailability
                        )

                        list.add(updatedWorker)
                    }
                }

                workerList = list
            }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("worker_list") { inclusive = true }
                    }
                }
            ) {
                Text("Logout")
            }
        }

        // 🔍 Search Field
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search by Area") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // 📋 Worker List
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {

            items(
                workerList.filter {
                    it.area.contains(searchText, ignoreCase = true)
                }
            ) { worker ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

                    elevation = CardDefaults.cardElevation(8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        AsyncImage(
                            model = worker.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                        Text(
                            text = worker.name,
                            fontSize = 22.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(text = "Skill: ${worker.skill}")
                        Text(text = "Area: ${worker.area}")
                        Text(text = "Rate: ₹${worker.rate}")
                        Text(text = "Rating: ${worker.rating}")

                        // ✅ Availability Display
                        Text(
                            text = if (worker.isAvailable) "Available" else "Not Available",
                            color = if (worker.isAvailable) Color(0xFF2E7D32) else Color.Red
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // 📞 Call Button
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:${worker.phone}")
                                }
                                context.startActivity(intent)
                            }
                        ) {
                            Text("Call Worker")
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // 👍 Rate Button
                        Button(
                            onClick = {
                                db.collection("workers")
                                    .document(worker.id)
                                    .update("rating", worker.rating + 1)
                            }
                        ) {
                            Text("👍 Rate Worker")
                        }
                    }
                }
            }
        }
    }
}