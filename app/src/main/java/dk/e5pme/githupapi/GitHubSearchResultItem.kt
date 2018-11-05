package dk.e5pme.githupapi

import org.json.JSONArray
import org.json.JSONObject

data class GithubSearchResultItem(var repoName: String = "",
                                  var ownerName: String = "",
                                  var repoDescription: String = "",
                                  var stargazerCount: Int = 0,
                                  var htmlLink: String = "")

fun githubSearchResultItemFromJSONObject(json: JSONObject): GithubSearchResultItem {
    return json.asSequence().fold(GithubSearchResultItem()) { result, pair ->
        val (key, value) = pair
        when (key) {
            "name" -> result.repoName = value as String
            "owner" -> result.ownerName = (value as JSONObject).getString("login")
            "description" -> result.repoDescription = value as String
            "stargazers_count" -> result.stargazerCount = value as Int
            "html_url" -> result.htmlLink = value as String
        }
        result
    }
}

fun githubSearchResultItemsFromJsonArray(json: JSONArray): List<GithubSearchResultItem> {
    return json.asSequence().fold(arrayListOf()) { list, item ->
        list.add(githubSearchResultItemFromJSONObject(item as JSONObject))
        list
    }
}