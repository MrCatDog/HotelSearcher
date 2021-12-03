package com.example.hotelsearcher.utils

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.util.Util
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.security.MessageDigest

class CutOffBorderTransformation(val borderSizeInPx: Int) : BitmapTransformation() {
    private val id = this::class.qualifiedName!!
    private val idBytes: ByteArrayInputStream = id.byteInputStream(Charset.forName("UTF-8"))

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap = Bitmap.createBitmap(
        toTransform,
        borderSizeInPx,
        borderSizeInPx,
        toTransform.width - borderSizeInPx * 2,
        toTransform.height - borderSizeInPx * 2
    )

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(idBytes.readBytes())

        val borderSizeInPxByte: ByteArray =
            ByteBuffer.allocate(Int.SIZE_BYTES).putInt(borderSizeInPx).array()
        messageDigest.update(borderSizeInPxByte)

    }

    override fun equals(other: Any?): Boolean {
        if (other is CutOffBorderTransformation) {
            return borderSizeInPx == other.borderSizeInPx
        }
        return false
    }

    override fun hashCode(): Int = Util.hashCode(
        id.hashCode(),
        Util.hashCode(borderSizeInPx)
    )

}