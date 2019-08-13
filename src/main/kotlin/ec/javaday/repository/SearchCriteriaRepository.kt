package ec.javaday.repository

//exposed
//libreria para sql tipados
import ec.javaday.domain.DateRange
import ec.javaday.domain.SearchCriteria
import ec.javaday.exposed.jsonb
import ec.javaday.moshiWithAdapters
import ec.javaday.repository.SearchCriteriaTable.id
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class SearchCriteriaRepository {

    //transaction de bdd
    fun insert(searchCriteria: SearchCriteria) =
        transaction {
            SearchCriteriaTable.insert {
                it[id] = UUID.randomUUID()
                //tipado
                it[name] = searchCriteria.name
                it[hashTags] = searchCriteria.hashtags
                // ? nullable safe en compilacion
                it[startDate] = searchCriteria.dateRange?.start?.toDateTime()
                it[endDate] = searchCriteria.dateRange?.end?.toDateTime()
                it[allowRetweets] = searchCriteria.allowRetweets
            }
        } get id

    fun getById(id: UUID) =
        transaction {
            SearchCriteriaTable
                .select { SearchCriteriaTable.id eq id }
                // ResultRow A searchCriteria
                .map { it.toSearchCriteria() }
                .firstOrNull()
        }

    private fun ResultRow.toSearchCriteria() = SearchCriteria(
        this[SearchCriteriaTable.name],
        this[SearchCriteriaTable.hashTags],
        this.toDateRange(),
        this[SearchCriteriaTable.allowRetweets]
    )

    private fun ResultRow.toDateRange(): DateRange? {
        val startDate = this[SearchCriteriaTable.startDate]?.toLocalDateTime()
        val endDate = this[SearchCriteriaTable.endDate]?.toLocalDateTime()
        return if (startDate != null && endDate != null) DateRange(startDate, endDate) else null
    }

}

//ORM kind of
//object -> singleton
object SearchCriteriaTable : Table("search_criteria") {
    val id = uuid("id").primaryKey()
    val name = varchar("name", 50).uniqueIndex()
    val startDate = datetime("start_date").nullable()
    val endDate = datetime("end_date").nullable()
    val hashTags = jsonb("hash_tags", moshiWithAdapters)
    val allowRetweets = bool("allow_retweets")
}
