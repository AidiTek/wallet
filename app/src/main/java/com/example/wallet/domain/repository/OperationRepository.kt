package com.example.wallet.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.wallet.data.dao.OperationDao
import com.example.wallet.data.entity.OperationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OperationRepository @Inject constructor (private val dao: OperationDao) {

    fun getAllOperations(): Flow<List<OperationEntity>> = dao.getAllOperations()

    suspend fun insertOperation(operations: OperationEntity){
        dao.insertOperation(operations)
    }

    suspend fun deleteOperation(operations: OperationEntity){
        dao.delete(operations)
    }

    fun getOperationsByAccountID(accountID: Int): LiveData<List<OperationEntity>> {
        return dao.getOperationsByAccountID(accountID).asLiveData()
    }


}