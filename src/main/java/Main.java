/*
 *  Copyright 2016 Technische Universität Darmstadt
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Contributors:
 *      - Uli Fahrer
 *
 */

import jwatson.JWatson;
import jwatson.answer.WatsonAnswer;
import jwatson.question.WatsonQuestion;

import java.io.IOException;

public class Main {
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
}
