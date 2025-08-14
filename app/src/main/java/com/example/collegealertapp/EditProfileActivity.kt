// EditProfileActivity.kt - REFACTORED
package com.example.collegealertapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class EditProfileActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var saveButton: ImageView
    private lateinit var profileImage: ImageView
    private lateinit var cameraIcon: ImageView
    private lateinit var saveButtonLayout: LinearLayout

    private lateinit var editName: TextInputEditText
    private lateinit var editEmail: TextInputEditText
    private lateinit var editPhone: TextInputEditText
    private lateinit var editDob: TextInputEditText
    private lateinit var editStudentId: TextInputEditText
    private lateinit var editDepartment: TextInputEditText
    private lateinit var editSemester: AutoCompleteTextView
    private lateinit var editAdmissionYear: TextInputEditText

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data
            if (imageUri != null) {
                profileImage.setImageURI(imageUri)
                Toast.makeText(this, "Profile picture selected!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        initViews()
        loadUserData()
        setupClickListeners()
        setupSemesterDropdown()
        setupBackPressHandler()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        saveButton = findViewById(R.id.saveButton)
        profileImage = findViewById(R.id.profileImage)
        cameraIcon = findViewById(R.id.cameraIcon)
        saveButtonLayout = findViewById(R.id.saveButtonLayout)
        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editPhone = findViewById(R.id.editPhone)
        editDob = findViewById(R.id.editDob)
        editStudentId = findViewById(R.id.editStudentId)
        editDepartment = findViewById(R.id.editDepartment)
        editSemester = findViewById(R.id.editSemester)
        editAdmissionYear = findViewById(R.id.editAdmissionYear)
    }

    private fun loadUserData() {
        intent.getStringExtra("CURRENT_NAME")?.let { editName.setText(it) }
        intent.getStringExtra("CURRENT_EMAIL")?.let { editEmail.setText(it) }
        intent.getStringExtra("CURRENT_PHONE")?.let { editPhone.setText(it) }
        intent.getStringExtra("CURRENT_DOB")?.let { editDob.setText(it) }
        intent.getStringExtra("CURRENT_SEMESTER")?.let { editSemester.setText(it, false) }
        intent.getStringExtra("CURRENT_STUDENT_ID")?.let { editStudentId.setText(it) }
        intent.getStringExtra("CURRENT_DEPARTMENT")?.let { editDepartment.setText(it) }
        intent.getStringExtra("CURRENT_ADMISSION_YEAR")?.let { editAdmissionYear.setText(it) }
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
        saveButton.setOnClickListener {
            saveProfile()
        }
        saveButtonLayout.setOnClickListener {
            saveProfile()
        }
        profileImage.setOnClickListener {
            openImagePicker()
        }
        cameraIcon.setOnClickListener {
            openImagePicker()
        }
        editDob.setOnClickListener {
            showDatePicker()
        }
    }

    private fun setupSemesterDropdown() {
        val semesters = arrayOf("1st Semester", "2nd Semester", "3rd Semester", "4th Semester", "5th Semester", "6th Semester", "7th Semester", "8th Semester")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, semesters)
        editSemester.setAdapter(adapter)
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    // =======================================================
    // YEH FUNCTION UPDATE HUA HAI - Data save karne ke liye
    // =======================================================
    private fun saveProfile() {
        if (!validateInputs()) {
            return
        }

        // Get SharedPreferences instance
        val prefs = getSharedPreferences("CampusAlertPrefs", MODE_PRIVATE)

        // Start editing and save the data
        prefs.edit().apply {
            putString("user_name", editName.text.toString().trim())
            putString("user_email", editEmail.text.toString().trim())
            putString("user_phone", editPhone.text.toString().trim())
            putString("user_dob", editDob.text.toString().trim())
            putString("user_semester", editSemester.text.toString().trim())
            // Non-editable fields don't need to be saved again unless they can change
            apply()
        }

        Toast.makeText(this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()

        // Just finish the activity. No need to send data back via Intent.
        finish()
    }

    private fun validateInputs(): Boolean {
        if (editName.text.toString().trim().isEmpty()) {
            editName.error = "Name is required"
            return false
        }
        val email = editEmail.text.toString().trim()
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.error = "Valid email is required"
            return false
        }
        if (editPhone.text.toString().trim().isEmpty()) {
            editPhone.error = "Phone number is required"
            return false
        }
        return true
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val months = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
                editDob.setText("$dayOfMonth ${months[month]}, $year")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }
}