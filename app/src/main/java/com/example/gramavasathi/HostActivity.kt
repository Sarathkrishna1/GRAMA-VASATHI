package com.example.gramavasathi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class HostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        val btnCalculate = findViewById<Button>(R.id.btnCalculateScore)
        val resultText = findViewById<TextView>(R.id.txtScoreResult)

        // 1. Link the 5 CheckBoxes to their XML IDs
        val checkBoxes = listOf(
            findViewById<CheckBox>(R.id.checkSheets),
            findViewById<CheckBox>(R.id.checkWater),
            findViewById<CheckBox>(R.id.checkToilet),
            findViewById<CheckBox>(R.id.checkSoap),
            findViewById<CheckBox>(R.id.checkFood)
        )

        btnCalculate.setOnClickListener {
            // 2. Count how many are checked
            var checkedCount = 0
            for (box in checkBoxes) {
                if (box.isChecked) checkedCount++
            }

            // 3. Simple Math (out of 100%)
            val score = (checkedCount.toDouble() / checkBoxes.size) * 100
            resultText.text = "Readiness Score: ${score.toInt()}%"

            // 4. If they pass (at least 3 boxes checked), save progress and move to Dashboard
            if (score >= 60.0) {
                val sharedPrefs = getSharedPreferences("GramaPrefs", Context.MODE_PRIVATE)
                val editor = sharedPrefs.edit()
                editor.putBoolean("isTrainingCompleted", true)
                editor.apply()

                Toast.makeText(this, "Progress Saved! Opening Dashboard", Toast.LENGTH_SHORT).show()

                // Move to Host Dashboard
                val intent = Intent(this, HostDashboardActivity::class.java)
                startActivity(intent)
                finish() // Close this screen
            } else {
                Toast.makeText(this, "Please complete more items (Need 60%)", Toast.LENGTH_SHORT).show()
            }
        }
    }
}