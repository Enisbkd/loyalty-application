package com.sbm.loyalty.web.rest;

import com.sbm.loyalty.service.DeuxGammaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/validuntil")
public class DeuxGammaResource {

    private final DeuxGammaService deuxGammaService;

    public DeuxGammaResource(DeuxGammaService deuxGammaService) {
        this.deuxGammaService = deuxGammaService;
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateEntity(@RequestParam String validUntil) {
        deuxGammaService.updateEntity(validUntil);

//        if (rowsUpdated > 0) {
            return ResponseEntity.ok("validUntil updated successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("validUntil not found or no changes made");
//        }
    }
}
