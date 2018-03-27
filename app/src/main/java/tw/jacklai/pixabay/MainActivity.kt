package tw.jacklai.pixabay

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import tw.jacklai.pixabay.model.api.PixabayService

class MainActivity : AppCompatActivity() {
    private val pixabayService by lazy { PixabayService.create() }

    private lateinit var imagesAdapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        imagesAdapter = ImagesAdapter()

        recyclerView.adapter = imagesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.search)

        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setIconifiedByDefault(false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null) return false
                search(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    private fun search(query: String) {
        pixabayService.search(Config.PIXABAY_API_KEY, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response -> imagesAdapter.setData(response.images) },
                        { error -> Log.e("asd", error.message) }
                )
    }
}
