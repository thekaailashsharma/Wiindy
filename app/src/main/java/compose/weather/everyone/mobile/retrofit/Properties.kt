package compose.weather.everyone.mobile.retrofit

data class Properties(
    val address_line1: String,
    val address_line2: String,
    val category: String,
    val city: String,
    val country: String,
    val country_code: String,
    val datasource: Datasource,
    val distance: Double,
    val district: String,
    val formatted: String,
    val housenumber: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val place_id: String,
    val postcode: String,
    val rank: Rank,
    val result_type: String,
    val state: String,
    val street: String,
    val suburb: String,
    val timezone: Timezone
)