package cn.zhsa.common.net

/**
 * 封装返回的数据
 */
class BaseResponse<T>(val code :Int,
                      val msg:String,
                      val data:T)