package com.namijapanese.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileAvatar(
    displayName: String?,
    email: String?,
    photoUrl: String?,
    size: Dp = 40.dp,
    modifier: Modifier = Modifier
) {
    // TODO: Add Coil for network image loading if needed in the future
    val initial = getInitial(displayName, email)
    val fontSize = when {
        size >= 64.dp -> 28.sp
        size >= 48.dp -> 20.sp
        else -> 16.sp
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

private fun getInitial(displayName: String?, email: String?): String {
    val name = displayName?.trim()
    if (!name.isNullOrEmpty()) {
        return name.first().uppercase()
    }
    val mail = email?.trim()
    if (!mail.isNullOrEmpty()) {
        return mail.first().uppercase()
    }
    return "?"
}
