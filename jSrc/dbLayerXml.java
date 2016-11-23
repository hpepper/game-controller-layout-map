import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
import jSrc.dbLayer;
 * 
 */

/**
 * 
 *
 */
public class dbLayerXml extends dbLayer {

	// TODO C This can't be static I need to solve some other way.
	static XmlIf m_cXmlIf;
	
	public void connectToDatabase(String sDatabaseName) {
		 try {
			m_cXmlIf = new XmlIf(sDatabaseName);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public NodeList getTableList(String sTableName) {
		// TODO V Throw an exception if the Root Element is null, that is probably because connectToDatabase() hasn'tbeen called.
		Element cRootElement = m_cXmlIf.GetRootElement();
		return(m_cXmlIf.GetNodeListByTagName(cRootElement, sTableName));
	} // end getTableList.
	
	public void disconnectFromDatabase() {
		
	}


	
}
