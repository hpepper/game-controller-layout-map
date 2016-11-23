import java.util.List;
import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 */

/**
 * This is the db layer for the Game Button and the coresponding actions.
 */
public class dbLayerXmlGameButtons extends dbLayerXml {

	/**
	 * Maybe later this should also support filters.
	 */
	public List<GameButton> GetGameButtons() {
		NodeList cNodeList = getTableList("Button");
		List<GameButton> lGameButtonList = new ArrayList<GameButton>();
		
		for(int nNodeIndex=0; nNodeIndex < cNodeList.getLength(); nNodeIndex++) {
			//System.out.println(cNodeList.item(nNodeIndex).getAttributes().getNamedItem("Headline"));
			// set the node.
            Node fstNode = cNodeList.item(nNodeIndex);
         // Only act on it if the node is an element.
            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
            	GameButton cGameButton = new GameButton(fstNode);
            	lGameButtonList.add(cGameButton);
            }
		} // next nnodeindex.
		return(lGameButtonList);
	}

	public String GetGameTitle() {
		return m_cXmlIf.GetRootElement().getAttribute("Title");
	}
	
	/**
	 * 
	 * @return Notes, or empty string if the no notes have been written.
	 */
	public String GetNotes() {
		String sNotes = "";
		sNotes=m_cXmlIf.GetChildDataBySingleTagName(m_cXmlIf.GetRootElement(), "Notes");
		if (sNotes == null) {
			sNotes="";
		}
		return(sNotes);
	}
} // end class.
