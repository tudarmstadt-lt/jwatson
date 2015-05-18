/*
 *  Copyright 2015 Technische Universität Darmstadt
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

package jwatson;

import jwatson.answer.WatsonAnswer;
import jwatson.feedback.Feedback;
import jwatson.question.WatsonQuestion;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Watson API adapter class.
 *
 * @author Uli Fahrer
 */
public class JWatson implements IWatsonRestService {

    private static Logger logger = Logger.getLogger(JWatson.class.getName());

    private final String url;
    private final String username;
    private final String password;

    private final Executor executor;

    public JWatson(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
        
        this.executor = Executor.newInstance().auth(username, password);
    }

    /**
     * Pings the service to verify that it is available.
     *
     * @return <code>true</code> if the service is available. Otherwise <code>false</code>.
     */
    public boolean ping() {
        HttpResponse response = null;
        try {
            Executor executor = Executor.newInstance().auth(username, password);
            URI serviceURI = new URI(url + "/v1/ping").normalize();

            System.out.print(serviceURI.toString());

            response = executor.execute(Request.Get(serviceURI)
                    .addHeader("X-SyncTimeout", "30")).returnResponse();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Got the following error: " + ex.getMessage(), ex);
        }
        int statusCode = response.getStatusLine().getStatusCode();

        //200: Service is available, 500: Server error
        return (statusCode == 200);
    }

    /**
     * Ask Watson a question via the Question and Answer API.
     *
     * @param questionText question to ask Watson
     * @return WatsonAnswer
     */
    public WatsonAnswer askQuestion(String questionText) {
        WatsonQuestion question = new WatsonQuestion.QuestionBuilder(questionText).create();
        return queryService(question);
    }

    public WatsonAnswer askQuestion(WatsonQuestion question) {
        return queryService(question);
    }

    public boolean sendFeedback(Feedback feedback) {
        HttpResponse response = null;
        try {
            Executor executor = Executor.newInstance().auth(username, password);
            URI serviceURI = new URI(url + "/v1/feedback").normalize();

            response = executor.execute(Request.Put(serviceURI)
                    .addHeader("Accept", "application/json")
                    .addHeader("X-SyncTimeout", "30")
                    .bodyString(feedback.toJson().toString(), ContentType.APPLICATION_JSON)).returnResponse();


        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Got the following error: " + ex.getMessage(), ex);
        }

        int statusCode = response.getStatusLine().getStatusCode();

        // 201 - Accepted, 400 - Bad request. Most likely the result of a missing required parameter.
        return (statusCode == 201);
    }

    private WatsonAnswer queryService(WatsonQuestion question) {
        Content content = null;
        try {
            URI serviceURI = new URI(url + "/v1/question").normalize();
            
            Response response = executor.execute(Request.Post(serviceURI)
                    .addHeader("Accept", "application/json")
                    .addHeader("X-SyncTimeout", "30")
                    .bodyString(question.toJsonString(), ContentType.APPLICATION_JSON));

            content = response.returnContent();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Got the following error: " + ex.getMessage(), ex);
        }
        
        return WatsonAnswer.createAnswerFromContent(content);
    }
}
