package library.android.simplecarsdk

import Location
import LocationConversion
import Range
import RangeConversion
import VehicleListConversion
import org.json.JSONArray
import org.json.JSONObject

class Converter : LocationConversion, RangeConversion,VehicleListConversion,ApiResultConversion{
    override fun convertLocation(body: String?): Location? {
        var jsonObject = JSONObject(body);
        var isSuccessful = jsonObject.getBoolean(ApiManager.SUCCESSFUL_ACTION)
        if (isSuccessful) {
            var latitude: Double = jsonObject.getDouble(ApiManager.LATITUDE)
            var longitude: Double = jsonObject.getDouble(ApiManager.LONGITUDE)
            return Location(latitude, longitude)
        }
        return null
    }

    override fun convertRange(body: String?): Range? {
        var jsonObject = JSONObject(body);
        var isSuccessful = jsonObject.getBoolean(ApiManager.SUCCESSFUL_ACTION)
        if (isSuccessful) {
            var percent: Double = jsonObject.getDouble(ApiManager.RANGE_PERCENT)
            var radius: Double = jsonObject.getDouble(ApiManager.RANGE_RADIUS)
            return Range(percent,radius)
        }
        return null
    }

    override fun convertVehicleList(body: String?): List<String>? {
        var jsonObject = JSONObject(body);
        var isSuccessful = jsonObject.getBoolean(ApiManager.SUCCESSFUL_ACTION)
        if (isSuccessful) {
            var jsonArray : JSONArray = jsonObject.getJSONArray(ApiManager.VEHICLE_IDS)
            var list : ArrayList<String> = ArrayList<String>()
            for (i in 0..jsonArray.length()){
                list.add(jsonArray.getString(i))

            }
            return list
        }
        return null
    }


    override fun convertApiResult(body: String?): Boolean {
        var jsonObject = JSONObject(body);
        return jsonObject.getBoolean(ApiManager.SUCCESSFUL_ACTION)
    }

}