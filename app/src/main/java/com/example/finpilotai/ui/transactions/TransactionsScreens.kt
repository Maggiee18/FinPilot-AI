package com.example.finpilotai.ui.transactions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.example.finpilotai.ui.components.EmptyState
import com.example.finpilotai.ui.components.FeatureScaffold

/** Transaction list. Replaced by the Room backed list in Milestone 2. */
@Composable
fun TransactionsScreen(onAddTransaction: () -> Unit) {
    FeatureScaffold(
        title = "Transactions",
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTransaction) {
                Icon(Icons.Filled.Add, contentDescription = "Add transaction")
            }
        },
    ) {
        EmptyState(
            icon = Icons.AutoMirrored.Outlined.ReceiptLong,
            title = "No transactions yet",
            message = "Your income and expenses will be listed here, with search and filters.",
            actionLabel = "Add transaction",
            onAction = onAddTransaction,
        )
    }
}

/** Add/edit form. The MVI based form with validation arrives in Milestone 2. */
@Composable
fun AddTransactionScreen(onBack: () -> Unit) {
    FeatureScaffold(title = "Add transaction", onBack = onBack) {
        EmptyState(
            icon = Icons.Outlined.EditNote,
            title = "Form not available yet",
            message = "Recording transactions needs local storage, which is added in the next milestone. " +
                "Nothing is saved from this screen.",
            actionLabel = "Go back",
            onAction = onBack,
        )
    }
}
