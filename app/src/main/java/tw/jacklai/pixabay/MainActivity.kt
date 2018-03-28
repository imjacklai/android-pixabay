package tw.jacklai.pixabay

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import tw.jacklai.pixabay.model.api.PixabayService

class MainActivity : AppCompatActivity() {
    private val pixabayService by lazy { PixabayService.create() }

    private lateinit var imagesAdapter: ImagesAdapter

    private var currentViewTypeItemId = R.id.list

    private var query = ""

    private val perPage = 20
    private var page = 1
    private var isLoading = false
    private var isLastPage = false
    private var isReload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))

        refreshLayout.setOnRefreshListener {
            reset()
            search(query)
        }

        imagesAdapter = ImagesAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = imagesAdapter

        setRecyclerViewScrollListener()

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
                reset()
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
        if (item == null || currentViewTypeItemId == item.itemId) {
            return super.onOptionsItemSelected(item)
        }

        when (item.itemId) {
            R.id.list -> setRecyclerViewLayoutManager(ViewType.LIST)
            R.id.grid -> setRecyclerViewLayoutManager(ViewType.GRID)
            R.id.staggered_grid -> setRecyclerViewLayoutManager(ViewType.STAGGERED_GRID)
        }

        currentViewTypeItemId = item.itemId

        return super.onOptionsItemSelected(item)
    }

    private fun search(query: String) {
        showLoading()
        this.query = query
        pixabayService.search(query, page, perPage)
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

                                if (Math.ceil(response.total / perPage.toDouble()).toInt() == page) {
                                    isLastPage = true
                                }

                                isLoading = false

                                if (response.total == 0) {
                                    Snackbar.make(coordinatorLayout, "No result", Snackbar.LENGTH_SHORT).show()
                                    return@run
                                }

                                imagesAdapter.addData(response.images)
                            }
                        },
                        { error ->
                            run {
                                hideLoading()
                                Snackbar.make(coordinatorLayout, "Connection failed", Snackbar.LENGTH_SHORT).show()
                            }
                        }
                )
    }

    private fun setRecyclerViewLayoutManager(viewType: ViewType) {
        recyclerView.layoutManager = when (viewType) {
            ViewType.LIST -> LinearLayoutManager(this)
            ViewType.GRID -> GridLayoutManager(this, 2)
            ViewType.STAGGERED_GRID -> StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        imagesAdapter.setViewType(viewType)
        setRecyclerViewScrollListener()
    }

    private fun setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : PaginationScrollListener(recyclerView.layoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                page++
                search(query)
            }

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading
        })
    }

    private fun showLoading() {
        refreshLayout.isRefreshing = true
    }

    private fun hideLoading() {
        refreshLayout.isRefreshing = false
    }

    private fun reset() {
        page = 1
        isLoading = true
        isLastPage = false
        isReload = true
    }
}
