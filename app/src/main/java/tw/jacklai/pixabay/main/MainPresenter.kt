package tw.jacklai.pixabay.main

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import tw.jacklai.pixabay.model.api.PixabayService

class MainPresenter(private val pixabayService: PixabayService) : MainContract.Presenter {
    private var mainView: MainContract.View? = null
    private var disposable: Disposable? = null

    override fun attachView(view: MainContract.View) {
        mainView = view
    }

    override fun detachView() {
        disposable?.dispose()
        mainView = null
    }

    override fun search(query: String, page: Int, perPage: Int) {
        disposable?.dispose()

        pixabayService.search(query, page, perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response -> mainView?.showImages(response) },
                        { _ -> mainView?.showConnectionFailed() },
                        { /* onComplete */ },
                        { disposable -> this.disposable = disposable }
                )
    }
}