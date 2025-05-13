package com.ayaan.kharbarkhotala.domain.usecases.appentry

import com.ayaan.kharbarkhotala.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//class ReadAppEntry(
//    private val localUserManager: LocalUserManager
//) {
//    operator fun invoke():Flow<Boolean>{
//        return localUserManager.readAppEntry()
//    }
//}
class ReadAppEntry @Inject constructor(
    private val localUserManger: LocalUserManager
) {

    operator fun invoke(): Flow<Boolean> {
        return localUserManger.readAppEntry()
    }

}