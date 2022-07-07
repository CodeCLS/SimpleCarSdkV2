package library.android.simplecarsdk

interface VehicleCallback {
    fun getVehicles(list : List<String>?)
    fun exception(exception: Exception)
}