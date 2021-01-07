package com.why.composetemplate

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeExactly

class NothingTest : FreeSpec({
    "nothing" {
        4 shouldBeExactly 2 + 2
    }
})
