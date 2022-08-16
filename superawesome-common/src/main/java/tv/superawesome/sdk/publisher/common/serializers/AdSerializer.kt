package tv.superawesome.sdk.publisher.common.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.Creative
import tv.superawesome.sdk.publisher.common.models.CreativeDetail
import tv.superawesome.sdk.publisher.common.models.CreativeFormatType

class AdSerializer: KSerializer<Ad> {

	override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Ad") {
		element<Int?>("advertiserId")
		element<Int>("publisherId")
		element<Float>("moat")
		element<Boolean>("is_fill")
		element<Boolean>("is_fallback")
		element<Int>("campaign_type")
		element<Int>("campaign_id")
		element<Boolean>("is_house")
		element<Boolean>("safe_ad_approved")
		element<Boolean>("show_padlock")
		element<Int>("line_item_id")
		element<Boolean>("test")
		element<Int>("app")
		element<String>("device")
		element<Creative>("creative")
	}

	override fun serialize(encoder: Encoder, value: Ad)
	{
		encoder.encodeStructure(descriptor) {

			// Optionals
			value.advertiserId?.let { encodeIntElement(descriptor, 0, value.advertiserId) }

			// Non-Optionals
			encodeIntElement(descriptor, 1, value.publisherId)
			encodeFloatElement(descriptor, 2, value.moat)
			encodeBooleanElement(descriptor, 3, value.isFill)
			encodeBooleanElement(descriptor, 4, value.isFallback)
			encodeIntElement(descriptor, 5, value.campaignType)
			encodeIntElement(descriptor, 6, value.campaignId)
			encodeBooleanElement(descriptor, 7, value.isHouse)
			encodeBooleanElement(descriptor, 8, value.safeAdApproved)
			encodeBooleanElement(descriptor, 9, value.showPadlock)
			encodeIntElement(descriptor, 10, value.lineItemId)
			encodeBooleanElement(descriptor, 11, value.test)
			encodeIntElement(descriptor, 12, value.app)
			encodeStringElement(descriptor, 13, value.device)

			// Custom Types
			encodeSerializableElement(descriptor, index = 14, Creative.serializer(), value.creative)
		}
	}

	override fun deserialize(decoder: Decoder): Ad =
		decoder.decodeStructure(descriptor) {

			var advertiserId: Int? = null
			var publisherId = 0
			var moat = 0.0f
			var isFill = false
			var isFallback = false
			var campaignType = 0
			var campaignId = 0
			var isHouse = false
			var safeAdApproved = false
			var showPadlock = false
			var lineItemId = 0
			var test = false
			var app = 0
			var device = ""
			var creative = Creative(
				0,
				null,
				CreativeFormatType.ImageWithLink,
				null,
				CreativeDetail(
					null,
					null,
					null,
					"",
					null,
					0,
					0,
					0,
					null
				)
			)

			while (true) {
				when (val index = decodeElementIndex(descriptor)) {
					0 -> advertiserId = decodeIntElement(descriptor, 0)
					1 -> publisherId = decodeIntElement(descriptor, 1)
					2 -> moat = decodeFloatElement(descriptor, 2)
					3 -> isFill = decodeBooleanElement(descriptor, 3)
					4 -> isFallback = decodeBooleanElement(descriptor, 4)
					5 -> campaignType = decodeIntElement(descriptor, 5)
					6 -> campaignId = decodeIntElement(descriptor, 6)
					7 -> isHouse = decodeBooleanElement(descriptor, 7)
					8 -> safeAdApproved = decodeBooleanElement(descriptor, 8)
					9 -> showPadlock = decodeBooleanElement(descriptor, 9)
					10 -> lineItemId = decodeIntElement(descriptor, 10)
					11 -> test = decodeBooleanElement(descriptor, 11)
					12 -> app = decodeIntElement(descriptor, 12)
					13 -> device = decodeStringElement(descriptor, 13)
					14 -> creative = decodeSerializableElement(descriptor, 14, Creative.serializer())
					CompositeDecoder.DECODE_DONE -> break
					else -> error("Unexpected index: $index")
				}
			}

			if (showPadlock && creative.isKSF) {
				showPadlock = false
			}

			Ad(
				advertiserId,
				publisherId,
				moat,
				isFill,
				isFallback,
				campaignType,
				campaignId,
				isHouse,
				safeAdApproved,
				showPadlock,
				lineItemId,
				test,
				app,
				device,
				creative
			)
		}
}