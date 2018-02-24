package fluper.com.getapikeyandvalues

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_layout.view.*


class MyAdapter(val list: ArrayList<Model>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
  
   override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
      
      val v = LayoutInflater.from(parent!!.context).inflate(R.layout.row_layout, parent, false)
      return ViewHolder(v)
   }
   
   override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
      holder!!.bindFun(list[position])
      
   }
   
   override fun getItemCount(): Int = list.size
   
   
   class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      fun bindFun(m: Model) {
         
         val data=m.std_list
         val str=data.split("~")
         itemView.tv_title.text = str[0]
         itemView.tv_name.text = str[1]
      }
   }
   
   
}