package com.namijapanese.feature.writing

data class ReferenceStroke(
    val points: List<Pair<Float, Float>>
)

data class KanaWritingTemplate(
    val characterId: String,
    val strokes: List<ReferenceStroke>
)

object KanaWritingTemplates {

    private val templates = mapOf(
        "h_a" to KanaWritingTemplate(
            characterId = "h_a",
            strokes = listOf(
                // Stroke 1: horizontal top curve
                ReferenceStroke(listOf(
                    0.25f to 0.35f, 0.35f to 0.33f, 0.45f to 0.32f,
                    0.55f to 0.33f, 0.65f to 0.35f
                )),
                // Stroke 2: vertical drop through center
                ReferenceStroke(listOf(
                    0.45f to 0.30f, 0.45f to 0.40f, 0.45f to 0.50f,
                    0.45f to 0.60f, 0.45f to 0.70f
                )),
                // Stroke 3: curved loop at bottom
                ReferenceStroke(listOf(
                    0.45f to 0.55f, 0.50f to 0.60f, 0.55f to 0.65f,
                    0.50f to 0.70f, 0.45f to 0.65f, 0.40f to 0.55f,
                    0.45f to 0.50f
                ))
            )
        ),
        "h_i" to KanaWritingTemplate(
            characterId = "h_i",
            strokes = listOf(
                // Stroke 1: left downward stroke
                ReferenceStroke(listOf(
                    0.35f to 0.30f, 0.33f to 0.35f, 0.32f to 0.45f,
                    0.33f to 0.55f, 0.35f to 0.65f
                )),
                // Stroke 2: right downward stroke
                ReferenceStroke(listOf(
                    0.65f to 0.30f, 0.67f to 0.35f, 0.68f to 0.45f,
                    0.67f to 0.55f, 0.65f to 0.65f
                ))
            )
        ),
        "h_u" to KanaWritingTemplate(
            characterId = "h_u",
            strokes = listOf(
                // Stroke 1: top hat mark
                ReferenceStroke(listOf(
                    0.40f to 0.30f, 0.45f to 0.28f, 0.50f to 0.27f,
                    0.55f to 0.28f, 0.60f to 0.30f
                )),
                // Stroke 2: vertical with hook
                ReferenceStroke(listOf(
                    0.48f to 0.35f, 0.47f to 0.40f, 0.47f to 0.50f,
                    0.47f to 0.60f, 0.48f to 0.68f, 0.52f to 0.70f
                ))
            )
        ),
        "h_e" to KanaWritingTemplate(
            characterId = "h_e",
            strokes = listOf(
                // Stroke 1: top-left angled stroke
                ReferenceStroke(listOf(
                    0.30f to 0.30f, 0.35f to 0.35f, 0.38f to 0.42f,
                    0.40f to 0.50f, 0.42f to 0.58f
                )),
                // Stroke 2: horizontal middle
                ReferenceStroke(listOf(
                    0.35f to 0.45f, 0.42f to 0.44f, 0.50f to 0.43f,
                    0.58f to 0.44f, 0.65f to 0.45f
                )),
                // Stroke 3: right vertical
                ReferenceStroke(listOf(
                    0.60f to 0.32f, 0.58f to 0.40f, 0.57f to 0.50f,
                    0.58f to 0.60f, 0.60f to 0.68f
                ))
            )
        ),
        "h_o" to KanaWritingTemplate(
            characterId = "h_o",
            strokes = listOf(
                // Stroke 1: horizontal top
                ReferenceStroke(listOf(
                    0.30f to 0.35f, 0.40f to 0.33f, 0.50f to 0.32f,
                    0.60f to 0.33f, 0.70f to 0.35f
                )),
                // Stroke 2: vertical through center
                ReferenceStroke(listOf(
                    0.50f to 0.30f, 0.50f to 0.38f, 0.50f to 0.48f,
                    0.50f to 0.58f, 0.50f to 0.68f
                )),
                // Stroke 3: bottom loop
                ReferenceStroke(listOf(
                    0.50f to 0.55f, 0.55f to 0.60f, 0.60f to 0.65f,
                    0.55f to 0.70f, 0.50f to 0.68f, 0.45f to 0.62f,
                    0.50f to 0.58f
                ))
            )
        ),
        "h_ka" to KanaWritingTemplate(
            characterId = "h_ka",
            strokes = listOf(
                // Stroke 1: top-left angled stroke
                ReferenceStroke(listOf(
                    0.30f to 0.30f, 0.35f to 0.35f, 0.38f to 0.42f,
                    0.40f to 0.50f, 0.42f to 0.58f
                )),
                // Stroke 2: horizontal middle
                ReferenceStroke(listOf(
                    0.35f to 0.45f, 0.42f to 0.44f, 0.50f to 0.43f,
                    0.58f to 0.44f, 0.65f to 0.45f
                )),
                // Stroke 3: right vertical
                ReferenceStroke(listOf(
                    0.60f to 0.32f, 0.58f to 0.40f, 0.57f to 0.50f,
                    0.58f to 0.60f, 0.60f to 0.68f
                ))
            )
        ),
        "h_ki" to KanaWritingTemplate(
            characterId = "h_ki",
            strokes = listOf(
                // Stroke 1: top horizontal
                ReferenceStroke(listOf(
                    0.25f to 0.35f, 0.35f to 0.33f, 0.45f to 0.32f,
                    0.55f to 0.33f, 0.65f to 0.35f
                )),
                // Stroke 2: bottom horizontal
                ReferenceStroke(listOf(
                    0.25f to 0.55f, 0.35f to 0.53f, 0.45f to 0.52f,
                    0.55f to 0.53f, 0.65f to 0.55f
                )),
                // Stroke 3: vertical through center
                ReferenceStroke(listOf(
                    0.48f to 0.28f, 0.47f to 0.35f, 0.47f to 0.45f,
                    0.47f to 0.55f, 0.48f to 0.65f
                ))
            )
        ),
        "h_ku" to KanaWritingTemplate(
            characterId = "h_ku",
            strokes = listOf(
                // Stroke 1: single angled stroke (wedge shape)
                ReferenceStroke(listOf(
                    0.30f to 0.35f, 0.40f to 0.38f, 0.50f to 0.42f,
                    0.60f to 0.48f, 0.70f to 0.55f
                ))
            )
        ),
        "h_ke" to KanaWritingTemplate(
            characterId = "h_ke",
            strokes = listOf(
                // Stroke 1: left vertical
                ReferenceStroke(listOf(
                    0.30f to 0.30f, 0.30f to 0.38f, 0.30f to 0.48f,
                    0.30f to 0.58f, 0.30f to 0.68f
                )),
                // Stroke 2: horizontal middle
                ReferenceStroke(listOf(
                    0.25f to 0.48f, 0.35f to 0.47f, 0.45f to 0.46f,
                    0.55f to 0.47f, 0.65f to 0.48f
                )),
                // Stroke 3: right vertical
                ReferenceStroke(listOf(
                    0.60f to 0.30f, 0.60f to 0.38f, 0.60f to 0.48f,
                    0.60f to 0.58f, 0.60f to 0.68f
                ))
            )
        ),
        "h_ko" to KanaWritingTemplate(
            characterId = "h_ko",
            strokes = listOf(
                // Stroke 1: top horizontal
                ReferenceStroke(listOf(
                    0.25f to 0.38f, 0.35f to 0.37f, 0.45f to 0.36f,
                    0.55f to 0.37f, 0.65f to 0.38f
                )),
                // Stroke 2: bottom horizontal
                ReferenceStroke(listOf(
                    0.25f to 0.58f, 0.35f to 0.57f, 0.45f to 0.56f,
                    0.55f to 0.57f, 0.65f to 0.58f
                ))
            )
        ),
        "h_sa" to KanaWritingTemplate(
            characterId = "h_sa",
            strokes = listOf(
                // Stroke 1: top horizontal
                ReferenceStroke(listOf(
                    0.25f to 0.35f, 0.35f to 0.33f, 0.45f to 0.32f,
                    0.55f to 0.33f, 0.65f to 0.35f
                )),
                // Stroke 2: angled downward stroke
                ReferenceStroke(listOf(
                    0.35f to 0.38f, 0.38f to 0.45f, 0.40f to 0.52f,
                    0.42f to 0.60f, 0.44f to 0.68f
                )),
                // Stroke 3: small horizontal tick at bottom
                ReferenceStroke(listOf(
                    0.40f to 0.58f, 0.45f to 0.57f, 0.50f to 0.56f,
                    0.55f to 0.57f
                ))
            )
        ),
        "h_shi" to KanaWritingTemplate(
            characterId = "h_shi",
            strokes = listOf(
                // Stroke 1: single curved vertical stroke (like a hook)
                ReferenceStroke(listOf(
                    0.50f to 0.30f, 0.48f to 0.38f, 0.47f to 0.48f,
                    0.48f to 0.58f, 0.52f to 0.65f, 0.58f to 0.68f
                ))
            )
        ),
        "h_su" to KanaWritingTemplate(
            characterId = "h_su",
            strokes = listOf(
                // Stroke 1: vertical down
                ReferenceStroke(listOf(
                    0.48f to 0.30f, 0.48f to 0.38f, 0.48f to 0.48f,
                    0.48f to 0.58f, 0.48f to 0.65f
                )),
                // Stroke 2: horizontal cross
                ReferenceStroke(listOf(
                    0.30f to 0.50f, 0.38f to 0.49f, 0.48f to 0.48f,
                    0.58f to 0.49f, 0.68f to 0.50f
                )),
                // Stroke 3: bottom loop
                ReferenceStroke(listOf(
                    0.48f to 0.55f, 0.53f to 0.60f, 0.56f to 0.65f,
                    0.53f to 0.70f, 0.48f to 0.68f, 0.44f to 0.62f,
                    0.48f to 0.58f
                ))
            )
        ),
        "h_se" to KanaWritingTemplate(
            characterId = "h_se",
            strokes = listOf(
                // Stroke 1: horizontal middle
                ReferenceStroke(listOf(
                    0.25f to 0.45f, 0.35f to 0.44f, 0.45f to 0.43f,
                    0.55f to 0.44f, 0.65f to 0.45f
                )),
                // Stroke 2: left vertical
                ReferenceStroke(listOf(
                    0.35f to 0.30f, 0.35f to 0.38f, 0.35f to 0.48f,
                    0.35f to 0.58f, 0.35f to 0.68f
                )),
                // Stroke 3: right vertical
                ReferenceStroke(listOf(
                    0.60f to 0.30f, 0.60f to 0.38f, 0.60f to 0.48f,
                    0.60f to 0.58f, 0.60f to 0.68f
                ))
            )
        ),
        "h_so" to KanaWritingTemplate(
            characterId = "h_so",
            strokes = listOf(
                // Stroke 1: top-left to bottom-right
                ReferenceStroke(listOf(
                    0.35f to 0.30f, 0.40f to 0.38f, 0.45f to 0.48f,
                    0.50f to 0.58f, 0.55f to 0.68f
                )),
                // Stroke 2: top-right curved stroke
                ReferenceStroke(listOf(
                    0.55f to 0.30f, 0.53f to 0.38f, 0.50f to 0.45f,
                    0.47f to 0.48f, 0.45f to 0.45f
                ))
            )
        ),
        "h_ta" to KanaWritingTemplate(
            characterId = "h_ta",
            strokes = listOf(
                // Stroke 1: horizontal top
                ReferenceStroke(listOf(
                    0.25f to 0.33f, 0.35f to 0.32f, 0.45f to 0.31f,
                    0.55f to 0.32f, 0.65f to 0.33f
                )),
                // Stroke 2: vertical through left
                ReferenceStroke(listOf(
                    0.38f to 0.28f, 0.38f to 0.35f, 0.38f to 0.45f,
                    0.38f to 0.55f, 0.38f to 0.65f
                )),
                // Stroke 3: short horizontal
                ReferenceStroke(listOf(
                    0.42f to 0.48f, 0.48f to 0.47f, 0.54f to 0.46f,
                    0.60f to 0.47f, 0.65f to 0.48f
                )),
                // Stroke 4: vertical right with hook
                ReferenceStroke(listOf(
                    0.55f to 0.40f, 0.55f to 0.48f, 0.55f to 0.56f,
                    0.55f to 0.62f, 0.56f to 0.68f, 0.58f to 0.65f
                ))
            )
        ),
        "h_chi" to KanaWritingTemplate(
            characterId = "h_chi",
            strokes = listOf(
                // Stroke 1: top horizontal
                ReferenceStroke(listOf(
                    0.25f to 0.35f, 0.35f to 0.33f, 0.45f to 0.32f,
                    0.55f to 0.33f, 0.65f to 0.35f
                )),
                // Stroke 2: vertical with curve at bottom
                ReferenceStroke(listOf(
                    0.48f to 0.30f, 0.47f to 0.38f, 0.47f to 0.48f,
                    0.47f to 0.56f, 0.48f to 0.62f, 0.52f to 0.65f,
                    0.56f to 0.62f
                ))
            )
        ),
        "h_tsu" to KanaWritingTemplate(
            characterId = "h_tsu",
            strokes = listOf(
                // Stroke 1: top hat mark
                ReferenceStroke(listOf(
                    0.40f to 0.30f, 0.45f to 0.28f, 0.50f to 0.27f,
                    0.55f to 0.28f, 0.60f to 0.30f
                )),
                // Stroke 2: vertical down
                ReferenceStroke(listOf(
                    0.48f to 0.35f, 0.47f to 0.42f, 0.47f to 0.52f,
                    0.47f to 0.62f, 0.48f to 0.70f
                )),
                // Stroke 3: right tick
                ReferenceStroke(listOf(
                    0.58f to 0.40f, 0.60f to 0.45f, 0.62f to 0.50f,
                    0.63f to 0.55f
                ))
            )
        ),
        "h_te" to KanaWritingTemplate(
            characterId = "h_te",
            strokes = listOf(
                // Stroke 1: horizontal top
                ReferenceStroke(listOf(
                    0.25f to 0.35f, 0.35f to 0.33f, 0.45f to 0.32f,
                    0.55f to 0.33f, 0.65f to 0.35f
                )),
                // Stroke 2: curved vertical
                ReferenceStroke(listOf(
                    0.48f to 0.30f, 0.47f to 0.38f, 0.45f to 0.48f,
                    0.44f to 0.56f, 0.46f to 0.65f
                ))
            )
        ),
        "h_to" to KanaWritingTemplate(
            characterId = "h_to",
            strokes = listOf(
                // Stroke 1: vertical left
                ReferenceStroke(listOf(
                    0.38f to 0.30f, 0.38f to 0.38f, 0.38f to 0.48f,
                    0.38f to 0.58f, 0.38f to 0.68f
                )),
                // Stroke 2: curved right
                ReferenceStroke(listOf(
                    0.50f to 0.38f, 0.53f to 0.42f, 0.56f to 0.48f,
                    0.57f to 0.55f, 0.56f to 0.62f
                ))
            )
        ),
        "h_na" to KanaWritingTemplate(
            characterId = "h_na",
            strokes = listOf(
                // Stroke 1: horizontal top
                ReferenceStroke(listOf(
                    0.25f to 0.33f, 0.35f to 0.32f, 0.45f to 0.31f,
                    0.55f to 0.32f, 0.65f to 0.33f
                )),
                // Stroke 2: vertical left
                ReferenceStroke(listOf(
                    0.38f to 0.28f, 0.38f to 0.35f, 0.38f to 0.45f,
                    0.38f to 0.55f, 0.38f to 0.65f
                )),
                // Stroke 3: horizontal middle
                ReferenceStroke(listOf(
                    0.42f to 0.48f, 0.48f to 0.47f, 0.54f to 0.46f,
                    0.60f to 0.47f, 0.65f to 0.48f
                )),
                // Stroke 4: right vertical with loop
                ReferenceStroke(listOf(
                    0.55f to 0.40f, 0.55f to 0.48f, 0.55f to 0.55f,
                    0.56f to 0.60f, 0.58f to 0.64f, 0.55f to 0.68f,
                    0.50f to 0.65f
                ))
            )
        ),
        "h_ni" to KanaWritingTemplate(
            characterId = "h_ni",
            strokes = listOf(
                // Stroke 1: vertical left
                ReferenceStroke(listOf(
                    0.35f to 0.30f, 0.35f to 0.38f, 0.35f to 0.48f,
                    0.35f to 0.58f, 0.35f to 0.68f
                )),
                // Stroke 2: top horizontal
                ReferenceStroke(listOf(
                    0.40f to 0.38f, 0.48f to 0.37f, 0.56f to 0.36f,
                    0.64f to 0.37f, 0.70f to 0.38f
                )),
                // Stroke 3: bottom horizontal
                ReferenceStroke(listOf(
                    0.40f to 0.58f, 0.48f to 0.57f, 0.56f to 0.56f,
                    0.64f to 0.57f, 0.70f to 0.58f
                ))
            )
        ),
        "h_nu" to KanaWritingTemplate(
            characterId = "h_nu",
            strokes = listOf(
                // Stroke 1: diagonal down-right
                ReferenceStroke(listOf(
                    0.30f to 0.30f, 0.35f to 0.38f, 0.40f to 0.48f,
                    0.45f to 0.58f, 0.50f to 0.65f
                )),
                // Stroke 2: loop at bottom
                ReferenceStroke(listOf(
                    0.45f to 0.55f, 0.50f to 0.60f, 0.55f to 0.65f,
                    0.50f to 0.70f, 0.45f to 0.68f, 0.42f to 0.62f,
                    0.45f to 0.58f
                ))
            )
        ),
        "h_ne" to KanaWritingTemplate(
            characterId = "h_ne",
            strokes = listOf(
                // Stroke 1: vertical left
                ReferenceStroke(listOf(
                    0.30f to 0.30f, 0.30f to 0.38f, 0.30f to 0.48f,
                    0.30f to 0.58f, 0.30f to 0.68f
                )),
                // Stroke 2: horizontal top
                ReferenceStroke(listOf(
                    0.35f to 0.38f, 0.42f to 0.37f, 0.50f to 0.36f,
                    0.58f to 0.37f, 0.65f to 0.38f
                )),
                // Stroke 3: curved right
                ReferenceStroke(listOf(
                    0.50f to 0.36f, 0.55f to 0.42f, 0.58f to 0.50f,
                    0.57f to 0.58f, 0.55f to 0.65f
                ))
            )
        ),
        "h_no" to KanaWritingTemplate(
            characterId = "h_no",
            strokes = listOf(
                // Stroke 1: single loop stroke (like a spiral)
                ReferenceStroke(listOf(
                    0.50f to 0.30f, 0.45f to 0.35f, 0.42f to 0.42f,
                    0.42f to 0.50f, 0.45f to 0.58f, 0.50f to 0.65f,
                    0.55f to 0.68f, 0.58f to 0.62f
                ))
            )
        ),
        "h_ha" to KanaWritingTemplate(
            characterId = "h_ha",
            strokes = listOf(
                // Stroke 1: vertical left
                ReferenceStroke(listOf(
                    0.35f to 0.30f, 0.35f to 0.38f, 0.35f to 0.48f,
                    0.35f to 0.58f, 0.35f to 0.68f
                )),
                // Stroke 2: horizontal middle
                ReferenceStroke(listOf(
                    0.30f to 0.48f, 0.38f to 0.47f, 0.48f to 0.46f,
                    0.58f to 0.47f, 0.65f to 0.48f
                )),
                // Stroke 3: right vertical with loop
                ReferenceStroke(listOf(
                    0.55f to 0.38f, 0.55f to 0.46f, 0.55f to 0.54f,
                    0.56f to 0.60f, 0.58f to 0.64f, 0.55f to 0.68f,
                    0.50f to 0.65f
                ))
            )
        ),
        "h_hi" to KanaWritingTemplate(
            characterId = "h_hi",
            strokes = listOf(
                // Stroke 1: single curved stroke (like a smile)
                ReferenceStroke(listOf(
                    0.30f to 0.35f, 0.38f to 0.42f, 0.48f to 0.48f,
                    0.58f to 0.52f, 0.68f to 0.55f
                )),
                // Stroke 2: small tick
                ReferenceStroke(listOf(
                    0.55f to 0.42f, 0.58f to 0.45f, 0.60f to 0.48f,
                    0.61f to 0.52f
                ))
            )
        ),
        "h_fu" to KanaWritingTemplate(
            characterId = "h_fu",
            strokes = listOf(
                // Stroke 1: top hat mark
                ReferenceStroke(listOf(
                    0.40f to 0.30f, 0.45f to 0.28f, 0.50f to 0.27f,
                    0.55f to 0.28f, 0.60f to 0.30f
                )),
                // Stroke 2: vertical down
                ReferenceStroke(listOf(
                    0.48f to 0.33f, 0.47f to 0.40f, 0.47f to 0.50f,
                    0.47f to 0.58f, 0.48f to 0.65f
                )),
                // Stroke 3: left tick
                ReferenceStroke(listOf(
                    0.42f to 0.42f, 0.40f to 0.45f, 0.38f to 0.48f,
                    0.37f to 0.52f
                )),
                // Stroke 4: right tick
                ReferenceStroke(listOf(
                    0.58f to 0.42f, 0.60f to 0.45f, 0.62f to 0.48f,
                    0.63f to 0.52f
                ))
            )
        ),
        "h_he" to KanaWritingTemplate(
            characterId = "h_he",
            strokes = listOf(
                // Stroke 1: single angled stroke (wedge shape, like く but smaller)
                ReferenceStroke(listOf(
                    0.35f to 0.38f, 0.42f to 0.42f, 0.50f to 0.48f,
                    0.58f to 0.55f, 0.65f to 0.60f
                ))
            )
        ),
        "h_ho" to KanaWritingTemplate(
            characterId = "h_ho",
            strokes = listOf(
                // Stroke 1: vertical left
                ReferenceStroke(listOf(
                    0.35f to 0.30f, 0.35f to 0.38f, 0.35f to 0.48f,
                    0.35f to 0.58f, 0.35f to 0.68f
                )),
                // Stroke 2: horizontal top
                ReferenceStroke(listOf(
                    0.30f to 0.38f, 0.38f to 0.37f, 0.48f to 0.36f,
                    0.58f to 0.37f, 0.65f to 0.38f
                )),
                // Stroke 3: horizontal bottom
                ReferenceStroke(listOf(
                    0.30f to 0.58f, 0.38f to 0.57f, 0.48f to 0.56f,
                    0.58f to 0.57f, 0.65f to 0.58f
                )),
                // Stroke 4: right vertical with loop
                ReferenceStroke(listOf(
                    0.55f to 0.38f, 0.55f to 0.46f, 0.55f to 0.54f,
                    0.56f to 0.60f, 0.58f to 0.64f, 0.55f to 0.68f,
                    0.50f to 0.65f
                ))
            )
        ),
        "h_ma" to KanaWritingTemplate(
            characterId = "h_ma",
            strokes = listOf(
                // Stroke 1: horizontal top
                ReferenceStroke(listOf(
                    0.25f to 0.33f, 0.35f to 0.32f, 0.45f to 0.31f,
                    0.55f to 0.32f, 0.65f to 0.33f
                )),
                // Stroke 2: horizontal middle
                ReferenceStroke(listOf(
                    0.25f to 0.48f, 0.35f to 0.47f, 0.45f to 0.46f,
                    0.55f to 0.47f, 0.65f to 0.48f
                )),
                // Stroke 3: vertical through center with loop
                ReferenceStroke(listOf(
                    0.48f to 0.28f, 0.48f to 0.35f, 0.48f to 0.45f,
                    0.48f to 0.55f, 0.49f to 0.60f, 0.51f to 0.64f,
                    0.48f to 0.68f, 0.44f to 0.64f
                ))
            )
        ),
        "h_mi" to KanaWritingTemplate(
            characterId = "h_mi",
            strokes = listOf(
                // Stroke 1: horizontal top
                ReferenceStroke(listOf(
                    0.25f to 0.38f, 0.35f to 0.37f, 0.45f to 0.36f,
                    0.55f to 0.37f, 0.65f to 0.38f
                )),
                // Stroke 2: vertical left
                ReferenceStroke(listOf(
                    0.40f to 0.36f, 0.40f to 0.44f, 0.40f to 0.54f,
                    0.40f to 0.62f, 0.42f to 0.68f
                )),
                // Stroke 3: vertical right with hook
                ReferenceStroke(listOf(
                    0.58f to 0.36f, 0.58f to 0.44f, 0.58f to 0.54f,
                    0.58f to 0.62f, 0.60f to 0.68f, 0.64f to 0.65f
                ))
            )
        ),
        "h_mu" to KanaWritingTemplate(
            characterId = "h_mu",
            strokes = listOf(
                // Stroke 1: top hat mark
                ReferenceStroke(listOf(
                    0.40f to 0.30f, 0.45f to 0.28f, 0.50f to 0.27f,
                    0.55f to 0.28f, 0.60f to 0.30f
                )),
                // Stroke 2: vertical down with loop
                ReferenceStroke(listOf(
                    0.48f to 0.33f, 0.48f to 0.40f, 0.48f to 0.50f,
                    0.49f to 0.58f, 0.51f to 0.64f, 0.48f to 0.68f,
                    0.44f to 0.64f
                )),
                // Stroke 3: right vertical
                ReferenceStroke(listOf(
                    0.58f to 0.40f, 0.58f to 0.48f, 0.58f to 0.56f,
                    0.58f to 0.64f
                ))
            )
        ),
        "h_me" to KanaWritingTemplate(
            characterId = "h_me",
            strokes = listOf(
                // Stroke 1: horizontal top
                ReferenceStroke(listOf(
                    0.25f to 0.38f, 0.35f to 0.37f, 0.45f to 0.36f,
                    0.55f to 0.37f, 0.65f to 0.38f
                )),
                // Stroke 2: vertical down with loop
                ReferenceStroke(listOf(
                    0.48f to 0.36f, 0.48f to 0.44f, 0.48f to 0.52f,
                    0.49f to 0.58f, 0.51f to 0.63f, 0.48f to 0.68f,
                    0.44f to 0.64f
                ))
            )
        ),
        "h_mo" to KanaWritingTemplate(
            characterId = "h_mo",
            strokes = listOf(
                // Stroke 1: horizontal top
                ReferenceStroke(listOf(
                    0.25f to 0.33f, 0.35f to 0.32f, 0.45f to 0.31f,
                    0.55f to 0.32f, 0.65f to 0.33f
                )),
                // Stroke 2: horizontal middle
                ReferenceStroke(listOf(
                    0.25f to 0.48f, 0.35f to 0.47f, 0.45f to 0.46f,
                    0.55f to 0.47f, 0.65f to 0.48f
                )),
                // Stroke 3: vertical through center
                ReferenceStroke(listOf(
                    0.48f to 0.28f, 0.48f to 0.35f, 0.48f to 0.45f,
                    0.48f to 0.55f, 0.48f to 0.65f
                ))
            )
        ),
        "h_ra" to KanaWritingTemplate(
            characterId = "h_ra",
            strokes = listOf(
                // Stroke 1: horizontal top
                ReferenceStroke(listOf(
                    0.25f to 0.35f, 0.35f to 0.33f, 0.45f to 0.32f,
                    0.55f to 0.33f, 0.65f to 0.35f
                )),
                // Stroke 2: vertical down with hook
                ReferenceStroke(listOf(
                    0.48f to 0.30f, 0.47f to 0.38f, 0.47f to 0.48f,
                    0.48f to 0.56f, 0.52f to 0.62f, 0.58f to 0.60f
                ))
            )
        ),
        "h_ri" to KanaWritingTemplate(
            characterId = "h_ri",
            strokes = listOf(
                // Stroke 1: left vertical
                ReferenceStroke(listOf(
                    0.35f to 0.30f, 0.35f to 0.38f, 0.35f to 0.48f,
                    0.35f to 0.58f, 0.35f to 0.68f
                )),
                // Stroke 2: right vertical with hook
                ReferenceStroke(listOf(
                    0.60f to 0.30f, 0.60f to 0.38f, 0.60f to 0.48f,
                    0.60f to 0.58f, 0.62f to 0.64f, 0.66f to 0.62f
                ))
            )
        ),
        "h_ru" to KanaWritingTemplate(
            characterId = "h_ru",
            strokes = listOf(
                // Stroke 1: single zigzag stroke
                ReferenceStroke(listOf(
                    0.35f to 0.30f, 0.40f to 0.38f, 0.50f to 0.42f,
                    0.55f to 0.48f, 0.58f to 0.55f, 0.55f to 0.62f,
                    0.48f to 0.68f
                ))
            )
        ),
        "h_re" to KanaWritingTemplate(
            characterId = "h_re",
            strokes = listOf(
                // Stroke 1: vertical left
                ReferenceStroke(listOf(
                    0.35f to 0.30f, 0.35f to 0.38f, 0.35f to 0.48f,
                    0.35f to 0.58f, 0.35f to 0.68f
                )),
                // Stroke 2: angled right stroke
                ReferenceStroke(listOf(
                    0.50f to 0.30f, 0.55f to 0.38f, 0.58f to 0.48f,
                    0.60f to 0.58f, 0.62f to 0.68f
                ))
            )
        ),
        "h_ro" to KanaWritingTemplate(
            characterId = "h_ro",
            strokes = listOf(
                // Stroke 1: horizontal top
                ReferenceStroke(listOf(
                    0.25f to 0.35f, 0.35f to 0.33f, 0.45f to 0.32f,
                    0.55f to 0.33f, 0.65f to 0.35f
                )),
                // Stroke 2: curved vertical
                ReferenceStroke(listOf(
                    0.48f to 0.30f, 0.47f to 0.38f, 0.45f to 0.48f,
                    0.44f to 0.56f, 0.46f to 0.65f
                ))
            )
        ),
        "h_ya" to KanaWritingTemplate(
            characterId = "h_ya",
            strokes = listOf(
                // Stroke 1: angled top-left stroke
                ReferenceStroke(listOf(
                    0.30f to 0.30f, 0.38f to 0.38f, 0.45f to 0.48f,
                    0.50f to 0.55f, 0.54f to 0.62f
                )),
                // Stroke 2: angled top-right stroke
                ReferenceStroke(listOf(
                    0.70f to 0.30f, 0.62f to 0.38f, 0.55f to 0.48f,
                    0.52f to 0.55f, 0.50f to 0.62f
                ))
            )
        ),
        "h_yu" to KanaWritingTemplate(
            characterId = "h_yu",
            strokes = listOf(
                // Stroke 1: angled top-left stroke
                ReferenceStroke(listOf(
                    0.30f to 0.30f, 0.38f to 0.38f, 0.45f to 0.48f,
                    0.50f to 0.55f, 0.54f to 0.62f
                )),
                // Stroke 2: horizontal middle
                ReferenceStroke(listOf(
                    0.35f to 0.50f, 0.42f to 0.49f, 0.50f to 0.48f,
                    0.58f to 0.49f, 0.65f to 0.50f
                ))
            )
        ),
        "h_yo" to KanaWritingTemplate(
            characterId = "h_yo",
            strokes = listOf(
                // Stroke 1: horizontal top
                ReferenceStroke(listOf(
                    0.25f to 0.38f, 0.35f to 0.37f, 0.45f to 0.36f,
                    0.55f to 0.37f, 0.65f to 0.38f
                )),
                // Stroke 2: horizontal bottom
                ReferenceStroke(listOf(
                    0.25f to 0.58f, 0.35f to 0.57f, 0.45f to 0.56f,
                    0.55f to 0.57f, 0.65f to 0.58f
                ))
            )
        ),
        "k_ya" to KanaWritingTemplate(
            characterId = "k_ya",
            strokes = listOf(
                // Stroke 1: angled top-left stroke
                ReferenceStroke(listOf(
                    0.30f to 0.30f, 0.38f to 0.38f, 0.45f to 0.48f,
                    0.50f to 0.55f, 0.54f to 0.62f
                )),
                // Stroke 2: angled top-right stroke
                ReferenceStroke(listOf(
                    0.70f to 0.30f, 0.62f to 0.38f, 0.55f to 0.48f,
                    0.52f to 0.55f, 0.50f to 0.62f
                ))
            )
        ),
        "k_yu" to KanaWritingTemplate(
            characterId = "k_yu",
            strokes = listOf(
                // Stroke 1: angled top-left stroke
                ReferenceStroke(listOf(
                    0.30f to 0.30f, 0.38f to 0.38f, 0.45f to 0.48f,
                    0.50f to 0.55f, 0.54f to 0.62f
                )),
                // Stroke 2: horizontal middle
                ReferenceStroke(listOf(
                    0.35f to 0.50f, 0.42f to 0.49f, 0.50f to 0.48f,
                    0.58f to 0.49f, 0.65f to 0.50f
                ))
            )
        ),
        "k_yo" to KanaWritingTemplate(
            characterId = "k_yo",
            strokes = listOf(
                // Stroke 1: horizontal top
                ReferenceStroke(listOf(
                    0.25f to 0.38f, 0.35f to 0.37f, 0.45f to 0.36f,
                    0.55f to 0.37f, 0.65f to 0.38f
                )),
                // Stroke 2: horizontal bottom
                ReferenceStroke(listOf(
                    0.25f to 0.58f, 0.35f to 0.57f, 0.45f to 0.56f,
                    0.55f to 0.57f, 0.65f to 0.58f
                ))
            )
        )
    )

    fun getTemplate(characterId: String): KanaWritingTemplate? = templates[characterId]

    fun hasTemplate(characterId: String): Boolean = templates.containsKey(characterId)

    fun getAllTemplateIds(): Set<String> = templates.keys
}
