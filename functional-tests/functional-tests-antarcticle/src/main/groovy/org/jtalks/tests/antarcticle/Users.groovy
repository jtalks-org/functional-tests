package org.jtalks.tests.antarcticle

class Users {
  static User signIn(User user = new User()){
    println "Signing in $user"
    return user
  }
}
