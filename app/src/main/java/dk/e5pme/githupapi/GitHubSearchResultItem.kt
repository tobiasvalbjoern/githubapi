package dk.e5pme.githupapi

import com.google.gson.annotations.SerializedName

data class GitHubResponse(var items: ArrayList<GithubRepoItem>)

data class GithubRepoItem(var name: String,
                          var owner: GithubRepoOwner,
                          var description: String,
                          @SerializedName("stargazers_count") var stargazerCount: Int,
                          var url: String,
                          var updated_at:String)

data class GithubRepoOwner(var login: String, var avatar_url:String, var html_url:String)