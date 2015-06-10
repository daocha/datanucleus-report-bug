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
		Email email = new Email();
		email.setEmail("ray@example.com");
		emailList.add(email);

		user.setEmails(emailList);

		assertTrue(emailList == user.getEmails());

		persistenceService.persist(user);

		persistenceService.flush();

		// User user = persistenceService.getUser("91");

		// load old email list from attached object
		ArrayList<Email> oldEmailList = user.getEmails();

		// create a new list
		ArrayList<Email> newEmailList = new ArrayList<Email>();
		user.setEmails(newEmailList);

		/*
		 * ######## Normally, as java developers, we think newEmailList is
		 * definitely equivalent to user.getEmails(). But the reality is not. It
		 * happens very often if someone just sets a new empty list to a field
		 * and in the later stage he processes some business logic and adds some
		 * data to that list. From their perspective, it's the same reference so
		 * they may not have the consciousness to call that setter again.
		 * ########
		 */
		assertTrue(newEmailList == user.getEmails());

		persistenceService.persist(user);

		persistenceService.flush();

		// add a new email to old email list, theoretically it should not affect
		// the user.emails
		oldEmailList.add(new Email());
		newEmailList.add(new Email());

		assertTrue(oldEmailList.size() == 2);

		assertTrue(newEmailList.size() == 1);

		// ### why this fails? ###
		assertTrue(user.getEmails().size() == 1);

	}

}
