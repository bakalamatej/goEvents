package com.example.goevents.ui.screens.events

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.goevents.data.remote.dto.EventRequest
import com.example.goevents.domain.model.EventType
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TextField
import com.example.goevents.ui.components.EventTypeDropdown

@Composable
fun AddEventScreen(
    navController: NavController,
    viewModel: EventViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var startDateDisplay by remember { mutableStateOf("") }
    var endDateDisplay by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val displayFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SnackbarHost(hostState = snackbarHostState)

        Text("Add New Event", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        EventTypeDropdown(
            selectedType = type,
            onTypeSelected = { type = it }
        )

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = link,
            onValueChange = { link = it },
            label = { Text("External Link (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val cal = Calendar.getInstance().apply {
                                set(year, month, dayOfMonth)
                            }
                            TimePickerDialog(
                                context,
                                { _, hourOfDay, minute ->
                                    startDate = String.format(
                                        Locale.ROOT,
                                        "%04d-%02d-%02dT%02d:%02d",
                                        year,
                                        month + 1,
                                        dayOfMonth,
                                        hourOfDay,
                                        minute
                                    )
                                    startDateDisplay = displayFormat.format(cal.time)
                                },
                                cal.get(Calendar.HOUR_OF_DAY),
                                cal.get(Calendar.MINUTE),
                                true
                            ).show()
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text(
                    text = if (startDate.isEmpty()) "Pick Start Date" else startDateDisplay,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val cal = Calendar.getInstance().apply {
                                set(year, month, dayOfMonth)
                            }
                            TimePickerDialog(
                                context,
                                { _, hourOfDay, minute ->
                                    endDate = String.format(
                                        Locale.ROOT,
                                        "%04d-%02d-%02dT%02d:%02d",
                                        year,
                                        month + 1,
                                        dayOfMonth,
                                        hourOfDay,
                                        minute
                                    )
                                    endDateDisplay = displayFormat.format(cal.time)
                                },
                                cal.get(Calendar.HOUR_OF_DAY),
                                cal.get(Calendar.MINUTE),
                                true
                            ).show()
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text(
                    text = if (endDate.isEmpty()) "Pick End Date" else endDateDisplay,
                    fontSize = 16.sp
                )
            }
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        if (title.isBlank() || location.isBlank() || type.isBlank() ||
                            description.isBlank() || startDate.isBlank() || endDate.isBlank()
                        ) {
                            snackbarHostState.showSnackbar("Please fill in all required fields.")
                            return@launch
                        }

                        val eventRequest = EventRequest(
                            id = null,
                            title = title,
                            description = description,
                            startDate = startDate,
                            endDate = endDate,
                            location = location,
                            eventType = EventType.valueOf(type.uppercase()),
                            imageUrl = imageUrl.takeIf { it.isNotBlank() },
                            link = link.takeIf { it.isNotBlank() }
                        )

                        Log.d("EventRequest", eventRequest.toString())

                        viewModel.createEvent(eventRequest) { success, errorMessage ->
                            coroutineScope.launch {
                                if (success) {
                                    snackbarHostState.showSnackbar("Event created successfully!")
                                    navController.popBackStack()
                                } else {
                                    snackbarHostState.showSnackbar("Error: $errorMessage")
                                    Log.d("EventRequest", eventRequest.toString())
                                }
                            }
                        }
                    } catch (e: Exception) {
                        snackbarHostState.showSnackbar("Error: ${e.localizedMessage ?: "Invalid input"}")
                    }
                }
            },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 12.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .align(Alignment.End)
                .height(50.dp)
                .widthIn(min = 120.dp)
        ) {
            Text("Save", fontSize = 16.sp)
        }
    }
}