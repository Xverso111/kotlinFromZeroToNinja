package com.javaday.domain

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class TweetQueryTest {

    @Test
    fun `should throw an error when name is empty`() {
        assertThatThrownBy {
            TweetQuery(
                "",
                emptyList()
            )
        }.hasMessage("la cosa no debe ser vacia")
    }
}
