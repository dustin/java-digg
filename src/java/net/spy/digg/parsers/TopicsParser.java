package net.spy.digg.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.spy.digg.TopicContainer;


/**
 * Parse the topics.
 */
public class TopicsParser extends BaseParser {

	private Map<String, TopicContainer> containers;

	/**
	 * Construct a TopicsParser with the given stream.
	 */
	public TopicsParser(InputStream is) throws SAXException, IOException {
		Document doc = getDocument(is, "topics");

		// First, build out all of the containers.
		containers=new HashMap<String, TopicContainer>();

		// Build out the topics.
		NodeList nl=doc.getDocumentElement().getElementsByTagName("topic");
		for(int i=0; i<nl.getLength(); i++) {
			Node n=nl.item(i);
			String name=getAttr(n, "name");
			String shortName=getAttr(n, "short_name");

			NodeList nlTmp=n.getChildNodes();
			Node cn=null;
			for(int j=0; j<nlTmp.getLength(); j++) {
				if(nlTmp.item(j).getNodeName().equals("container")) {
					cn=nlTmp.item(j);
				}
			}
			assert cn != null : "Couldn't find a container";
			assert cn.getNodeName().equals("container")
				: "Expected container, got " + cn.getNodeName();
			String cname=getAttr(cn, "name");
			String cshortName=getAttr(cn, "short_name");

			TopicContainer tc=containers.get(cshortName);
			if(tc == null) {
				tc=new TopicContainerImpl(cname, cshortName);
				containers.put(cshortName, tc);
			}
			tc.add(new TopicImpl(name, shortName));
		}
	}

	/**
	 * Get a map of all topic containers by short name.
	 */
	public Map<String, TopicContainer> getContainers() {
		return containers;
	}
}
