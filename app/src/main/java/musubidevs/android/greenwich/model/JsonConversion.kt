package musubidevs.android.greenwich.model

data class JsonConversion(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val sourceUtcOffsetHour: Int,
    val sourceUtcOffsetMinute: Int,
    val targetUtcOffsetHour: Int,
    val targetUtcOffsetMinute: Int
) {


    fun convertToConversion(): Conversion {
//        val sourceTimestamp = SourceTimestamp(DateTime(year, month, day, hour, minute), DateTimeZone.UTC)
//        val dtz = DateTimeZone.UTC
////        DateTimeZone.forOffsetHoursMinutes()
        return Conversion()
    }
}