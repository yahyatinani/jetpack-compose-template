package com.why.template.compose

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeExactly

class NothingTest : FreeSpec(
    {
        "nothing" {
            2 + 2 shouldBeExactly 4
        }
    }
)
