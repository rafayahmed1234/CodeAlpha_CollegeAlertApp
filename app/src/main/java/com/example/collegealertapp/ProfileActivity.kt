// ProfileActivity.kt - REFACTORED
package com.example.collegealertapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var editButton: ImageView
    private lateinit var profileImage: ImageView
    private lateinit var cameraIcon: ImageView

    private lateinit var userName: TextView
    private lateinit var userRole: TextView
    private lateinit var studentId: TextView
    private lateinit var userEmail: TextView
    private lateinit var userPhone: TextView
    private lateinit var userDob: TextView
    private lateinit var userDepartment: TextView
    private lateinit var userSemester: TextView
    private lateinit var userCgpa: TextView
    private lateinit var userAdmissionYear: TextView
    private lateinit var attendancePercentage: TextView
    private lateinit var coursesCount: TextView

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data
            if (imageUri != null) {
                profileImage.setImageURI(imageUri)
                Toast.makeText(this, "Profile picture updated!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initViews()
        loadUserProfile()
        setupClickListeners()
        setupBackPressHandler()
    }

    // =======================================================
    // ZAROORI: Jab aap EditProfileActivity se wapas aayein,
    // toh data dobara load karein.
    // =======================================================
    override fun onResume() {
        super.onResume()
        loadUserProfile()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        editButton = findViewById(R.id.editButton)
        profileImage = findViewById(R.id.profileImage)
        cameraIcon = findViewById(R.id.cameraIcon)
        userName = findViewById(R.id.userName)
        userRole = findViewById(R.id.userRole)
        studentId = findViewById(R.id.studentId)
        userEmail = findViewById(R.id.userEmail)
        userPhone = findViewById(R.id.userPhone)
        userDob = findViewById(R.id.userDob)
        userDepartment = findViewById(R.id.userDepartment)
        userSemester = findViewById(R.id.userSemester)
        userCgpa = findViewById(R.id.userCgpa)
        userAdmissionYear = findViewById(R.id.userAdmissionYear)
        attendancePercentage = findViewById(R.id.attendancePercentage)
        coursesCount = findViewById(R.id.coursesCount)
    }

    // =======================================================
    // YEH FUNCTION UPDATE HUA HAI - Data load karne ke liye
    // =======================================================
    private fun loadUserProfile() {
        val prefs = getSharedPreferences("CampusAlertPrefs", MODE_PRIVATE)

        // SharedPreferences se data load karein.
        // Agar value nahi milti, toh default value istemal hogi.
        userName.text = prefs.getString("user_name", "Ahmed Ali")
        userRole.text = "Student" // Role shayad fix hai
        studentId.text = prefs.getString("student_id", "2021-CS-123") // Maan lete hain yeh key hai
        userEmail.text = prefs.getString("user_email", "ahmed.ali@university.edu")
        userPhone.text = prefs.getString("user_phone", "+92 300 1234567")
        userDob.text = prefs.getString("user_dob", "15 March, 2000")
        userDepartment.text = prefs.getString("department", "Computer Science") // Maan lete hain yeh key hai
        userSemester.text = prefs.getString("user_semester", "6th Semester")
        userCgpa.text = "3.75" // CGPA shayad fix hai
        userAdmissionYear.text = prefs.getString("admission_year", "2021") // Maan lete hain yeh key hai
        attendancePercentage.text = "87%" // Yeh bhi shayad fix hai
        coursesCount.text = "6" // Yeh bhi shayad fix hai
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
        editButton.setOnClickListener {
            openEditProfile()
        }
        profileImage.setOnClickListener {
            openImagePicker()
        }
        cameraIcon.setOnClickListener {
            openImagePicker()
        }
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    // =======================================================
    // YEH FUNCTION UPDATE HUA HAI - Ab yeh result ka intezar nahi karega
    // =======================================================
    private fun openEditProfile() {
        val intent = Intent(this, EditProfileActivity::class.java).apply {
            // Hum ab bhi purana data bhej rahe hain taaki edit screen par fields bhari hui aayen
            putExtra("CURRENT_NAME", userName.text.toString())
            putExtra("CURRENT_EMAIL", userEmail.text.toString())
            putExtra("CURRENT_PHONE", userPhone.text.toString())
            putExtra("CURRENT_DOB", userDob.text.toString())
            putExtra("CURRENT_SEMESTER", userSemester.text.toString())
            putExtra("CURRENT_STUDENT_ID", studentId.text.toString())
            putExtra("CURRENT_DEPARTMENT", userDepartment.text.toString())
            putExtra("CURRENT_ADMISSION_YEAR", userAdmissionYear.text.toString())
        }
        // Ab hum simple startActivity istemal karenge
        startActivity(intent)
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }
}