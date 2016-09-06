package com.devconference.stackoverflow

inline fun returningTrue(block: () -> Unit): Boolean {
  block()
  return true
}
