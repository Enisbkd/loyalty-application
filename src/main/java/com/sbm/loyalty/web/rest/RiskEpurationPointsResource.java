//package com.sbm.loyalty.web.rest;
//
//import com.sbm.loyalty.service.CalculateEpurationPointsService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * REST controller for managing {@link com.sbm.loyalty.domain.RiskEpurationPoints}.
// */
//@RestController
//@RequestMapping("/clients")
//public class RiskEpurationPointsResource {
//
//    private static final Logger LOG = LoggerFactory.getLogger(RiskEpurationPointsResource.class);
//
//    private final CalculateEpurationPointsService calculateEpurationPointsService;
//
//    public RiskEpurationPointsResource(CalculateEpurationPointsService calculateEpurationPointsService) {
//        this.calculateEpurationPointsService = calculateEpurationPointsService;
//    }
//
//
//    @GetMapping("/{clientId}/points")
//    public ResponseEntity<Integer> getAllRiskEpurationPoints(
//        @PathVariable("clientId") Integer clientId) {
//        if (clientId == null) {
//            LOG.debug("clientId null");
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//        LOG.debug("REST request to get a page of RiskEpurationPoints");
//        return ResponseEntity.ok().body(calculateEpurationPointsService.findEntitiesByStatus());
//    }
//
//    @GetMapping("")
//    public ResponseEntity<String> getAllRiskEpurationPoints() {
//
//        return ResponseEntity.ok().body("Hello World ! ");
//    }
//
//}
