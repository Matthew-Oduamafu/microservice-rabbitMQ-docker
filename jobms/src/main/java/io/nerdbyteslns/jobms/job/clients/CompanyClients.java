package io.nerdbyteslns.jobms.job.clients;


import io.nerdbyteslns.jobms.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COMPANY-MICROSERVICE",
        url = "${company-service.url}")
public interface CompanyClients {

    @GetMapping("/companies/{id}")
    Company getCompanyById(@PathVariable("id") Long id);
}
