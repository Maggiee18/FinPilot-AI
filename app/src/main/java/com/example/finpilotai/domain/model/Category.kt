package com.example.finpilotai.domain.model

/**
 * Default categories. In Milestone 2 these become seed rows in a Room table so that
 * users can add their own; this enum stays as the stable set of built-in keys.
 */
enum class Category(val displayName: String, val appliesTo: Set<TransactionType>) {
    FOOD("Food", setOf(TransactionType.EXPENSE)),
    TRANSPORT("Transport", setOf(TransactionType.EXPENSE)),
    EDUCATION("Education", setOf(TransactionType.EXPENSE)),
    SHOPPING("Shopping", setOf(TransactionType.EXPENSE)),
    HEALTHCARE("Healthcare", setOf(TransactionType.EXPENSE)),
    ENTERTAINMENT("Entertainment", setOf(TransactionType.EXPENSE)),
    BILLS("Bills", setOf(TransactionType.EXPENSE)),
    HOUSING("Housing", setOf(TransactionType.EXPENSE)),
    SAVINGS("Savings", setOf(TransactionType.EXPENSE)),
    SALARY("Salary", setOf(TransactionType.INCOME)),
    OTHER("Other", setOf(TransactionType.EXPENSE, TransactionType.INCOME));
}

enum class TransactionType { INCOME, EXPENSE }
