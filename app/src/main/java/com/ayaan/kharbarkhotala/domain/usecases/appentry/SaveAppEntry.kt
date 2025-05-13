package com.ayaan.kharbarkhotala.domain.usecases.appentry

import com.ayaan.kharbarkhotala.domain.manager.LocalUserManager
import javax.inject.Inject


class SaveAppEntry @Inject constructor(
    private val localUserManger: LocalUserManager
) {

    suspend operator fun invoke(){
        localUserManger.saveAppEntry()
    }

}