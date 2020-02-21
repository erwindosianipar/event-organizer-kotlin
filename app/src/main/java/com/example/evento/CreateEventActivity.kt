package com.example.evento

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.example.event_organizer.repository.RepoEventOrganizerPostgresql
import com.example.evento.models.Event
import com.example.evento.models.ResponseCreateEvent
import com.example.evento.models.ResponseListEvent
import kotlinx.android.synthetic.main.activity_create_event.*
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

class CreateEventActivity: AppCompatActivity() {

    lateinit var name: TextView
    lateinit var lokasi: TextView
    lateinit var event_date: TextView
    lateinit var kuota: TextView
    lateinit var harga: TextView
    lateinit var yourFileUri: Uri
    lateinit var yourFile:File

    var currentPath: String? = null
    val TAKE_PICTURE = 1
    val SELECT_PICTURE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        name = findViewById(R.id.txtEventName)
        lokasi = findViewById(R.id.txtLocation)
        event_date = findViewById(R.id.txtEventDate)
        kuota = findViewById(R.id.txtQuota)
        harga = findViewById(R.id.txtPrice)

        val actionBar = supportActionBar
        actionBar?.setTitle("Add Event")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#2962ff")))

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, _, monthOfYear, dayOfMonth ->
            txtEventDate.setText("""$dayOfMonth/${monthOfYear + 1}/$year""")
        }, year, month, day)
        dpd.show()
        println(view)
    }

    fun clickUploadBanner(view :View) {
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

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            try {
                val file = File(currentPath.toString())
                val url = Uri.fromFile(file)
                imgViewBanner.setImageURI(url)
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


    fun clickCreateEvent(view: View) {
        val postNewEvent = RepoEventOrganizerPostgresql.CreateUserService()

        val user_id = readFile("id_user.txt")
        val event_name: RequestBody = RequestBody.create(MultipartBody.FORM, name.text.toString())
        val event_location: RequestBody = RequestBody.create(MultipartBody.FORM, lokasi.text.toString())
        val event_date: RequestBody = RequestBody.create(MultipartBody.FORM, event_date.text.toString())
        val event_kuota: RequestBody = RequestBody.create(MultipartBody.FORM, kuota.text.toString())
        val event_price: RequestBody = RequestBody.create(MultipartBody.FORM, harga.text.toString())

        var originalFile: File = yourFile
        var filePart = RequestBody.create(
            MediaType.parse(contentResolver.getType(yourFileUri)),
            originalFile
        )
        var file :MultipartBody.Part = MultipartBody.Part.createFormData("banner_foto",originalFile.name,filePart)

        postNewEvent.addEvent(
            user_id.toInt(),
            event_name,
            event_location,
            event_date,
            event_kuota,
            event_price,
            file

        ).enqueue(object : Callback<ResponseCreateEvent> {
            override fun onFailure(call: Call<ResponseCreateEvent>, t: Throwable) {
                Log.e("ERROR", "errornya ${t.message}")
            }

            override fun onResponse(
                call: Call<ResponseCreateEvent>,
                response: Response<ResponseCreateEvent>
            ) {
                if (response.code() == 201) {
                    Toast.makeText(
                        this@CreateEventActivity,
                        "Upgrade Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@CreateEventActivity,
                        "WRONG",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                var data = response.body()
                Log.d("Test Res", data.toString())

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