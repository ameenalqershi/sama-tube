package com.example.snaptube.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun Color.applyOpacity(enabled: Boolean): Color {
    return if (enabled) this else this.copy(alpha = 0.62f)
}

@Composable
@ReadOnlyComposable
fun Color.harmonizeWithPrimary(): Color =
    this.harmonizeWith(other = androidx.compose.material3.MaterialTheme.colorScheme.primary)

@Composable
@ReadOnlyComposable
fun Color.harmonizeWith(other: Color): Color {
    // Simple harmonization logic since MaterialColors is not available
    // This is a basic approximation - in a real app, you'd implement proper color harmonization
    return Color((this.toArgb() + other.toArgb()) / 2)
}

/**
 * Fixed color roles that don't change between light/dark themes
 * Based on Material Design 3 specifications
 */
@Immutable
data class FixedColorRoles(
    val primaryFixed: Color,
    val primaryFixedDim: Color, 
    val onPrimaryFixed: Color,
    val onPrimaryFixedVariant: Color,
    val secondaryFixed: Color,
    val secondaryFixedDim: Color,
    val onSecondaryFixed: Color,
    val onSecondaryFixedVariant: Color,
    val tertiaryFixed: Color,
    val tertiaryFixedDim: Color,
    val onTertiaryFixed: Color,
    val onTertiaryFixedVariant: Color,
) {
    companion object {
        @Stable
        val Unspecified = FixedColorRoles(
            primaryFixed = Color.Unspecified,
            primaryFixedDim = Color.Unspecified,
            onPrimaryFixed = Color.Unspecified,
            onPrimaryFixedVariant = Color.Unspecified,
            secondaryFixed = Color.Unspecified,
            secondaryFixedDim = Color.Unspecified,
            onSecondaryFixed = Color.Unspecified,
            onSecondaryFixedVariant = Color.Unspecified,
            tertiaryFixed = Color.Unspecified,
            tertiaryFixedDim = Color.Unspecified,
            onTertiaryFixed = Color.Unspecified,
            onTertiaryFixedVariant = Color.Unspecified,
        )

        @Stable
        internal fun fromColorSchemes(
            lightColors: ColorScheme,
            darkColors: ColorScheme,
        ): FixedColorRoles {
            return FixedColorRoles(
                primaryFixed = lightColors.primaryContainer,
                onPrimaryFixed = lightColors.onPrimaryContainer,
                onPrimaryFixedVariant = darkColors.primaryContainer,
                secondaryFixed = lightColors.secondaryContainer,
                onSecondaryFixed = lightColors.onSecondaryContainer,
                onSecondaryFixedVariant = darkColors.secondaryContainer,
                tertiaryFixed = lightColors.tertiaryContainer,
                onTertiaryFixed = lightColors.onTertiaryContainer,
                onTertiaryFixedVariant = darkColors.tertiaryContainer,
                primaryFixedDim = darkColors.primary,
                secondaryFixedDim = darkColors.secondary,
                tertiaryFixedDim = darkColors.tertiary,
            )
        }
    }
}

// Default seed color for Snaptube - Professional blue-green
const val DEFAULT_SEED_COLOR = 0xFF1976D2

/**
 * Snaptube brand colors
 */
object SnaptubeColors {
    val Primary = Color(0xFF1976D2) // Professional blue
    val PrimaryVariant = Color(0xFF1565C0)
    val Secondary = Color(0xFF26A69A) // Teal accent
    val SecondaryVariant = Color(0xFF00796B)
    val Success = Color(0xFF4CAF50)
    val Warning = Color(0xFFFF9800)
    val Error = Color(0xFFF44336)
    val Info = Color(0xFF2196F3)
    
    // Gradient colors for professional look
    val GradientStart = Color(0xFF1976D2)
    val GradientEnd = Color(0xFF26A69A)
    
    // Surface colors with subtle tints
    val SurfaceTint = Color(0xFFF8FAFE)
    val SurfaceVariant = Color(0xFFF1F5F9)
}

/**
 * Extended color scheme for more granular control
 */
@Immutable
data class ExtendedColors(
    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,
    val warning: Color,
    val onWarning: Color,
    val warningContainer: Color,
    val onWarningContainer: Color,
    val info: Color,
    val onInfo: Color,
    val infoContainer: Color,
    val onInfoContainer: Color,
    val neutral: Color,
    val onNeutral: Color,
    val neutralContainer: Color,
    val onNeutralContainer: Color,
) {
    companion object {
        fun getDefault(isDark: Boolean = false): ExtendedColors {
            return if (isDark) {
                ExtendedColors(
                    success = Color(0xFF4CAF50),
                    onSuccess = Color(0xFF000000),
                    successContainer = Color(0xFF1B5E20),
                    onSuccessContainer = Color(0xFFA5D6A7),
                    warning = Color(0xFFFF9800),
                    onWarning = Color(0xFF000000),
                    warningContainer = Color(0xFFE65100),
                    onWarningContainer = Color(0xFFFFCC02),
                    info = Color(0xFF2196F3),
                    onInfo = Color(0xFFFFFFFF),
                    infoContainer = Color(0xFF0D47A1),
                    onInfoContainer = Color(0xFFBBDEFB),
                    neutral = Color(0xFF9E9E9E),
                    onNeutral = Color(0xFF000000),
                    neutralContainer = Color(0xFF424242),
                    onNeutralContainer = Color(0xFFE0E0E0),
                )
            } else {
                ExtendedColors(
                    success = Color(0xFF2E7D32),
                    onSuccess = Color(0xFFFFFFFF),
                    successContainer = Color(0xFFA5D6A7),
                    onSuccessContainer = Color(0xFF1B5E20),
                    warning = Color(0xFFE65100),
                    onWarning = Color(0xFFFFFFFF),
                    warningContainer = Color(0xFFFFCC02),
                    onWarningContainer = Color(0xFFE65100),
                    info = Color(0xFF1976D2),
                    onInfo = Color(0xFFFFFFFF),
                    infoContainer = Color(0xFFBBDEFB),
                    onInfoContainer = Color(0xFF0D47A1),
                    neutral = Color(0xFF757575),
                    onNeutral = Color(0xFFFFFFFF),
                    neutralContainer = Color(0xFFE0E0E0),
                    onNeutralContainer = Color(0xFF424242),
                )
            }
        }
    }
}
