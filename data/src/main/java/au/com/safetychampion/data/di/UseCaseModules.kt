package au.com.safetychampion.data.di

import au.com.safetychampion.data.domain.usecase.action.CreateNewActionUseCase
import au.com.safetychampion.data.domain.usecase.action.EditActionUseCase
import au.com.safetychampion.data.domain.usecase.action.GetActionSignOffDetailsUseCase
import au.com.safetychampion.data.domain.usecase.action.GetListActionUseCase
import au.com.safetychampion.data.domain.usecase.activetask.AssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.UnAssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val useCasesModule = module {

    factoryOf(::AssignManyTasksStatusItemUseCase)

    factoryOf(::AssignTaskStatusItemUseCase)

    factoryOf(::GetAllActiveTaskUseCase)

    factoryOf(::AssignTaskUseCase)

    factoryOf(::UnAssignTaskUseCase)

    factoryOf(::CreateNewActionUseCase)

    factoryOf(::GetListActionUseCase)

    factoryOf(::GetActionSignOffDetailsUseCase)

    factoryOf(::EditActionUseCase)
}
