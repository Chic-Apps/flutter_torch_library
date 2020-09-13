package fr.g123k.torch_compat.impl

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class TorchCamera2Impl(var context: Context) : BaseTorch() {

    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private val cameraId = cameraManager.cameraIdList.first { cameraId ->
        cameraManager.getCameraCharacteristics(cameraId)[CameraCharacteristics.FLASH_INFO_AVAILABLE] != null
    } ?: null

    override fun turnOn() {
        turn(on = true)
    }

    override fun turnOff() {
        turn(on = false)
    }

    private fun turn(on: Boolean) {
        try{
            if (cameraId != null) {
                cameraManager.setTorchMode(cameraId, on)
            }
        }catch (e: Exception){
            this.cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        }
    }

    override fun hasTorch(): Boolean = cameraId != null

    override fun dispose() {}

}