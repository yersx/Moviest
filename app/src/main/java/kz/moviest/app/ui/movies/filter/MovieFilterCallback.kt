package kz.moviest.app.ui.movies.filter

import java.io.Serializable

interface MovieFilterCallback : Serializable {

    fun onApplyBtnClick(filter: Map<String, Any>?)

}