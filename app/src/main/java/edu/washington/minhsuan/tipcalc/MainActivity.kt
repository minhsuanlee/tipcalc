package edu.washington.minhsuan.tipcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import java.text.NumberFormat
import android.widget.AdapterView

class MainActivity : AppCompatActivity() {
    private val cur : Regex = Regex("[$,.]")
    private var percentage: Double = 0.15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.percent_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.setSelection(1)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    percentage = parent.getItemAtPosition(position).toString().substring(0, 2).toDouble() / 100
                }
            }

        }

        val tipBtn = findViewById<Button>(R.id.btnTip)
        tipBtn.isEnabled = false

        val txtBox = findViewById<EditText>(R.id.txtInput)
        txtBox.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
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
