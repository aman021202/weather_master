package code.aman.weatherapk

import android.app.DownloadManager
import android.graphics.Color.parseColor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min
import kotlin.text.Typography.degree

//private val JSONObject.coord: Any
  //  get() {}

class MainActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lat=intent.getStringExtra("lat")
        var long=intent.getStringExtra("long")

        Log.d("aman", long.toString())
        Log.d("aman1", lat.toString())

        getJsonData("$lat","$long")


    }
    private fun getJsonData(lat:String?, long:String?) {
        val API_KEY = "d9a733b6776c36cc2fb62af0ed65abb3"
        val queue = Volley.newRequestQueue(this)
        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_KEY}"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                setValues(response)
            },
            Response.ErrorListener { Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show() })

        queue.add(jsonRequest)

    }
           fun setValues(response: JSONObject) {
            city.text = response.getString("name")
            var lat = response.getJSONObject("coord").getString("coord.lat")
            var long = response.getJSONObject("coord").getString("coord.long")
            coordinates.text = "${lat}, ${long}"
            weather.text = response.getJSONArray("weather").getJSONObject(0).getString("main")
            var tempr = response.getJSONObject("main").getString("temp")
             //  var tempr=response.main.te.toString()
            tempr = ((((tempr).toFloat() - 273.15)).toInt()).toString()
            temp.text = "${tempr}°C"

            var mintemp = response.getJSONObject("main").getString("temp_min")
            mintemp = ((((mintemp).toFloat() - 273.15)).toInt()).toString()
            min_temp.text = mintemp + "°C"

            var maxtemp = response.getJSONObject("main").getString("temp_max")
            maxtemp = ((ceil((maxtemp).toFloat() - 273.15)).toInt()).toString()
            max_temp.text = maxtemp + "°C"

            pressure.text = response.getJSONObject("main").getString("pressure")
            humidity.text = response.getJSONObject("main").getString("humidity") + "%"
            wind.text = response.getJSONObject("wind").getString("speed")
            degree.text = "Degree :" + response.getJSONObject("wind").getString("deg") + "°"
            // gust.text="Gust :"+response.getJSONObject("wind").getString("gust")

        }
    }




  






