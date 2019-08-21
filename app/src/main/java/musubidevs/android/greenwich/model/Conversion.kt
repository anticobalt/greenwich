package musubidevs.android.greenwich.model

data class Conversion(
    var sourceTimestamp: SourceTimestamp = SourceTimestamp(),
    var targetTimestamp: TargetTimestamp = TargetTimestamp()
) {
    fun updateTimestamp(timestamp: Timestamp) {
        when (timestamp) {
            is SourceTimestamp -> {
                sourceTimestamp = timestamp
                targetTimestamp = targetTimestamp.withSource(sourceTimestamp)
            }
            is TargetTimestamp -> targetTimestamp = timestamp
            else -> throw ClassNotFoundException("${timestamp.javaClass.name} is not a valid Timestamp type")
        }
    }
}