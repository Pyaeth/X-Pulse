package com.anaem.xpulsebo.service;

import org.springframework.stereotype.Service;

import com.anaem.xpulsebo.dao.UserDAO;
import com.anaem.xpulsebo.model.Preferences;
import com.anaem.xpulsebo.model.User;

import java.util.Optional;

@Service
public class UserService {

	UserDAO dao = new UserDAO();

	public Optional<User> addNewUser(User u) throws Exception {
		return dao.addNewUser(u);
	}

	public Optional<User> deleteUser(User u) throws Exception {
		return dao.deleteUser(u);
	}

	public Optional<User> changeFirstName(User u) throws Exception {
		return dao.changeFirstName(u);
	}

	public Optional<User> changeLastName(User u) throws Exception {
		return dao.changeLastName(u);
	}

	public Optional<User> changeRole(User u) throws Exception {
		return dao.changeRole(u);
	}

	public Optional<User> changeUsername(User u) throws Exception {
		return dao.changeUsername(u);
	}

	public Optional<User> changeUserPreferences(int id, Preferences prefs) throws Exception {
		Optional<User> optUser = this.findUser(id);
		if (optUser.isPresent()) {
			User u = optUser.get();
			Preferences preferences = u.getPreferences();
			if (prefs.getCurrencyCode() != null)
				preferences.setCurrencyCode(prefs.getCurrencyCode());
			if (prefs.getTimeFrame() != null)
				preferences.setTimeFrame(prefs.getTimeFrame());
			u.setPreferences(preferences);
			return dao.changeUserPreferences(u);
		}
		return Optional.empty();
	}

	public Optional<User> changePassword(User u, String nP) throws Exception {
		return dao.changePassword(u, nP);
	}

	public Optional<User> getUserLogin(String username, String password) throws Exception {
		return dao.getUserLogin(username, password);
	}

	private Optional<User> findUser(int id) throws Exception {
		return dao.findUserById(id);
	}
}
