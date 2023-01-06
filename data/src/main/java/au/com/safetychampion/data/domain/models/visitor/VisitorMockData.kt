package au.com.safetychampion.data.domain.models.visitor

import au.com.safetychampion.data.domain.models.ManualQRCodeInputModel

/**
 * Created by Minh Khoi MAI on 24/11/20.
 */

object VisitorMockData {
    val visitorSitePayload: String = """
            {
        "_id": "5fb1b388d8e89b4f006d071b",
        "type": "core.module.visitor.site",
        "tier": {
          "type": "core.tier.T3",
          "_id": "5fb1b0cdd8e89b4f006d060f"
        },
        "forms": [
          {
            "role": {
              "type": "core.module.visitor",
              "title": "Customer"
            },
            "arrive": {
              "form": {
                "_id": "5fb1b368d8e89b4f006d071a",
                "type": "core.module.visitor.form",
                "tier": {
                  "type": "core.tier.T3",
                  "_id": "5fb1b0cdd8e89b4f006d060f"
                },
                "title": "My Sample Form - 1",
                "description": "This is a description",
                "messages": {
                  "pre": null,
                  "in": {
                    "type": "video/youtube",
                    "value": "This is my message!"
                  },
                  "post": {
                    "type": "text/html",
                    "value": "Goodbye!"
                  }
                },
                "createdBy": {
                  "type": "core.user",
                  "_id": "5fb1b0cfd8e89b4f006d0613",
                  "name": "Pete Kiehn",
                  "email": "u3_1@cm56.co"
                },
                "tzDateCreated": "UTC",
                "dateCreated": "2020-11-15",
                "cusvals": [
                  {
                    "type": "color",
                    "required": true,
                    "title": "Question 1?",
                    "description": null,
                    "_id": "99qPi9U5C"
                  }
                ]
              },
              "messages": {
                "pre": null,
                "in": {
                  "type": "text/html",
                  "value": "<p><strong>The test of html view</strong></p><ol><li><strong>awesome 1</strong></li><li><font color='red'>red _test color</font></li><li><span style='color: rgb(230, 0, 0);'>red with style attribute</span></li><li>awesome normal style</li><li><strong>awesome bold again</strong></li></ol><h1>h1 normal style</h1><h2>h2 styling</h2>"
                },
                "post": {
                  "type": "text/html",
                  "value": "Goodbye, stay safe."
                }
              }
            },
            "leave":  {
                "form": {
                  "_id": "5fb1b368d8e89b4f006d071a",
                  "type": "core.module.visitor.form",
                  "tier": {
                    "type": "core.tier.T3",
                    "_id": "5fb1b0cdd8e89b4f006d060f"
                  },
                  "title": "My Sample Form - 1",
                  "description": "This is a description",
                  "messages": {
                    "pre": null,
                    "in": {
                      "type": "text/html",
                      "value": "This is my message!"
                    },
                    "post": {
                      "type": "text/html",
                      "value": "Goodbye!"
                    }
                  },
                  "createdBy": {
                    "type": "core.user",
                    "_id": "5fb1b0cfd8e89b4f006d0613",
                    "name": "Pete Kiehn",
                    "email": "u3_1@cm56.co"
                  },
                  "tzDateCreated": "UTC",
                  "dateCreated": "2020-11-15",
                  "cusvals": [
                    {
                      "type": "color",
                      "required": true,
                      "title": "Question 1?",
                      "description": null,
                      "_id": "99qPi9U5C"
                    }
                  ]
                },
                "messages": {
                  "pre": null,
                  "in": {
                    "type": "text/html",
                    "value": "<p><strong>The test of html view</strong></p><ol><li><strong>awesome 1</strong></li><li><font color='red'>red _test color</font></li><li><span style='color: rgb(230, 0, 0);'>red with style attribute</span></li><li>awesome normal style</li><li><strong>awesome bold again</strong></li></ol><h1>h1 normal style</h1><h2>h2 styling</h2>"
                  },
                  "post": {
                    "type": "text/html",
                    "value": "Goodbye, stay safe."
                  }
                }
              }
          }, {
            "role": {
              "type": "core.module.hr",
              "title": "Staff"
            },
            "arrive": {
              "form": {
                "_id": "5fb1b368d8e89b4f006d071a",
                "type": "core.module.visitor.form",
                "tier": {
                  "type": "core.tier.T3",
                  "_id": "5fb1b0cdd8e89b4f006d060f"
                },
                "title": "My Sample Form - 1",
                "description": "This is a description",
                "messages": {
                  "pre": null,
                  "in": {
                    "type": "text/html",
                    "value": "This is my message!"
                  },
                  "post": {
                    "type": "text/html",
                    "value": "Goodbye!"
                  }
                },
                "createdBy": {
                  "type": "core.user",
                  "_id": "5fb1b0cfd8e89b4f006d0613",
                  "name": "Pete Kiehn",
                  "email": "u3_1@cm56.co"
                },
                "tzDateCreated": "UTC",
                "dateCreated": "2020-11-15",
                "cusvals": [
                  {
                    "type": "color",
                    "required": true,
                    "title": "Question 1?",
                    "description": null,
                    "_id": "99qPi9U5C"
                  }
                ]
              },
              "messages": {
                "pre": null,
                "in": {
                  "type": "text/html",
                  "value": "<p><strong>The test of html view</strong></p><ol><li><strong>awesome 1</strong></li><li><font color='red'>red _test color</font></li><li><span style='color: rgb(230, 0, 0);'>red with style attribute</span></li><li>awesome normal style</li><li><strong>awesome bold again</strong></li></ol><h1>h1 normal style</h1><h2>h2 styling</h2>"
                },
                "post": {
                  "type": "text/html",
                  "value": "Goodbye, stay safe."
                }
              }
            },
            "leave":  {
                "form": {
                  "_id": "5fb1b368d8e89b4f006d071a",
                  "type": "core.module.visitor.form",
                  "tier": {
                    "type": "core.tier.T3",
                    "_id": "5fb1b0cdd8e89b4f006d060f"
                  },
                  "title": "My Sample Form - 1",
                  "description": "This is a description",
                  "messages": {
                    "pre": null,
                    "in": {
                      "type": "text/html",
                      "value": "This is my message!"
                    },
                    "post": {
                      "type": "text/html",
                      "value": "Goodbye!"
                    }
                  },
                  "createdBy": {
                    "type": "core.user",
                    "_id": "5fb1b0cfd8e89b4f006d0613",
                    "name": "Pete Kiehn",
                    "email": "u3_1@cm56.co"
                  },
                  "tzDateCreated": "UTC",
                  "dateCreated": "2020-11-15",
                  "cusvals": [
                    {
                      "type": "color",
                      "required": true,
                      "title": "Question 1?",
                      "description": null,
                      "_id": "99qPi9U5C"
                    }
                  ]
                },
                "messages": {
                  "pre": null,
                  "in": {
                    "type": "text/html",
                    "value": "<p><strong>The test of html view</strong></p><ol><li><strong>awesome 1</strong></li><li><font color='red'>red _test color</font></li><li><span style='color: rgb(230, 0, 0);'>red with style attribute</span></li><li>awesome normal style</li><li><strong>awesome bold again</strong></li></ol><h1>h1 normal style</h1><h2>h2 styling</h2>"
                  },
                  "post": {
                    "type": "text/html",
                    "value": "Goodbye, stay safe."
                  }
                }
              }
          }
        ],
        "title": "Jorel Site Test v1",
        "description": "desc test update",
        "category": "ab1c",
        "categoryOther": null,
        "subcategory": "xy1z",
        "subcategoryOther": null,
        "createdBy": {
          "type": "core.user",
          "_id": "5fb1b0cfd8e89b4f006d0613",
          "name": "Pete Kiehn",
          "email": "u3_1@cm56.co"
        },
        "tzDateCreated": "Asia/Manila",
        "dateCreated": "2020-11-16"
      }
    """.trimIndent()
    val mockProfileId = "PROFILE_00001"

    val qrManualInput = ManualQRCodeInputModel("https://dev.safetychampion.tech/visitor/5efbeb98c6bac31619e11bc9/signin/5ff9d596f5b0744ca4c10bdd")

    val evidenceStringData = """
        {"_id":"6050dcd977024920ceb58e9e","arrive":{"_id":"5fbb06bbd19334706f1c64ab","cusvals":[{"_id":"ATS_OxixU","required":false,"title":"Question 1?","type":"color","value":"#D3FFAD","verified":true}],"date":"2021-03-16","time":{"hours":23,"minutes":29},"type":"core.module.visitor.form","tz":"Asia/Ho_Chi_Minh"},"isAutoSignOutActive":false,"site":{"_id":"5fc575243935f022ce713aff","category":"ABC","control":{"geoArrive":false,"geoLeave":true,"pin":false},"description":"Come here to have a party babe","forms":[{"arrive":{"form":{"_id":"5fbb06bbd19334706f1c64ab","createdBy":{"_id":"5efbeba4c6bac31619e11be4","email":"demomanager@safetychampion.online","name":"Nadia Gorczany","type":"core.user"},"cusvals":[{"_id":"ATS_OxixU","required":true,"title":"Question 1?","type":"color","value":"#D3FFAD","verified":true}],"dateCreated":"2020-11-23","description":"This is a description","messages":{"in":{"type":"text/html","value":"This is my message!"},"post":{"type":"text/html","value":"Goodbye!"}},"selectedRole":{"_id":"f93aM5TiF-","title":"Visitor","type":"core.module.visitor"},"tier":{"_id":"5efbeb98c6bac31619e11bc9","type":"core.tier.T3"},"title":"My Sample Form - 2","type":"core.module.visitor.form","tzDateCreated":"UTC"},"messages":{"in":{"type":"text/html","value":"\u003cp\u003eMy Middle Message\u003c/p\u003e"},"post":{"type":"text/html","value":"\u003cp\u003eMy Ending Message\u003c/p\u003e"},"pre":{"type":"text/html","value":"\u003cp\u003eMy intro message.\u003c/p\u003e"}}},"leave":{"form":{"_id":"5fbb238bd19334706f1c64b2","type":"core.module.visitor.form"},"messages":{"in":{"type":"text/html","value":"\u003cp\u003eMy leave Middle message\u003c/p\u003e"},"post":{"type":"text/html","value":"\u003cp\u003eMy leave End Message\u003c/p\u003e"}}},"role":{"_id":"f93aM5TiF-","title":"Visitor","type":"core.module.visitor"}}],"geoData":{"lat":10.863028023808093,"lon":106.73931197290649},"geofenceRadius":915,"subcategory":"Blue","tier":{"VISIT_TERMS":{"arrive":"Arrive","leave":"Leave"},"_id":"5efbeb98c6bac31619e11bc9","name":"ABC Warehousing","type":"core.tier.T3"},"title":"Minh\u0027s Visitor Site","type":"core.module.visitor.site"},"token":"d45257726f214718efe4403e8359dd60-1caecaa2c540b8a300a3f99b8ec7310d7856de0504ec57fef963943b1267f15ee637615a1b64ff2a595b022b45955cb717ddea7b5b5d4c31f565be760e546898f6e165a348e8df1189ce53ab90238eebca40b247538226e022a6ae021ff67945","type":"core.module.visitor.visit","visitor":{"pii":{"email":"mmkhoi@gmail.com","name":"Minh Khoi Mai","phone":"1234567890","phoneCountryCode":"061"},"role":{"_id":"f93aM5TiF-","title":"Visitor","type":"core.module.visitor"}}}
    """.trimIndent()
}
