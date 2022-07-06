import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SimpleCarSdk {
    private lateinit var apiCode: String
    private lateinit var smartCarCode: String
    private lateinit var uid: String
    private val version : Int = 10

    private lateinit var service : ApiService
    constructor(apiCode: String, smartCarCode : String, uid : String){
        this.apiCode = apiCode
        this.smartCarCode = smartCarCode
        this.uid = uid
    }
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .build()

        service = retrofit.create(ApiService::class.java)
    }
    public fun getVersion() : Int {
        return version
    }

    companion object {
        var instance: SimpleCarSdk? = null
        fun get(apiCode: String, smartCarCode: String, uid:String) : SimpleCarSdk{
            if(instance == null)
                instance = SimpleCarSdk(apiCode,smartCarCode,uid)
            return instance as SimpleCarSdk
        }
    }
    fun getVehicle(vehicleListCallback: VehicleCallback) {
        service.getCars(apiCode,smartCarCode,uid).enqueue(object :
            Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                vehicleListCallback.getVehicles(Converter().convertVehicleList(response.body()?.string()))
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                vehicleListCallback.exception(Exception(t.message,ExceptionManager.EXCEPTION_API_CALL_EXTERNAL))

            }

        })

    }
    fun getRange(id : String,rangeCallback: RangeCallback) {
        service.getVehicleRange(apiCode,smartCarCode,id,uid).enqueue(object :
            Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                rangeCallback.range(Converter().convertRange(response.body()?.string()))
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                rangeCallback.exception(Exception(t.message,ExceptionManager.EXCEPTION_API_CALL_EXTERNAL))

            }

        })

    }
    fun getLocation(locationCallback: LocationCallback) {
        service.getCars(apiCode,smartCarCode,uid).enqueue(object :
            Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                locationCallback.location(Converter().convertLocation(response.body()?.string()))
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                locationCallback.exception(Exception(t.message,ExceptionManager.EXCEPTION_API_CALL_EXTERNAL))

            }

        })

    }
    fun unlockVehicle(id:String,apiResult: ApiResult) {
        service.unlockVehicle(apiCode,smartCarCode,id,uid).enqueue(object :
            Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                apiResult.result(Converter().convertApiResult(response.body()?.string()))
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                apiResult.result(false)

            }

        })

    }




}