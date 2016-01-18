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

package jwatson;

import jwatson.answer.WatsonAnswer;
import jwatson.feedback.Feedback;
import jwatson.question.WatsonQuestion;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Watson API adapter class.
 *
 * @author Uli Fahrer
 */
public class JWatson implements IWatsonRestService {

    private static Logger logger = Logger.getLogger(JWatson.class.getName());

    private final URL url;
    private final String username;
    private final String password;

    public JWatson(String username, String password, String url) throws MalformedURLException {
        this.username = username;
        this.password = password;
        this.url = new URL(url);

        setAuthentication();
    }


    private void setAuthentication() {
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(JWatson.this.username,
                        JWatson.this.password.toCharArray());
            }
        });
    }


    /**
     * Pings the service to verify that it is available.
     *
     * @return <code>true</code> if the service is available. Otherwise <code>false</code>.
     */
    public boolean ping() {
//        HttpResponse response = null;
//        try {
//            Executor executor = Executor.newInstance().auth(username, password);
//            URI serviceURI = new URI(url + "/v1/ping").normalize();
//
//            System.out.print(serviceURI.toString());
//
//            response = executor.execute(Request.Get(serviceURI)
//                    .addHeader("X-SyncTimeout", "30")).returnResponse();
//
//        } catch (Exception ex) {
//            logger.log(Level.SEVERE, "Got the following error: " + ex.getMessage(), ex);
//        }
//        int statusCode = response.getStatusLine().getStatusCode();
//
//        //200: Service is available, 500: Server error
//        return (statusCode == 200);
        return false;
    }

    /**
     * Ask Watson a question via the Question and Answer API.
     *
     * @param questionText question to ask Watson
     * @return WatsonAnswer
     * @throws IOException
     */
    public WatsonAnswer askQuestion(String questionText) throws IOException {
        WatsonQuestion question = new WatsonQuestion.QuestionBuilder(questionText).create();
        return queryService(question);
    }

    public WatsonAnswer askQuestion(WatsonQuestion question) throws IOException {
        return queryService(question);
    }

    public boolean sendFeedback(Feedback feedback) {
//        HttpResponse response = null;
//        try {
//            Executor executor = Executor.newInstance().auth(username, password);
//            URI serviceURI = new URI(url + "/v1/feedback").normalize();
//
//            response = executor.execute(Request.Put(serviceURI)
//                    .addHeader("Accept", "application/json")
//                    .addHeader("X-SyncTimeout", "30")
//                    .bodyString(feedback.toJson().toString(), ContentType.APPLICATION_JSON)).returnResponse();
//
//
//        } catch (Exception ex) {
//            logger.log(Level.SEVERE, "Got the following error: " + ex.getMessage(), ex);
//        }
//
//        int statusCode = response.getStatusLine().getStatusCode();
//
//        // 201 - Accepted, 400 - Bad request. Most likely the result of a missing required parameter.
//        return (statusCode == 201);
        return false;
    }

    private WatsonAnswer queryService(WatsonQuestion question) throws IOException {

        HttpURLConnection urlConnection;
        WatsonAnswer answer;
        URL queryUrl = new URL(url, "v1/question/");
        logger.log(Level.INFO, "Connecting to: " + queryUrl.toString());
        urlConnection = (HttpURLConnection) queryUrl.openConnection();

        // Connect
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("X-SyncTimeout", "30");
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "APPLICATION/JSON");
        urlConnection.connect();

        // Write
        OutputStream out = urlConnection.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(out);
        writer.write(question.toJsonString());
        writer.close();
        out.close();

        logger.log(Level.INFO, "Sending Query: " + question.toJsonString());
        int status = urlConnection.getResponseCode();
        logger.log(Level.INFO, "Response Code " + status);

        // Read
        InputStream in = (status >= HttpURLConnection.HTTP_BAD_REQUEST) ? urlConnection.getErrorStream(): urlConnection.getInputStream();
        String content = convertStreamToString(in);
        in.close();

        if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
            logger.log(Level.SEVERE, content);
            throw new IOException(String.format("%d : %s", status, in));
        } else {
            answer = WatsonAnswer.createAnswerFromContent(content);
        }
        urlConnection.disconnect();
        return answer;
    }

    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}
