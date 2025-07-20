package com.example.goevents.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.goevents.domain.model.EventType
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    location: String,
    onLocationChange: (String) -> Unit,
    eventType: String?,
    onEventTypeChange: (String?) -> Unit,
    date: String?,
    onDateChange: (String?) -> Unit,
    onDismiss: () -> Unit,
    onApply: (String?) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val eventTypes = EventType.entries.map { it.name }

    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var isoDate by rememberSaveable { mutableStateOf<String?>(null) }

    fun convertToISO8601(dateStr: String): String? {
        return try {
            val inputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val parsedDate = inputFormat.parse(dateStr)

            val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'00:00:00'Z'", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getTimeZone("UTC")
            outputFormat.format(parsedDate)
        } catch (e: Exception) {
            null
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filter Events") },
        text = {
            Column {
                OutlinedTextField(
                    value = location,
                    onValueChange = onLocationChange,
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = eventType ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Event Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("ANY") },
                            onClick = {
                                onEventTypeChange(null)
                                expanded = false
                            }
                        )
                        eventTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    onEventTypeChange(type)
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = date ?: "",
                    onValueChange = {},
                    label = { Text("Date") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.CalendarToday, contentDescription = "Pick Date")
                        }
                    }
                )

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                showDatePicker = false
                            }) {
                                Text("OK")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("Cancel")
                            }
                        }
                    ) {
                        val datePickerState = rememberDatePickerState()
                        DatePicker(state = datePickerState)

                        LaunchedEffect(datePickerState.selectedDateMillis) {
                            datePickerState.selectedDateMillis?.let {
                                val formattedUI = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                                    .format(Date(it))
                                onDateChange(formattedUI)

                                isoDate = convertToISO8601(formattedUI)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onApply(isoDate)
            }) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
