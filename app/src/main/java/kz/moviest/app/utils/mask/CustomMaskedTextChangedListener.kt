package kz.moviest.app.utils.mask

import android.text.TextWatcher
import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener

class CustomMaskedTextChangedListener(
    format: String,
    autocomplete: Boolean,
    val field: EditText,
    listener: TextWatcher?,
    valueListener: ValueListener?
) : MaskedTextChangedListener(format, autocomplete, field, listener, valueListener) {
//
//    override fun onFocusChange(view: View?, hasFocus: Boolean) {
//        super.onFocusChange(view, hasFocus)
//        if (hasFocus) {
//            if(field.text?.isNullOrEmpty() == true)  {
//            }
//        }
//
////        else {
////            field.hint = ""
////        }
//    }

}