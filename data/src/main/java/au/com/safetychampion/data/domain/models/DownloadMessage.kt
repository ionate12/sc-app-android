package au.com.safetychampion.data.domain.models

class DownloadMessage(
    var _id: String? = null,
    var task: String? = null,
    var contentLength: Long = 0,
    var increasingBytes: Long = 0
) {
    override fun toString(): String {
        return "id: $_id | task: $task | contentLength: $contentLength | increasingBytes: $increasingBytes"
    }

    companion object {
        const val TASK_START = "start"
        const val FETCH_START = "fetch_start"
        const val FETCH_PROGRESS = "fetch_progress"
        const val TASK_END = "end"
    }
}
