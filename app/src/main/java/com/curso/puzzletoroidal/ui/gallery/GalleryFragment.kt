package com.curso.puzzletoroidal.ui.gallery

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.curso.puzzletoroidal.R
import kotlinx.android.synthetic.main.fragment_gallery.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URI


class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    lateinit var imagen : ImageView
    var pictureName = "photo.jpg"
    var capturedImageUri : Uri? = null

    companion object {
        var arr: Array<Bitmap?> = arrayOfNulls<Bitmap?>(17)
        var arrUri: Array<URI?> = arrayOfNulls<URI?>(17)
        var switch: Boolean = false
        var switchGaleria: Boolean = false
        var bit : Bitmap? = null
        var uri : URI? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        return root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imagen = view?.findViewById<ImageView>(R.id.imagengallery)!!
        if(bit!=null) imagen.setImageBitmap(bit)
        var cam: ImageButton? = view?.findViewById<ImageButton>(R.id.camerabutton)
        if(cam!=null){
            cam.setOnClickListener(){

                var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(i, 123)
            }
        }

        var pick = view?.findViewById<ImageButton>(R.id.selector)
        if(pick!=null){
            pick.setOnClickListener(){
                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery, 124)

            }
        }

        var pastos = view?.findViewById<ImageButton>(R.id.pastos)
        if(pastos!=null){
            pastos.setOnClickListener(){
                var imagebit = BitmapFactory.decodeResource(resources, R.drawable.pastos)
                if(imagebit!=null) {
                    bit = imagebit
                    imagengallery.setImageBitmap(bit)
                    saveBitMapToStorage()
                }
            }
        }
        var arco = view?.findViewById<ImageButton>(R.id.arco)
        if(arco!=null){
            arco.setOnClickListener(){
                var imagebit = BitmapFactory.decodeResource(resources, R.drawable.arco)
                if(imagebit!=null) {
                    bit = imagebit
                    imagengallery.setImageBitmap(bit)
                    saveBitMapToStorage()
                }
            }
        }
        var plaza = view?.findViewById<ImageButton>(R.id.plaza)
        if(plaza!=null){
            plaza.setOnClickListener(){
                var imagebit = BitmapFactory.decodeResource(resources, R.drawable.plaza)
                if(imagebit!=null) {
                    bit = imagebit
                    imagengallery.setImageBitmap(bit)
                    saveBitMapToStorage()
                }
            }
        }
    }






    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123){
            var bitt = data?.extras?.get("data") as Bitmap
            var ancho = bitt!!.width
            var alto = bitt!!.height
            var menor = ancho
            if(ancho>alto) menor = alto
            bit = Bitmap.createBitmap(bitt!!, 0, 0, menor, menor)
            imagengallery.setImageBitmap(bit)
            saveBitMapToStorage()


        }

        if(requestCode == 124){
            var image = data?.getData()
            if(image==null) return
            imagengallery.setImageURI(image)
            var bitt = MediaStore.Images.Media.getBitmap(context?.contentResolver, image)
            var ancho = bitt!!.width
            var alto = bitt!!.height
            var menor = ancho
            if(ancho>alto) menor = alto
            bit = Bitmap.createBitmap(bitt!!, 0, 0, menor, menor)
            imagengallery.setImageBitmap(bit)
            saveBitMapToStorage()

        }


    }

    fun saveBitMapToStorage(){
        if(bit == null){
            return
        }
        val image = bit
        val ancho = image!!.width/4
        val alto = image!!.height/4
        var count = 1
        for(i in 0 until 4){
            for(j:Int in 0 until 4){
                val b = image?.let { Bitmap.createBitmap(bit!!, j*ancho, i*alto, ancho, alto) }
                arr[count] = b
                count++
            }
        }
        switch = true

    }


    fun saveToGallery(context: Context?, bitmap: Bitmap, albumName: String) {
        val filename = "${System.currentTimeMillis()}.png"
        val write: (OutputStream) -> Boolean = {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_DCIM}/$albumName")
            }

            context?.contentResolver.let {
                it?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let { uri ->
                    it.openOutputStream(uri)?.let(write)
                }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + albumName
            val file = File(imagesDir)
            if (!file.exists()) {
                file.mkdir()
            }
            val image = File(imagesDir, filename)
            write(FileOutputStream(image))
        }
    }





}





