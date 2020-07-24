package io.hustler.enparadignwaether.ui.home.frags

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.hustler.enparadignwaether.R
import io.hustler.enparadignwaether.data.model.ResWeatherData
import io.hustler.enparadignwaether.di.components.FragmentComponent
import io.hustler.enparadignwaether.ui.base.BaseFragment
import io.hustler.enparadignwaether.ui.home.HomeViewModel
import io.hustler.enparadignwaether.utils.CalenderUtils
import io.hustler.enparadignwaether.utils.MessageUtils
import io.hustler.enparadignwaether.utils.common.Status
import io.hustler.enparadignwaether.utils.constants.Constants
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*
import kotlin.collections.ArrayList


class WeatherHomeFragment : BaseFragment<HomeViewModel>() {
    private lateinit var hourlyForecastAdapter: HourlyForecastAdapter
    private lateinit var dailyForecastAdapter: DailyForecastAdapter
    private lateinit var todayForecastAdapter: TodayForecastAdapter

    companion object {
        const val FRAG_TAG: String = "WEATHER_FRAG_TAG"

        fun newInstance(): WeatherHomeFragment {

            return WeatherHomeFragment()
        }
    }

    override fun provideLayoutId(): Int = R.layout.home_fragment

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.injectHomeFragment(this)
    }

    override fun setupView(view: View) {
        setupHourlyForeCastAdapter()
        setupDailyForeCastAdapter()
        setupForeCastAdapter()
    }

    private fun setupHourlyForeCastAdapter() {
        rv_frag_home_hourly_forecast.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        hourlyForecastAdapter = HourlyForecastAdapter()
        rv_frag_home_hourly_forecast.adapter = hourlyForecastAdapter
    }

    private fun setupDailyForeCastAdapter() {
        rv_frag_home_weekly_forecast.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        dailyForecastAdapter = DailyForecastAdapter()
        rv_frag_home_weekly_forecast.adapter = dailyForecastAdapter
    }

    private fun setupForeCastAdapter() {
        rv_frag_home_weather_data.layoutManager =
            GridLayoutManager(requireContext(), 2)
        todayForecastAdapter = TodayForecastAdapter()
        rv_frag_home_weather_data.adapter = todayForecastAdapter
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.weatherLiveData.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    MessageUtils.showDismissableSnackBar(
                        this.view,
                        (it.data as ResWeatherData).timezone
                    )
                    inflateData(it.data)
                }
                Status.ERROR -> {
                    MessageUtils.showDismissableSnackBar(this.view, it.toString())
                }
                Status.LOADING -> {
                    MessageUtils.showDismissableSnackBar(this.view, it.toString())

                }
                Status.UNKNOWN -> {

                }
            }
        })

    }

    @SuppressLint("SetTextI18n")
    private fun inflateData(data: ResWeatherData) {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (currentHour in 6..18) {
            weatherRootView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.day
                )
            )
            requireActivity().window.statusBarColor =
                (ContextCompat.getColor(requireContext(), R.color.day))
        } else {
            weatherRootView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.night
                )
            )
            requireActivity().window.statusBarColor =
                (ContextCompat.getColor(requireContext(), R.color.night))

        }
        Picasso.get().load(Constants.getIconImageUrl(data.current.weather[0].icon))
            .into(weatherIcon)
        tv_frag_home_current_temp.text =
            data.current.temp.toInt().toString() + getString(R.string.degree)
        tv_frag_home_current_sub_temp.text =
            data.current.weather[0].main + " " + data.current.temp.toInt().toString() +
                    " / " +
                    data.current.feels_like.toInt().toString() + getString(R.string.degree) + " C"
        hourlyForecastAdapter.addData(data.hourly)
        dailyForecastAdapter.addData(data.daily)
        val headList = ArrayList<String>().apply {
            add("Sunrise")
            add("Sunset")
            add("Temperature")
            add("FeelsLike")
            add("Pressure")
            add("Humidity")
            add("UV Index")
            add("Wind Speed")
        }
        val dataList = ArrayList<String>().apply {
            add(
                CalenderUtils.convertDate(
                    data.current.sunrise.toLong(),
                    CalenderUtils.DATE_FORMAT_1
                )
            )
            add(
                CalenderUtils.convertDate(
                    data.current.sunset.toLong(),
                    CalenderUtils.DATE_FORMAT_1
                )
            )
            add(data.current.temp.toString() + " " + getString(R.string.degree) + "  C")
            add(data.current.feels_like.toString() + " " + getString(R.string.degree) + "  C")
            add(data.current.pressure.toString() + " hPa")
            add(data.current.humidity.toString() + " %")
            add(data.current.uvi.toString())
            add(data.current.wind_speed.toString() + " km/h")
        }
        todayForecastAdapter.addData(
            headList,
            dataList,
            resources.obtainTypedArray(R.array.weatherIcons)
        )


    }
}