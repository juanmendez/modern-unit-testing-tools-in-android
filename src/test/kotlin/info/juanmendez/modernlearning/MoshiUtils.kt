package info.juanmendez.modernlearning

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types.newParameterizedType
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.support.AnnotationConsumer
import java.nio.charset.Charset
import java.util.stream.Stream

class MoshiUtils {
    private var myMoshi = Moshi.Builder()
        .build()

    val moshi: Moshi
        get() = myMoshi

    fun readResourceFile(fileLocation: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileLocation)
        return inputStream?.readBytes()?.toString(Charset.defaultCharset()) ?: ""
    }

    inline fun <reified T> fromJsonList(fileLocation: String): MutableList<T>? {
        val jsonString = readResourceFile(fileLocation)
        val type = newParameterizedType(MutableList::class.java, T::class.java)
        val adapter: JsonAdapter<MutableList<T>> = moshi.adapter(type)
        return adapter.fromJson(jsonString)
    }
}

@ArgumentsSource(JsonArgumentsProvider::class)
annotation class FileSource(val text: String)

class JsonArgumentsProvider : ArgumentsProvider, AnnotationConsumer<FileSource> {
    private lateinit var fileLocation: String

    override fun accept(annotation: FileSource) {
        this.fileLocation = annotation.text
    }

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments>? {
        return MoshiUtils().fromJsonList<Map<String, String>>(fileLocation)?.stream()?.map {
            Arguments.of(it)
        }
    }
}