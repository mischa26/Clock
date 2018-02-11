package mischa.arcillas.com.clock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var getResult: String = intent!!.getStringExtra("extra")

        var service: Intent = Intent(context, RingtoneService::class.java)
        service.putExtra("extra", getResult)
        context!!.startService(service)
    }
}
