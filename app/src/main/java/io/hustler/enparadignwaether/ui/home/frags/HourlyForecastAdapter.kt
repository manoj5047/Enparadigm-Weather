package io.hustler.enparadignwaether.ui.home.frags

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.hustler.enparadignwaether.R
import io.hustler.enparadignwaether.data.model.Daily
import io.hustler.enparadignwaether.data.model.Hourly
import io.hustler.enparadignwaether.utils.CalenderUtils
import io.hustler.enparadignwaether.utils.constants.Constants
import kotlinx.android.synthetic.main.forecast_item_rv.view.*
import kotlinx.android.synthetic.main.hourly_rv_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class HourlyForecastAdapter : RecyclerView.Adapter<HourlyForecastViewholder>() {
    private var hoursList = ArrayList<Hourly>()
    fun addData(newHours: ArrayList<Hourly>) {
        this.hoursList = newHours
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewholder {
        return HourlyForecastViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.hourly_rv_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int = hoursList.size

    override fun onBindViewHolder(holder: HourlyForecastViewholder, position: Int) {
        val item = hoursList[position]
        holder.itemView.tv_rv_item_hourly_hour.text =
            CalenderUtils.convertDate(item.dt, CalenderUtils.DATE_FORMAT_5)
        holder.itemView.tv_rv_item_hourly_temp.text =
            item.temp.toInt().toString() + holder.itemView.context.getString(R.string.degree)
        Picasso.get().load(Constants.getIconImageUrl(item.weather[0].icon))
            .into(holder.itemView.iv_rv_item_hourly_type)
    }

}

class HourlyForecastViewholder(itemView: View) : RecyclerView.ViewHolder(itemView)


class DailyForecastAdapter : RecyclerView.Adapter<DailyForecastViewholder>() {
    private var hoursList = ArrayList<Daily>()
    fun addData(newHours: ArrayList<Daily>) {
        this.hoursList = newHours
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewholder {

        return DailyForecastViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.hourly_rv_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int = hoursList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DailyForecastViewholder, position: Int) {
        val item = hoursList[position]
        val cal = Calendar.getInstance()
        cal.time = Date(item.dt)
        val dayofweek: Int = cal.get(Calendar.DAY_OF_WEEK)

        holder.itemView.tv_rv_item_hourly_hour.text =
            CalenderUtils.getDayOfWeekFromDate(item.sunrise) + "\n" + CalenderUtils.convertDate(
                item.dt,
                CalenderUtils.DATE_FORMAT_6
            )
        holder.itemView.tv_rv_item_hourly_temp.text =
            item.temp.max.toInt()
                .toString() + holder.itemView.context.getString(R.string.degree) + " / " + item.temp.min.toInt()
                .toString() + holder.itemView.context.getString(R.string.degree)
        Picasso.get().load(Constants.getIconImageUrl(item.weather[0].icon))
            .into(holder.itemView.iv_rv_item_hourly_type)
    }

}

class DailyForecastViewholder(itemView: View) : RecyclerView.ViewHolder(itemView)


class TodayForecastAdapter : RecyclerView.Adapter<TodayForecastViewHolder>() {
    private var headsList = ArrayList<String>()
    private var dataList = ArrayList<String>()
    lateinit var typedArray :TypedArray

    fun addData(headslist: ArrayList<String>, dataList: ArrayList<String>,typedArray: TypedArray) {
        this.headsList = headslist
        this.dataList = dataList
        this.typedArray = typedArray
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayForecastViewHolder {
        return TodayForecastViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.forecast_item_rv, parent, false
            )
        )
    }

    override fun getItemCount(): Int =
        dataList.size


    override fun onBindViewHolder(holder: TodayForecastViewHolder, position: Int) {
        holder.itemView.tv_rv_item_today_forecast_head.text = headsList[position]
        holder.itemView.tv_rv_item_today_forecast_message.text = dataList[position]
        holder.itemView.iv_rv_item_today_forecast_image.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context,typedArray.getResourceId(position,R.drawable.ic_summer)))
    }

}

class TodayForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
