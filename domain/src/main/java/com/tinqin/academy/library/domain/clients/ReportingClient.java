package com.tinqin.academy.library.domain.clients;

import com.tinqin.academy.reporting.restexport.ReportingRestExport;
import org.springframework.cloud.openfeign.FeignClient;



@FeignClient(name = "reportingClient",url = "${reporting.url}",
        configuration = ReportingClientConfiguration.class)
public interface ReportingClient extends ReportingRestExport {

}
