package com.mycompany.itleague.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by Сергей on 20.06.2015.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class ViolationsDataResponse {

    @JsonProperty("violations")
    private List<List<ViolationsMainData>> violations;

    public List<List<ViolationsMainData>> getMainViolationsData()
    {
        return violations;
    }
}