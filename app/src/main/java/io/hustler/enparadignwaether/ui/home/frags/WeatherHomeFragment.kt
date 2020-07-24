package io.hustler.enparadignwaether.ui.home.frags

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.hustler.enparadignwaether.R
import io.hustler.enparadignwaether.data.model.Cities
import io.hustler.enparadignwaether.data.model.CitiesItem
import io.hustler.enparadignwaether.data.model.ResWeatherData
import io.hustler.enparadignwaether.di.components.FragmentComponent
import io.hustler.enparadignwaether.ui.base.BaseFragment
import io.hustler.enparadignwaether.ui.home.HomeViewModel
import io.hustler.enparadignwaether.utils.CalenderUtils
import io.hustler.enparadignwaether.utils.MessageUtils
import io.hustler.enparadignwaether.utils.common.Status
import io.hustler.enparadignwaether.utils.constants.Constants
import kotlinx.android.synthetic.main.home_fragment.*
import java.io.IOException
import java.io.InputStream


class WeatherHomeFragment : BaseFragment<HomeViewModel>() {
    private lateinit var hourlyForecastAdapter: HourlyForecastAdapter
    private lateinit var dailyForecastAdapter: DailyForecastAdapter
    private lateinit var todayForecastAdapter: TodayForecastAdapter
    private lateinit var citiesData: Cities
    private var currentCityIndex = 0

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
        getCitiesData()
        setupHourlyForeCastAdapter()
        setupDailyForeCastAdapter()
        setupForeCastAdapter()
        tv_frag_home_current_city.setOnClickListener {
            showCitiesDialog()
        }
        weather_refrehser.setOnRefreshListener {
           if(null == citiesData){
              loadNewCiyData(currentCityIndex)
           }else{
               getCitiesData()
           }
        }

    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.isMorningLiveData.observe(this, Observer {
            if (it) {
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
        })
        viewModel.cityNameLiveData.observe(this, Observer {
            tv_frag_home_current_city.text = it
        })
        viewModel.weatherLiveData.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    weather_refrehser.isRefreshing = false

                    MessageUtils.showDismissableSnackBar(
                        this.view,
                        (it.data as ResWeatherData).timezone
                    )
                    inflateData(it.data)
                }
                Status.ERROR -> {
                    weather_refrehser.isRefreshing = false

                    MessageUtils.showDismissableSnackBar(this.view, it.toString())
                }
                Status.LOADING -> {
                    weather_refrehser.isRefreshing = true
                    MessageUtils.showDismissableSnackBar(
                        this.view,
                        getString(R.string.fetching_weather_updates)
                    )

                }
                Status.UNKNOWN -> {

                }
            }
        })

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


    private fun showCitiesDialog() {
        val citiesList: ArrayList<CitiesItem> = if (citiesData == null) {
            getCitiesData()!!.cities
        } else {
            citiesData.cities
        }
        val alertDialog1: AlertDialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.select_your_city))
        builder.setSingleChoiceItems(
            Array(citiesList.size) { index -> citiesList[index].admin }, -1
        ) { dialog, which ->
            loadNewCiyData(which)
            dialog.dismiss()
        }
        alertDialog1 = builder.create()
        alertDialog1.show()
    }

    @SuppressLint("SetTextI18n")
    private fun inflateData(data: ResWeatherData) {
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

    private fun getCitiesData(): Cities? {
        val json: String?
        json = try {
            val `is`: InputStream = context!!.assets.open("CitiesData.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        this.citiesData = Gson().fromJson(json, Cities::class.java)
        loadNewCiyData(0)
        currentCityIndex = 0
        return citiesData
    }

    private fun loadNewCiyData(index: Int) {
        val city = citiesData.cities[index]
        viewModel.getWeatherData(
            city.lat.toDouble(),
            city.lng.toDouble()
        )
        viewModel.onCityNameChange(city.admin)
        currentCityIndex = index
    }

}

