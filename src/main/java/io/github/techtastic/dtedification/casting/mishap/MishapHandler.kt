package io.github.techtastic.dtedification.casting.mishap

import at.petrak.hexcasting.api.casting.mishaps.Mishap

object MishapHandler {
    fun throwMishap(mishap: Mishap) {
        throw mishap
    }
}