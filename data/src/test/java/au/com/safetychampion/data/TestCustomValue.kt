package au.com.safetychampion.data

import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.customvalues.CustomValueDropdown
import au.com.safetychampion.data.domain.models.customvalues.CustomValuePL
import au.com.safetychampion.data.domain.models.customvalues.CustomValueString
import au.com.safetychampion.util.GsonManager
import au.com.safetychampion.util.IGsonManager
import au.com.safetychampion.util.koinGet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Test
import org.koin.dsl.ModuleDeclaration

class TestCustomValue : BaseTest() {
    val templateData = """
        [
          {
            "title": "add",
            "description": null,
            "type": "select_multiple",
            "required": true,
            "options": [
              {
                "name": "1"
              },
              {
                "name": "2",
                "options": [
                  {
                    "name": "3"
                  },
                  {
                    "name": "4"
                  }
                ]
              },
              {
                "name": "3"
              }
            ],
            "_id": "J7ZB2t7jp"
          },
          {
            "title": "Name",
            "description": null,
            "type": "text",
            "required": true,
            "_id": "C5YvMqaPl"
          },
          {
            "title": "DropDown",
            "description": "DropDown",
            "type": "select_single",
            "required": false,
            "options": [
              {
                "name": "Bill Wang (bill.wang@safetychampion.online)"
              },
              {
                "name": "ssss"
              }
            ],
            "_id": "ZlvloL7UE"
          },
          {
            "title": "Checkbox",
            "description": "Checkbox",
            "type": "tel",
            "required": false,
            "_id": "bAOaPvnV-"
          }
        ]
    """.trimIndent()

    lateinit var gson: Gson

    override fun declareModules(): ModuleDeclaration = {
        single<IGsonManager> { GsonManager() }
    }

    @Test
    fun testCustomValueDeserializer() {
        gson = koinGet<IGsonManager>().gson
        val token = object : TypeToken<List<CustomValue>>() {}.type
        val data: List<CustomValue> = gson.fromJson(templateData, token)
        assert(data[0] is CustomValueDropdown)
        assert(data[1] is CustomValueString)
        assert(data[2] is CustomValueDropdown)
        assert(data[3] is CustomValueString)
    }

    @Test
    fun testCustomPLSerializer() {
        gson = koinGet<IGsonManager>().gson
        val token = object : TypeToken<List<CustomValue>>() {}.type
        val data: List<CustomValue> = gson.fromJson(templateData, token)
        data[0].trySet(listOf(listOf("1")))
        data[1].trySet("12345")
        data[3].trySet("123 456")
        assert(data[0].value == listOf(listOf("1")))
        assert(data[1].value == "12345")
        assert(data[2].value == null)
        assert(data[3].value == "123 456")
        val pl: List<CustomValuePL> = data.map { it.toPayload() }
        val json = gson.toJsonTree(pl).asJsonArray
        assert(json[0].asJsonObject.has("value"))
        assert(!json[2].asJsonObject.has("value"))
    }
}
