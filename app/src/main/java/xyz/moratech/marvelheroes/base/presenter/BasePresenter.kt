package xyz.moratech.marvelheroes.base.presenter

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.moratech.marvelheroes.BuildConfig
import xyz.moratech.marvelheroes.base.service.ServicesAPI
import java.io.IOException
import java.io.Serializable
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * @author Juan Mora
 * @since 1.0.0
 * 16/10/2018 05:14 PM
 *
 */
abstract class BasePresenter {

    protected val mServicesAPI = createAPI()
    protected val mNotifyLiveData: MutableLiveData<String> = MutableLiveData()
    protected val mGson = Gson()
    protected val mFailureListener = object : FailureServiceListener<String> {
        override fun onErrorResponse(message: String) {
            mNotifyLiveData.postValue(message)
        }
    }

    private fun createAPI(): ServicesAPI {
        return createRetrofit().create(ServicesAPI::class.java)
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(getRetrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getRetrofitClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    protected fun <R: Serializable> requestWS(clazz: Class<R>, call: Call<ResponseBody>,
                                              successServiceListener: SuccessServiceListener) {
        if (isConnected()) {
            if (!call.isExecuted && !call.isCanceled) {
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful && response.body() != null) {
                            val jsonResponse = response.body()!!.string()
                            successServiceListener.onSuccessResponse(mGson.fromJson(jsonResponse, clazz))
                        } else {
                            mFailureListener.onErrorResponse(response.message())
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        mFailureListener.onErrorResponse(t.localizedMessage ?: "Unknown error")
                        t.printStackTrace()
                    }
                })
            }
        } else {
            mFailureListener.onErrorResponse("Sin conexión a internet")
        }
    }

    private fun isConnected(): Boolean {
        val executors = Executors.newSingleThreadExecutor()
        val callable = Callable {
            try {
                val timeoutMs = 1500
                val socket = Socket()
                val socketAddress = InetSocketAddress("8.8.8.8", 53)

                socket.connect(socketAddress, timeoutMs)
                socket.close()

                true
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }

        val future = executors.submit(callable)
        executors.shutdown()

        return future.get() == true
    }

    protected interface SuccessServiceListener {
        fun <R: Serializable> onSuccessResponse(body: R)
    }

    protected interface FailureServiceListener<String> {
        fun onErrorResponse(message: String)
    }

    fun getError(): MutableLiveData<String> {
        return mNotifyLiveData
    }
}
