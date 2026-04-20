package com.example.tugas3mobile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FormMahasiswa()
        }
    }
}

@Composable
fun FormMahasiswa() {
    val context = LocalContext.current

    var nama by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }

    var hobiMembaca by remember { mutableStateOf(false) }
    var hobiCoding by remember { mutableStateOf(false) }
    var hobiOlahraga by remember { mutableStateOf(false) }

    var hasil by remember { mutableStateOf("") }
    var errorNama by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        // 🔵 HEADER
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = stringResource(R.string.judul_form),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ⚪ FORM
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Nama
                OutlinedTextField(
                    value = nama,
                    onValueChange = {
                        nama = it
                        errorNama = false
                    },
                    label = { Text(stringResource(R.string.nama_label)) },
                    placeholder = { Text(stringResource(R.string.nama_placeholder)) },
                    isError = errorNama,
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorNama) {
                    Text(
                        stringResource(R.string.error_nama),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // Gender
                Text(stringResource(R.string.jenis_kelamin))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    GenderItem(stringResource(R.string.laki), selectedGender) {
                        selectedGender = it
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    GenderItem(stringResource(R.string.perempuan), selectedGender) {
                        selectedGender = it
                    }
                }

                // Hobi
                Text(stringResource(R.string.hobi))

                HobiItem(stringResource(R.string.membaca), hobiMembaca) { hobiMembaca = it }
                HobiItem(stringResource(R.string.coding), hobiCoding) { hobiCoding = it }
                HobiItem(stringResource(R.string.olahraga), hobiOlahraga) { hobiOlahraga = it }

                Spacer(modifier = Modifier.height(8.dp))

                // Button
                Button(
                    onClick = {
                        if (nama.isEmpty()) {
                            errorNama = true
                            Toast.makeText(
                                context,
                                context.getString(R.string.toast_nama),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (selectedGender.isEmpty()) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.toast_gender),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val hobiList = mutableListOf<String>()
                            if (hobiMembaca) hobiList.add(context.getString(R.string.membaca))
                            if (hobiCoding) hobiList.add(context.getString(R.string.coding))
                            if (hobiOlahraga) hobiList.add(context.getString(R.string.olahraga))

                            hasil = """
                                Nama : $nama
                                Kelamin : $selectedGender
                                Hobi : ${hobiList.joinToString(", ")}
                            """.trimIndent()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(stringResource(R.string.tampilkan))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔲 HASIL
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.medium
                ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = if (hasil.isEmpty())
                    stringResource(R.string.placeholder_hasil)
                else
                    hasil,
                modifier = Modifier.padding(16.dp),
                color = if (hasil.isEmpty())
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                else
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun GenderItem(text: String, selected: String, onSelect: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected == text,
            onClick = { onSelect(text) }
        )
        Text(text)
    }
}

@Composable
fun HobiItem(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(text)
    }
}