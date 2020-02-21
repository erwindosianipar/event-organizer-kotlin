package com.example.evento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener

class DetailEventActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        var name = intent.getStringExtra("name")
        var lokasi = intent.getStringExtra("lokasi")
        var event_date = intent.getStringExtra("event_date")
        var harga = intent.getIntExtra("harga", 0)
        var kuota = intent.getIntExtra("kuota", 0)
        var banner = intent.getStringExtra("banner_foto")

        val viewName = findViewById<TextView>(R.id.txtEventName)
        val viewLokasi = findViewById<TextView>(R.id.txtLocation)
        val viewDate = findViewById<TextView>(R.id.tglEvent)
        val viewHarga = findViewById<TextView>(R.id.txtPrice)
        val viewKuota = findViewById<TextView>(R.id.txtKuota)

        val sampleImages = arrayOf(
            "http://10.0.2.2:8083/"+banner?.toString(),
            "http://10.0.2.2:8083/"+banner?.toString()
        )

        val imageListener: ImageListener = object : ImageListener {
            override fun setImageForPosition(position: Int, imageView: ImageView) {
                // You can use Glide or Picasso here
                Picasso.get().load(sampleImages[position]).into(imageView)
            }
        }

        val carouselView = findViewById(R.id.carouselView) as CarouselView
        carouselView.setPageCount(sampleImages.size)
        carouselView.setImageListener(imageListener)

        viewName.setText(name)
        viewLokasi.setText(lokasi)
        viewDate.setText(event_date)
        viewHarga.setText(harga.toString())
        viewKuota.setText(kuota.toString())

    }

}
