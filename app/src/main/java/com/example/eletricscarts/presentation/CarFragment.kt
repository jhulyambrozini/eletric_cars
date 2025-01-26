package com.example.eletricscarts.presentation

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewCacheExtension
import com.example.eletricscarts.R
import com.example.eletricscarts.domain.Car
import com.example.eletricscarts.presentation.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONTokener
import java.net.HttpURLConnection
import java.net.URL

class CarFragment : Fragment() {
    lateinit var btnRedirect: FloatingActionButton
    lateinit var listCars: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var noInternetImage: ImageView
    lateinit var noIntenertText: TextView

    var carsArray: ArrayList<Car> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupListeners()


    }

    override fun onResume() {
        super.onResume()
        if(checkConnectForInternet(context)){
            callService()

        } else {
            emptyState()
        }
    }

    fun emptyState(){
        progressBar.visibility = View.GONE
        listCars.visibility = View.GONE
        noInternetImage.visibility = View.VISIBLE
        noIntenertText.visibility = View.VISIBLE
    }

    fun setupListeners() {
        btnRedirect.setOnClickListener {
            startActivity(Intent(context, CalcularAutonomiaActivity::class.java))

        }
    }

    fun callService() {
        GetCarInfos().execute("https://igorbag.github.io/cars-api/cars.json")
        progressBar.visibility = View.VISIBLE
    }

    fun setupList() {
        listCars.apply {
            visibility = View.VISIBLE
            val adapter = CarAdapter(carsArray)
            listCars.adapter = adapter
        }
    }

    fun setupView(view: View) {
        btnRedirect = view.findViewById(R.id.fav_calcular)
        listCars = view.findViewById(R.id.rv_list_cars)
        progressBar = view.findViewById(R.id.pb_loader)
        noInternetImage = view.findViewById(R.id.iv_empty_state)
        noIntenertText = view.findViewById(R.id.tv_no_wifi)
    }

    fun checkConnectForInternet(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return  when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
         } else {
            val network = connectivityManager.activeNetworkInfo ?: return false
            return network.isConnected
        }
    }

    inner class GetCarInfos : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            var urlConnection: HttpURLConnection? = null

            try {
                var urlBase = URL(url[0])
                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                )
                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    var response = urlConnection.inputStream.bufferedReader().use { it.readText() }
                    publishProgress(response)
                } else {
                    Log.e("ERROR", "SERVIÇO INDISPONIVEL....")
                }
            } catch (e: Exception) {
                Log.e("ERROR", "ERROR AO INICIAR PROCESSAMENTO.... $e")
            } finally {
                urlConnection?.disconnect()
            }

            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {

                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray
                for (i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getString("id")
                    val preco = jsonArray.getJSONObject(i).getString("preco")
                    val bateria = jsonArray.getJSONObject(i).getString("bateria")
                    val potencia = jsonArray.getJSONObject(i).getString("potencia")
                    val recarga = jsonArray.getJSONObject(i).getString("recarga")
                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")

                    val car = Car(
                        id = id.toInt(),
                        preco = preco,
                        potencia = potencia,
                        bateria = bateria,
                        recarga = recarga,
                        urlFoto = urlPhoto
                    )

                    carsArray.add(car)

                }
                progressBar.visibility = View.GONE
                noInternetImage.visibility = View.GONE
                noIntenertText.visibility = View.GONE
                setupList()

            } catch (error: Exception) {
                Log.e("ERROR OA PROCESSAR ADDOS", error.toString())
            }
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }
    }


}