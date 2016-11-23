import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * 
 */

/**
 *
 */
public class GameButton {
	String m_sButtonName;
	String m_sButtonAction;

	public GameButton(Node fstNode) {
		NamedNodeMap cNamedNodeMap = fstNode.getAttributes();
		Node cNode = cNamedNodeMap.getNamedItem("Name");
		m_sButtonName = cNode.getNodeValue();
		//m_sButtonAction = fstNode.getNodeValue();
		cNode = fstNode.getFirstChild();
		if ( cNode != null ) {
			m_sButtonAction = cNode.getNodeValue();			
		} else {
			m_sButtonAction = "";
		}
		//System.out.println("   GameButton: " + m_sButtonName + " - " + m_sButtonAction);
	}

}
