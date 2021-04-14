package tv.superawesome.sdk.publisher.common.testutil

import android.os.Build
import java.lang.reflect.Field
import java.lang.reflect.Modifier

object BuildUtil {
    fun mockSdkInt(sdkInt: Int) {
        val field = Build.VERSION::class.java.getField("SDK_INT")
        field.isAccessible = true
        Field::class.java.getDeclaredField("modifiers").also {
            it.isAccessible = true
            it.set(field, field.modifiers and Modifier.FINAL.inv())
        }
        field.set(null, sdkInt)
    }
}
