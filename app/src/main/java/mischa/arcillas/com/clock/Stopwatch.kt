package mischa.arcillas.com.clock

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import java.util.*
import java.util.Arrays.asList
import kotlin.collections.ArrayList


class Stopwatch : AppCompatActivity() {

    lateinit var textView: TextView

    lateinit var btnStart: Button
    lateinit var btnPause: Button
    lateinit var btnReset: Button
    lateinit var btnSaveLap: Button

    lateinit var listView: ListView

    var milliSecondTime: Long = 0
    var startingTime: Long = 0
    var timeBuff: Long = 0
    var updateTime = 0L

    lateinit var handler: Handler

    var seconds: Int = 0
    var minutes: Int = 0
    var milliSeconds: Int = 0

    var listElements = arrayOf<String>()

    lateinit var listElementsArrayList: MutableList<String>
    lateinit var adapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        textView = findViewById<TextView>(R.id.textViewTime)
        btnStart = findViewById<Button>(R.id.btnStart)
        btnPause = findViewById<Button>(R.id.btnPause)
        btnReset = findViewById<Button>(R.id.btnReset)
        btnSaveLap = findViewById<Button>(R.id.btnSaveLap)
        listView = findViewById<ListView>(R.id.listview1)

        handler = Handler()

        listElementsArrayList = ArrayList<String>(Arrays.asList(*listElements))

        adapter = ArrayAdapter<String>(this@Stopwatch, android.R.layout.simple_list_item_1, listElementsArrayList)

        listView.adapter = adapter

        btnStart.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                startingTime = SystemClock.uptimeMillis()
                handler.postDelayed(runnable, 0)

                btnReset.isEnabled = false
            }
        })

        btnPause.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                timeBuff += milliSecondTime

                handler.removeCallbacks(runnable)

                btnReset.isEnabled = true
            }
        })

        btnReset.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                milliSecondTime = 0L
                startingTime = 0L
                timeBuff = 0L
                updateTime = 0L
                seconds = 0
                minutes = 0
                milliSeconds = 0

                textView.text = "00:00:00"

                listElementsArrayList.clear()

                adapter.notifyDataSetChanged()
            }
        })

        btnSaveLap.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                listElementsArrayList.add(textView.text.toString())
                adapter.notifyDataSetChanged()
            }
        })
    }

    var runnable: Runnable = object : Runnable {

        override fun run() {

            milliSecondTime = SystemClock.uptimeMillis() - startingTime

            updateTime = timeBuff + milliSecondTime

            seconds = (updateTime / 1000).toInt()

            minutes = seconds / 60

            seconds = seconds % 60

            milliSeconds = (updateTime % 1000).toInt()

            textView.text = ("" + minutes + ":"
                    + String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliSeconds))

            handler.postDelayed(this, 0)
        }

    }


}