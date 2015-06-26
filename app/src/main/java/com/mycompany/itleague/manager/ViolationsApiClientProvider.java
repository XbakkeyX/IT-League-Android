package com.mycompany.itleague.manager;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by Сергей on 19.06.2015.
 */
@EBean
public class ViolationsApiClientProvider {
    @RestService
    /*package*/ ViolationsApiClient apiClient;

    public ViolationsApiClient getApiClient() { return this.apiClient; }
}
