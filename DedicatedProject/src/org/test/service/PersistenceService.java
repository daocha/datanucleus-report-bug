package org.test.service;

import java.util.List;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.ObjectState;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.test.data.User;

public class PersistenceService extends JdoDaoSupport {

	public void persist(User user) {

		PersistenceManager pm = user == null ? null : JDOHelper
				.getPersistenceManager(user);

		// if no persistence manager attached, return new one.
		pm = getPersistenceManager();

		pm.makePersistent(user);

	}

	public User getUser(String userKey) {

		PersistenceManager pm = getPersistenceManager();

		Extent e = pm.getExtent(User.class, true);
		Query q = pm.newQuery(e);
		q.setFilter("this.key == my_key");
		q.declareParameters("String my_key");
		Object[] params = { userKey };

		q.addExtension("datanucleus.multivaluedFetch", "none");

		q.compile();

		List<User> entries = (List<User>) q.executeWithArray(params);

		if (entries.size() > 0) {
			return entries.get(0);
		} else {
			return null;
		}

	}

	public void flush() {
		PersistenceManager pm = getPersistenceManager();
		pm.flush();
	}

}
