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
			//get all transactions
			String sql1 = "SELECT t.*, u.username FROM transactions t, users u WHERE t.uid = u.id AND t.uid = ?";
			//insert new transaction
			String sql2 = "INSERT INTO transactions(tid, uid, drequest, dprocess, type, details, amount) VALUES (NULL, ?, ?, ?, ?, ?, ?)";
			
			//get all transactions from the past week/month/year
			String sql3 = "SELECT t.*, u.username FROM transactions t, users u WHERE t.uid = u.id and u.id = ? AND t.drequest >= DATE(NOW() - INTERVAL ?)";
			
			//get transaction summary from previous week/month/year
			String sql4 = "SELECT t.*, u.username FROM transactions t, users u WHERE t.uid = u.id and u.id = ? AND t.drequest BETWEEN DATE(NOW() - INTERVAL 2 ?) AND DATE(NOW() - INTERVAL 1 ?)";
			
			//get specific transaction
			String sql5 = "SELECT t.*, u.username FROM transactions t, users u WHERE t.uid = u.id AND t.uid = ? AND t.tid = ?";
			
			//noIncoming + noOutgoing = noOfTransactions
			String sql6 = "SELECT COUNT(*) FROM transactions t, users u WHERE t.uid = u.id AND t.uid = ? AND t.amount ? 0 AND t.drequest >= DATE(NOW() - INTERVAL ?)";
			//incoming + outgoing = balance;
			String sql7 = "SELECT SUM(t.amount) FROM transactions t, users u WHERE t.uid = u.id AND t.uid = ? AND t.amount ? 0 AND t.drequest >= DATE(NOW() - INTERVAL ?)";
			
			//comparedToLast
			String sql8 = "SELECT SUM(t.amount) FROM transactions t, users u WHERE t.uid = u.id AND t.uid = ? AND t.drequest BETWEEN DATE(NOW() - INTERVAL 2 ?) AND DATE(NOW() - INTERVAL 1 ?)";
			
			this.stmt1 = con.prepareStatement(sql1);
			this.stmt2 = con.prepareStatement(sql2);
			this.stmt3 = con.prepareStatement(sql3);
			this.stmt4 = con.prepareStatement(sql4);
			this.stmt5 = con.prepareStatement(sql5);
			this.stmt6 = con.prepareStatement(sql6);
			this.stmt7 = con.prepareStatement(sql7);
			this.stmt8 = con.prepareStatement(sql8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PreparedStatement stmt1, stmt2, stmt3, stmt4, stmt5, stmt6, stmt7, stmt8;

	private void setTransactionDetails(ResultSet rs, Transaction t) throws SQLException {
		System.out.println(rs);
		t.setUid(rs.getInt("uid"));
		t.setTid(rs.getInt("tid"));
		t.setDrequest(rs.getDate("drequest"));
		t.setDprocess(rs.getDate("dprocess"));
		t.setType(rs.getString("type"));
		t.setDetails(rs.getString("details"));
		t.setAmount(rs.getFloat("amount"));
	}

	public Optional<List<Transaction>> retrieveAllTransactions(int uid) throws SQLException {
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
		stmt3.setInt(1, uid);
		stmt3.setString(2, interval);
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
		stmt4.setInt(1, uid);
		stmt4.setString(2, interval);
		stmt4.setString(3, interval);
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

	public Optional<Transaction> retrieveTransaction(int uid, int tid) throws SQLException {
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
		stmt6.setInt(1, uid);
		stmt6.setString(2, compare);
		stmt6.setString(3, interval);
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
		stmt6.setInt(1, uid);
		stmt6.setString(2, compare);
		stmt6.setString(3, interval);
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
		stmt8.setInt(1, uid);
		stmt8.setString(2, interval);
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
