package com.curso.puzzletoroidal.ui.home

import java.io.Serializable


class Movimientos (num1 : Int, num2 : Int, num3 : Int, num4 : Int): Serializable{
    private var n1 = num1
    private var n2 = num2
    private var n3 = num3
    private var n4 = num4

    public fun getn1(): Int {
        return n1
    }
    public fun getn2(): Int {
        return n2
    }
    public fun getn3(): Int {
        return n3
    }
    public fun getn4(): Int {
        return n4
    }
}