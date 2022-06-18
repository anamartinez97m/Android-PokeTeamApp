package com.mimo.poketeamapp.settings

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.mimo.poketeamapp.R
import com.mimo.poketeamapp.database.AppDatabase
import com.mimo.poketeamapp.databinding.ActivitySettingsBinding
import com.squareup.picasso.Picasso

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "pokemon-database")
            .allowMainThreadQueries()
            .build()

        val toolbar: Toolbar = binding.myToolbar
        toolbar.setTitle(R.string.title_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.profilePictureSettings.setOnClickListener {
            modifyProfilePicture()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun changeLanguage(languagePosition: Int) {
        // TODO: terminar
        //val currentLocale = this.resources.configuration.locales[0]
        //val langCode = languagesCodesMap[languages[languagePosition]]
        //val conf: Configuration = this.resources.configuration
        //val locale = Locale(langCode)
        //Locale.setDefault(locale)
        //conf.setLocale(locale)
        //createConfigurationContext(conf)
        //this.setContentView(R.layout.activity_settings)
    }

    private fun modifyProfilePicture() {
        if(!checkPermissions()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                SettingsActivity.REQUEST_PERMISSIONS_REQUEST_CODE
            )
        } else {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).also { pictureIntent ->
                pictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(pictureIntent, SettingsActivity.PICK_IMAGE_REQUEST)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SettingsActivity.PICK_IMAGE_REQUEST -> {
                if(resultCode == RESULT_OK && data != null && data.data != null) {
                    val image = data.data as Uri
                    Picasso.get().load(image).into(binding.profilePictureSettings)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(SettingsActivity.TAG, "onRequestPermissionResult")
        if (requestCode == SettingsActivity.REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) -> RESULT_OK
                else -> Toast.makeText(this, R.string.have_no_permissions, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermissions(): Boolean =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    companion object {
        const val TAG = "GetAccessToGallery"
        const val REQUEST_PERMISSIONS_REQUEST_CODE = 1
        const val PICK_IMAGE_REQUEST = 2
    }

}