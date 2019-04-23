package edu.washington.minhsuan.tipcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private val percentage: Double = 0.15
    private val cur : Regex = Regex("[$,.]")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tipBtn = findViewById<Button>(R.id.btnTip)
        tipBtn.isEnabled = false
        val txtBox = findViewById<EditText>(R.id.txtInput)

        txtBox.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //val format = NumberFormat.getCurrencyInstance(Locale.US)
                //txtBox.setText(format.format(s.toString()))
//                NumberFormat.getInstance(Locale.US)
//                txtBox.setText("$" + txtBox.getText().toString())
                tipBtn.isEnabled = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().equals("")){
                    txtBox.removeTextChangedListener(this)

                    var cleanString: String = s.toString().replace(cur, "")

                    var amount = cleanString.toDouble()
                    var formatted = NumberFormat.getCurrencyInstance().format((amount/100))

                    txtBox.setText(formatted);
                    txtBox.setSelection(formatted.length)

                    txtBox.addTextChangedListener(this)
                }
            }
        })

        tipBtn.setOnClickListener {
            var tip = (txtBox.text.toString().substring(1).toDouble() * 100).toInt() * percentage / 100
            var roundTip = "%.2f".format(tip).toDouble()
            Toast.makeText(this, "\$$roundTip", Toast.LENGTH_LONG).show()
        }
    }
}
