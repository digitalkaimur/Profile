package fluper.com.getapikeyandvalues

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject



class MainActivity : AppCompatActivity() {
   
   private lateinit var list:ArrayList<Model>
   private lateinit var myAdapter:MyAdapter
   
   
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
      list= ArrayList()
   
      list_student.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
   
      initJson()
   }
   
   private fun initJson() {
      AndroidNetworking.get("https://raw.githubusercontent.com/digitalkaimur/DPSApp/master/student_details")
       .build()
       .getAsJSONObject(object : JSONObjectRequestListener {
          override fun onResponse(response: JSONObject) {
             val arr=response.getJSONArray("result")
             for(i in 0 until arr.length()){
                val obj=arr.getJSONObject(i)
                Log.d("TAGS",obj.toString())
                parseJson(obj)
             }
          }
       
          override fun onError(error: ANError) {
             Log.d("TAGS",error.toString())
          }
       })
   }
   private fun parseJson(data: JSONObject?) {
      if (data != null) {
         val it = data.keys()
         while (it.hasNext()) {
            val key = it.next()
            try {
               if (data.get(key) is JSONArray) {
                  val arry = data.getJSONArray(key)
                  val size = arry.length()
                  for (i in 0 until size) {
                     parseJson(arry.getJSONObject(i))
                  }
               } else if (data.get(key) is JSONObject) {
                  parseJson(data.getJSONObject(key))
               } else {
                  list.add(Model(key+"~"+data.optString(key)))
                  Log.d("TAGS", "" + key + " : " + data.optString(key))
               }
            } catch (e: Throwable) {
               e.printStackTrace()
            }
         }
      }
   
      Log.d("TAGS", "List"+list.toString())
   
      myAdapter = MyAdapter(list)
      list_student.adapter = myAdapter
   }
}
