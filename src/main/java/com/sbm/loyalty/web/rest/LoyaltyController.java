package com.sbm.loyalty.web.rest;


import com.sbm.loyalty.domain.Response;
import com.sbm.loyalty.service.CalculateEpurationPointsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class LoyaltyController {

    private final CalculateEpurationPointsService calculateEpurationPointsService;

    public LoyaltyController(CalculateEpurationPointsService calculateEpurationPointsService) {
        this.calculateEpurationPointsService = calculateEpurationPointsService;
    }

    @GetMapping("/{clientId}/points")
    public Response getEpurationPoints(@PathVariable int clientId) {
        return calculateEpurationPointsService.constructResponse(clientId);
    }

    @GetMapping("/test")
    public Integer test() {
        return 0;
    }
}
