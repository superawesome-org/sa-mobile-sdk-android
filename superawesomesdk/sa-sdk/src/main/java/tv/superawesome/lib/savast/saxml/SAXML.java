package tv.superawesome.lib.savast.saxml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tv.superawesome.lib.sautils.SALog;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAXML {

    // private function
    private static void searchSiblingsAndChildrenOf(Node node, String name, List<Element> list) {

        // get the subnodes
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
                // get element
                Element e = (Element) subnodes.item(i);

                // add elements
                if (e.getTagName().equals(name)){
                    list.add(e);
                }

                // search recursevly
                if (e.getFirstChild() != null){
                    searchSiblingsAndChildrenOf(e.getFirstChild(), name, list);
                }
            }
        }
    }

    public static List<Element> searchSiblingsAndChildrenOf(Node node, String name) {
        List<Element> list = new ArrayList<Element>();
        searchSiblingsAndChildrenOf(node, name, list);
        return list;
    }

    public static Element findFirstInstanceInSiblingsAndChildrenOf(Node node, String name){
        List<Element> list = searchSiblingsAndChildrenOf(node, name);
        if (list.size() >= 1){
            return list.get(0);
        }

        return null;
    }

    public static void  searchSiblingsAndChildrenOf(Node node, String name, SAXMLIterator block) {
        List<Element> list = searchSiblingsAndChildrenOf(node, name);
        Iterator<Element> it = list.iterator();
        while (it.hasNext()){
            block.foundElement(it.next());
        }
    }

    public static boolean checkSiblingsAndChildrenOf(Node node, String name){
        List<Element> list = searchSiblingsAndChildrenOf(node, name);
        if (list.size() > 0){
            return true;
        }

        return false;
    }
}
