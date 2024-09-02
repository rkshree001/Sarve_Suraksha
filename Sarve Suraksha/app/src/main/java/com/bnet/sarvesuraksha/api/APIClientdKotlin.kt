import com.bnet.sarvesuraksha.api.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClientdKotlin {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "http://178.162.1.15:3000/api/v1/"

    fun getapiClient(): Retrofit {
        if (retrofit == null) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }
        return retrofit!!
    }

    fun getapiInterface(): ApiInterface {
        return getapiClient().create(ApiInterface::class.java)
    }
}
