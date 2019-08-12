package ec.javaday.repository

import ec.javaday.domain.DateRange
import ec.javaday.domain.SearchCriteria
import ec.javaday.exposed.jsonb
import ec.javaday.moshi
import ec.javaday.repository.SearchCriteriaTable.allowRetweets
import ec.javaday.repository.SearchCriteriaTable.endDate
import ec.javaday.repository.SearchCriteriaTable.hashTags
import ec.javaday.repository.SearchCriteriaTable.id
import ec.javaday.repository.SearchCriteriaTable.name
import ec.javaday.repository.SearchCriteriaTable.startDate
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class SearchCriteriaRepository {

    fun insert(query: SearchCriteria) =
        transaction {
            SearchCriteriaTable.insert {
                it[id] = UUID.randomUUID()
                it[name] = query.name
                it[hashTags] = query.hashTags
                it[startDate] = query.dateRange?.start?.toDateTime()
                it[endDate] = query.dateRange?.end?.toDateTime()
                it[allowRetweets] = query.allowRetweets
            }
        } get id

    fun getById(id: UUID) =
        transaction {
            SearchCriteriaTable
                .select { SearchCriteriaTable.id eq id }
                .map { it.toSearchCriteria() }
                .firstOrNull()
        }

    private fun ResultRow.toSearchCriteria() = SearchCriteria(
        this[name],
        this[hashTags],
        this.toDateRange(),
        this[allowRetweets]
    )

    private fun ResultRow.toDateRange(): DateRange? {
        val startDate = this[startDate]?.toLocalDateTime()
        val endDate = this[endDate]?.toLocalDateTime()
        return if (startDate != null && endDate != null) DateRange(startDate, endDate) else null
    }
}

object SearchCriteriaTable: Table("search_criteria") {
    val id = uuid("id").primaryKey()
    val name = varchar("name", 50).uniqueIndex()
    val startDate = datetime("start_date").nullable()
    val endDate = datetime("end_date").nullable()
    val hashTags = jsonb("hash_tags", moshi)
    val allowRetweets = bool("allow_retweets")
}
