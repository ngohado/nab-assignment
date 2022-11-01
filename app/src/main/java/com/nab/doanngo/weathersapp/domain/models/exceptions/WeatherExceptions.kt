package com.nab.doanngo.weathersapp.domain.models.exceptions

/**
 * Occurred when the [city] is not existed,
 */
class CityNotFoundException(
    val city: String
) : Exception("Cannot found result with city value \"$city\"")

/**
 * Others kind of error occurred when server returned the code not success.
 */
object OthersRemoteErrorException :
    Exception("Error code returned is not match with any defined code")
