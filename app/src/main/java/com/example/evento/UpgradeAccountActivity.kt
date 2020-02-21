package com.example.evento

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.example.event_organizer.repository.RepoEventOrganizerPostgresql
import com.example.evento.models.EventOrganizer
import com.example.evento.models.ResponseUpgradeData
import com.example.evento.models.UpgradeUser
import kotlinx.android.synthetic.main.activity_upgrade_account.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class UpgradeAccountActivity : AppCompatActivity() {


    lateinit var nameEO: TextView
    lateinit var noKTP: TextView
    lateinit var noSIUP: TextView
    var currentPath: String? = null
    val TAKE_PICTURE = 1
    val SELECT_PICTURE = 2
    lateinit var yourFileUri: Uri
    lateinit var yourFile:File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upgrade_account)


        nameEO = findViewById(R.id.txtNameEO)
        noKTP = findViewById(R.id.txtNoKtp)
        noSIUP = findViewById(R.id.txtNoSIUP)

        val actionBar = supportActionBar
        actionBar?.setTitle("Upgrade Account")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#2962ff")))
    }

    fun clickUploadFoto(view: View) {
        createAlertDialog()
    }

    fun createAlertDialog() {
        val array = arrayOf("CAMERA", "GALLERY")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Image")
        builder.setItems(array) { _, which ->
            val selected = array[which]

            try {
                if (selected == "CAMERA") {
                    dispatchCameraIntent()
                } else if (selected == "GALLERY") {
                    dispatchGalleryIntent()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    @SuppressWarnings("unused")
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            try {
                val file = File(currentPath)
                val url = Uri.fromFile(file)
                imgViewKtp.setImageURI(url)
                Log.d("Test File Camera",file.toString())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            try {
                val uri = data!!.data
                imgViewKtp.setImageURI(uri)
                if (uri != null) {
                    yourFileUri = uri
                    val test:String = uri.path.toString()
                    val file1:File = File(test)
                    yourFile =file1
                    Log.d("Test File gallery",uri.toString())
                    Log.d("Test File gallery",test)
                    Log.d("Test File gallery",file1.name.toString())
                    Log.d("Test File gallery",file1.toString())
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    fun dispatchGalleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECT_PICTURE)
    }

    private fun dispatchCameraIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImage()
                yourFile = photoFile
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (photoFile != null) {
                var photoUrl = FileProvider.getUriForFile(
                    this,
                    "com.example.evento.fileprovider", photoFile
                )
                yourFileUri = photoUrl
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUrl)
                startActivityForResult(intent, TAKE_PICTURE)
            }
        }
    }


    fun createImage(): File {
        val timeStap = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageName = "JPEG_" + timeStap + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageName, ".jpg", storageDir)
        currentPath = image.absolutePath
        return image
    }

    fun clickUpgradeUser(view: View) {
        val upgradeUserService = RepoEventOrganizerPostgresql.CreateUserService()

        var idUser = readFile("id_user.txt")
        var name_eo: RequestBody = RequestBody.create(MultipartBody.FORM, nameEO.text.toString())
        var no_ktp: RequestBody = RequestBody.create(MultipartBody.FORM, noKTP.text.toString())
        var no_siup: RequestBody = RequestBody.create(MultipartBody.FORM, noSIUP.text.toString())
        var originalFile:File = yourFile
        var filePart = RequestBody.create(
            MediaType.parse(contentResolver.getType(yourFileUri)),
            originalFile
        )
        Log.d("INI Body", MediaType.parse(contentResolver.getType(yourFileUri)).toString())
        Log.d("INI Body",filePart.toString())
        var file :MultipartBody.Part = MultipartBody.Part.createFormData("ktp_photo",originalFile.name,filePart)
        Log.d("Hmmmm", file.headers().toString())
        upgradeUserService.upgradeUser(
            idUser.toInt(),
            name_eo,
            no_ktp,
            no_siup,
            file

        ).enqueue(object : Callback<ResponseUpgradeData> {
            override fun onFailure(call: Call<ResponseUpgradeData>, t: Throwable) {
                Log.e("tag", "errornya ${t.message}")
            }

            override fun onResponse(
                call: Call<ResponseUpgradeData>,
                response: Response<ResponseUpgradeData>
            ) {
                var data = response.body()
                Log.d("Test Res", data?.userUpgradeResponse?.event_organizer?.name_eo.toString())
                Log.d("Test Res", data?.userUpgradeResponse?.submission_status.toString())
                Toast.makeText(
                    this@UpgradeAccountActivity,
                    "Upgrade Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }

        })
    }

    private fun readFile(fileName: String): String {
        //Read file in strogae internal
        var fileInputStream: FileInputStream
        fileInputStream = openFileInput(fileName)
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine();text }() != null) {
            stringBuilder.append(text)
        }

        return stringBuilder.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}