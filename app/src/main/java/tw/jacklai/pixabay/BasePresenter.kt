package tw.jacklai.pixabay

interface BasePresenter<in T> {
    fun attachView(view: T)
    fun detachView()
}