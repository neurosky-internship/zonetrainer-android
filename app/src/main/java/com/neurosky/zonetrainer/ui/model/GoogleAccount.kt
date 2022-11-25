package com.neurosky.zonetrainer.ui.model

import android.net.Uri
import android.os.Parcelable
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoogleAccount(
    val id: String,
    val token: String,
    val name: String,
    val photoUrl: Uri
) : Parcelable

fun GoogleSignInAccount.toModel() =
    GoogleAccount(
        id = this.id!!,
        token = this.idToken!!,
        name = this.displayName!!,
        photoUrl = this.photoUrl!!
    )
