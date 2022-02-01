package com.example.pokebrowser.extensions

fun String.getPokemonFromRoute(): String {
    if(this.isEmpty()) return ""

    val splitRoute = this.split('/')
    if(splitRoute.size < 2) return ""

    return splitRoute[1]
}
