package com.tinqin.library.book.domain.clients;

import com.tinqin.library.reporting.restexport.ReportingRestExport;
import org.springframework.cloud.openfeign.FeignClient;



@FeignClient(name = "reportingClient",url = "${reporting.url}",
        configuration = ReportingClientConfiguration.class)
public interface ReportingClient extends ReportingRestExport {

}
