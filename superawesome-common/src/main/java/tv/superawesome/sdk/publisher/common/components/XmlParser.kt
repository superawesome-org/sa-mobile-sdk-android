package tv.superawesome.sdk.publisher.common.components

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.ArrayList
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

internal interface XmlParserType {
    /**
     * Function that parses a XML document
     *
     * @param xml xml string
     * @return a Document object
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    fun parse(xml: String): Document

    /**
     * Method that returns a list of XML elements after performing a thorough search of all
     * the node parameter's siblings and children, by a given "name".
     *
     * @param node the parent node
     * @param name the name to search for
     * @return a List of XML elements
     */
    fun findAll(node: Node, name: String): List<Element>

    /**
     * Finds only the first instance of a XML element with given name by searching in all of
     * the node parameter's siblings and children.
     *
     * @param node the parent node
     * @param name the name to search for
     * @return the first element found
     */
    fun findFirst(node: Node, name: String): Element?

    /**
     * Method that checks if in all children and siblings of a XML node, there exists
     * at least one element with given name
     *
     * @param node parent XML node
     * @param name name to search for
     * @return true if found any, false otherwise
     */
    fun exists(node: Node, name: String): Boolean
}

/**
 * Class that abstracts away the complexities of XML parsing into a series of utility methods
 */
internal class XmlParser : XmlParserType {

    @Throws(ParserConfigurationException::class, IOException::class, SAXException::class)
    override fun parse(xml: String): Document {
        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        val doc = db.parse(InputSource(ByteArrayInputStream(xml.toByteArray(charset("utf-8")))))
        doc.documentElement.normalize()
        return doc
    }

    override fun findAll(node: Node, name: String): List<Element> {
        val list: MutableList<Element> = ArrayList()
        searchSiblingsAndChildrenOf(node, name, list)
        return list
    }

    override fun findFirst(node: Node, name: String): Element? {
        val list = findAll(node, name)
        return list.firstOrNull()
    }

    override fun exists(node: Node, name: String): Boolean {
        val list = findAll(node, name)
        return list.isNotEmpty()
    }

    /**
     * Method that makes a search in the current node's siblings and children by a
     * given "name" string parameter.
     * It will return the result into the list of XML elements given as paramter.
     *
     * @param node the XML parent node
     * @param name the XML name ot search for
     * @param list a list of returned Elements
     */
    private fun searchSiblingsAndChildrenOf(node: Node, name: String, list: MutableList<Element>) {

        // get the sub-nodes
        var subnodes: NodeList = object : NodeList {
            override fun item(index: Int): Node? {
                return null
            }

            override fun getLength(): Int {
                return 0
            }
        }
        if (node.nodeType == Node.ELEMENT_NODE) {
            subnodes = (node as Element).getElementsByTagName(name)
        }
        if (node.nodeType == Node.DOCUMENT_NODE) {
            subnodes = (node as Document).getElementsByTagName(name)
        }
        for (i in 0 until subnodes.length) {
            if (subnodes.item(i).nodeType == Node.ELEMENT_NODE) {
                // get element
                val e = subnodes.item(i) as Element

                // add elements
                if (e.tagName == name) {
                    list.add(e)
                }

                // search recursively
                if (e.firstChild != null) {
                    searchSiblingsAndChildrenOf(e.firstChild, name, list)
                }
            }
        }
    }
}
