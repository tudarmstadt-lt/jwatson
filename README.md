jwatson [![Build Status](https://magnum.travis-ci.com/Tooa/jwatson.svg?token=fU3LeFzvsi3Z9zuFsRzz)](https://magnum.travis-ci.com/Tooa/jwatson)
=======

A Java wrapper for the IBM Watson DQA service

Getting started
---------------

The latest release is [1.0](https://github.com/...).

It is available in Maven Central as
[](http://search.maven.org/#artifactdetails%7C):

```xml
<dependency>
  <groupId></groupId>
  <artifactId></artifactId>
  <version>1.0</version>
</dependency>
```


Usage - In a Nutshell
-----
```java
// Create a Watson instance with your URL and credentials
Watson watson = new Watson("someuser", "xxxxxxx", "link to watson instance");

// Query Watson to retrieve answers for a specific question
WatsonAnswer answer = watson.askQuestion("Who is Angela Merkel?");

// ... or use the QuestionBuilder to construct complex questions for Watson
WatsonQuestion question = new WatsonQuestion.QuestionBuilder("Who is Anglea Merkel?")
                .setNumberOfAnswers(3) // Provide three possible answers
                .formatAnswer()        // Instruct Watson to deliver answers in HTML 
                .create();
WatsonAnswer answer = watson.askQuestion(question);             
```

Question & Answer Service
-------

Feedback Service
-------

Ping Service
----
```java
boolean isAvailable = watson.ping();
System.out.print(isAvailable ? "Watson Service is available" : "Watson Service is not available");
```

License
-------

```
Copyright 2015 Technische Universität Darmstadt.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

See also
--------
[IBM Watson Developer Cloud API Reference](http://www.ibm.com/smarterplanet/us/en/ibmwatson/developercloud/apis/#!/Question_Answer)
