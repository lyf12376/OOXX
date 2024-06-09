package com.Yi.videoplayer.utils.pictrueUtils

import android.Manifest
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.roomtest.pictrue.PictureResult
import com.example.roomtest.pictrue.SelectPicture
import com.example.roomtest.pictrue.TakePhoto
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PhotoComponent {

    private var openGalleryLauncher: ManagedActivityResultLauncher<Unit?, PictureResult>? = null
    private var takePhotoLauncher: ManagedActivityResultLauncher<Unit?, PictureResult>? = null

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    companion object {
        val instance get() = Helper.obj
    }

    private object Helper {
        val obj = PhotoComponent()
    }

    //监听拍照权限flow
    private val checkCameraPermission =
        MutableSharedFlow<Boolean?>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private fun setCheckCameraPermissionState(value: Boolean?) {
        scope.launch {
            checkCameraPermission.emit(value)
        }
    }

    //相册权限flow
    private val checkGalleryImagePermission =
        MutableSharedFlow<Boolean?>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private fun setCheckGalleryPermissionState(value: Boolean?) {
        scope.launch {
            checkGalleryImagePermission.emit(value)
        }
    }

    /**
     * @param galleryCallback 相册结果回调
     * @param graphCallback 拍照结果回调
     * @param permissionRationale 权限拒绝状态回调
     **/
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun Register(
        galleryCallback: (selectResult: PictureResult) -> Unit,
        graphCallback: (graphResult: PictureResult) -> Unit,
        permissionRationale: ((gallery: Boolean) -> Unit)? = null,
    ) {
        val rememberGraphCallback = rememberUpdatedState(newValue = graphCallback)
        val rememberGalleryCallback = rememberUpdatedState(newValue = galleryCallback)
        openGalleryLauncher = rememberLauncherForActivityResult(contract = SelectPicture()) {
            rememberGalleryCallback.value.invoke(it)
        }
        takePhotoLauncher = rememberLauncherForActivityResult(contract = TakePhoto.instance) {
            rememberGraphCallback.value.invoke(it)
        }
        val readGalleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //13以上的权限申请
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        var permissionCameraState by rememberSaveable { mutableStateOf(false) }
        var permissionGalleryState by rememberSaveable { mutableStateOf(false) }
        val permissionList = arrayListOf(
            Manifest.permission.CAMERA,
            readGalleryPermission,
        )
        val galleryPermissionState = rememberPermissionState(readGalleryPermission)
        val cameraPermissionState = rememberMultiplePermissionsState(permissionList)
        LaunchedEffect(Unit) {
            checkCameraPermission.collectLatest {
                permissionCameraState = it == true
                if (it == true) {
                    if (cameraPermissionState.allPermissionsGranted) {
                        setCheckCameraPermissionState(null)
                        takePhotoLauncher?.launch(null)
                    } else if (cameraPermissionState.shouldShowRationale) {
                        setCheckCameraPermissionState(null)
                        permissionRationale?.invoke(false)
                    } else {
                        cameraPermissionState.launchMultiplePermissionRequest()
                    }
                }
            }
        }
        LaunchedEffect(Unit) {
            checkGalleryImagePermission.collectLatest {
                permissionGalleryState = it == true
                if (it == true) {
                    if (galleryPermissionState.status.isGranted) {
                        setCheckGalleryPermissionState(null)
                        openGalleryLauncher?.launch(null)
                    } else if (galleryPermissionState.status.shouldShowRationale) {
                        setCheckGalleryPermissionState(null)
                        permissionRationale?.invoke(true)
                    } else {
                        galleryPermissionState.launchPermissionRequest()
                    }
                }
            }
        }
        LaunchedEffect(cameraPermissionState.allPermissionsGranted) {
            if (cameraPermissionState.allPermissionsGranted && permissionCameraState) {
                setCheckCameraPermissionState(null)
                takePhotoLauncher?.launch(null)
            }
        }
        LaunchedEffect(galleryPermissionState.status.isGranted) {
            if (galleryPermissionState.status.isGranted && permissionGalleryState) {
                setCheckGalleryPermissionState(null)
                openGalleryLauncher?.launch(null)
            }
        }
    }

    //调用选择图片功能
    fun selectImage() {
        setCheckGalleryPermissionState(true)
    }
    //调用拍照
    fun takePhoto() {
        setCheckCameraPermissionState(true)
    }

}
