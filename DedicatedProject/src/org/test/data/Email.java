package org.test.data;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(detachable = "true")
public class Email implements Serializable {

	private static final long serialVersionUID = 4868878293612910012L;

	@Persistent
	String key;

	@Persistent
	String email;

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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return "[key=" + key + "] email=" + email;
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
					&& ((Email) o).getKey() != null
					&& this.getKey().equals(((Email) o).getKey())) {
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
