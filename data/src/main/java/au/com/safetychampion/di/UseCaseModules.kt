package au.com.safetychampion.di

import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCasesModule = module {

    singleOf(::AssignManyTasksStatusItemUseCase)

    singleOf(::AssignTaskStatusItemUseCase)

    singleOf(::GetAllActiveTaskUseCase)
}
