package tw.jacklai.pixabay.main

import io.reactivex.Observable
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import tw.jacklai.pixabay.BaseTest
import tw.jacklai.pixabay.FakeData
import tw.jacklai.pixabay.model.api.PixabayService

class MainPresenterTest : BaseTest() {
    private lateinit var presenter: MainPresenter
    @Mock private lateinit var view: MainContract.View
    @Mock private lateinit var apiService: PixabayService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(apiService)
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun searchOnSuccess() {
        val response = FakeData.getResponse()

        `when`(apiService.search(anyString(), anyInt(), anyInt()))
                .thenReturn(Observable.just(response))

        presenter.search("", 1, 20)

        verify(view).showImages(response)

        assertEquals(3296399, response.images[0].id)
        assertEquals(3299525, response.images[1].id)
        assertEquals(3294681, response.images[2].id)
    }

    @Test
    fun searchOnFailed() {
        `when`(apiService.search(anyString(), anyInt(), anyInt()))
                .thenReturn(Observable.error(RuntimeException()))

        presenter.search("", 1, 20)

        verify(view).showConnectionFailed()
    }
}