package ec.javaday.domain

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class SearchCriteriaTest {

    @Test
    fun `should throw an error when name is empty`() {

        assertThatThrownBy {
            SearchCriteria(
                name = "",
                hashtags = listOf("any hashtag")
            )
        }
            .hasMessage("Cannot create a SearchCriteria without a name")
    }
}
