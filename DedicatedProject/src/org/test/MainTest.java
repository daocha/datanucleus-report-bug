package org.test;

import java.util.ArrayList;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.test.data.Email;
import org.test.data.User;
import org.test.service.PersistenceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class MainTest {

	@Autowired
	PersistenceService persistenceService;

	@Test
	@Rollback(true)
	@Transactional(propagation = Propagation.REQUIRED)
	public void testMain() {

		User user = new User();

		ArrayList<Email> emailList = new ArrayList<Email>();

		user.setEmails(emailList);

		persistenceService.persist(user);

		persistenceService.flush();

		User userDB = persistenceService.getUser(user.getKey());

		// load old email list from attached object
		ArrayList<Email> oldEmailList = userDB.getEmails();

		// create a new list
		ArrayList<Email> newEmailList = new ArrayList<Email>();
		userDB.setEmails(newEmailList);

		persistenceService.persist(userDB);

		persistenceService.flush();

		// add a new email to old email list, theoretically it should not affect
		// the user.emails
		oldEmailList.add(new Email());

		assertTrue(oldEmailList.size() == 1);

		assertTrue(newEmailList.size() == 0);

		// ### why this fails? ###
		assertTrue(userDB.getEmails().size() == 0);

	}

}
