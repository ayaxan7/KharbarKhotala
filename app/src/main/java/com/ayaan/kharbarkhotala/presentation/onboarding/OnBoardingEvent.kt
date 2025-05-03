package com.ayaan.kharbarkhotala.presentation.onboarding

sealed class OnBoardingEvent {
    data object SaveAppEntry: OnBoardingEvent()
}