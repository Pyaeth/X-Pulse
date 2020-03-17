package com.anaem.xpulsebo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.anaem.xpulsebo.model.Transaction;

public class TransactionDAO {

	Connection con;

	public TransactionDAO() {
		try {
			con = DBConnection.getDBConnection();
			// get all transactions
			sql1 = "SELECT t.*, u.username FROM transactions t, users u WHERE t.uid = u.id AND t.uid = ?";
			// insert new transaction
			sql2 = "INSERT INTO transactions(tid, uid, drequest, dprocess, type, details, amount) VALUES (NULL, ?, ?, ?, ?, ?, ?)";
			// get all transactions from the past week/month/year
			sql3 = sql1 + " AND t.drequest >= DATE(NOW() - INTERVAL 1 ";
			// get transaction summary from previous week/month/year
			sql4 = sql1 + " AND u.id = ? AND t.drequest BETWEEN DATE(NOW() - INTERVAL 1 ";
			// get specific transaction
			sql5 = sql1 + " AND t.tid = ?";
			// noIncoming + noOutgoing = noOfTransactions
			sql6 = "SELECT COUNT(*) FROM transactions t, users u WHERE t.uid = u.id AND t.uid = ? AND t.drequest >= DATE(NOW() - INTERVAL 1 ";
			// incoming + outgoing = balance;
			sql7 = "SELECT SUM(t.amount) FROM transactions t, users u WHERE t.uid = u.id AND t.uid = ? AND t.drequest >= DATE(NOW() - INTERVAL 1 ";
			// comparedToLast
			sql8 = "SELECT SUM(t.amount) FROM transactions t, users u WHERE t.uid = u.id AND t.uid = ? AND t.drequest BETWEEN DATE(NOW() - INTERVAL 2 ";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PreparedStatement stmt1, stmt2, stmt3, stmt4, stmt5, stmt6, stmt7, stmt8;
	private String sql1, sql2, sql3, sql4, sql5, sql6, sql7, sql8;

	private void setTransactionDetails(ResultSet rs, Transaction t) throws SQLException {
		t.setUid(rs.getInt("uid"));
		t.setTid(rs.getInt("tid"));
		t.setDrequest(rs.getDate("drequest"));
		t.setDprocess(rs.getDate("dprocess"));
		t.setType(rs.getString("type"));
		t.setDetails(rs.getString("details"));
		t.setAmount(rs.getFloat("amount"));
	}

	public Optional<List<Transaction>> retrieveAllTransactions(int uid) throws SQLException {
		this.stmt1 = con.prepareStatement(sql1);
		stmt1.setInt(1, uid);
		List<Transaction> transactions = new ArrayList<>();
		try (ResultSet rs = stmt1.executeQuery()) {
			while (rs.next()) {
				Transaction tr = new Transaction();
				setTransactionDetails(rs, tr);
				transactions.add(tr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(transactions);
	}

	public Optional<Transaction> addNewTransaction(Transaction t) throws SQLException {
		this.stmt2 = con.prepareStatement(sql2);
		stmt2.setInt(1, t.getUid());
		stmt2.setDate(2, t.getDrequest());
		stmt2.setDate(3, t.getDprocess());
		stmt2.setString(4, t.getType());
		stmt2.setString(5, t.getDetails());
		stmt2.setFloat(6, t.getAmount());
		stmt2.executeUpdate();
		return retrieveTransaction(t.getUid(), t.getTid());
	}

	public Optional<List<Transaction>> retrieveTransactionsInPeriod(int uid, String interval) throws SQLException {
		String sql32 = sql3 + interval + ")";
		//System.out.println(sql32);
		this.stmt3 = con.prepareStatement(sql32);
		stmt3.setInt(1, uid);
		List<Transaction> transactions = new ArrayList<>();
		try (ResultSet rs = stmt3.executeQuery()) {
			while (rs.next()) {
				Transaction tr = new Transaction();
				setTransactionDetails(rs, tr);
				transactions.add(tr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(transactions);
	}

	public Optional<List<Transaction>> retrieveTransactionsLastPeriod(int uid, String interval) throws SQLException {
		String sql42 = sql4 + "2 " + interval + ") AND DATE(NOW() - INTERVAL 1 " + interval + ")";
		//System.out.println(sql42);
		this.stmt4 = con.prepareStatement(sql42);
		stmt4.setInt(1, uid);
		List<Transaction> transactions = new ArrayList<>();
		try (ResultSet rs = stmt4.executeQuery()) {
			while (rs.next()) {
				Transaction tr = new Transaction();
				setTransactionDetails(rs, tr);
				transactions.add(tr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(transactions);
	}

	public Optional<Transaction> retrieveTransaction(int uid, int tid) throws SQLException {
		this.stmt5 = con.prepareStatement(sql5);
		stmt5.setInt(1, uid);
		stmt5.setInt(2, tid);
		Transaction transaction = new Transaction();
		try (ResultSet rs = stmt5.executeQuery()) {
			if (rs.next()) {
				setTransactionDetails(rs, transaction);
			} else {
				System.out.println("No such transaction found!");
				return Optional.empty();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(transaction);
	}

	public int countTransactions(int uid, String compare, String interval) throws SQLException {
		int result = 0;
		String sql62 = sql6 + interval + ") ";
		if (compare.equals(">=")) {
			sql62 += "AND t.amount >= 0 ";
		} else {
			sql62 += "AND t.amount < 0 ";
		}
		//System.out.println(sql62);
		stmt6 = con.prepareStatement(sql62);
		stmt6.setInt(1, uid);
		try (ResultSet rs = stmt6.executeQuery()) {
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public float sumTransactions(int uid, String compare, String interval) throws SQLException {
		float result = 0;
		String sql72 = sql7 + interval + ") ";
		if (compare.equals(">=")) {
			sql72 += "AND t.amount >= 0 ";
		} else {
			sql72 += "AND t.amount < 0 ";
		}
		//System.out.println(sql72);
		this.stmt7 = con.prepareStatement(sql72);
		stmt7.setInt(1, uid);
		try (ResultSet rs = stmt7.executeQuery()) {
			if (rs.next()) {
				result = rs.getFloat(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public float sumTransactionsLast(int uid, String interval) throws SQLException {
		float result = 0;
		String sql82 = sql8 + interval + ") AND DATE(NOW() - INTERVAL 1 " + interval + ")";
		//System.out.println(sql82);
		this.stmt8 = con.prepareStatement(sql82);
		stmt8.setInt(1, uid);
		try (ResultSet rs = stmt8.executeQuery()) {
			if (rs.next()) {
				result = rs.getFloat(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
