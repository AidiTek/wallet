package com.example.wallet.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.wallet.R
import com.example.wallet.data.dao.OperationDao
import com.example.wallet.data.entity.AccountData
import com.example.wallet.data.entity.OperationEntity
import com.example.wallet.data.rvdata.allOerationData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val operationDao: OperationDao
) : ViewModel() {
    val outlauSelectedIcon = MutableLiveData<Int>()
    val selectedIcon = MutableLiveData<Int>()
    val categoryInput = MutableLiveData<String>()
    val amountInput = MutableLiveData<Int>()
    val selectedCategoryPosition =
        MutableLiveData<Int>() // Для сохранения индекса выбранной категории
    val isScroled = MutableLiveData<Boolean>()

    val selectedAccountId = MutableLiveData<Int?>()

    val allAccounts: LiveData<List<OperationEntity>> = operationDao.getAllAccounts().asLiveData()

    // Информация о выбранном счете
    val selectedAccount: LiveData<OperationEntity?> = selectedAccountId.switchMap { accountId ->
        liveData {
            if (accountId != null) {
                val accounts = operationDao.getAllAccounts().firstOrNull()
                emit(accounts?.find { it.id == accountId })
            } else {
                emit(null)
            }
        }
    }

    fun getOperationsBySelectedAccount(): LiveData<List<OperationEntity>> {
        return selectedAccountId.switchMap { accountId ->
            if (accountId == null) {
                MutableLiveData(emptyList())
            } else {
                operationDao.getOperationsByAccountID(accountId).asLiveData()
            }
        }
    }

    fun setSelectedAccount(accountId: Int) {
        selectedAccountId.value = accountId
    }

    fun addAccount(title: String, amount: Int){
        viewModelScope.launch {
            val newAccount = OperationEntity(
                accountID = 0,
                category = title,
                amount = amount,
                percentage = "0%",
                isIncome = true,
                iconeResId = R.drawable.polygon_1,
                description = null,
                date = System.currentTimeMillis(),
                isAccount = true
            )
            operationDao.insertOperation(newAccount)
        }
    }

    fun addOperation(operation: OperationEntity) {
        viewModelScope.launch {
            operationDao.insertOperation(operation)
        }
    }

    fun updateAccountBalance(accountId: Int, amountChange: Int) {
        viewModelScope.launch {
            val accounts = operationDao.getAllAccounts().firstOrNull()
            val accountToUpdate = accounts?.find { it.id == accountId }
            if (accountToUpdate != null) {
                val updatedAccount = accountToUpdate.copy(amount = accountToUpdate.amount + amountChange)
                operationDao.insertOperation(updatedAccount)
            }
        }
    }

    fun resetSelectedAccount() {
        selectedAccountId.value = null
    }

    // Метод для получения ID текущего счета
    fun getSelectedAccountId(): Int? {
        return selectedAccountId.value
    }
}