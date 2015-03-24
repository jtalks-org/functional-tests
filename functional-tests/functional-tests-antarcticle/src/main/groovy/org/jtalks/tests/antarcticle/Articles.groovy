package org.jtalks.tests.antarcticle

class Articles {
  static Article create(Article article = new Article()){
    println "Creating $article"
    return article
  }

  static void assertArticleExists(Article article, String errorMessage = ''){
    println "Asserting article exists: $article"
    assert article, errorMessage
  }
}
