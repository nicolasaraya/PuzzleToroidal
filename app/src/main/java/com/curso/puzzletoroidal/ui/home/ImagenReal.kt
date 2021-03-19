package com.curso.puzzletoroidal.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.curso.puzzletoroidal.R
import com.curso.puzzletoroidal.ui.gallery.GalleryFragment
import kotlinx.android.synthetic.main.fragment_home.*

class ImagenReal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagen_real)
        if(GalleryFragment.switch==true){
            imageView2.setImageBitmap(GalleryFragment.bit)
        }
    }
}
