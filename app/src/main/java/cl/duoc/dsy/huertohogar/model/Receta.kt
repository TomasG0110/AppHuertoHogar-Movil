package cl.duoc.dsy.huertohogar.model


data class RecetaResponse(
    val meals: List<Receta>
)

data class Receta(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)