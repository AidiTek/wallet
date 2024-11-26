package com.example.wallet.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.wallet.data.dao.OperationDao
import com.example.wallet.data.entity.OperationEntity
import com.example.wallet.domain.repository.OperationRepository
import com.example.wallet.domain.useCase.AddOperationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperationViewModel @Inject constructor(
    private val operationDao: OperationDao,
    private val operationUseCase: AddOperationUseCase,
    private val repository: OperationRepository
) : ViewModel() {

    // Все операции
    val allOperations: LiveData<List<OperationEntity>> =
        repository.getAllOperations().asLiveData()

    // Доходы
    val incomeOperation: LiveData<List<OperationEntity>> =
        allOperations.map { it.filter { operation -> operation.isIncome } }

    // Расходы
    val expenceOperation: LiveData<List<OperationEntity>> =
        allOperations.map { it.filter { operation -> !operation.isIncome } }

    // Общий баланс
    val totalBalance: LiveData<Int> = allOperations.map { operations ->
        operations.fold(0) { acc, operation ->
            if (operation.isIncome) acc + operation.amount else acc - operation.amount
        }
    }

    // Операции по конкретному счету
    fun getOperationsByAccount(accountID: Int): LiveData<List<OperationEntity>> {
        return operationDao.getOperationsByAccountID(accountID).asLiveData()
    }

    // Добавление операции
    fun addOperation(
        accountId: Int,
        category: String,
        amount: Int,
        percentage: String,
        isIncome: Boolean,
        iconeResId: Int,
        date: Long
    ) {
        viewModelScope.launch {
            val operation = OperationEntity(
                accountID = accountId,
                category = category,
                amount = amount,
                percentage = percentage,
                isIncome = isIncome,
                iconeResId = iconeResId,
                date = date
            )
            operationUseCase(operation)
        }
    }

    // Удаление операции
    fun deleteOperation(operation: OperationEntity) {
        viewModelScope.launch {
            repository.deleteOperation(operation)
        }
    }
}
