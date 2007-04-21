package net.spy.digg.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.spy.digg.User;


/**
 * User result parser.
 */
public class UsersParser extends PagedItemParser<User> {

	private Map<String, User> users=new HashMap<String, User>();

	/**
	 * Parse a users response from the given input stream.
	 */
	public UsersParser(InputStream is) throws SAXException, IOException {
		Document doc = getDocument(is, "users");

		parseCommonFields(doc);
		users=new HashMap<String, User>(getCount());

		NodeList nl=doc.getElementsByTagName("user");
		for(int i=0; i<nl.getLength(); i++) {
			Node n=nl.item(i);
			UserImpl u=new UserImpl(n);
			users.put(u.getName(), u);
			addItem(u);
		}
	}

	/**
	 * Get the users returned in this result (keyed off of username).
	 */
	public Map<String, User> getUsers() {
		return users;
	}

}
