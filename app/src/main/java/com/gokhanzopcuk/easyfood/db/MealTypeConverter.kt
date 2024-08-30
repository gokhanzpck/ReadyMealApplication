package com.gokhanzopcuk.easyfood.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.jar.Attributes

@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun fromAnyToString(attributes: Any?):String {
        if (attributes == null)
            return ""
            return attributes as String
    }
        @TypeConverter
        fun fromStringToAny(attribute: String?): Any {
            if (attribute == null)
                return ""
            return attribute
    }
}