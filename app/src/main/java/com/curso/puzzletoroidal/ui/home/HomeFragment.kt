package com.curso.puzzletoroidal.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup

import androidx.fragment.app.Fragment

import com.curso.puzzletoroidal.R

import android.view.MotionEvent

import android.view.View.OnClickListener
import android.widget.*

import com.curso.puzzletoroidal.ui.creditos.Creditos
import com.curso.puzzletoroidal.ui.gallery.GalleryFragment
import org.w3c.dom.Text

import kotlin.random.Random

import java.util.*

import kotlin.math.absoluteValue

class HomeFragment : Fragment(), View.OnTouchListener, OnClickListener {
    private var Ibtns= arrayOfNulls<ImageButton>(16)
    private var Tablero= mutableMapOf<ImageButton?,Bloque?>()
    private var info : Array<Bloque?> =  arrayOfNulls<Bloque>(16)
    private var pcountinit:Float = 0F
    private var pcountinit2:Float = 0F
    private var tiempo = 0L
    var movArriba:Boolean = false
    var movBajo:Boolean= false
    var movIz:Boolean = false
    var movDe:Boolean = false
    private var infovec : Array<Int?> = arrayOfNulls<Int>(16)
    private  var movs : Stack<Movimientos> = Stack()
    private  var elegida : Stack<Movimientos> = Stack()
    private lateinit var movidasTotal: TextView
    private lateinit var crono: Chronometer
    private lateinit var img : ImageView



    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return IBSelected(event, v as ImageButton)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        if(savedInstanceState!=null) println("no nulo")
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val botoncito = root.findViewById<ImageButton>(R.id.original)
        botoncito.setOnClickListener(this)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val numRand = FisherYates()

        val boton1 = view?.findViewById<ImageButton>(R.id.atras)
        boton1?.setOnClickListener(this)
        val boton2 = view?.findViewById<ImageButton>(R.id.resetear)
        boton2?.setOnClickListener(this)
        val boton3 = view?.findViewById<ImageButton>(R.id.orden)
        boton3?.setOnClickListener(this)
        crono = requireView().findViewById(R.id.tiempito)
        crono.base = SystemClock.elapsedRealtime()
        tiempo = SystemClock.elapsedRealtime()
        crono.start()
        movidasTotal= requireView().findViewById(R.id.movidasTotal)
        if(savedInstanceState!=null){
            tiempo = savedInstanceState.getSerializable("tiempo") as Long
            movs = savedInstanceState.getSerializable("stack") as Stack<Movimientos>
            elegida = savedInstanceState.getSerializable("elegida") as Stack<Movimientos>
            infovec = savedInstanceState.getSerializable("infovec") as Array<Int?>
            crono.base = SystemClock.elapsedRealtime()-(SystemClock.elapsedRealtime()-tiempo)
            for(i in 0 until Ibtns.size){
                var Ibutton=view?.findViewById<ImageButton>(resources.getIdentifier("btn$i", "id", context?.packageName))
                Ibutton?.setOnTouchListener(this)
                Ibtns[i] = Ibutton
                info[i] = Bloque(Ibtns[i], infovec[i]!!, i)
                info[i]?.Update()
                info[i]?.ColumnayFila()
                Tablero[Ibutton] = info[i]

            }
            movidasTotal.text = "Lleva ${movs.size} movidas"

        }
        else {
            for (i in 0 until Ibtns.size) {
                var Ibutton=view?.findViewById<ImageButton>(resources.getIdentifier("btn$i", "id", context?.packageName))
                Ibutton?.setOnTouchListener(this)
                infovec[i] = numRand[i]
                Ibtns[i] = Ibutton
                info[i] = Bloque(Ibtns[i], numRand[i], i)
                info[i]?.Update()
                info[i]?.ColumnayFila()
                Tablero[Ibutton] = info[i]
            }
        }
        movidasTotal.text = "Lleva ${movs.size} movidas"
        if(GalleryFragment.switch==true){
            val imagen = view?.findViewById<ImageView>(R.id.imageView2)
            imagen?.setImageBitmap(GalleryFragment.bit)

        }

    }

    private fun IBSelected(event: MotionEvent?, casillero: ImageButton): Boolean {

        val pCount = event?.pointerCount

        for(i in 0 until pCount!!){
            when(event.action){
                MotionEvent.ACTION_DOWN ->{
                    pcountinit = event.getX(i)
                    pcountinit2 = event.getY(i)
                }
                MotionEvent.ACTION_UP->{
                    if(movArriba == true) {
                        MUp(casillero)
                        movArriba = false
                    }
                    if(movBajo== true) {
                        MDown(casillero)
                        movBajo = false
                    }
                    if(movIz == true) {
                        MovIzquierda(casillero)
                        movIz = false
                    }
                    if(movDe== true) {
                        MovDerecha(casillero)

                        movDe = false
                    }
                    vector()
                    if(movs.size!=0)Ganado()
                }
                MotionEvent.ACTION_MOVE->{
                    if(movDe==false && movArriba==false && movIz==false && movBajo==false) {
                        if ((event.getY(i) - pcountinit2).absoluteValue <= casillero.height && (event.getX(i) - pcountinit).absoluteValue > casillero.width) {
                            if (event.getX(i) > pcountinit) movDe = true
                            if (event.getX(i) < pcountinit) movIz = true
                        }
                        if ((event.getX(i) - pcountinit).absoluteValue <= casillero.width && (event.getY(i) - pcountinit2).absoluteValue > casillero.height ) {
                            if (event.getY(i) < pcountinit2) movArriba = true
                            if (event.getY(i) > pcountinit2) movBajo = true
                        }
                    }
                }
            }
        }

        return true
    }
    private fun MDown(boton: ImageButton){
        var arreglo= Array(4, { indice -> indice})
        var seleccionada= Array(4, { indice -> indice})
        var j = 1
        for(i in 0 until Tablero.size){
            if(Tablero[boton]?.columna == Tablero[Ibtns[i]]?.columna){
                if(j==4) j=0
                seleccionada[j] = i
                arreglo[j] = Tablero[Ibtns[i]]?.cambio!!
                j++

            }
        }
        elegida.push(Movimientos(seleccionada[1],seleccionada[2],seleccionada[3],seleccionada[0]))
        movs.push(Movimientos(arreglo[1],arreglo[2],arreglo[3],arreglo[0]))
        movidasTotal.text = "Lleva ${movs.size} movidas"
        j=0
        for(i in 0 until Tablero.size){
            if(Tablero[boton]?.columna == Tablero[Ibtns[i]]?.columna) {
                Tablero[Ibtns[i]]?.cambio(arreglo[j])
                Tablero[Ibtns[i]]?.Update()
                j++

            }
        }
    }
    private fun MUp(boton: ImageButton){
        var arreglo= Array(4, { indice -> indice})
        var seleccionada= Array(4, { indice -> indice})
        var j = 3
        for(i in 0 until Tablero.size){
            if(Tablero[boton]?.columna == Tablero[Ibtns[i]]?.columna){
                if(j==4) j=0
                seleccionada[j] = i
                arreglo[j] = Tablero[Ibtns[i]]?.cambio!!
                j++
            }
        }
        elegida.push(Movimientos(seleccionada[3],seleccionada[0],seleccionada[1],seleccionada[2]))
        movs.push(Movimientos(arreglo[3],arreglo[0],arreglo[1],arreglo[2]))
        movidasTotal.text = "Lleva ${movs.size} movidas"
        j=0
        for(i in 0 until Tablero.size){
            if(Tablero[boton]?.columna == Tablero[Ibtns[i]]?.columna) {
                Tablero[Ibtns[i]]?.cambio(arreglo[j])
                Tablero[Ibtns[i]]?.Update()
                j++

            }
        }
    }
    private fun MovIzquierda(boton: ImageButton){
        var arreglo= Array(4, { indice -> indice})
        var seleccionada= Array(4, { indice -> indice})
        var j=3
        for(i in 0 until Tablero.size){
            if(Tablero[boton]?.fila == Tablero[Ibtns[i]]?.fila){
                if(j==4) j=0
                seleccionada[j] = i
                arreglo[j] = Tablero[Ibtns[i]]?.cambio!!
                j++
            }
        }
        elegida.push(Movimientos(seleccionada[3],seleccionada[0],seleccionada[1],seleccionada[2]))
        movs.push(Movimientos(arreglo[3],arreglo[0],arreglo[1],arreglo[2]))
        movidasTotal.text = "Lleva ${movs.size} movidas"
        j=0
        for(i in 0 until Tablero.size){
            if(Tablero[boton]?.fila == Tablero[Ibtns[i]]?.fila) {
                Tablero[Ibtns[i]]?.cambio(arreglo[j])
                Tablero[Ibtns[i]]?.Update()
                j++

            }
        }
    }
    private fun MovDerecha(boton: ImageButton){
        var arreglo= Array(4, { indice -> indice})
        var seleccionada= Array(4, { indice -> indice})
        var j = 1
        for(i in 0 until Tablero.size){
            if(Tablero[boton]?.fila == Tablero[Ibtns[i]]?.fila){
                if(j==4) j=0
                seleccionada[j] = i
                arreglo[j] = Tablero[Ibtns[i]]?.cambio!!
                j++
            }
        }
        elegida.push(Movimientos(seleccionada[1],seleccionada[2],seleccionada[3],seleccionada[0]))
        movs.push(Movimientos(arreglo[1],arreglo[2],arreglo[3],arreglo[0]))
        movidasTotal.text = "Lleva ${movs.size} movidas"
        j=0
        for(i in 0 until Tablero.size){
            if(Tablero[boton]?.fila == Tablero[Ibtns[i]]?.fila) {
                Tablero[Ibtns[i]]?.cambio(arreglo[j])
                Tablero[Ibtns[i]]?.Update()
                j++

            }
        }
    }

    private fun FisherYates(): Array<Int> {
        var arreglo = Array(16, { indice -> indice})
        for(i in arreglo.size-1 downTo  1){
            var j = Random.nextInt(1+i)
            var aux = arreglo[i]
            arreglo[i] = arreglo[j]
            arreglo[j] = aux
        }
        return arreglo
    }
    private fun Ganado(){
        for(i in 0 until Tablero.size){
            if(Tablero[Ibtns[i]]?.pos !=  Tablero[Ibtns[i]]?.cambio)break
            if(i == Tablero.size-1){
                movidasTotal.text = "Esta todo en orden, logrado con ${movs.size} movidas"
                crono.stop()
            }
        }

    }
    private fun MovAtras(){
        if(movs.empty() && elegida.empty()){
            Toast.makeText(activity,"No hay movimientos anteriores", Toast.LENGTH_SHORT).show()
            return
        }
        Tablero[Ibtns[elegida.peek().getn1()]]?.cambio = movs.peek().getn1()
        Tablero[Ibtns[elegida.peek().getn2()]]?.cambio = movs.peek().getn2()
        Tablero[Ibtns[elegida.peek().getn3()]]?.cambio = movs.peek().getn3()
        Tablero[Ibtns[elegida.peek().getn4()]]?.cambio = movs.peek().getn4()
        Tablero[Ibtns[elegida.peek().getn1()]]?.Update()
        Tablero[Ibtns[elegida.peek().getn2()]]?.Update()
        Tablero[Ibtns[elegida.peek().getn3()]]?.Update()
        Tablero[Ibtns[elegida.peek().getn4()]]?.Update()
        elegida.pop()
        movs.pop()
        movidasTotal.text = "Lleva ${movs.size} movidas"
        vector()
    }
    private fun ResetVal(){
        movs.clear()
        elegida.clear()
        movidasTotal.text = "Lleva ${movs.size} movidas"
        val numRand = FisherYates()
        for (i in 0 until Ibtns.size){
            Tablero[Ibtns[i]]?.cambio(numRand[i])
            Tablero[Ibtns[i]]?.Update()
        }
        crono.base = SystemClock.elapsedRealtime()
        tiempo = SystemClock.elapsedRealtime()
        crono.start()
        vector()
    }
    private fun OrdenAutomatico(){
        movs.clear()
        elegida.clear()
        movidasTotal.text = "Ordenado Automático"
        for (i in 0 until Ibtns.size){
            Tablero[Ibtns[i]]?.cambio(i)
            Tablero[Ibtns[i]]?.Update()
        }
        vector()

    }
    private fun vector(){
        for(i in 0 until Ibtns.size){
            infovec[i] = info[i]?.cambio

        }
    }
    private fun imagenCompleta(){
       val intent = Intent(activity?.baseContext, ImagenReal::class.java)

        startActivity(intent)
    }
    override fun onClick(v: View?) {

        if(v?.id ==R.id.resetear)ResetVal()
        if(v?.id ==R.id.atras)MovAtras()
        if(v?.id ==R.id.orden)OrdenAutomatico()
        if(v?.id ==R.id.original)imagenCompleta()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("stack", movs)
        outState.putSerializable("elegida", elegida)
        outState.putSerializable("infovec",infovec)
        outState.putSerializable("tiempo",tiempo)




    }

}

