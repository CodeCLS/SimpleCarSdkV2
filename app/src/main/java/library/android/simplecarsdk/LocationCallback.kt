package library.android.simplecarsdk

interface LocationCallback {
    fun location(location: Location?)
    fun exception(exception: Exception)
}