package com.example.finpilotai.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CurrencyRupee
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.finpilotai.BuildConfig
import com.example.finpilotai.ui.components.FeatureScaffold
import com.example.finpilotai.util.MoneyFormatter

/** Settings (MVVM screen). Items become interactive as their milestones land. */
@Composable
fun SettingsScreen() {
    FeatureScaffold(title = "Settings") {
        Column(Modifier.fillMaxWidth()) {
            SettingRow(Icons.Outlined.AccountCircle, "Account", "Not signed in. Sign in is added with Firebase.")
            HorizontalDivider()
            SettingRow(Icons.Outlined.CurrencyRupee, "Currency", "Indian Rupee (${MoneyFormatter.CURRENCY_CODE})")
            HorizontalDivider()
            SettingRow(Icons.Outlined.Notifications, "Budget alerts", "Not available yet")
            HorizontalDivider()
            SettingRow(Icons.Outlined.AutoAwesome, "AI insights and privacy", "Off. Nothing is shared.")
            HorizontalDivider()
            SettingRow(Icons.Outlined.Info, "About FinPilot AI", "Version ${BuildConfig.VERSION_NAME}")
        }
    }
}

@Composable
private fun SettingRow(icon: ImageVector, title: String, subtitle: String) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(subtitle) },
        leadingContent = { Icon(icon, contentDescription = null) },
    )
}
