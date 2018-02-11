package mischa.arcillas.com.clock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var alarmTimePicker: TimePicker
    lateinit var alarmManager: AlarmManager
    lateinit var context: Context
    lateinit var setAlarm: Button
    lateinit var stopAlarm: Button
    lateinit var button: Button
    lateinit var textView: TextView
    lateinit var pendinIntent: PendingIntent

    var hour: Int = 0
    var min: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.context = this
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmTimePicker = findViewById<TimePicker>(R.id.timePick)
        setAlarm = findViewById<Button>(R.id.setAlarm)
        stopAlarm = findViewById<Button>(R.id.stopAlarm)
        button = findViewById<Button>(R.id.button)
        textView = findViewById<TextView>(R.id.textView)
        var calendar: Calendar = Calendar.getInstance()
        var myIntent: Intent = Intent(this, AlarmReceiver::class.java)

        setAlarm.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.hour)
                    calendar.set(Calendar.MINUTE, alarmTimePicker.minute)
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    hour = alarmTimePicker.hour
                    min = alarmTimePicker.minute
                }else{
                    calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.currentHour)
                    calendar.set(Calendar.MINUTE, alarmTimePicker.currentMinute)
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    hour = alarmTimePicker.currentHour
                    min = alarmTimePicker.currentMinute
                }
                var hr_str: String = hour.toString()
                var min_str: String = min.toString()
                if(hour > 12){
                    hr_str = (hour - 12).toString()
                }
                if (min < 10) {
                    min_str = "0$min"
                }
                set_alarm_text("Alarm set to $hr_str : $min_str")
                myIntent.putExtra("extra","on")
                pendinIntent = PendingIntent.getBroadcast(this@MainActivity, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendinIntent)
                }
            }

        })

        stopAlarm.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                set_alarm_text("Alarm off")
                pendinIntent = PendingIntent.getBroadcast(this@MainActivity, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                alarmManager.cancel(pendinIntent)
                myIntent.putExtra("extra","off")
                sendBroadcast(myIntent)
            }
        })

        button.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var intent: Intent = Intent(this@MainActivity, Stopwatch::class.java)
                startActivity(intent)
            }
        })
    }

    private fun set_alarm_text(s: String) {
        textView.setText(s)
    }
}
