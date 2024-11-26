package com.example.wallet.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wallet.data.entity.OperationEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface OperationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOperation(operation: OperationEntity)

    @Query("SELECT * FROM operations WHERE isAccount = 1")
    fun getAllAccounts():Flow<List<OperationEntity>>

    @Query("SELECT * FROM operations WHERE isAccount = 0")
    fun getAllOperations():Flow<List<OperationEntity>>

    @Delete
    suspend fun delete(operation: OperationEntity)

    @Query("SELECT * FROM operations WHERE accountID = :accountID AND isAccount = 0")
    fun getOperationsByAccountID(accountID: Int): Flow<List<OperationEntity>>

}