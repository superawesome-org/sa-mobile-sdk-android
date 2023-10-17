package tv.superawesome.sdk.publisher.components

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import tv.superawesome.sdk.publisher.testutil.ResourceReader
import kotlin.test.assertEquals

class XmlParserTest {

    private val sut = XmlParser()

    @Test
    fun `exists return true for an existing node`() {
        // Arrange
        val xml = ResourceReader.readResource("mock_xml_response_1.xml")
        val document = sut.parse(xml)

        // Act
        val result = sut.exists(
            node = document,
            name = "VAST"
        )

        // Assert
        assertTrue(result)
    }

    @Test
    fun `exists return false for an non-existing node`() {
        // Arrange
        val xml = ResourceReader.readResource("mock_xml_response_1.xml")
        val document = sut.parse(xml)

        // Act
        val result = sut.exists(
            node = document,
            name = "FALSE"
        )

        // Assert
        assertFalse(result)
    }

    @Test
    fun `findAll returns all nodes with the given name`() {
        // Arrange
        val xml = ResourceReader.readResource("mock_xml_response_2.xml")
        val document = sut.parse(xml)

        // Act
        val result = sut.findAll(
            node = document,
            name = "Impression"
        )

        // Assert
        assertEquals(3, result.size)
    }

    @Test
    fun `findFirst return the first node with the given name`() {
        // Arrange
        val xml = ResourceReader.readResource("mock_xml_response_2.xml")
        val document = sut.parse(xml)

        // Act
        val result = sut.findFirst(
            node = document,
            name = "Impression"
        )

        // Assert
        assertEquals("https://ads.staging.superawesome.tv/v2/impr1/", result?.textContent)
    }
}