package com.olvr.maskmoney

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        inputEdtPlate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                labelNewPattern.text = "New Pattern: ${inputEdtPlate.isNewPatternRegistrationPlate()}"
                labelOldPattern.text = "Old Pattern: ${inputEdtPlate.isOldPatternRegistrationPlate()}"
                labelValid.text = "Valid: ${inputEdtPlate.isRegistrationPlateValid()}"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })

        cbSeparetorEnabled.setOnCheckedChangeListener { _, isChecked ->
            inputEdtPlate.enableMask(isChecked)
        }

        btnAdd.setOnClickListener {
            inputEdtPlate.setTextPlate(edtPlate.text.toString())
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
