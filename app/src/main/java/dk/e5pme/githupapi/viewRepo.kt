package dk.e5pme.githupapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dk.e5pme.githubapi.R
import kotlinx.android.synthetic.main.activity_view_repo.*
import kotlinx.android.synthetic.main.activity_view_repo.view.*

class viewRepo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_repo)

        val extras = intent.extras ?: return
        name.text=extras.getString("name")
        description.text=extras.getString("description")
        stars.text=extras.getString("stars")
        Toast.makeText(this, extras.getString("stars"), Toast.LENGTH_SHORT).show()
        date.text=extras.getString("date")
        val url=extras.getString("url")
        val avatar_url=extras.getString("avatar_url")
    }
}
