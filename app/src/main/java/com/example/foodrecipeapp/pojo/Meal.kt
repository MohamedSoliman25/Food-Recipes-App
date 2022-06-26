package com.example.foodrecipeapp.pojo


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "mealInformation")
data class Meal(
    @SerializedName("dateModified")
    var dateModified: Any?,
    @SerializedName("idMeal")
    @PrimaryKey
    var idMeal: String,
    @SerializedName("strArea")
    var strArea: String?,
    @SerializedName("strCategory")
    var strCategory: String?,
    @SerializedName("strCreativeCommonsConfirmed")
    var strCreativeCommonsConfirmed: Any?,
    @SerializedName("strDrinkAlternate")
    var strDrinkAlternate: Any?,
    @SerializedName("strImageSource")
    var strImageSource: Any?,
    @SerializedName("strIngredient1")
    var strIngredient1: String?,
    @SerializedName("strIngredient10")
    var strIngredient10: String?,
    @SerializedName("strIngredient11")
    var strIngredient11: String?,
    @SerializedName("strIngredient12")
    var strIngredient12: String?,
    @SerializedName("strIngredient13")
    var strIngredient13: String?,
    @SerializedName("strIngredient14")
    var strIngredient14: String?,
    @SerializedName("strIngredient15")
    var strIngredient15: String?,
    @SerializedName("strIngredient16")
    var strIngredient16: String?,
    @SerializedName("strIngredient17")
    var strIngredient17: String?,
    @SerializedName("strIngredient18")
    var strIngredient18: String?,
    @SerializedName("strIngredient19")
    var strIngredient19: String?,
    @SerializedName("strIngredient2")
    var strIngredient2: String?,
    @SerializedName("strIngredient20")
    var strIngredient20: String?,
    @SerializedName("strIngredient3")
    var strIngredient3: String?,
    @SerializedName("strIngredient4")
    var strIngredient4: String?,
    @SerializedName("strIngredient5")
    var strIngredient5: String?,
    @SerializedName("strIngredient6")
    var strIngredient6: String?,
    @SerializedName("strIngredient7")
    var strIngredient7: String?,
    @SerializedName("strIngredient8")
    var strIngredient8: String?,
    @SerializedName("strIngredient9")
    var strIngredient9: String?,
    @SerializedName("strInstructions")
    var strInstructions: String?,
    @SerializedName("strMeal")
    var strMeal: String?,
    @SerializedName("strMealThumb")
    var strMealThumb: String?,
    @SerializedName("strMeasure1")
    var strMeasure1: String?,
    @SerializedName("strMeasure10")
    var strMeasure10: String?,
    @SerializedName("strMeasure11")
    var strMeasure11: String?,
    @SerializedName("strMeasure12")
    var strMeasure12: String?,
    @SerializedName("strMeasure13")
    var strMeasure13: String?,
    @SerializedName("strMeasure14")
    var strMeasure14: String?,
    @SerializedName("strMeasure15")
    var strMeasure15: String?,
    @SerializedName("strMeasure16")
    var strMeasure16: String?,
    @SerializedName("strMeasure17")
    var strMeasure17: String?,
    @SerializedName("strMeasure18")
    var strMeasure18: String?,
    @SerializedName("strMeasure19")
    var strMeasure19: String?,
    @SerializedName("strMeasure2")
    var strMeasure2: String?,
    @SerializedName("strMeasure20")
    var strMeasure20: String?,
    @SerializedName("strMeasure3")
    var strMeasure3: String?,
    @SerializedName("strMeasure4")
    var strMeasure4: String?,
    @SerializedName("strMeasure5")
    var strMeasure5: String?,
    @SerializedName("strMeasure6")
    var strMeasure6: String?,
    @SerializedName("strMeasure7")
    var strMeasure7: String?,
    @SerializedName("strMeasure8")
    var strMeasure8: String?,
    @SerializedName("strMeasure9")
    var strMeasure9: String?,
    @SerializedName("strSource")
    var strSource: String?,
    @SerializedName("strTags")
    var strTags: String?,
    @SerializedName("strYoutube")
    var strYoutube: String?
)