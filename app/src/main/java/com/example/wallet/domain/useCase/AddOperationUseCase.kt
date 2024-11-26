package com.example.wallet.domain.useCase

import com.example.wallet.data.entity.OperationEntity
import com.example.wallet.domain.repository.OperationRepository
import javax.inject.Inject


class AddOperationUseCase @Inject constructor (private  val repository : OperationRepository){

    suspend operator fun invoke(operation: OperationEntity){
        repository.insertOperation(operation)
    }

}