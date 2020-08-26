package kz.moviest.app.network.exceptions

import kz.moviest.app.data.models.network.error.ApiError

class ApiException(val apiError: ApiError) : Exception()