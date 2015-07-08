package com.mycompany.itleague.manager;

import com.mycompany.itleague.model.NewsDataResponse;
import com.mycompany.itleague.model.NewsMainData;
import com.mycompany.itleague.model.TableLeaguesResponse;
import com.mycompany.itleague.model.ViolationsDataResponse;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * Created by Сергей on 22.06.2015.
 */
@Rest(rootUrl = "http://football.kharkov.ua/api/v1/tournaments/itleague",
        converters = {MappingJacksonHttpMessageConverter.class})
public interface MainApiClient {

    @Get("/news?page={pageNumber}&per_page={newsPerPage}")
    @Accept(MediaType.APPLICATION_JSON)
    NewsDataResponse getListNews(long pageNumber, long newsPerPage);

    @Get("/news/{newsId}")
    @Accept(MediaType.APPLICATION_JSON)
    NewsMainData getNewsInfo(long newsId);

    @Get("/violations/")
    @Accept(MediaType.APPLICATION_JSON)
    ViolationsDataResponse getViolationInfo();

    @Get("/tables")
    @Accept(MediaType.APPLICATION_JSON)
    TableLeaguesResponse getTableData();
}