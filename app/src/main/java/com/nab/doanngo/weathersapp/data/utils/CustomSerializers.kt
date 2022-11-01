package com.nab.doanngo.weathersapp.data.utils

import com.nab.doanngo.weathersapp.data.datasources.remote.entities.ResponseCode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Serializer to convert response code from string format to [ResponseCode] enum.
 */
object ResponseCodeSerializer : KSerializer<ResponseCode> {
    override fun deserialize(decoder: Decoder): ResponseCode {
        val code = decoder.decodeString()
        return ResponseCode.values()
            .find { value -> value.code == code.toInt() } ?: ResponseCode.Others
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(
            "ResponseCode",
            PrimitiveKind.INT
        )

    override fun serialize(encoder: Encoder, value: ResponseCode) {
        encoder.encodeString(value.code.toString())
    }
}
