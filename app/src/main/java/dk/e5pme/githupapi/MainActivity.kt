/*
*  https://www.raywenderlich.com/367-android-recyclerview-tutorial-with-kotlin
*  The truth is that ListViews and GridViews only do half the job of achieving
*  true memory efficiency. They recycle the item layout, but don’t keep references
*  to the layout children, forcing you to call findViewById() for every child of your item layout
*  every time you call getView(). All this calling around can become very processor-intensive,
*  especially for complicated layouts. Furthermore, the situation can cause your ListView scrolling
*  to become jerky or non-responsive as it frantically tries to grab references
*  to the views you need.
*  Android engineers initially provided a solution to this problem on the Android Developers
*  site with smooth scrolling, via the power of the View Holder pattern.
*  When you use this pattern, you create a class that becomes an in-memory reference
*  to all the views needed to fill your layout.
*  The benefit is you set the references once and reuse them, effectively working around the performance hit that comes with repeatedly calling findViewById().
*  The problem is that it’s an optional pattern for a ListView or GridView. If you’re unaware of this detail,
*  then you may wonder why your precious ListViews and GridViews are so slow.
*/
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

import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: GithubSearchResultAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Create Adapter
        adapter = GithubSearchResultAdapter(this)
        adapter.setOnItemClickListener(object : ClickListener {
            override fun onItemClick(position: Int, item: GithubRepoItem) {
                val i = Intent(this@MainActivity, viewRepo::class.java)
                i.putExtra("description",item.description)
                i.putExtra("name",item.name)
                i.putExtra("stars",item.stargazerCount.toString())
                i.putExtra("date",item.updated_at)
                i.putExtra("avatar_url",item.owner.avatar_url)
                i.putExtra("html_url",item.owner.html_url)
                startActivity(i)
            }
        })

        // Creates a vertical Layout Manager
        //Layout Managers are used to position views inside the RecyclerView.
        // They also determine when to reuse item views that are no longer visible to the user.
        linearLayoutManager=LinearLayoutManager(this)
        mainRecyclerView.layoutManager = linearLayoutManager
        //The adapter creates new items in the form of ViewHolders, populates the ViewHolders with data,
        // and returns information about the data.
        mainRecyclerView.adapter= adapter

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                requestGithubSearchAPI(query)
            }
        }
    }
    private fun updateUI(items: List<GithubRepoItem>) {
        runOnUiThread {
            adapter.searchResults = items
        }
    }
    private fun requestGithubSearchAPI(query : String) {
        "https://api.github.com/search/repositories?q=$query+language:kotlin&sort=stars".httpGet().responseString {
                request, response, result ->
            Log.d("API", request.toString())
            Log.d("API", response.toString())

            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    Toast.makeText(this@MainActivity,ex.toString(),Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    val data = result.get()
                    val gson = Gson()
                    val parsedResult = gson.fromJson(data, GitHubResponse::class.java)
                    updateUI(parsedResult.items)
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
        return super.onCreateOptionsMenu(menu)
    }
}

