/** 
 * XmlIf.java
 * NAME
 *  XmlIf.java
 *
 * SYNOPSIS
 *  Simple XML if for Java. Make it a bit easier to access the xml structures.
 *
 * EXAMPLE
 *  import SqlconstructorMlXml.SqlconstructorMlXml;
 *  import SqlconstructorMlXml.XmlIf;
 *  
 *  public class TrainingPlan {
 *     SqlconstructorMlXml m_cSqlconstructorMlXml;
 *     InitModule(sFileName);
 *     List<XmlIf> lXmlIf = m_cSqlconstructorMlXml.XmlIf("");
 *  }
 *  
 *  
 *  
 *  
 *  java -jar 
 *  
 *
 * SEE ALSO
 * 
 * --------------------------------------------------
 * HISTORY
 * Date       Name              Description
 * ---------- ----------------  ---------------------
 * yyyy-mm-dd sqlconstructor    Auto generated.
 *
 * javadoc --src xml2web.pl --doc doc --singlefile --html
 *
 * @author      sqlconstructor
 * @version     %I%, %G%
 * @since       1.0
 */



import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlIf  { 


	
	Document m_cXmlDoc = null;
	
	/**
	* Constructor. Create the Document structure from the XML file given in sXmlFileName.
	* @param sXmlFileName
	* @throws ParserConfigurationException
	* @throws SAXException
	* @throws IOException
	* TODO V Verify that the sXmlFileName exists before using it.
	*/
	public XmlIf(String sXmlFileName) throws ParserConfigurationException, SAXException, IOException {
		File file = new File(sXmlFileName);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		m_cXmlDoc = db.parse(file);
		m_cXmlDoc.getDocumentElement().normalize();
		//System.out.println("Root element " + m_cXmlDoc.getDocumentElement().getNodeName());
		
	} // end constructor.
	
	/**
	*
	* @param cBaseElement
	* @param sTagName
	* @return
	*/
	public String GetChildDataBySingleTagName(Element cBaseElement, String sTagName) {
		NodeList fstNmElmntLst = cBaseElement.getElementsByTagName(sTagName);
		Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
		
		NodeList fstNm = fstNmElmnt.getChildNodes();
		String sReturnData=null;
		
		if (fstNm.getLength() > 0 )   {
			sReturnData = ((Node) fstNm.item(0)).getNodeValue();
		}
		return (sReturnData);
	} // end GetChildDataBySingleTagName.
	
	
	/**
	*
	* @param cBaseElement
	* @param sTagName
	* @return
	*/
	public List<String> GetChildDataListBySingleTagName(Element cBaseElement, String sTagName) {
		List<String> lReturnList = new ArrayList<String>();
		
		NodeList fstNmElmntLst = cBaseElement.getElementsByTagName(sTagName);

		for (int nIndex=0; nIndex<fstNmElmntLst.getLength(); nIndex++ )   {
			lReturnList.add( ( fstNmElmntLst.item(nIndex)).getTextContent() );
		}

		return (lReturnList);
	} // end GetChildDataBySingleTagName.	
	
	
	public int GetChildDataBySingleTagNameInt(Element cBaseElement, String sTagName) {
		int nData=0;
		String sTmpData = GetChildDataBySingleTagName(cBaseElement, sTagName);
		if (sTmpData != null ) {
			nData = Integer.valueOf( sTmpData ).intValue();
		}
		return(nData);
	}
	
	
	/**
	* GetNodeListByTagName: Returns a list of nodes where the tagname is sTagName
	* @param sTagName - Name of tag to look for.
	* @return NodeList
	*
	* e.g.:
	* NodeList nodeLst = cXmlIf.GetNodeListByTagName("dir");
	*/
	public NodeList GetNodeListByTagName(String sTagName) {
		NodeList nodeLst = GetNodeListByTagName(m_cXmlDoc.getDocumentElement(), sTagName);
		return (nodeLst);
	} // end NodeList
	
	/**
	* GetNodeListByTagName: Returns a list of nodes where the tagname is sTagName
	* @param sTagName - Name of tag to look for.
	* @return NodeList
	*
	* e.g.:
	* NodeList nodeLst = cXmlIf.GetNodeListByTagName("dir");
	*/
	public NodeList GetNodeListByTagName(Element xmlElement, String sTagName) {
		NodeList nodeLst = xmlElement.getElementsByTagName(sTagName);
		return (nodeLst);
	} // end NodeList

	/**
	 * 
	 * @return root element, if there is no document then 'null' is returned.
	 */
	public Element GetRootElement() {
		Element cReturnElement;
		if ( m_cXmlDoc != null) {
			cReturnElement = m_cXmlDoc.getDocumentElement();
		} else {
			cReturnElement = null;
		}
		return(cReturnElement);
	}
} // end class.




// This ends the java module/package definition.
