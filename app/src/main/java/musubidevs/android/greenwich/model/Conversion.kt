package musubidevs.android.greenwich.model

data class Conversion(
    val sourceTimestamp: SourceTimestamp = SourceTimestamp(),
    val targetTimestamp: TargetTimestamp = TargetTimestamp()
)