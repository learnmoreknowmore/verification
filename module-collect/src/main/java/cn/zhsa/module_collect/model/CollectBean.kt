package cn.zhsa.module_collect.model

data class CollectBean(
    var id:Long = 0,
    var title:String = "",
    var status:Int = 0,
    var time:Long = 0,
    var storeCover:String = ""
) {
}