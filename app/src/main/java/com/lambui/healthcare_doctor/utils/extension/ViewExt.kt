package com.lambui.healthcare_doctor.utils.extension

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.utils.StringUtils
import com.lambui.healthcare_doctor.widget.viewInApp.BottomButton

fun View.show(isShow: Boolean = true) {
    visibility = if (isShow) View.VISIBLE else View.INVISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.gone(isShow: Boolean = true) {
    visibility = if (isShow) View.VISIBLE else View.GONE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun Context.showToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

@SuppressLint("SetJavaScriptEnabled")
fun WebView.loadWebViewUrl(url: String?, progressBar: ProgressBar?) {
    if (url.isNullOrEmpty()) return
    if (progressBar == null) {
        webViewClient = WebViewClient()
    } else {
        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }
    }
    with(settings) {
        javaScriptEnabled = true
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
    }
    loadUrl(url)
}

fun ImageView.loadImageUrl(url: String?) {
    url.notNull {
        Glide.with(this.context).load(it)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .dontTransform()
            .into(this)
    }
}

fun ImageView.loadImageUri(uri: Uri?) {
    uri.notNull {
        Glide.with(this.context)
            .load(uri)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(this)
    }
}

fun ImageView.loadImageUriWithBitmap(bitmap: Bitmap?) {
    bitmap.notNull {
        Glide.with(this.context)
            .load(bitmap)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(this)
    }
}

fun View.OnClickListener.listenToViews(vararg views: View) {
    views.forEach { it.setOnClickListener(this) }
}

fun View.setIsSelected(isSelect: Boolean = true) {
    this.isSelected = isSelect
}

@SuppressLint("ClickableViewAccessibility")
fun View.setOnVeryLongClickListener(timeDelay: Long = 3000, listener: () -> Unit) {
    setOnTouchListener(object : View.OnTouchListener {

        private val handler = Handler(Looper.getMainLooper())

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if (event?.action == MotionEvent.ACTION_DOWN) {
                handler.postDelayed({
                    listener.invoke()
                }, timeDelay)
            } else if (event?.action == MotionEvent.ACTION_UP) {
                handler.removeCallbacksAndMessages(null)
            }
            return true
        }
    })
}

fun BottomButton.validateSelectedTextView(editTexts: List<EditText>) {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            val selectBool = StringUtils.isNotEmptyEditText(editTexts)
            setButtonSelected(selectBool)
        }
    }

    for (editText in editTexts) {
        editText.addTextChangedListener(textWatcher)
    }
}

fun View.enable(enabled: Boolean = true) {
    this.isEnabled = enabled
}

fun TextView.setTextWithSpan(color: Int, clickablePart: String, onClick: () -> Unit) {
    SpannableString(this.text).also {
        it.withClickableSpan(
            color,
            this,
            clickablePart
        ) {
            onClick.invoke()
        }
        setText(it, TextView.BufferType.SPANNABLE)
    }
}

fun List<EditText>.showHidePassword(isChecked: Boolean) {
    for (ediText in this) {
        if (isChecked) {
            ediText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            ediText.setSelection(ediText.length())
            continue
        }
        ediText.transformationMethod = PasswordTransformationMethod.getInstance()
        ediText.setSelection(ediText.length())
    }
}

fun List<EditText>.validateContent(): Boolean {
    for (ediText in this) {
        if (ediText.text.isBlank()) {
            return false
        }
    }
    return true
}

fun ImageView.loadUrlWithProgressbar(url: String?, progressBar: ProgressBar?) {
    url.notNull {
        Glide.with(this.context).load(it)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .dontTransform()
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any?,
                                          target: com.bumptech.glide.request.target.Target<Drawable?>?,
                                          isFirstResource: Boolean): Boolean {
                    progressBar?.gone()
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?,
                                             target: com.bumptech.glide.request.target.Target<Drawable?>?, dataSource: DataSource?,
                                             isFirstResource: Boolean): Boolean {
                    progressBar?.gone()
                    return false
                }
            })
            .into(this)
    }
}
fun ImageView.loadImageUrlInCircle(url: String?, errorIcon: Int = R.drawable.img_profile_doctor) {
    url.notNull {
        val options = RequestOptions()
            .circleCrop()
            .placeholder(R.drawable.img_profile_doctor)
            .error(errorIcon)
            .dontTransform()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .override(300, 300)

        Glide.with(this.context).asBitmap().load(it)
            .apply(options)
            .into(this)
    }
}