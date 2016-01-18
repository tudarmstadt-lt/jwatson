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

package jwaston.question;


import jwatson.question.WatsonQuestion;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class WatsonQuestionTest {

    @Test
    public void setDefaultFormatAnswer() {
        WatsonQuestion question = createDefaultQuestion();
        boolean actual = question.getQuestion().isFormattedAnswer();

        assertFalse(actual);
    }

    @Test
    public void setDefaultAnswers() {
        WatsonQuestion question = createDefaultQuestion();
        int actual = question.getQuestion().getNumberOfAnswers();

        assertEquals(5, actual);
    }

    @Test
    public void excludeNotInitializedFieldsFromJsonString() {
        WatsonQuestion actual = new WatsonQuestion.QuestionBuilder("Sample Question")
                .setCategory("Test Category")
                .create();

        String expected = "{\"question\":{\"questionText\":\"Sample Question\",\"items\":5," +
                "\"category\":\"Test Category\",\"formattedAnswer\":false}}";

        assertEquals(expected, actual.toJsonString());
    }

    private WatsonQuestion createDefaultQuestion() {
        return new WatsonQuestion.QuestionBuilder("Sample Question").create();
    }
}
