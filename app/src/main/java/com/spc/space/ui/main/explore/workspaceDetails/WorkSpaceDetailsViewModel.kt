package com.spc.space.ui.main.explore.workspaceDetails

import androidx.lifecycle.ViewModel
import com.spc.space.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkSpaceDetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
}
