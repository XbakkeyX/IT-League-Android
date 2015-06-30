package com.mycompany.itleague.manager;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by Сергей on 22.06.2015.
 */
    @EBean
    public class MainApiClientProvider {
     @RestService
     /*package*/ MainApiClient apiClient;

     public MainApiClient getMainApiClient(){return this.apiClient;}
     }