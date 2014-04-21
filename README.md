###IntelliJ IDEA set up for work with functional tests
1. Make sure that you have installed JDK, Maven and GIT and set all environment variables
from [wiki-guide](http://jtalks.org/pages/viewpage.action?pageId=6422581)
2. Download [IntelliJ IDEA](http://www.jetbrains.com/idea/download/index.html) (choose Community Edition FREE)
3. Clone repository for functional tests via Git or IntelliJ IDEA.
4. Open file **pom.xml** with IntelliJ IDEA
5. Open menu **File** - **Project Structure**. Select **Project** item and under **Project SDK** select JDK.
Choose folder where you installed JDK.
6. Add Maven to your IntelliJ IDEA: go to **File** - **Settings** - **Maven**. Select checkbox **Override** at the **Maven home directory** item.
Paste path to Maven folder in textbox.
7. Go to menu **Run** - **Edit** Configuration.
Add new configuration for type "TestNG". Fill **Name** and **Suite** fields with path to testng.xml,
i.e. %path_to_project%/functional-tests-jcommune/src/test/resources/testng.xml.
Fill "Working directory" field with %path_to_project%.
Set "Use classpath of module" to maven module name (functional-tests-jcommune)
Press "Ok".

####Running with Local Selenium Server
If you'd like to use your local browser to run tests, [download selenium server](http://selenium.googlecode.com/files/selenium-java-2.35.0.zip),
unzip it and run as `java -jar selenium-server-standalone-2.35.0.jar`. It will start on http://localhost:4444/wd/console.
Now in IntelliJ you have to specify env vars: **Edit Configuration -> Environment Variables**:
- SELENIUM_VERSION - your browser version (e.g. 20)
- SELENIUM_BROWSER - your browser name (firefox)
- SELENIUM_URL - http://localhost:4444/wd/hub

In your testng.xml file you should also set:
```xml
<parameter name="webDriverUrl" value="http://localhost:4444/wd/hub"/>
```

###Functional Tests Best Practices
* Do not use XPath if possible, use IDs or CSS. XPaths depend on all the elements in the hierarchy of HTML. If you need
  to find a link and you're doing something like this `//tbody/tr/td/a[contains(@href,'branches')]` you'll get in
  trouble when at least one of the elements `tbody`, `tr`, `td`, `a` changes. The less elements to be changed,
  the better. Moreover XPath usually is a bit complicated.
