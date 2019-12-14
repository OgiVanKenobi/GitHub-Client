package com.example.smallpdftest.util

/**
 * Functional interface which consumes given data which type is set
 *
 * @param <T> object which will be consumed  </T>
 */
interface Consumer<T> {
    fun consume(arg: T)
}