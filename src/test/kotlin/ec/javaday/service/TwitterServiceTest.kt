package ec.javaday.service

import ec.javaday.domain.SearchCriteria
import ec.javaday.repository.SearchCriteriaRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class TwitterServiceTest {

    @Test
    fun `should insert provided search criteria`() {
        //mockk libreria para dobles de prueba
        val searchCriteriaRepository =
            mockk<SearchCriteriaRepository>(relaxed = true)

        val searchCriteria = mockk<SearchCriteria>()

        val twitterService = TwitterService(searchCriteriaRepository)

        twitterService.createSearchCriteria(searchCriteria)

        verify(exactly = 1) { searchCriteriaRepository.insert(searchCriteria) }
    }
}
