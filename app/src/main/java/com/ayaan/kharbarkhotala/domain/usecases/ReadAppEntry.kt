package com.ayaan.kharbarkhotala.domain.usecases

import com.ayaan.kharbarkhotala.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke():Flow<Boolean>{

        return localUserManager.readAppEntry()
    }
}