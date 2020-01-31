package com.anaem.xpulsebo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anaem.xpulsebo.model.Statistic;
import com.anaem.xpulsebo.model.Transaction;
import com.anaem.xpulsebo.service.TransactionService;

@RestController
@EnableAutoConfiguration
@CrossOrigin(
        allowCredentials = "true",
        origins = "*",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT}
)
public class TransactionController {
	TransactionService transactionService = new TransactionService();
	
	@PostMapping(value = "/transaction/all/{unit}")
    public ResponseEntity retrieveAllTransactions(@RequestBody Transaction transaction, @PathVariable String unit) throws Exception {
		Optional<Statistic> statistic = transactionService.retrieveTransactionsInPeriod(transaction.getUid(), unit);
        if (statistic.isPresent()) {
    		return ResponseEntity.ok(statistic);
    	} else {
    		return ResponseEntity.badRequest().body("Invalid UID.");
    	}
    	
    }
	
	protected class FEStats {
		int uid;
		String unit;
	}
	
	@PostMapping(value = "/statistics")
    public ResponseEntity retrieveStatistics(@RequestBody FEStats input) throws Exception {
		Optional<Statistic> statistic = transactionService.retrieveTransactionsInPeriod(input.uid, input.unit);
        if (statistic.isPresent()) {
    		return ResponseEntity.ok(statistic);
    	} else {
    		return ResponseEntity.badRequest().body("Invalid UID.");
    	}
    	
    }
}
