package dk.e5pme.githupapi

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.widget.Toast
import dk.e5pme.githubapi.R
import kotlinx.android.synthetic.main.activity_main.*

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private val adapter = GithubSearchResultAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Creates a vertical Layout Manager
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter= adapter

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Toast.makeText(this@MainActivity,query,Toast.LENGTH_SHORT).show()
                requestGithubSearchAPI(query)
            }
        }
    }

    private fun updateUI(items: List<GithubSearchResultItem>) {
        adapter.searchResults = items
    }

    private fun requestGithubSearchAPI(query : String) {
        "https://api.github.com/search/repositories?q=$query+language:kotlin&sort=stars".httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    Toast.makeText(this@MainActivity,ex.toString(),Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    val data = result.get()
                    val json = JSONObject(data)
                    val results = githubSearchResultItemsFromJsonArray(json.getJSONArray("items"))
                    updateUI(results)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.main_menu_search)?.actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }
        //return super.onCreateOptionsMenu(menu)
        return true
    }


}

