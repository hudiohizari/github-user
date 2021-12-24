package id.hudiohizari.githubuser.data.model.user.search


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    val items: List<Item>?,
    @SerializedName("total_count")
    val totalCount: Int?
)