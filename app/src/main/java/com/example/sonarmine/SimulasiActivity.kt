package com.example.sonarmine

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.sonarmine.databinding.ActivitySimulasiBinding
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

class SimulasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimulasiBinding
    private lateinit var tflite: Interpreter
    private val inputFields = mutableListOf<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimulasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tambahkan 10 input EditText secara dinamis
        for (i in 1..10) {
            val editText = EditText(this).apply {
                hint = "Fitur $i"
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                setPadding(32, 24, 32, 24)
                textSize = 16f
                setTextColor(Color.BLACK)
                setHintTextColor(Color.DKGRAY)
                backgroundTintList = getColorStateList(R.color.teal_700)
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 16
                }
            }
            binding.inputContainer.addView(editText)
            inputFields.add(editText)
        }

        // Load model dari assets
        val modelFd = assets.openFd("sonar_mine_pca.tflite")
        val inputStream = modelFd.createInputStream()
        val modelBytes = inputStream.readBytes()
        val modelBuffer = ByteBuffer.allocateDirect(modelBytes.size).apply {
            order(ByteOrder.nativeOrder())
            put(modelBytes)
            rewind()
        }
        tflite = Interpreter(modelBuffer)

        // Tombol prediksi
        binding.btnPrediksi.setOnClickListener {
            prediksi()
        }

        // Tombol isi dummy
        binding.btnIsiDummy.setOnClickListener {
            isiInputDummy()
        }
    }

    private fun prediksi() {
        try {
            val inputValues = FloatArray(10)
            for (i in inputFields.indices) {
                val text = inputFields[i].text.toString().trim()
                if (text.isBlank()) {
                    binding.hasilPrediksi.text = "Fitur ke-${i + 1} masih kosong!"
                    return
                }
                try {
                    inputValues[i] = text.toFloat()
                } catch (e: NumberFormatException) {
                    binding.hasilPrediksi.text = "Fitur ke-${i + 1} bukan angka valid!"
                    return
                }
            }

            val inputBuffer = ByteBuffer.allocateDirect(4 * 10)
                .order(ByteOrder.nativeOrder())
            inputValues.forEach { inputBuffer.putFloat(it) }

            val outputBuffer = ByteBuffer.allocateDirect(4 * 2) // 2 output: Rock vs Mine
                .order(ByteOrder.nativeOrder())
            tflite.run(inputBuffer, outputBuffer)

            outputBuffer.rewind()
            val output1 = outputBuffer.float
            val output2 = outputBuffer.float

            val result = if (output1 > output2) "Rock" else "Mine"
            val confidence = maxOf(output1, output2)

            binding.hasilPrediksi.text = "Hasil: $result\nConfidence: ${"%.2f".format(confidence)}"

        } catch (e: Exception) {
            binding.hasilPrediksi.text = "Terjadi kesalahan saat memproses input!"
        }
    }

    private fun isiInputDummy() {
        for (i in inputFields.indices) {
            val dummyValue = 0.1f * (i + 1)
            inputFields[i].setText(dummyValue.toString())
        }
        binding.hasilPrediksi.text = "Input dummy telah diisi"
    }
}
