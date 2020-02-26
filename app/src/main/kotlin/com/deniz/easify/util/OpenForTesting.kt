package com.deniz.easify.util

/**
 * @User: deniz.demirci
 * @Date: 2020-02-24
 */

@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class OpenClass

@OpenClass
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting
