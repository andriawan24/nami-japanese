package com.namijapanese.feature.writing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritingPracticeScreen(
    onBackClick: () -> Unit,
    onComplete: () -> Unit,
    viewModel: WritingPracticeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val sakuraCoral = MaterialTheme.colorScheme.primary
    val guideLineColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
    val referenceColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.07f)
    val canDraw = !uiState.isCompleted && !uiState.isSaving

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text("Practice Writing", color = MaterialTheme.colorScheme.primary)
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.primary)
                }
            }
        )

        uiState.character?.let { character ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Character info card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = character.character,
                            fontSize = 48.sp,
                            color = MaterialTheme.colorScheme.primary,
                            lineHeight = 56.sp
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = character.romaji.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "  •  ",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = character.type.displayName,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "  •  ",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${character.strokeCount} strokes",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Canvas practice pad
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(32.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .onSizeChanged { size: IntSize ->
                            viewModel.updateCanvasSize(size.width.toFloat(), size.height.toFloat())
                        }
                        .then(
                            if (canDraw) {
                                Modifier.pointerInput(Unit) {
                                    detectDragGestures(
                                        onDragStart = { offset -> viewModel.startStroke(offset.x, offset.y) },
                                        onDrag = { change, _ -> viewModel.continueStroke(change.position.x, change.position.y) },
                                        onDragEnd = { viewModel.endStroke() }
                                    )
                                }
                            } else Modifier
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Faint grid lines + reference kana
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Vertical center line
                        drawLine(
                            color = guideLineColor,
                            start = Offset(size.width / 2, 0f),
                            end = Offset(size.width / 2, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                        // Horizontal center line
                        drawLine(
                            color = guideLineColor,
                            start = Offset(0f, size.height / 2),
                            end = Offset(size.width, size.height / 2),
                            strokeWidth = 1.dp.toPx()
                        )

                        // User strokes
                        uiState.strokes.forEach { stroke ->
                            if (stroke.points.size > 1) {
                                val path = Path().apply {
                                    moveTo(stroke.points.first().first, stroke.points.first().second)
                                    for (i in 1 until stroke.points.size) {
                                        lineTo(stroke.points[i].first, stroke.points[i].second)
                                    }
                                }
                                drawPath(path, sakuraCoral, style = Stroke(width = 10f))
                            }
                        }
                        if (uiState.currentStroke.size > 1) {
                            val path = Path().apply {
                                moveTo(uiState.currentStroke.first().first, uiState.currentStroke.first().second)
                                for (i in 1 until uiState.currentStroke.size) {
                                    lineTo(uiState.currentStroke[i].first, uiState.currentStroke[i].second)
                                }
                            }
                            drawPath(path, sakuraCoral, style = Stroke(width = 10f))
                        }
                    }

                    // Faint reference kana (behind strokes)
                    Text(
                        text = character.character,
                        fontSize = 180.sp,
                        color = referenceColor,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    // Bottom toolbar overlay
                    if (!uiState.isCompleted && !uiState.isSaving) {
                        WritingToolbar(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(12.dp),
                            canUndo = uiState.strokes.isNotEmpty(),
                            canCheck = uiState.strokes.isNotEmpty(),
                            canDelete = uiState.strokes.isNotEmpty() || uiState.currentStroke.isNotEmpty(),
                            onUndo = { viewModel.undoLastStroke() },
                            onCheck = { viewModel.completePractice() },
                            onDelete = { viewModel.clearCanvas() }
                        )
                    }

                    // Saving overlay
                    if (uiState.isSaving) {
                        Card(
                            modifier = Modifier.align(Alignment.TopCenter).padding(12.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)
                            )
                        ) {
                            Text(
                                text = "Checking...",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Result overlay
                    if (uiState.isCompleted) {
                        WritingResultOverlay(
                            modifier = Modifier.align(Alignment.TopCenter).padding(12.dp),
                            score = uiState.score ?: 0,
                            passed = uiState.passed == true,
                            scoringMode = uiState.scoringMode,
                            errorMessage = uiState.errorMessage,
                            onTryAgain = { viewModel.clearCanvas() },
                            onDone = onComplete
                        )
                    }
                }

                // Helper text
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Follow the stroke order guides to complete the character.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun WritingToolbar(
    modifier: Modifier = Modifier,
    canUndo: Boolean,
    canCheck: Boolean,
    canDelete: Boolean,
    onUndo: () -> Unit,
    onCheck: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Undo
            IconButton(
                onClick = onUndo,
                enabled = canUndo,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Undo",
                    tint = if (canUndo) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                    modifier = Modifier.size(20.dp)
                )
            }

            // Check button (primary pill)
            Button(
                onClick = onCheck,
                enabled = canCheck,
                modifier = Modifier.height(40.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (canCheck) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (canCheck) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Check", style = MaterialTheme.typography.labelMedium)
            }

            // Delete
            IconButton(
                onClick = onDelete,
                enabled = canDelete,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = if (canDelete) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun WritingResultOverlay(
    modifier: Modifier = Modifier,
    score: Int,
    passed: Boolean,
    scoringMode: String?,
    errorMessage: String?,
    onTryAgain: () -> Unit,
    onDone: () -> Unit
) {
    val resultColor = if (passed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Score: $score",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = resultColor
            )

            Text(
                text = if (passed) "Nice work!" else "Keep practicing",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            scoringMode?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            errorMessage?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onTryAgain,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Try Again")
                }
                Button(
                    onClick = onDone,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Done")
                }
            }
        }
    }
}
