package serat.maemaeen.mahdavistories.di.core.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import serat.maemaeen.data.util.DiskExecutor
import serat.maemaeen.data.util.DispatchersProvider
import serat.maemaeen.data.util.DispatchersProviderImpl
import serat.maemaeen.mahdavistories.util.*
import javax.inject.Singleton
@Module
class AppModule constructor(context: Context) {
    private val appContext = context.applicationContext
    @Singleton
    @Provides
    fun provideAppContext():Context{
        return appContext
    }

    @Provides
    fun provideDiskExecutor(): DiskExecutor {
        return DiskExecutor()

    }

    @Provides
    fun provideDispatchersProvider(): DispatchersProvider{
        return DispatchersProviderImpl
    }

    @Provides
    fun provideResourceProvider(context: Context): ResourceProvider {
        return ResourceProvider(context)
    }



    @Provides
    fun provideGlideProvider():GlideLoading = GlideLoadingImpl()

//    @Provides
//    fun provideCheckerProvider(netCheck: NetCheck):Checker = Checker(netCheck)


}