package com.example.tugas2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var rg_operator: RadioGroup
    private lateinit var btn_hitung: Button
    private lateinit var et_nilai1: EditText
    private lateinit var et_angka2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        rg_operator = findViewById(R.id.rg_operator)
        btn_hitung = findViewById(R.id.btn_Hitung)
        et_nilai1 = findViewById(R.id.et_angka1)
        et_angka2 = findViewById(R.id.et_angka2)

        btn_hitung.setOnClickListener{
            if (et_nilai1.text.toString() == "") {
                et_nilai1.error = "Angka 1 Wajib diisi"
                return@setOnClickListener
            }

            if (et_angka2.text.toString() == "") {
                et_angka2.error = "Angka 2 Wajib diisi"
                return@setOnClickListener
            }

            val nilai_angka1 = et_nilai1.text.toString().toInt()
            val nilai_angka2 = et_angka2.text.toString().toInt()

            val hasil = do_hitung_hasil(nilai_angka1, nilai_angka2)

            //Intent untuk berpindah ke ResultActivity
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("RESULT", hasil)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun do_hitung_hasil(nilai1:Int, nilai2:Int): String {
        var hitung_hasil:Int = 0
        val selectOperatorId = rg_operator.checkedRadioButtonId
        if (selectOperatorId == -1) {
            Toast.makeText(this, "Pilih Operator", Toast.LENGTH_SHORT).show()
            return "0"
        }

        when(selectOperatorId) {
            R.id.rb_plus -> hitung_hasil = nilai1 + nilai2
            R.id.rb_minus -> hitung_hasil = nilai1 - nilai2
            R.id.rb_multiply -> hitung_hasil = nilai1 * nilai2
            R.id.rb_divide -> {
                if (nilai2 == 0) {
                    Toast.makeText(this, "Pembagi Tidak Boleh 0", Toast.LENGTH_SHORT).show()
                    return "0"
                }
                hitung_hasil = nilai1 / nilai2
            }
        }

        return hitung_hasil.toString()

    }
}