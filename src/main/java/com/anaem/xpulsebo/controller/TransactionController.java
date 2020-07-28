package com.anaem.xpulsebo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anaem.xpulsebo.model.Statistic;
import com.anaem.xpulsebo.model.Transaction;
import com.anaem.xpulsebo.service.TransactionService;
import com.anaem.xpulsebo.utils.CSVDateParser;
import com.anaem.xpulsebo.utils.FEStats;

@RestController
@EnableAutoConfiguration
@CrossOrigin(allowCredentials = "true", origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET,
		RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
public class TransactionController {
	TransactionService transactionService = new TransactionService();
	private static final Logger logger = LogManager.getLogger(TransactionController.class);

	@PostMapping(value = "/statistics")
	public ResponseEntity retrieveStatistics(@RequestBody FEStats input) throws Exception {
		logger.debug(input);

		Optional<Statistic> statistic = transactionService.retrieveTransactionsInPeriod(input.uid,
				input.unit.toUpperCase());
		if (statistic.isPresent()) {
			return ResponseEntity.ok(statistic);
		} else {
			return ResponseEntity.badRequest().body("Invalid UID.");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/uploadCSV")
	public void handleFileUpload(@RequestParam MultipartFile file) throws IOException {
		if (file != null) {

			if (!file.getOriginalFilename().endsWith("csv")) {
				logger.error("INVALID FILE FORMAT!! --- > File " + file.getOriginalFilename());
			} else {

				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
				List<String> lines = new ArrayList<>();
				lines.addAll(br.lines().collect(Collectors.toList()));
				br.close();
				Transaction t = null;
				for (String line : lines) {
					
					String[] records = line.split(",");
					if (!records[0].isEmpty()) { //new transaction header
						t = new Transaction();
						t.setDprocess(CSVDateParser.parseCSVDate(records[0]));
						t.setDetails(records[2]);
						if (!records[5].isEmpty()) {
							t.setAmount(-Float.parseFloat(records[5].replaceAll(".", "").replaceAll(",", ".")));
						}
						if (!records[7].isEmpty()) {
							t.setAmount(Float.parseFloat(records[7].replaceAll(".", "").replaceAll(",", ".")));
						}
					} else if (!records[2].isEmpty() && records[1].isEmpty()) { //transaction details and NOT the header of the document
						if (records[2].length() == 29 && CSVDateParser.matches(records[2].split(" ")[0])) {
							t.setDrequest(CSVDateParser.parseDate(records[2].split(" ")[0]));
						}
						t.setDetails(t.getDetails() + "\n" + line);
					}

				}
			}
		}
	}
}
