package dk.e5pme.githupapi

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dk.e5pme.githubapi.R
import kotlinx.android.synthetic.main.custom_list_item.view.*
import kotlin.properties.Delegates

class GithubSearchResultAdapter(private val context: Context) : RecyclerView.Adapter<SearchResultViewHolder>() {

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

    var searchResults by Delegates.observable(listOf<GithubSearchResultItem>()) { _, _, _-> notifyDataSetChanged() }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        //return items.size
        return searchResults.count()
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(viewHolder: SearchResultViewHolder, pos: Int) {
        //holder.tvAnimalType?.text = items[pos]

        val item = searchResults[pos]
        viewHolder.view.searchResultTitleText.text = item.repoName
        viewHolder.view.searchResultSubtitleText.text = item.ownerName
        viewHolder.view.searchResultDescriptionText.text = item.repoDescription
        viewHolder.view.searchResultStargazerCount.text = item.stargazerCount.toString()

    }

}

class SearchResultViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private var clickListener: ((View) -> Unit)? = null

    init {
        view.setOnClickListener {
            clickListener?.invoke(it)
        }
    }

}

/*
class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvAnimalType: TextView? = view.custom_list_item_name
}
        */