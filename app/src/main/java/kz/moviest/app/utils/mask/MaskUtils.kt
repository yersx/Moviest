package kz.moviest.app.utils.mask

import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.CaretString
import kz.moviest.app.data.enums.app.MaskTypeEnums
import kz.moviest.app.utils.Logger

object MaskUtils {

    fun initPhoneMask(et: EditText) {
        initMask(
            MaskTypeEnums.PHONE.id,
            et
        )
    }

    private fun initMask(maskFormat: String, et: EditText) {
        val listener = getMaskedTextChangedListener(maskFormat, et)
        et.addTextChangedListener(listener)
//        et.onFocusChangeListener = listener
        et.hint = listener.placeholder()
    }

    private fun getMaskedTextChangedListener(
        maskFormat: String,
        editText: EditText
    ): MaskedTextChangedListener {
//        return CustomMaskedTextChangedListener(
        return MaskedTextChangedListener(
            maskFormat,
            true,
            editText,
            null,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    Logger.e(javaClass, extractedValue)
                    Logger.e(javaClass, maskFilled.toString())
                }
            }
        )
    }

    fun getResult(maskFormat: String, input: String): Mask.Result {
        val mask = Mask(maskFormat)
        return mask.apply(
            CaretString(
                input,
                input.length
            ),
            true // you may consider disabling autocompletion for your case
        )
    }

}