package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.domain.models.reviewplan.ReviewPlanSignoffTaskPL
import au.com.safetychampion.data.domain.usecase.reviewplan.FilterRVPlanPL

interface ReviewPlanAPI {
    class Actions(reviewPlanId: String) :
        NetworkAPI.Get(
            path = "reviewplans/$reviewPlanId/links/actions"
        ),
        IStorable

    class List(filter: FilterRVPlanPL) :
        NetworkAPI.Post(
            path = "reviewplans/list",
            body = filter
        ),
        IStorable

    class Fetch(
        reviewPlanId: String
    ) :
        NetworkAPI.Get(
            path = "reviewPlans/$reviewPlanId/fetch"
        ),
        IStorable

    class Task(
        reviewPlanId: String,
        taskId: String
    ) : NetworkAPI.Get(
        path = "reviewPlans/$reviewPlanId/tasks/$taskId"
    )

    class Tasks(
        reviewPlanId: String
    ) :
        NetworkAPI.Get(
            path = "reviewPlans/$reviewPlanId/tasks"
        ),
        IStorable

    class SignOff(
        reviewPlanId: String,
        taskId: String,
        payload: ReviewPlanSignoffTaskPL
    ) : NetworkAPI.PostMultiParts(
        path = "reviewPlans/$reviewPlanId/tasks/$taskId/signoff",
        body = payload
    )

    class EvidencesTask(reviewPlanId: String) :
        NetworkAPI.Get(
            path = "reviewPlans/$reviewPlanId/tasks/"
        )
}
