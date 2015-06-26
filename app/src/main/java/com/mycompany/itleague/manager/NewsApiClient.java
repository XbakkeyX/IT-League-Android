package com.mycompany.itleague.manager;

import com.mycompany.itleague.model.NewsDataResponse;
import com.mycompany.itleague.model.TableLeaguesData;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * Created by Сергей on 22.06.2015.
 */
@Rest(rootUrl = "http://football.kharkov.ua/api/v1/tournaments/itleague", converters = {MappingJacksonHttpMessageConverter.class})
public interface NewsApiClient {
    @Get("/news?page={pageNumber}&per_page={newsPerPage}")
    @Accept(MediaType.APPLICATION_JSON)
    NewsDataResponse getListNews (long pageNumber, long newsPerPage);

    @Get("/news/{newsId}")
    @Accept(MediaType.APPLICATION_JSON)
    NewsDataResponse getNewsInfo (long newsId);
}