import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 */

/**
 *
 */
public class dbLayerXmlControllerLayout extends dbLayerXml {
	/**
	 * Maybe later this should also support filters.
	 */
	public List<ButtonLayout> GetGameButtons() {
		System.out.println("   dbLayerXmlControllerLayout::GetGameButtons()");
		NodeList cNodeList = getTableList("Button");
		List<ButtonLayout> lButtonLayoutList = new ArrayList<ButtonLayout>();
		
		for(int nNodeIndex=0; nNodeIndex < cNodeList.getLength(); nNodeIndex++) {
			//System.out.println(cNodeList.item(nNodeIndex).getAttributes().getNamedItem("Headline"));
			// set the node.
            Node fstNode = cNodeList.item(nNodeIndex);
         // Only act on it if the node is an element.
            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
            	ButtonLayout cButtonLayout = new ButtonLayout(fstNode);
            	lButtonLayoutList.add(cButtonLayout);
            }
		} // next nnodeindex.
		return(lButtonLayoutList);
	}

	public String GetGameTitle() {
		return m_cXmlIf.GetRootElement().getAttribute("Title");
	}

	public String GetTemplateFileName() {
		return m_cXmlIf.GetChildDataBySingleTagName(m_cXmlIf.GetRootElement(), "PictureFile");
	}

}
