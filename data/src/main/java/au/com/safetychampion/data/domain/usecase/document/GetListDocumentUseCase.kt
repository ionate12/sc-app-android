package au.com.safetychampion.data.domain.usecase.document

import au.com.safetychampion.data.data.document.IDocumentRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.domain.models.TierType
import au.com.safetychampion.data.domain.models.document.Document

class GetListDocumentUseCase(
    private val repository: IDocumentRepository
) {
    /**
     * @param userId the current user id.
     * @param tierType the current tier type
     */
    suspend operator fun invoke(
        userId: String? = null,
        tierType: TierType
    ): Result<List<Document>> {
        return repository.list()
            .flatMap { docs ->
                Result.Success(
                    if (tierType == TierType.T3) {
                        removeTier4Allocated(docs)
                    } else {
                        docs
                    }
                )
            }
    }

    /**
     * Maintain the documents by add all T4 allocated tier id into allocationList and remove it from document list.
     */
    private fun removeTier4Allocated(documents: List<Document>): List<Document> {
        val allocatedTier4Docs = documents.filter { it.tier?.type == TierType.T4 && it.allocationOf != null }
        allocatedTier4Docs.forEach { docT4 ->
            documents.firstOrNull { it._id == docT4.allocationOf?._id } // 1a. Find the document has same id with docT4 allocated.
                ?.apply { allocationList.add(docT4.tier?._id ?: "") } // 1b. Add the T4 tier id into document workplace ids list
        }

        // 2. make the document list mutable and remove all allocatedTier4Docs
        return documents.toMutableList().apply { removeAll(allocatedTier4Docs) }
    }
}
