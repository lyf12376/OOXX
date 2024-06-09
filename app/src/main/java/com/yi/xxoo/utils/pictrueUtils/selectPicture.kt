package com.example.roomtest.pictrue

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class SelectPicture : ActivityResultContract<Unit?, PictureResult>() {

    private var context: Context? = null

    override fun createIntent(context: Context, input: Unit?): Intent {
        this.context = context
        return Intent(Intent.ACTION_PICK).setType("image/*")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): PictureResult {
        return PictureResult(intent?.data, resultCode == Activity.RESULT_OK)
    }
}
//图片结果
class PictureResult(val uri: Uri?, val isSuccess: Boolean)
