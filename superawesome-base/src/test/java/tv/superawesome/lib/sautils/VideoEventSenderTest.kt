package tv.superawesome.lib.sautils

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterface

class VideoEventSenderTest {

    @Test
    fun `send adPlaying if not sent before`() {
        var timesSent = 0
        val eventSender = EventSender(1234)
        eventSender.listener = SAInterface { placement, event ->
            if (++timesSent >= 2) {
                fail()
            }
            assertEquals(1234, placement)
            assertEquals(SAEvent.adPlaying, event)
        }

        eventSender.adPlaying()
    }

    @Test
    fun `send adPlaying should not send if sent before`() {
        var timesSent = 0
        val eventSender = EventSender(1234)
        eventSender.listener = SAInterface { placement, event ->
            if (++timesSent >= 2) {
                fail()
            }
            assertEquals(1234, placement)
            assertEquals(SAEvent.adPlaying, event)
        }

        eventSender.adPlaying()
        eventSender.adPlaying()
    }

    @Test
    fun `send adShown should send if adPlaying was sent already`() {
        var timesSent = 0

        val eventSender = EventSender(1234)
        eventSender.listener = SAInterface { placement, event ->
            assertEquals(1234, placement)
            if (++timesSent == 1) {
                assertEquals(event, SAEvent.adPlaying)
            } else {
                assertEquals(event, SAEvent.adShown)
            }
        }

        eventSender.adPlaying()
        eventSender.adShown()
    }

    @Test
    fun `playing and paused can be sent one after the other`() {
        var timesSent = 0

        val eventSender = EventSender(1234)
        eventSender.listener = SAInterface { placement, event ->
            assertEquals(1234, placement)

            when(++timesSent) {
                1,3 -> assertEquals(event, SAEvent.adPlaying)
                2,4 -> assertEquals(event, SAEvent.adPaused)
            }
        }

        eventSender.adPlaying()
        eventSender.adPaused()
        eventSender.adPlaying()
        eventSender.adPaused()
    }
}