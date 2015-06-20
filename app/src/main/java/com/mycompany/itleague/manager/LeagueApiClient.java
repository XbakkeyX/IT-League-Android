package com.mycompany.itleague.manager;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;


/**
 * Created by Сергей on 19.06.2015.
 */

@Rest(rootUrl = "", converters = {MappingJackson2HttpMessageConverter.class})
public interface LeagueApiClient {
    @Get("")
    @Accept(MediaType.APPLICATION_JSON)
}
