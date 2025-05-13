package com.ayaan.kharbarkhotala.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.ayaan.kharbarkhotala.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConverter {

    @TypeConverter
    fun sourceToString(source: Source): String {
        return "${source.id},${source.name}"
    }
    @TypeConverter
    fun stringToSource(sourceString: String): Source {
        val source= sourceString.split(",").let{sourceArray->
            Source(
                id = sourceArray[0],
                name = sourceArray[1]
            )
        }
        return source
    }
}