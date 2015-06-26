package com.mycompany.itleague.manager;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by Сергей on 22.06.2015.
 */
    @EBean
    public class TableApiClientProvider {
    @RestService
     /*package*/ TableApiClient apiClient;

    public TableApiClient getTableApiClient(){return this.apiClient;}
 }
