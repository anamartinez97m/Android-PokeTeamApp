package com.mimo.poketeamapp.settings

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.mimo.poketeamapp.R
import com.mimo.poketeamapp.database.AppDatabase
import com.mimo.poketeamapp.databinding.ActivitySettingsBinding
import com.mimo.poketeamapp.login.LoginActivity
import com.mimo.poketeamapp.login.LoginViewModel
import com.mimo.poketeamapp.login.LoginViewModelFactory
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SettingsActivity : AppCompatActivity() {

    private var expandableListTitle: List<String> = ArrayList()
    private var expandableListDetail: HashMap<String, List<String>> = HashMap()
    private var languages: List<String> = ArrayList()
    private var languagesCodesMap: HashMap<String, String> = HashMap()

    private lateinit var loginViewModel: LoginViewModel
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

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(db))
            .get(LoginViewModel::class.java)

        val expandableListView: ExpandableListView = binding.expandableListView
        addDataToHashMap()

        val expandableListAdapter: ExpandableListAdapter =
            CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail)
        expandableListView.setAdapter(expandableListAdapter)

        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, _ ->
            when (groupPosition) {
                0 -> changeLanguage(childPosition)
                1 -> modifyUserData(childPosition)
                //2 -> modifyProfilePicture()
            }
            false
        }

        /*loginViewModel.loggedInUser.observe(this@SettingsActivity, Observer {
            val result = it ?: return@Observer

            Log.d("user", result.toString())
        })*/
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun addDataToHashMap() {
        expandableListTitle = expandableListTitle + getString(R.string.change_language)
        expandableListTitle = expandableListTitle + getString(R.string.modify_user_data)
        expandableListTitle = expandableListTitle + getString(R.string.modify_profile_picture)

        languages = languages + getString(R.string.en_US)
        languages = languages + getString(R.string.es_ES)
        languagesCodesMap[getString(R.string.en_US)] = "en_US"
        languagesCodesMap[getString(R.string.es_ES)] = "es_ES"
        var userData: List<String> = ArrayList()
        userData = userData + getString(R.string.prompt_email)
        userData = userData + getString(R.string.prompt_password)
        var profilePictureList: List<String> = ArrayList(1)
        profilePictureList = profilePictureList + ""

        expandableListDetail[expandableListTitle[0]] = languages
        expandableListDetail[expandableListTitle[1]] = userData
        expandableListDetail[expandableListTitle[2]] = profilePictureList
    }

    private fun changeLanguage(languagePosition: Int) {
        // TODO: terminar
        val currentLocale = this.resources.configuration.locales[0]
        val langCode = languagesCodesMap[languages[languagePosition]]
        val conf: Configuration = this.resources.configuration
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        conf.setLocale(locale)
        createConfigurationContext(conf)
        //this.setContentView(R.layout.activity_settings)
    }

    private fun modifyUserData(childPosition: Int) {
        when (childPosition) {
            0 -> Log.d("modifyUserData", "email")
            1 -> Log.d("modifyUserData", "password")
        }
    }

//    private fun modifyProfilePicture() {
//        if(!checkPermissions()) {
//            ActivityCompat.requestPermissions(
//                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                LoginActivity.REQUEST_PERMISSIONS_REQUEST_CODE
//            )
//        } else {
//            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).also { pictureIntent ->
//                pictureIntent.resolveActivity(packageManager)?.also {
//                    startActivityForResult(pictureIntent, LoginActivity.PICK_IMAGE_REQUEST)
//                }
//            }
//        }
//    }

//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        when (requestCode) {
//            LoginActivity.PICK_IMAGE_REQUEST -> {
//                if(resultCode == RESULT_OK && data != null && data.data != null) {
//                    val image = data.data as Uri
//                    Picasso.get().load(image).into(binding.profilePictureLogin)
//                }
//            }
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        Log.i(LoginActivity.TAG, "onRequestPermissionResult")
//        if (requestCode == LoginActivity.REQUEST_PERMISSIONS_REQUEST_CODE) {
//            when {
//                (grantResults[0] == PackageManager.PERMISSION_GRANTED
//                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) -> RESULT_OK
//                else -> Toast.makeText(this, R.string.have_no_permissions, Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun checkPermissions(): Boolean =
//        ActivityCompat.checkSelfPermission(
//            this,
//            Manifest.permission.READ_EXTERNAL_STORAGE
//        ) == PackageManager.PERMISSION_GRANTED
//
//    companion object {
//        const val TAG = "GetAccessToGallery"
//        const val REQUEST_PERMISSIONS_REQUEST_CODE = 1
//        const val PICK_IMAGE_REQUEST = 2
//    }

}