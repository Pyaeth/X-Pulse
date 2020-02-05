package com.anaem.xpulsebo.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anaem.xpulsebo.dao.TransactionDAO;
import com.anaem.xpulsebo.model.Statistic;
import com.anaem.xpulsebo.model.Transaction;

@Service
public class TransactionService {
	TransactionDAO dao = new TransactionDAO();
	
	public Optional<List<Transaction>> retrieveAllTransactions(int uid) throws Exception {
		return dao.retrieveAllTransactions(uid);
	}
	
	public Optional<Transaction> addNewTransaction(Transaction t) throws SQLException {
		return dao.addNewTransaction(t);
	}
	
	public Optional<Statistic> retrieveTransactionsInPeriod(int uid, String interval) throws Exception {
		System.out.println("DB queries: ");
		List<Transaction> transactions = dao.retrieveTransactionsInPeriod(uid, interval).get();
		Statistic statistic = new Statistic();
		statistic.setNoIncoming(dao.countTransactions(uid, ">=", interval));
		statistic.setNoOutgoing(dao.countTransactions(uid, "<", interval));
		statistic.setNoOfTransactions(statistic.getNoIncoming() + statistic.getNoOutgoing());
		statistic.setIncoming(dao.sumTransactions(uid, ">=", interval));
		statistic.setOutgoing(dao.sumTransactions(uid, "<", interval));
		statistic.setBalance(statistic.getIncoming() + statistic.getOutgoing());
		statistic.setComparedToLast(statistic.getBalance() - dao.sumTransactionsLast(uid, interval));
		
		System.out.println("Transactions retrieved:");
		transactions.forEach(System.out::println);
		
		statistic.statisticsFlt = new HashMap<>();
		statistic.statisticsInt = new HashMap<>();
		
		for (Transaction t : transactions) {
			if (!statistic.statisticsFlt.isEmpty() && statistic.statisticsFlt.containsKey(t.getType())) {
				statistic.statisticsFlt.put(t.getType(), statistic.statisticsFlt.get(t.getType() + t.getAmount()));
			} else {
				statistic.statisticsFlt.put(t.getType(), t.getAmount());
			}
			
			if (!statistic.statisticsInt.isEmpty() && statistic.statisticsInt.containsKey(t.getType())) {
				statistic.statisticsInt.put(t.getType(), statistic.statisticsInt.get(t.getType() + 1));
			} else {
				statistic.statisticsInt.put(t.getType(), 1);
			}
		}
		return Optional.ofNullable(statistic);
	}
	
	public Optional<List<Transaction>> retrieveTransactionsLastPeriod(int uid, String interval) throws Exception {
		return dao.retrieveTransactionsLastPeriod(uid, interval);
	}
	
	public Optional<Transaction> retrieveTransaction(int uid, int tid) throws Exception {
		return dao.retrieveTransaction(uid, tid);
	}
	
}
