package tv.superawesome.sdk.publisher.common.extensions

import org.junit.Test
import kotlin.test.assertEquals

class InputStreamTests {

    @Test
    fun `readAllBytesLegacy converts input stream to byte array`() {
        // given
        val inputText = "test"
        val inputStream = inputText.byteInputStream()

        // when
        val result = inputStream.readAllBytesLegacy()

        // then
        assertEquals(String(result), inputText)
    }

    @Test
    fun `readAllBytesLegacy converts input stream to byte array with empty string`() {
        // given
        val inputText = ""
        val inputStream = inputText.byteInputStream()

        // when
        val result = inputStream.readAllBytesLegacy()

        // then
        assertEquals(String(result), inputText)
    }

    @Test
    fun `readAllBytesLegacy converts input stream to byte array with long string`() {
        // given
        val inputText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultrices " +
                "nisl quis nulla dictum aliquet. Duis malesuada ex odio, at laoreet arcu tincidunt " +
                "vitae. Curabitur egestas pharetra sodales. Aliquam sit amet massa eget sem " +
                "imperdiet posuere. Suspendisse potenti. Praesent ipsum sem, blandit vel suscipit" +
                " a, fringilla eget dolor. Vivamus rhoncus dapibus elit nec vehicula.\n" +
                "Quisque pellentesque dui a mauris tempus hendrerit. Ut cursus ornare risus," +
                " quis rutrum nulla feugiat ac. Morbi tincidunt finibus condimentum. Quisque " +
                "hendrerit velit id purus ornare, non gravida orci dignissim. Fusce ac luctus " +
                "libero. Curabitur hendrerit vestibulum semper. Morbi orci eros, porttitor " +
                "eget orci at, tempor lobortis lorem. Mauris at ligula iaculis, consequat " +
                "dui ac, malesuada quam. Maecenas luctus suscipit eros, eget bibendum massa " +
                "luctus lobortis. Phasellus tempor, tortor sit amet eleifend ultrices, enim " +
                "felis suscipit justo, ac accumsan magna quam et libero.\n" +
                "Proin quis ex vel ex gravida tincidunt in a velit. Sed ut volutpat tortor, " +
                "vitae convallis diam. In congue mattis lobortis. Aliquam a ullamcorper felis." +
                " Proin eget suscipit nisl. Nullam non ipsum sollicitudin, lobortis odio commodo," +
                " finibus arcu. Vivamus est libero, sodales a tincidunt nec, viverra nec diam." +
                " Suspendisse quam sem, efficitur in erat nec, tempor ultrices risus. Sed lacus" +
                " ante, venenatis sed vehicula faucibus, rhoncus sit amet turpis. Orci varius " +
                "natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. " +
                "Nam sed est mattis nunc faucibus ultricies. Suspendisse semper, arcu " +
                "ultricies posuere sollicitudin, purus felis iaculis massa, eu ultrices " +
                "felis dui at dolor.\n" +
                "Sed nec orci eget dolor porta gravida eget feugiat eros. In urna ex, " +
                "condimentum in molestie quis, aliquet sagittis orci. Vestibulum efficitur " +
                "ex metus, in lobortis nisi laoreet quis. Phasellus quis euismod sapien. " +
                "Integer accumsan venenatis tempor. Ut pharetra justo eu dolor pellentesque " +
                "interdum. Aenean vitae pellentesque lorem. Fusce ac erat quis velit imperdiet" +
                " mattis quis non felis. Sed sollicitudin eget nulla a congue. Phasellus libero" +
                " dui, egestas sed ex sed, lacinia laoreet libero. Aliquam rutrum, nunc nec" +
                " mollis suscipit, diam massa gravida turpis, non congue justo lectus eu neque." +
                " Aenean lectus ante, efficitur ac rhoncus in, semper non elit. Nam lacinia " +
                "eget mi at placerat.\n" +
                "Ut fermentum velit dapibus sodales ultrices. In sit amet augue nisl. " +
                "Suspendisse varius nisl vitae est interdum, vitae elementum leo porta. " +
                "Etiam in sem turpis. Sed semper orci in imperdiet pulvinar. Ut sit amet " +
                "interdum quam, sit amet tristique odio. Aliquam nec volutpat orci. Integer " +
                "vitae tincidunt nulla, at porta ex. Aliquam sit amet tempus dui. Aenean " +
                "tincidunt commodo augue eget eleifend. Suspendisse lobortis tortor " +
                "ullamcorper pellentesque mattis."

        val inputStream = inputText.byteInputStream()

        // when
        val result = inputStream.readAllBytesLegacy()

        // then
        assertEquals(String(result), inputText)
    }
}
