package tw.jacklai.pixabay

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.BeforeClass

open class BaseTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            RxJavaPlugins.setIoSchedulerHandler {
                Schedulers.trampoline()
            }

            RxAndroidPlugins.setInitMainThreadSchedulerHandler {
                Schedulers.trampoline()
            }
        }

        @AfterClass
        @JvmStatic
        fun tearDownClass() {
            RxJavaPlugins.reset()
            RxAndroidPlugins.reset()
        }
    }
}