package com.example.banksampah

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: LoginPref) : ViewModel() {

    private fun login(dataLogin: DataLogin) {
        viewModelScope.launch {
            pref.login(dataLogin)
        }
    }

    fun saveLogin(username: String, nama: String, tipeUser: String, nohp: String) {
        login(DataLogin(username, nama, tipeUser, nohp))
    }

    fun getUser(): LiveData<DataLogin> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}