package musubidevs.android.greenwich

import android.app.Application
import com.jaredrummler.cyanea.Cyanea

class GreenwichApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Cyanea.init(this, resources)
    }
}