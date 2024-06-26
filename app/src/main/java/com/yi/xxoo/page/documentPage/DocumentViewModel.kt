package com.yi.xxoo.page.documentPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.yi.xxoo.Const.GameMode
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.user.UserDao
import com.yi.xxoo.network.user.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(private val userService: UserService,private val userDao: UserDao): ViewModel(){

    private val _editSuccess = MutableStateFlow(0)
    val editSuccess:StateFlow<Int> = _editSuccess

    private val _avatarFailed = MutableStateFlow(false)
    val avatarFailed:StateFlow<Boolean> = _avatarFailed

    private val _nameFailed = MutableStateFlow(false)
    val nameFailed:StateFlow<Boolean> = _nameFailed

    fun updateAvatar(file:File) {
        if (GameMode.isNetworkEnabled){
            try {
                viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        try {
                            val requestFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                            val body: MultipartBody.Part = MultipartBody.Part.createFormData("avatar", file.name, requestFile)
                            val userResponse = userService.updateUserAvatar(UserData.account,body)
                            if (userResponse.code == 200){
                                UserData.photo = userResponse.data
                                _editSuccess.value ++
                            }else{
                                _avatarFailed.value = true
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    }
                }
            }catch (e:Exception){
                Log.e("TAG", "updateAvatar: ${e.message}" )
            }
        }else{
            try {
                viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        try {
                            userDao.updateUserPhoto(UserData.account,file.absolutePath)
                            UserData.photo = file.absolutePath
                            _editSuccess.value ++
                            Log.d("TAG", "updateAvatar: ${file.absolutePath}")
                        }catch (e:Exception){
                            e.printStackTrace()
                        }

                    }
                }
            }catch (e:Exception){
                Log.e("TAG", "updateAvatar: ${e.message}" )
            }
        }

    }

    fun updateName(name:String) {
        if (GameMode.isNetworkEnabled) {
            try {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        val userResponse = userService.updateName(UserData.account, name)
                        if (userResponse.code == 200) {
                            UserData.name = name
                            _editSuccess.value++
                        } else {
                            _nameFailed.value = true
                        }
                    }
                }
            }catch (e:Exception){
                Log.e("TAG", "updateName: ${e.message}" )
            }
        }else{
            try {
                viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        userDao.updateUserName(UserData.account,name)
                        UserData.name = name
                        _editSuccess.value ++
                    }
                }
            }catch (e:Exception){
                Log.e("TAG", "updateName: ${e.message}" )
            }
        }
    }
}