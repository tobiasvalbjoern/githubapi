package dk.e5pme.githupapi

import org.json.JSONArray

/**
 * Created by Kittinun Vantasin on 8/29/15.
 */

fun JSONArray.asSequence(): Sequence<Any> {
    return object : Sequence<Any> {

        override fun iterator() = object : Iterator<Any> {

            val it = (0 until this@asSequence.length()).iterator()

            override fun next(): Any {
                val i = it.next()
                return this@asSequence.get(i)
            }

            override fun hasNext() = it.hasNext()

        }
    }
}


