package org.jtalks.tests.antarcticle

import spock.lang.Specification

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic

class ArticleManagementTest extends Specification {
  def 'article title negative cases'() {
    given: 'user is created and logged in'
    when: "user creates an article with $caseName"
    then: 'article should not get created'
    where:
      caseName          | articleTitle         | messageIfCaseFailed
      'Empty title'     | ''                   | 'Empty article was possible to create while this is not allowed'
      'Spaces in title' | '  '                 | 'Title with only spaces should not be allowed!'
      'Too long title'  | randomAlphabetic(61) | 'It was possible to create an article with too long title'
  }

  def 'article title positive cases'() {
    given: 'user is created and logged in'
    when: "user creates an article with $caseName"
    then: "article should not get created"
    where:
      caseName        | articleTitle         | messageIfCaseFailed
      'Min boundary'  | randomAlphabetic(1)  | 'Could not create article with min boundary'
      'Max boundary'  | randomAlphabetic(60) | 'Could not create an article with max boundary'
      'Average title' | randomAlphabetic(30) | 'Could not create an article of average length'
  }
}  
