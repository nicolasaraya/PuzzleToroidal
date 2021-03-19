package com.curso.puzzletoroidal.ui.home

import android.content.res.Resources
import android.os.Parcelable
import android.provider.Settings.Global.getString
import android.widget.ImageButton
import com.curso.puzzletoroidal.R
import com.curso.puzzletoroidal.ui.gallery.GalleryFragment
import java.io.Serializable

class Bloque (val boton: ImageButton?, entero: Int, val pos: Int): Serializable{
    var fila = 0
    var columna = 0
    var cambio = entero

    public fun ColumnayFila(){
        if(pos == 0 || pos == 1|| pos == 2 || pos == 3) fila= 1
        if(pos == 4 || pos == 5|| pos == 6 || pos == 7) fila= 2
        if(pos == 8 || pos == 9|| pos == 10 || pos ==11) fila= 3
        if(pos == 12 || pos == 13|| pos == 14 || pos ==15) fila= 4

        if(pos == 0 || pos == 4|| pos == 8 || pos == 12) columna= 1
        if(pos == 1 || pos == 5|| pos == 9 || pos == 13) columna= 2
        if(pos == 2 || pos == 10|| pos == 6 || pos ==14) columna= 3
        if(pos == 3 || pos == 7|| pos == 15 || pos ==11) columna= 4

    }
    public fun cambio(valor: Int){
        cambio = valor
    }
    public fun Update(){
        if(cambio == 0){

            boton?.setImageResource(R.drawable.uno)

            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[1])
            }

        }
        if(cambio == 1){
            boton?.setImageResource(R.drawable.dos)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[2])
            }
        }
        if(cambio == 2){
            boton?.setImageResource(R.drawable.tres)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[3])
            }
        }
        if(cambio == 3){
            boton?.setImageResource(R.drawable.cuatro)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[4])
            }
        }
        if(cambio == 4){
            boton?.setImageResource(R.drawable.cinco)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[5])
            }
        }
        if(cambio == 5){
            boton?.setImageResource(R.drawable.seis)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[6])
            }
        }
        if(cambio == 6){
            boton?.setImageResource(R.drawable.siete)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[7])
            }
        }
        if(cambio == 7){
            boton?.setImageResource(R.drawable.ocho)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[8])
            }
        }
        if(cambio == 8){
            boton?.setImageResource(R.drawable.nueve)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[9])
            }
        }
        if(cambio == 9){
            boton?.setImageResource(R.drawable.diez)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[10])
            }
        }
        if(cambio == 10){
            boton?.setImageResource(R.drawable.once)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[11])
            }
        }
        if(cambio == 11){
            boton?.setImageResource(R.drawable.doce)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[12])
            }
        }
        if(cambio == 12){
            boton?.setImageResource(R.drawable.trece)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[13])
            }
        }
        if(cambio == 13){
            boton?.setImageResource(R.drawable.catorc)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[14])
            }
        }
        if(cambio == 14){
            boton?.setImageResource(R.drawable.quince)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[15])
            }
        }
        if(cambio == 15){
            boton?.setImageResource(R.drawable.dseis)
            if(GalleryFragment.switch==true){
                boton?.setImageBitmap(GalleryFragment.arr[16])
            }
        }
    }

}



