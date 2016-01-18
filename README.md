jwatson
=======

A Java wrapper for the IBM Watson DQA service.

Getting started
---------------

The latest release is [jwatson 1.0](https://github.com/tudarmstadt-lt/jwatson/releases/tag/1.0.0).

Build
---------------

Use `sbt clean compile assembly` to build the project. The fat `jar` file can be found in the `/target` folder.

Usage - In a Nutshell
-----
```java
public static void main(String[] args) throws IOException {
   // Create a Watson instance with your URL and credentials
   JWatson watson = new JWatson("username", "password", "https://url/instance/xxxxx/deepqa/");

   // Query Watson to retrieve answers for a specific question
   // WatsonAnswer answer = watson.askQuestion("Who is Angela Merkel?");

   // ... or use the QuestionBuilder to construct complex questions for Watson
   WatsonQuestion question = new WatsonQuestion.QuestionBuilder("Who is Angela Merkel?")
        .setNumberOfAnswers(3) // Provide three possible answers
        .formatAnswer()        // Instruct Watson to deliver answers in HTML
        .create();

   WatsonAnswer answer = watson.askQuestion(question);
   System.out.println(answer);
}        
```

#### Note

* Make sure that the URL contains a trailing slash at the end.


Known bugs and issues
----------------
* Ping and Feedback endpoints are not implemented yet.
* To use JWatson with the Bluemix QA service instead of an university (private-) instance, you need make slightly modifications in the JSON structure.  

License
-------

```
Copyright 2016 Technische Universität Darmstadt.

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
