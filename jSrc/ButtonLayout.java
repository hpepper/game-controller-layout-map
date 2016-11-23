import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * 
 */

/**
 *
 */
public class ButtonLayout extends dbLayerXmlControllerLayout {
	String m_sButtonName;
	int m_nJustificationPositionX;
	int m_nJustificationPositionY;
	char m_cJustification;
	int m_nLineToX;
	int m_nLineToY;

	public ButtonLayout(Node fstNode) {
		m_nJustificationPositionX=0;
		NamedNodeMap cNamedNodeMap = fstNode.getAttributes();
		Node cNode = cNamedNodeMap.getNamedItem("Name");
		m_sButtonName = cNode.getNodeValue();
		m_nJustificationPositionX = m_cXmlIf.GetChildDataBySingleTagNameInt((Element)fstNode, "JustificationPositionX");
		m_nJustificationPositionY = m_cXmlIf.GetChildDataBySingleTagNameInt((Element)fstNode, "JustificationPositionY");
		String sTmpJustification = m_cXmlIf.GetChildDataBySingleTagName((Element)fstNode, "Justification");
		m_cJustification = sTmpJustification.charAt(0);
		m_nLineToX = m_cXmlIf.GetChildDataBySingleTagNameInt((Element)fstNode, "LineToX");
		m_nLineToY = m_cXmlIf.GetChildDataBySingleTagNameInt((Element)fstNode, "LineToY");
		//System.out.println("   ButtonLayout: " + m_sButtonName + " - JustificationPositionX=" + m_nJustificationPositionX + " - JustificationPositionY=" + m_nJustificationPositionY + " Justification:" + m_cJustification);
	}


}
