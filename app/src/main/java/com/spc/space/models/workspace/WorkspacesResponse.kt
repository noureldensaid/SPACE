package com.spc.space.models.workspace

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkspacesResponse(
    val workSpace: List<WorkSpaceItem>? = emptyList(),
    val message: String? = null
) : Parcelable

@Parcelize
data class WorkSpaceItem(
    @SerializedName("_id")
    val id: String? = null,
    val avgRate: Int,
    val contact: WorkspaceContact,
    val description: String? = null,
    val images: List<String>? = emptyList(),
    val location: WorkspaceLocation,
    val name: String? = null,
    val ownerId: String? = null,
    val schedule: WorkspaceSchedule,
) : Parcelable

@Parcelize
data class WorkspaceLocation(
    @SerializedName("_id")
    val id: String? = null,
    val buildingNumber: String? = null,
    val city: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val region: String? = null,
    val streetName: String? = null
) : Parcelable

@Parcelize
data class WorkspaceSchedule(
    @SerializedName("_id")
    val id: String? = null,
    val closingTime: String? = null,
    val holidays: List<String>? = emptyList(),
    val openingTime: String? = null
) : Parcelable

@Parcelize
data class WorkspaceContact(
    @SerializedName("_id")
    val id: String? = null,
    val email: List<String>? = emptyList(),
    val phone: List<Int>? = emptyList(),
    val socialMedia: List<String>? = emptyList(),
) : Parcelable