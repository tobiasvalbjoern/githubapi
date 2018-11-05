package dk.e5pme.githupapi
import org.json.JSONObject

/**
 * Created by Kittinun Vantasin on 8/29/15.
 */

fun JSONObject.asSequence(): Sequence<Pair<String, Any>> {
    return object : Sequence<Pair<String, Any>> {

        override fun iterator() = object : Iterator<Pair<String, Any>> {

            val it = keys()

            override fun next(): Pair<String, Any> {
                val key = it.next()
                val value = get(key)
                return (key to value)
            }

            override fun hasNext() = it.hasNext()

        }

    }
}
