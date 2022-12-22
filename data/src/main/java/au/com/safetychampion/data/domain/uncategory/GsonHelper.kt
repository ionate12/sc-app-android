package au.com.safetychampion.data.domain.uncategory

import android.graphics.Bitmap
import android.net.Uri
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableLong
import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.models.contractor.ContractorLink
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.customvalues.CustomValueOption
import au.com.safetychampion.data.domain.models.noticeboard.NoticeboardBlock
import au.com.safetychampion.data.domain.models.visitor.SCBaseMessage
import au.com.safetychampion.data.domain.models.visitor.VisitorPayload
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.BitmapSerializer
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.UriSerializer
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.ObservableField.LiveDataTypeConverter
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.ObservableField.OFBooleanTypeAdapter
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.ObservableField.OFNumberTypeAdapter
import au.com.safetychampion.scmobile.gsonTypeConverter.typeAdapter.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.scmobile.gsonTypeConverter.typeAdapter.ObservableField.* // ktlint-disable no-wildcard-imports
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class GsonHelper {
    companion object {
        @JvmStatic
        fun getGsonBuilder(): GsonBuilder {
            return GsonBuilder()
                .registerTypeAdapter(Uri::class.java, UriSerializer())
                .registerTypeAdapter(Bitmap::class.java, BitmapSerializer())
                .registerTypeAdapter(object : TypeToken<ObservableField<String>>() {}.type, OFStringTypeAdapter())
                .registerTypeAdapter(ObservableNullableBoolean::class.java, OFNullableBooleanTypeAdapter())
                .registerTypeAdapter(ObservableLong::class.java, OFLongTypeAdapter())
                .registerTypeAdapter(ObservableInt::class.java, OFNumberTypeAdapter())
                .registerTypeAdapter(MutableLiveData::class.java, LiveDataTypeConverter())
                .registerTypeAdapter(ObservableBoolean::class.java, OFBooleanTypeAdapter())
                .registerTypeAdapter(ContractorLink::class.java, ContractorLinkTypeAdapter())
                .registerTypeAdapter(CustomValueOption::class.java, CustomValueOptionTypeAdapter())
                .registerTypeAdapter(CustomValue::class.java, CustomValueTypeAdapter())
                .registerTypeAdapter(VisitorPayload.Form::class.java, VisitorFormPayloadTypeAdapter())
                .registerTypeAdapter(NoticeboardBlock::class.java, NoticeboardBlockTypeAdapter())
                .registerTypeAdapter(SCBaseMessage::class.java, SCBaseMessageTypeAdapter())
        }

        @JvmStatic
        fun getGson(): Gson {
            TODO("this function will be removed")
        }

        @JvmStatic
        fun getGsonSerializeNulls(): Gson {
            return getGsonBuilder().serializeNulls().create()
        }

        val CLEAN_INSTANCE = GsonBuilder().create()
    }
}

fun getGson(): Gson = TODO("this function will be removed")
fun getGsonSerializeNulls() = GsonHelper.getGsonSerializeNulls()
