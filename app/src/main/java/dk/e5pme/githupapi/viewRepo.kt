package dk.e5pme.githupapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dk.e5pme.githubapi.R
import kotlinx.android.synthetic.main.activity_view_repo.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide

class viewRepo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_repo)

        val extras = intent.extras ?: return
        name.text=extras.getString("name")
        description.text=extras.getString("description")
        stars.text=extras.getString("stars")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val timestamp = LocalDate.parse(extras.getString("date"),formatter).toString()
        date.text=timestamp

        Glide.with(this)
            .load(extras.getString("avatar_url"))
            .into(avatar_url)

        repoBtn.setOnClickListener{
            val implicit = Intent(Intent.ACTION_VIEW, Uri.parse(extras.getString("html_url")))
            startActivity(implicit)
        }
    }

}
