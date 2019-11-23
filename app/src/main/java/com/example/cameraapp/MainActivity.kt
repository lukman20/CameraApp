package com.example.cameraapp

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    val REQUEST_PERMISSION_CODE = 100
    val REQUEST_CAMERA_CODE = 101
    var currentPhoto : String?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //cek perizininan
        val dafterIzin = mutableListOf<String>()
            if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
                dafterIzin.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            dafterIzin.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            dafterIzin.add(android.Manifest.permission.CAMERA)
        }

        if (dafterIzin.size>0){
            val iz = arrayOfNulls<String>(dafterIzin.size)
            for (i in 0 until dafterIzin.size){
                iz[i] = dafterIzin.get(i)
            }
            ActivityCompat.requestPermissions(this, iz, REQUEST_PERMISSION_CODE)

        } else {
            // do nothing
        }
        //tambahkan fungsi klik pada image view (dgn ID foto)
        // jadi ketika di klik akan memanggil fungsi senyum yaitu membuka aplikasi kamera android
        //dan file foto disimpan dengan nama foto1.jpg
        foto.setOnClickListener{
            senyum("foto1.jpg")
        }
    }

    // fungsi ambil gambar
    fun senyum(namaFile: String){
        //persiapan membuka aplikasi camera bawaan android
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // siapkan file yang akan menyimpan hasil foto
        val filePhoto = File(getExternalFilesDir(null), namaFile)
        //siapkan public URI, jadi aplikasi kamerabisa simpan hasil foto di folder kita
        val uriPhoto = FileProvider.getUriForFile(this, "com.example.cameraapp.fileprovider",
            filePhoto)
        //ambil lokasi file foto tersebut untuk di tampilkan nanti di image view
        currentPhoto = filePhoto.absolutePath
        //infokan ke palikasi kamera lokasi tempat simpan hasil fotonya
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto)
        // start aplikasi kamera
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }
}
