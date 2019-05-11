package com.loosu.alog.cache

import com.loosu.alog.BuildConfig

/**
 * default cache strategy for log
 */
internal class DefaultCache : DiskCache(BuildConfig.APPLICATION_ID) {

    override fun startSaveCache(size: Int): Boolean = size > 300
}