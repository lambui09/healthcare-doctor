package com.lambui.healthcare_doctor.ui.main.setting.account.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.StrictMode
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.cloudinary.ConfigCloudinary
import com.lambui.healthcare_doctor.constant.Constants.TEMPORARY_PATH_FILE
import com.lambui.healthcare_doctor.constant.Constants.TEMPORARY_URI_FILE
import com.lambui.healthcare_doctor.ui.main.setting.account.AccountVM
import com.lambui.healthcare_doctor.utils.FileContentProviderUtil
import com.lambui.healthcare_doctor.utils.FileUtils.compressImage
import com.lambui.healthcare_doctor.utils.FileUtils.getRealPathFromURI
import com.lambui.healthcare_doctor.utils.IntentGallerySystem
import com.lambui.healthcare_doctor.utils.PermissionUtil
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.extension.loadImageUri
import com.lambui.healthcare_doctor.utils.extension.notNull
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.File

class ProfileFragment : BaseFragment<AccountVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_profile
    override val viewModelx: AccountVM by sharedViewModel()
    private var imageFrontUri: Uri? = null
    private var imageFrontBitmap: Bitmap? = null
    private var fileFrontData: File? = null

    override fun initialize() {

    }

    override fun onSubscribeObserver() {

    }

    override fun registerOnClick() {
        btnSubmit.setOnClickListener {
            imageFrontUri?.let {
                ConfigCloudinary.uploadImage(it)
            }
        }
        launchDisposable {
            RxView.clicks(ivAvatar, false)
                .subscribe {
                    PermissionUtil.checkPermissionTakeAvatar(
                        activity
                    ) {
                        val builder = StrictMode.VmPolicy.Builder()
                        StrictMode.setVmPolicy(builder.build())
                        context?.let {
                            startActivityForResult(
                                IntentGallerySystem.getPickImageChooserIntent(it),
                                REQUEST_TAKE_IMAGE
                            )
                        }
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_TAKE_IMAGE) {
            if ((data != null && data.data == null) || data == null) {
                context?.let {
                    if (TEMPORARY_URI_FILE != null && TEMPORARY_PATH_FILE != null) {
                        bindUriAndPathFileAfterTakePhotoOrChooseFromGallery()
                        validate()
                        return
                    }
                }
            } else {
                context?.let {
                    val uri = IntentGallerySystem.getPickImageResultUri(it, data)
                    if (uri != null) {
                        imageFrontUri = uri
                        ivAvatar.loadImageUri(imageFrontUri)

                        if (IntentGallerySystem.isFromCamera(data)) {
                            val imageFrontFileCache = IntentGallerySystem.getImageFileFromCache(it)
                            imageFrontFileCache.notNull {
                                fileFrontData = compressImage(requireContext(), it)
                            }
                            validate()
                            return@let
                        }

                        imageFrontUri?.let {
                            val getPath =
                                getRealPathFromURI(context!!, requireContext().contentResolver, uri)
                            fileFrontData = if (getPath == "null") {
                                val file = FileContentProviderUtil.from(context!!, imageFrontUri!!)
                                compressImage(requireContext(), file)
                            } else {
                                compressImage(requireContext(), File(getPath))
                            }

                            if ((fileFrontData as File).path.isEmpty()) {
                                bindUriAndPathFileAfterTakePhotoOrChooseFromGallery()
                            }
                            validate()
                            return@let
                        }
                    }
                }
            }
        }
        TEMPORARY_URI_FILE = null
        TEMPORARY_PATH_FILE = null
    }

    private fun validate() {
        btnSubmit.isSelected = imageFrontUri != null
    }

    private fun bindUriAndPathFileAfterTakePhotoOrChooseFromGallery() {
        imageFrontUri = TEMPORARY_URI_FILE
        ivAvatar.loadImageUri(TEMPORARY_URI_FILE)
        fileFrontData = compressImage(requireContext(), File(TEMPORARY_PATH_FILE))
        TEMPORARY_URI_FILE = null
        TEMPORARY_PATH_FILE = null
    }

    companion object {
        const val REQUEST_TAKE_IMAGE = 100
    }
}