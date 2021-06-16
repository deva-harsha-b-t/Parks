package com.dtl.parks.data;

import com.dtl.parks.model.Park;

import java.util.List;

public interface AsyncResponse {
    void processPark(List<Park> parks);
}
