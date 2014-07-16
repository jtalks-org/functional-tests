package org.jtalks.tests.antarcticle

import org.apache.commons.lang3.StringUtils

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic

class Article {
  String title = randomAlphabetic(40)
  String content = randomAlphabetic(60)

  String toString(){
    return "Article[$title, ${StringUtils.left(content, 20)}]"
  }
}
