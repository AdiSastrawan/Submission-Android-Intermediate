package com.adisastrawan.storyapp.ui.component

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.adisastrawan.storyapp.R

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) :
    AppCompatEditText(context, attrs) {

        init{
            addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(text.toString().isNotEmpty()){
                        error = if (text.toString().length < 8){
                            context.getString(R.string.password_warning)
                        }else{
                            null
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
        }

}