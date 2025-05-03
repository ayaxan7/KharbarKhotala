package com.ayaan.kharbarkhotala.domain.usecases.appentry

import com.ayaan.kharbarkhotala.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}