package org.jtalks.tests.antarcticle

import spock.lang.Specification

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic

class ArticleManagementTest extends Specification {

  def 'article title positive cases'() {
    given: 'user is created and logged in'
    when: "user creates an article with $caseName"
    then: 'article should not get created'
    where:
      caseName        | articleTitle         | messageIfCaseFailed
      'Min boundary'  | randomAlphabetic(1)  | 'Could not create article with min title boundary'
      'Max boundary'  | randomAlphabetic(60) | 'Could not create an article with max title boundary'
      'Average title' | randomAlphabetic(30) | 'Could not create an article of average title length'
  }

  def 'article title negative cases'() {
    given: 'user is created and logged in'
    when: "user creates an article with $caseName"
    then: 'article should not get created'
    where:
      caseName          | articleTitle         | messageIfCaseFailed
      'Empty title'     | ''                   | 'Article with empty title was possible to create while this is not allowed'
      'Spaces in title' | '  '                 | 'Title with only spaces should not be allowed!'
      'Too long title'  | randomAlphabetic(61) | 'It was possible to create an article with too long title'
  }

  def 'article with valid content should be created (positive cases)'() {
    given: 'user is created and logged in'
      Users.signIn();
    when: "user creates an article with $caseName"
      Article article = Articles.create(new Article(content: articleContent))
    then: 'article should not get created'
      Articles.assertArticleExists(article, messageIfCaseFailed)
    where:
      caseName        | articleContent           | messageIfCaseFailed
      'Empty content' | ''                       | 'Could not create article with empty body'
      'Max boundary'  | randomAlphabetic(65000)  | 'Could not create an article with max content boundary'
      'Average title' | randomAlphabetic(100)    | 'Could not create an article of average content length'
  }

  def 'translation title positive cases'() {
    given: 'user is created and logged in'
      and: 'article is created and opened'
    when: "user creates a translation with $caseName"
    then: 'translation should get created'
    where:
      caseName        | articleTitle         | messageIfCaseFailed
      'Min boundary'  | randomAlphabetic(1)  | 'Could not create translation of article with min title boundary'
      'Max boundary'  | randomAlphabetic(60) | 'Could not create a translation of article with max title boundary'
      'Average title' | randomAlphabetic(30) | 'Could not create a translation of article of average title length'
  }

  def 'translation title negative cases'() {
    given: 'user is created and logged in'
      and: 'article is created and opened'
    when: "user creates a translation with $caseName"
    then: 'translation should not get created'
    where:
      caseName          | articleTitle         | messageIfCaseFailed
      'Empty title'     | ''                   | 'Translation of article with empty title was possible to create while this is not allowed'
      'Spaces in title' | ' '                  | 'Title with only spaces should not be allowed!'
      'Too long title'  | randomAlphabetic(61) | 'It was possible to create a translation of article with too long title'
  }

  def 'translation content positive cases'() {
    given: 'user is created and logged in'
      and: 'article is created and opened'
    when: "user creates a translation with $caseName"
    then: 'translation should get created'
    where:
      caseName        | articleContent          | messageIfCaseFailed
      'Empty content' | ''                      | 'Could not create translation of article with empty body'
      'Max boundary'  | randomAlphabetic(65000) | 'Could not create a translation of article with max content boundary'
      'Average title' | randomAlphabetic(100)   | 'Could not create a translation of article of average content length'
  }

  def "translation content negative cases"() {
    given: 'user is created and logged in'
      and: 'article is created and opened'
    when: "user creates a translation with $caseName"
    then: 'translation of article should not get created'
    where:
      caseName          | articleContent          | messageIfCaseFailed
      'Max boundary +1' | randomAlphabetic(65001) | 'It was possible to create a translation of article with too long content'
  }

  def "create translation as anonymous user (should fail)"() {
    given: 'article is created and opened'
    when: "user creates a translation"
    then: 'translation of article should not get created'
  }

  def "create second translation"() {
    given: 'user is created and logged in'
      and: 'article is created and opened'
      and: 'translation in English is created'
    when: "user creates a translation on $language"
    then: 'translation of article should get created'
    where:
      language        | translationLanguage | messageIfCaseFailed
      'second'        | $randomLanguage     | 'Could not create second translation of article'
      'same language' | 'English'           | 'Could not create translation of article on same language'
  }

}