package com.mycompany.itleague.manager;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by Сергей on 19.06.2015.
 */
@EBean
public class LeagueApiClientProvider {
    @RestService
    /*package*/ LeagueApiClient apiClient;

    public LeagueApiClient getApiClient() {return this.apiClient;}
}
