package org.jtalks.tests.antarcticle

class User {
  /**
   * This is temporary solution, waiting for registration feature. Should be replaced by random values.
   */
  String username = 'admin'
  String password = 'admin'

  String toString(){
    return "[$username, $password]"
  }
}
