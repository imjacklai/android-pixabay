package tw.jacklai.pixabay

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import tw.jacklai.pixabay.model.api.PixabayService

class MainActivity : AppCompatActivity() {
    private val pixabayService by lazy { PixabayService.create() }

    private lateinit var imagesAdapter: ImagesAdapter

    private lateinit var layoutManager: RecyclerView.LayoutManager

    private var query = ""

    private var isReload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))

        refreshLayout.setOnRefreshListener {
            isReload = true
            search(query)
        }

        imagesAdapter = ImagesAdapter()

        layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = imagesAdapter

        search(query)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.search)

        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setIconifiedByDefault(false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null) return false
                isReload = true
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        setRecyclerViewLayoutManager(item?.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun search(query: String) {
        showLoading()
        this.query = query
        pixabayService.search(Config.PIXABAY_API_KEY, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            run {
                                hideLoading()

                                if (isReload) {
                                    imagesAdapter.removeData()
                                    isReload = false
                                }

                                imagesAdapter.addData(response.images)
                            }
                        },
                        { error ->
                            run {
                                hideLoading()
                                Log.e("asd", error.message)
                            }
                        }
                )
    }

    private fun setRecyclerViewLayoutManager(type: Int?) {
        val viewType: ViewType

        when (type) {
            R.id.list -> {
                viewType = ViewType.LIST
                layoutManager = LinearLayoutManager(this)
            }
            R.id.grid -> {
                viewType = ViewType.GRID
                layoutManager = GridLayoutManager(this, 2)
            }
            R.id.staggered_grid -> {
                viewType = ViewType.STAGGERED_GRID
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }
            else -> {
                viewType = ViewType.LIST
                layoutManager = LinearLayoutManager(this)
            }
        }

        recyclerView.layoutManager = layoutManager
        imagesAdapter.setViewType(viewType)
    }

    private fun showLoading() {
        refreshLayout.isRefreshing = true
    }

    private fun hideLoading() {
        refreshLayout.isRefreshing = false
    }
}
