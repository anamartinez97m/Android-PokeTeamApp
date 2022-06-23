package com.mimo.poketeamapp.settings

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
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
import com.mimo.poketeamapp.data.DataStoreManager
import com.mimo.poketeamapp.database.AppDatabase
import com.mimo.poketeamapp.databinding.ActivitySettingsBinding
import com.mimo.poketeamapp.login.LoginActivity
import com.mimo.poketeamapp.model.UserModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import java.util.*

@DelicateCoroutinesApi
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var db: AppDatabase
    private lateinit var context: Context
    private lateinit var res: Resources
    private var idUserToModify: Int = 0
    private lateinit var imageUri: String
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "pokemon-database")
            .allowMainThreadQueries()
            .build()
        dataStoreManager = DataStoreManager(this@SettingsActivity)

        val toolbar: Toolbar = binding.myToolbar
        val profilePicture = binding.profilePictureSettings
        val checkboxEnglish = binding.checkBoxLanguageEn
        val checkboxSpanish = binding.checkBoxLanguageEs
        val editTextEmail = binding.modifyUserEmail
        val editTextPassword = binding.modifyUserPassword
        val buttonSave = binding.saveChanges

        toolbar.setTitle(R.string.title_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        checkboxEnglish.isChecked = Locale.getDefault().toString() == "en_US"
        checkboxSpanish.isChecked = Locale.getDefault().toString() == "es_ES"

        checkboxEnglish.setOnClickListener{
            checkboxSpanish.isChecked = false
            val localeHelper = LocaleHelper()
            context = localeHelper.setLocale(this, "en_US")
            res = context.resources
        }

        checkboxSpanish.setOnClickListener{
            checkboxEnglish.isChecked = false
            val localeHelper = LocaleHelper()
            context = localeHelper.setLocale(this, "es_ES")
            res = context.resources
        }

        val intent: Intent = intent
        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        editTextEmail.setText(username.toString())
        editTextPassword.setText(password.toString())

        if (username != null && password != null) {
            val user = db.userDao().getUser(username, password)
            idUserToModify = user.id
        }

//        GlobalScope.launch(Dispatchers.IO) {
//            dataStoreManager.getFromDataStore().catch { e ->
//                e.printStackTrace()
//            }.collect {
//                withContext(Dispatchers.Main) {
//                    imageUri = it.image
//                }
//            }
//        }

        if(this::imageUri.isInitialized && imageUri.isNotEmpty()) {
            profilePicture.setColorFilter(Color.argb(0, 255, 255, 255))
            val temporal = "content://media/external/images/media/19723"
            Picasso.get().load(temporal).into(binding.profilePictureSettings)
        }

        profilePicture.setOnClickListener {
            modifyProfilePicture()
            profilePicture.setColorFilter(Color.argb(0, 255, 255, 255))
        }

        buttonSave.setOnClickListener {
            if(idUserToModify != 0) {
                if (editTextEmail.text.isNotEmpty() && editTextEmail.text.toString() != username) {
                    db.userDao().updateUserEmail(editTextEmail.text.toString(), idUserToModify)
                    val loginIntent = Intent(applicationContext, LoginActivity::class.java)
                    loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(loginIntent)
                }
                if (editTextPassword.text.isNotEmpty() && editTextPassword.text.toString() != password) {
                    db.userDao().updateUserPassword(editTextPassword.text.toString(), idUserToModify)
                    val loginIntent = Intent(applicationContext, LoginActivity::class.java)
                    loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(loginIntent)
                }
                if(profilePicture.drawable.current::class.simpleName.toString() == "PicassoDrawable") {
//                    GlobalScope.launch(Dispatchers.IO) {
//                        dataStoreManager.saveToDataStore(
//                            user = UserModel(
//                                id = idUserToModify.toString(),
//                                email = binding.modifyUserEmail.text.toString(),
//                                image = imageUri
//                            )
//                        )
//                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun modifyProfilePicture() {
        if(!checkPermissions()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        } else {
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).also { pictureIntent ->
                pictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(pictureIntent, PICK_IMAGE_REQUEST)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_IMAGE_REQUEST -> {
                if(resultCode == RESULT_OK && data != null && data.data != null) {
                    val image = data.data as Uri
                    imageUri = image.toString()
                    Picasso.get().load(image).into(binding.profilePictureSettings)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> RESULT_OK
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