package com.example.finpilotai.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.finpilotai.domain.model.Category
import com.example.finpilotai.ui.theme.FinPilotTheme

/** UI only mapping from a domain category to an icon. Keeps the domain free of Compose. */
fun Category.icon(): ImageVector = when (this) {
    Category.FOOD -> Icons.Outlined.Restaurant
    Category.TRANSPORT -> Icons.Outlined.DirectionsBus
    Category.EDUCATION -> Icons.Outlined.School
    Category.SHOPPING -> Icons.Outlined.ShoppingBag
    Category.HEALTHCARE -> Icons.Outlined.LocalHospital
    Category.ENTERTAINMENT -> Icons.Outlined.Movie
    Category.BILLS -> Icons.AutoMirrored.Outlined.ReceiptLong
    Category.HOUSING -> Icons.Outlined.Home
    Category.SAVINGS -> Icons.Outlined.Savings
    Category.SALARY -> Icons.Outlined.Payments
    Category.OTHER -> Icons.Outlined.Category
}

@Composable
fun Category.color(): Color {
    val palette = FinPilotTheme.colors.categoryPalette
    return palette[ordinal % palette.size]
}

/** Round tinted badge showing a category icon. */
@Composable
fun CategoryBadge(category: Category, modifier: Modifier = Modifier, size: Dp = 40.dp) {
    val tint = category.color()
    Surface(shape = CircleShape, color = tint.copy(alpha = 0.15f), modifier = modifier.size(size)) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = category.icon(),
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(size / 2),
            )
        }
    }
}
