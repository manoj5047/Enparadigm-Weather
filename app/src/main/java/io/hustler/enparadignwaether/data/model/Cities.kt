package io.hustler.enparadignwaether.data.model

class Cities : ArrayList<CitiesItem>()

data class CitiesItem(
    val admin: String,
    val capital: String,
    val city: String,
    val country: String,
    val iso2: String,
    val lat: String,
    val lng: String,
    val population: String,
    val population_proper: String
)