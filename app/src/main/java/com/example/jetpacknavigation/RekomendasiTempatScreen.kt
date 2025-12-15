package com.example.jetpacknavigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class TempatWisata(
    val nama: String,
    val lokasi: String,
    val deskripsi: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RekomendasiTempatScreen(
    onBackToLogin: () -> Unit
) {
    val daftarTempat = listOf(
        TempatWisata(
            "Pantai Kuta",
            "Bali",
            "Pantai yang terkenal dengan sunset yang indah"
        ),
        TempatWisata(
            "Candi Borobudur",
            "Jawa Tengah",
            "Candi Buddha terbesar di dunia"
        ),
        TempatWisata(
            "Danau Toba",
            "Sumatera Utara",
            "Danau vulkanik terbesar di Indonesia"
        ),
        TempatWisata(
            "Raja Ampat",
            "Papua Barat",
            "Surga bawah laut dengan keanekaragaman hayati tertinggi"
        ),
        TempatWisata(
            "Bromo",
            "Jawa Timur",
            "Gunung berapi aktif dengan pemandangan sunrise yang menakjubkan"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rekomendasi Tempat Wisata") },
                actions = {
                    TextButton(onClick = onBackToLogin) {
                        Text("Logout")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(daftarTempat) { tempat ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = tempat.nama,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tempat.lokasi,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = tempat.deskripsi,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

