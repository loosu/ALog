package com.loosu.alog

/**
 * cache strategy for log
 */
abstract class CacheStrategy {

    /**
     * 是否缓存该log
     *
     * @param level log 等级
     * @return true: 缓存; false：不缓存
     */
    protected open fun needSave(level: Level): Boolean {
        return true
    }

    /**
     * 保存
     *
     * @param level     log 等级
     * @param tagObj    tag 标签
     * @param msgObj    msg 信息
     * @param throwable throwable 信息
     */

    fun save(level: Level, tagObj: Any?, msgObj: Any?, throwable: Throwable?) {
        if (needSave(level)) {
            onSave(level, tagObj, msgObj, throwable)
        }
    }

    protected abstract fun onSave(level: Level, tag: Any?, msg: Any?, throwable: Throwable?)
}
