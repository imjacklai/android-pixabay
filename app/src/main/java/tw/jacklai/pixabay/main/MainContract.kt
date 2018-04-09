package tw.jacklai.pixabay.main

import tw.jacklai.pixabay.BasePresenter
import tw.jacklai.pixabay.model.Response

interface MainContract {
    interface View {
        fun showImages(response: Response)
        fun showConnectionFailed()
    }

    interface Presenter : BasePresenter<View> {
        fun search(query: String, page: Int, perPage: Int)
    }
}