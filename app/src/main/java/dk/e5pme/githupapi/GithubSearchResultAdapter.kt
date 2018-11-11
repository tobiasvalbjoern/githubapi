package dk.e5pme.githupapi

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dk.e5pme.githubapi.R
import kotlinx.android.synthetic.main.custom_list_item.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

interface ClickListener {
    fun onItemClick(position: Int, item: GithubRepoItem)
}

class GithubSearchResultAdapter(private val context: Context) : RecyclerView.Adapter<GithubSearchResultAdapter.SearchResultViewHolder>() {

    var clickListener: ClickListener = object : ClickListener {
        override fun onItemClick(position: Int, item: GithubRepoItem) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }


    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.custom_list_item,
                parent,
                false
            )
        )
    }

    var searchResults by Delegates.observable(listOf<GithubRepoItem>()) { _, _, _-> notifyDataSetChanged() }

    override fun getItemCount(): Int {
        return searchResults.count()
    }

    fun setOnItemClickListener(listener: ClickListener) {
        clickListener = listener
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(viewHolder: SearchResultViewHolder, pos: Int) {
        val item = searchResults[pos]
        viewHolder.view.searchResultTitleText.text = item.name
        viewHolder.view.searchResultSubtitleText.text = item.owner.login
        viewHolder.view.searchResultDescriptionText.text = item.description
        viewHolder.view.searchResultStargazerCount.text = item.stargazerCount.toString()


        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

        val timestamp = LocalDate.parse(item.updated_at,formatter).toString()

        viewHolder.view.searchResultUpdatedAt.text=timestamp

        viewHolder.view.setOnClickListener {
            clickListener.onItemClick(pos, item)
        }
    }

    class SearchResultViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}