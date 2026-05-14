package com.example.manekelsa.ui.worker

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.manekelsa.models.Worker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

@Composable
fun WorkerProfileScreen(navController: NavController) {

    var name by remember { mutableStateOf("") }
    var skill by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var rate by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var isAvailable by remember { mutableStateOf(true) }

    // 📷 Image
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Worker Profile", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(phone, { phone = it }, label = { Text("Phone") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(skill, { skill = it }, label = { Text("Skill") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(area, { area = it }, label = { Text("Area") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(rate, { rate = it }, label = { Text("Rate") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Available")
            Spacer(modifier = Modifier.weight(1f))
            Switch(checked = isAvailable, onCheckedChange = { isAvailable = it })
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 📷 Select Image Button
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Profile Image")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 💾 Save Button
        Button(
            onClick = {

                val workerId = db.collection("workers").document().id

                val storageRef = FirebaseStorage.getInstance().reference
                val imageRef = storageRef.child("worker_images/$workerId.jpg")

                if (imageUri != null) {

                    imageRef.putFile(imageUri!!)
                        .continueWithTask {
                            imageRef.downloadUrl
                        }
                        .addOnSuccessListener { uri ->

                            val worker = Worker(
                                id = workerId,
                                name = name,
                                phone = phone,
                                skill = skill,
                                rate = rate,
                                area = area,
                                isAvailable = isAvailable,
                                rating = 0,
                                imageUrl = uri.toString()
                            )

                            db.collection("workers")
                                .document(workerId)
                                .set(worker)

                            Toast.makeText(context, "Profile Saved", Toast.LENGTH_SHORT).show()
                            navController.navigate("worker_list")
                        }

                } else {

                    val worker = Worker(
                        id = workerId,
                        name = name,
                        phone = phone,
                        skill = skill,
                        rate = rate,
                        area = area,
                        isAvailable = isAvailable,
                        rating = 0,
                        imageUrl = ""
                    )

                    db.collection("workers")
                        .document(workerId)
                        .set(worker)

                    navController.navigate("worker_list")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Profile")
        }
    }
}