package com.mycompany.itleague.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Сергей on 20.06.2015.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class ViolationsDataResponse {

    @JsonProperty("violations")
    private ArrayList<ArrayList<ViolationModel>> violations;

    public ArrayList<ArrayList<ViolationModel>> getMainViolationsData() {
        return violations;
    }
}
