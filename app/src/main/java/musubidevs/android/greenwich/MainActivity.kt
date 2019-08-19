package musubidevs.android.greenwich

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.concurrent.TimeUnit


data class Timestamp(val dateTimeZone: DateTimeZone) {

    // https://stackoverflow.com/a/21420765
    val offsetInHours: Long
        get() {
            val instant = DateTime.now().millis
            val offsetInMillis = dateTimeZone.getOffset(instant).toLong()
            return TimeUnit.MILLISECONDS.toHours(offsetInMillis)
        }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timestamp = Timestamp(DateTimeZone.getDefault())
        timezoneView.text = getString(R.string.utc, timestamp.offsetInHours)
    }

}
