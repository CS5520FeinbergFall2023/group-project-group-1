package edu.northeastern.numad23fa_groupproject1.Learn;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LearnModule {

    @Provides
    @Singleton
    @Named("LearnModuleServiceImpl")
    public LearnModuleService provideLearnModuleService() {
        return new LearnModuleServiceImpl();
    }
}
