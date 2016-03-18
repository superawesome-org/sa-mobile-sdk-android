package tv.superawesome.lib.savast;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAXML {

    /**
     * private function that makes the most generic search
     * @param node - the XML parent node
     * @param name - the XML name ot search for
     * @param list - a list of returned Elements
     */
    private static void searchSiblingsAndChildrenOf(Node node, String name, List<Element> list) {

        /** get the subnodes */
        NodeList subnodes = new NodeList() {
            @Override
            public Node item(int index) {
                return null;
            }

            @Override
            public int getLength() {
                return 0;
            }
        };

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            subnodes = ((Element) node).getElementsByTagName(name);
        }

        for (int i = 0; i < subnodes.getLength(); i++){
            if (subnodes.item(i).getNodeType() == Node.ELEMENT_NODE){
                /** get element */
                Element e = (Element) subnodes.item(i);

                /** add elements */
                if (e.getTagName().equals(name)){
                    list.add(e);
                }

                /** search recursevly */
                if (e.getFirstChild() != null){
                    searchSiblingsAndChildrenOf(e.getFirstChild(), name, list);
                }
            }
        }
    }

    /**
     * Public search function that returns a List of XML elements
     * @param node - the parent node
     * @param name - the name to search for
     * @return - a List of XML elements
     */
    public static List<Element> searchSiblingsAndChildrenOf(Node node, String name) {
        List<Element> list = new ArrayList<Element>();
        searchSiblingsAndChildrenOf(node, name, list);
        return list;
    }

    /**
     * Finds only the first instance of a XML element with given name
     * @param node - the parent node
     * @param name - the name to search for
     * @return - the first element found
     */
    public static Element findFirstInstanceInSiblingsAndChildrenOf(Node node, String name){
        List<Element> list = searchSiblingsAndChildrenOf(node, name);
        if (list.size() >= 1){
            return list.get(0);
        }

        return null;
    }

    /**
     * The same function as above, but with an iterator block
     * @param node - the XML parent node
     * @param name - the name of the XML element
     * @param block - a block of type SAXMLIterator
     */
    public static void  searchSiblingsAndChildrenOf(Node node, String name, SAXMLIterator block) {
        List<Element> list = searchSiblingsAndChildrenOf(node, name);
        Iterator<Element> it = list.iterator();
        while (it.hasNext()){
            block.foundElement(it.next());
        }
    }

    /**
     * Function that checks if in all children and siblings of a XML node, there exists
     * at least one element with given name
     * @param node - parent XML node
     * @param name - name to search for
     * @return true if found any, false otherwise
     */
    public static boolean checkSiblingsAndChildrenOf(Node node, String name){
        List<Element> list = searchSiblingsAndChildrenOf(node, name);
        if (list.size() > 0){
            return true;
        }

        return false;
    }

    /**
     * ************************************************************
     * Created by gabriel.coman on 22/12/15.
     * ************************************************************
     */
    public interface SAXMLIterator {
        /**
         * small iterator function
         * @param e - XML element as callback return parameter
         */
        void foundElement(Element e);
    }

}
