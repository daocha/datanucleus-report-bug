package org.test.data;

import java.io.Serializable;
import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(detachable = "true")
public class User implements Serializable {

	private static final long serialVersionUID = 466730601526497810L;

	@Persistent
	String key;

	@Persistent
	ArrayList<Email> emails;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the emails
	 */
	public ArrayList<Email> getEmails() {
		return emails;
	}

	/**
	 * @param emails
	 *            the emails to set
	 */
	public void setEmails(ArrayList<Email> emails) {
		this.emails = emails;
	}

	public String toString() {
		String output = "[key=" + key + "] email={";
		if (emails != null) {
			for (Email email : emails) {
				output += email.toString();
			}
		}
		output += "}";
		return output;
	}

	@Override
	public int hashCode() {
		int objectNameHash = this.getClass().getName().hashCode();

		if (key == null)
			return objectNameHash;

		return 31 * objectNameHash + key.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		try {
			if (this != null && o != null && this.getKey() != null
					&& ((User) o).getKey() != null
					&& this.getKey().equals(((User) o).getKey())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
