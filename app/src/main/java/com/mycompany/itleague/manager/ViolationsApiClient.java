package com.mycompany.itleague.manager;

import com.mycompany.itleague.model.ViolationsDataResponse;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;


/**
 * Created by Сергей on 19.06.2015.
 */
//убрали violations
@Rest(rootUrl = "http://football.kharkov.ua/api/v1/tournaments/itleague", converters = {MappingJacksonHttpMessageConverter.class})
public interface ViolationsApiClient {
    @Get("/violations")
    @Accept(MediaType.APPLICATION_JSON)
    ViolationsDataResponse getViolationInfo ();
}
