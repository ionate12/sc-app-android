package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.visitor.domain.models.VisitorPayload

interface VisitorApi {
    class Token(
        body: VisitorPayload.Token
    ) : NetworkAPI.Post("/visitors/visit/token", body)

    class SiteFetch(
        body: VisitorPayload.SiteFetch
    ) : NetworkAPI.Post("/visitors/linkaccess/site/fetch", body)

    class FormFetch(
        body: VisitorPayload.FormFetch
    ) : NetworkAPI.Post("/visitors/linkaccess/form/fetch", body)

    class Arrive(
        body: VisitorPayload.Arrive
    ) : NetworkAPI.Post("/visitors/linkaccess/visit/arrive", body)

    class Leave(
        body: VisitorPayload.Leave
    ) : NetworkAPI.Post("/visitors/linkaccess/visit/leave", body)

    class EvidenceFetch(
        body: VisitorPayload.EvidencesFetch
    ) : NetworkAPI.Post("/visitors/linkaccess/visit/fetch", body)
}
