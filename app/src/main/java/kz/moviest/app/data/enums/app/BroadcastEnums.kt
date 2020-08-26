package kz.moviest.app.data.enums.app

import kz.moviest.app.BuildConfig

enum class BroadcastEnums(val id: String) {

    FOREGROUND_PUSH("${BuildConfig.APPLICATION_ID}.foreground_push")

}