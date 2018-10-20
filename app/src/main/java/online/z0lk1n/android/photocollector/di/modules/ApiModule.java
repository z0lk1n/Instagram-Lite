package online.z0lk1n.android.photocollector.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Named("baseUrl")
    @Provides
    public String getBaseUrl()  {
        return "https://api.instagram.com/";
    }


}
