package cn.zhsa.common.api


import cn.zhsa.common.data.LoginResult
import cn.zhsa.common.net.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 * Api 接口
 */

interface ApiService{
    /**
     * 登录接口
     */
    @GET("login")
    fun login(@QueryMap map: MutableMap<String,Any>):Observable<BaseResponse<LoginResult>>
}