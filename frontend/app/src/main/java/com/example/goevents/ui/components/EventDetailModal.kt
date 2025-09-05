package com.example.goevents.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.goevents.domain.model.Event
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun EventDetailModal(
    event: Event,
    onDismiss: () -> Unit
) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd.M.yyyy HH:mm", Locale.getDefault())

    val startDateParsed = remember(event.startDate) {
        try {
            inputFormat.parse(event.startDate)
        } catch (e: Exception) {
            null
        }
    }
    val endDateParsed = remember(event.endDate) {
        try {
            inputFormat.parse(event.endDate)
        } catch (e: Exception) {
            null
        }
    }

    val startDateText = startDateParsed?.let { outputFormat.format(it) } ?: "Invalid start date"
    val endDateText = endDateParsed?.let { outputFormat.format(it) } ?: "Invalid end date"

    Dialog(onDismissRequest = onDismiss) {
        Box(
            Modifier.Companion
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.large)
        ) {
            Column(modifier = Modifier.Companion.padding(16.dp)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Companion.Bold
                    )
                )
                Spacer(modifier = Modifier.Companion.height(8.dp))
                event.imageUrl?.let { url ->
                    Image(
                        painter = rememberAsyncImagePainter(url),
                        contentDescription = event.title,
                        modifier = Modifier.Companion
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Companion.Crop
                    )
                    Spacer(modifier = Modifier.Companion.height(12.dp))
                }
                Text("${event.eventType}")
                Text(event.location)
                Text("$startDateText - $endDateText")
                Spacer(modifier = Modifier.Companion.height(8.dp))
                Text("Description: ${event.description}")
            }
        }
    }
}