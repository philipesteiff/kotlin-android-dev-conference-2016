package com.devconference.stackoverflow

import com.google.gson.annotations.SerializedName

/**
 * Compilador já gera:
 * - equals()/hashCode()
 * - toString()
 * - copy()
 *
 * Para garantir consistencias no código gerado:
 * - O construtor primario precisa no mínimo um parametro.
 * - Os parametros precisam ser marcados com `val` ou `var`
 *
 */
data class StackOverflowQuestions(
    @SerializedName("items") val items: List<StackOverflowQuestion> = emptyList()
)
