package com.solsticesquared.kalahari.util

/**
 * Represents a mechanism for hashing arbitrary numbers of objects.
 *
 * This method is inspired by both Python's method of hashing objects as well
 * as the Kotlin library HashKode, the latter from which the constants are
 * taken.  This method allows classes to write one-line hash code
 * implementations without needing to concern themselves with best practices
 * or explicit result values.  The implementation itself comes from Josh
 * Bloch's "Effective Java" and uses both a prime initial value as well as a
 * per-object multiplier to achieve a reasonable degree of uniqueness, in
 * addition to holding up the general contract of [Object.hashCode].
 *
 * @param fields
 *        A variable list of object to hash.
 * @param initialValue
 *        The initial value to use; this should be prime.
 * @param multiplier
 *        The per-object hash multiplier to use; this should also be prime.
 * @return A reasonably unique hash code.
 */
fun hash(vararg fields: Any?, initialValue: Int = 17, multiplier: Int = 37)
    : Int {
    var result = initialValue
    fields.forEach {
        result = (result * multiplier) + (it?.hashCode() ?: 0)
    }
    return result
}
