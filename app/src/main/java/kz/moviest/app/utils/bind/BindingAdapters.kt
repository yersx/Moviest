package kz.moviest.app.utils.bind

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kz.moviest.app.R
import kz.moviest.app.data.enums.app.DatePatternEnums
import kz.moviest.app.utils.convertDateToString
import java.util.*

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage ?: ""
}

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: Int?) {
    if (errorMessage == null
        || errorMessage == 0
    ) {
        view.error = null
    } else {
        view.error = view.context.getString(errorMessage)
    }
}

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("bindIcon")
fun bindIcon(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("bindIconUri")
fun bindIconUri(imageView: ImageView, uri: Uri) {
    Glide.with(imageView.context).load(uri).into(imageView)
//    imageView.setImageURI(uri)
}

//http://image.tmdb.org/t/p/w200/bOKjzWDxiDkgEQznhzP4kdeAHNI.jpg
@BindingAdapter("bindIconUrl")
fun bindIconUrl(imageView: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        val circularProgressDrawable = CircularProgressDrawable(imageView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide
            .with(imageView.context)
            .load("https://image.tmdb.org/t/p/w400$url")
            .placeholder(circularProgressDrawable)
            .into(imageView)

    } else {
        imageView.setBackgroundResource(R.color.transparent)
    }
}

@BindingAdapter("bindDrawable")
fun bindDrawable(imageView: ImageView, drawable: Drawable?) {
    if (drawable == null) {
        imageView.setImageResource(R.color.transparent)
    } else {
        imageView.setImageDrawable(drawable)

    }
}

@BindingAdapter("bindTint")
fun bindTint(view: ImageView, @ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        view,
        ColorStateList.valueOf(ContextCompat.getColor(view.context, colorRes))
    )
}

@BindingAdapter("bindDrawableEnd")
fun bindTint(view: TextInputEditText, res: Int) {
    view.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
}

@BindingAdapter("bindDate")
fun bindDate(textView: TextView, date: Date?) {
    if (date != null) {
        textView.text = convertDateToString(date, DatePatternEnums.DDMMYYYY_BY_PERIOD.id)
    } else {
        textView.text = ""
    }
}

@BindingAdapter("bindBitmap")
fun bindBitmap(imageView: ImageView, bitmap: Bitmap?) {
    if (bitmap == null) {
        imageView.setImageResource(R.color.transparent)

    } else {
        imageView.setImageBitmap(bitmap)

    }
}

@BindingAdapter("bindSelected")
fun bindSelected(view: View, isSelected: Boolean?) {
    if (isSelected != null) {
        view.isSelected = isSelected

    } else {
        view.isSelected = false
    }
}

@BindingAdapter("bindEnabled")
fun bindEnabled(view: View, enabled: Boolean?) {
    view.isEnabled = enabled ?: true
}

@BindingAdapter("bindHtmlText")
fun bindHtmlText(textView: TextView, text: String?) {
    if (!text.isNullOrEmpty()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)

        } else {
            textView.text = Html.fromHtml(text);
        }

    } else {
        textView.text = ""
    }
}

@BindingAdapter("android:text")
fun setText(textView: TextView, stringRes: Int?) {
    if (stringRes == null
        || stringRes == 0
    ) {
        textView.text = ""
    } else {
        textView.text = textView.context.getString(stringRes)
    }
}

@BindingAdapter("android:text")
fun setText(textView: TextView, any: Any?) {
    if (any != null) {
        when (any) {
            is String -> {
                textView.text = any
            }
            is Int -> {
                if (any == 0) {
                    textView.text = ""
                } else {
                    textView.text = textView.context.getString(any)
                }
            }
        }

    } else {
        textView.text = ""
    }
}