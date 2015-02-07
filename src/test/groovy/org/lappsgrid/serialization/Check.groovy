package org.lappsgrid.serialization

import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
class Check {
    void check(def actual, def expected) {
        assertEquals "Expected: ${expected.toString()}\nActual: ${actual.toString()}", actual, expected
    }
}
