package com.example.collegealertapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.*

class SettingsActivity : AppCompatActivity() {

    // Views
    private lateinit var btnBack: ImageButton
    private lateinit var switchPushNotifications: Switch
    private lateinit var switchEmailNotifications: Switch
    private lateinit var switchDarkMode: Switch

    // Clickable Layouts
    private lateinit var profileSetting: LinearLayout
    private lateinit var changePasswordSetting: LinearLayout
    private lateinit var privacyPolicySetting: LinearLayout
    private lateinit var termsSetting: LinearLayout
    private lateinit var languageSetting: LinearLayout
    private lateinit var aboutAppSetting: LinearLayout
    private lateinit var logoutSetting: LinearLayout

    // SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply language before anything else
        applySavedLanguage()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("CampusAlertPrefs", MODE_PRIVATE)

        initViews()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        // Ensure switch states are correct every time the screen is shown
        loadAndSetSwitchStates()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        switchPushNotifications = findViewById(R.id.switchPushNotifications)
        switchEmailNotifications = findViewById(R.id.switchEmailNotifications)
        switchDarkMode = findViewById(R.id.switchDarkMode)

        profileSetting = findViewById(R.id.profileSetting)
        changePasswordSetting = findViewById(R.id.changePasswordSetting)
        privacyPolicySetting = findViewById(R.id.privacyPolicySetting)
        termsSetting = findViewById(R.id.termsSetting)
        languageSetting = findViewById(R.id.languageSetting)
        aboutAppSetting = findViewById(R.id.aboutAppSetting)
        logoutSetting = findViewById(R.id.logoutSetting)
    }

    private fun setupClickListeners() {
        // Back button
        btnBack.setOnClickListener {
            onBackPressed()
        }

        // ===========================================
        // DARK MODE SWITCH - SAHI AUR SIMPLE TAREEKA
        // ===========================================
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            // Save the user's choice
            saveBooleanPreference("dark_mode", isChecked)

            // Tell the system to apply the new theme
            val mode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        // Other Listeners (No change needed)
        switchPushNotifications.setOnCheckedChangeListener { _, isChecked ->
            saveBooleanPreference("push_notifications", isChecked)
        }
        switchEmailNotifications.setOnCheckedChangeListener { _, isChecked ->
            saveBooleanPreference("email_notifications", isChecked)
        }
        profileSetting.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        changePasswordSetting.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
        privacyPolicySetting.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", "https://campusalert.com/privacy-policy")
            intent.putExtra("title", "Privacy Policy")
            startActivity(intent)
        }
        termsSetting.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", "https://campusalert.com/terms-conditions")
            intent.putExtra("title", "Terms & Conditions")
            startActivity(intent)
        }
        languageSetting.setOnClickListener {
            showLanguageDialog()
        }
        aboutAppSetting.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
        logoutSetting.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun loadAndSetSwitchStates() {
        // Dark mode switch ko system ki current state ke hisaab se set karein
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        switchDarkMode.isChecked = currentNightMode == Configuration.UI_MODE_NIGHT_YES

        // Load other switch preferences
        switchPushNotifications.isChecked = getBooleanPreference("push_notifications", true)
        switchEmailNotifications.isChecked = getBooleanPreference("email_notifications", false)
    }

    // ========================================================
    // NICHAY DIYE GAYE FUNCTIONS DELETE KAR DIYE GAYE HAIN:
    // - applyThemeSmooth()
    // - applyInstantThemeChanges()
    // - updateTextColorsInLayout()
    // - applyCurrentTheme()
    // Kyunki inki zaroorat nahi hai. System theme khud handle karta hai.
    // ========================================================

    // --- Helper and Other Functions (No change needed) ---

    private fun saveBooleanPreference(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    private fun getBooleanPreference(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    // --- Language Functions (Kept as is) ---

    private fun applySavedLanguage() {
        val prefs = getSharedPreferences("CampusAlertPrefs", MODE_PRIVATE)
        val savedLanguage = prefs.getString("selected_language", "English") ?: "English"
        val locale = when (savedLanguage) {
            "English" -> Locale("en")
            "Urdu" -> Locale("ur")
            "Arabic" -> Locale("ar")
            else -> Locale("en")
        }
        setLocale(locale)
    }

    private fun setLocale(locale: Locale) {
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun showLanguageDialog() {
        // This function remains the same
        try {
            val languages = arrayOf("English", "اردو", "العربية")
            val languageCodes = arrayOf("en", "ur", "ar")
            val languageLabels = arrayOf("English", "Urdu", "Arabic")

            val currentLanguage = sharedPreferences.getString("selected_language", "English")
            var selectedIndex = languageLabels.indexOf(currentLanguage).takeIf { it != -1 } ?: 0

            AlertDialog.Builder(this)
                .setTitle("Select Language / زبان منتخب کریں / اختر اللغة")
                .setSingleChoiceItems(languages, selectedIndex) { _, which -> selectedIndex = which }
                .setPositiveButton("OK") { dialog, _ ->
                    val selectedLanguage = languageLabels[selectedIndex]
                    val selectedCode = languageCodes[selectedIndex]
                    sharedPreferences.edit()
                        .putString("selected_language", selectedLanguage)
                        .putString("language_code", selectedCode)
                        .apply()
                    recreateActivityWithLanguage()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel", null)
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening language settings", Toast.LENGTH_SHORT).show()
        }
    }

    private fun recreateActivityWithLanguage() {
        // Using a handler to allow any dialogs to close smoothly
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SettingsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 200)
    }

    // --- Logout Functions (Kept as is) ---
    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ -> performLogout() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun performLogout() {
        sharedPreferences.edit().clear().apply() // Clear all prefs on logout
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}