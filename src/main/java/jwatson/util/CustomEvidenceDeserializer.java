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

package jwatson.util;

import com.google.gson.*;
import jwatson.answer.Evidencelist;

import java.lang.reflect.Type;

public class CustomEvidenceDeserializer implements JsonDeserializer<Evidencelist> {

    @Override
    public Evidencelist deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

        if (jsonElement.toString().equals("{}")) {
            return null;
        }
        
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Gson gson = new Gson();
        Evidencelist evidence = gson.fromJson(jsonObject, Evidencelist.class);
        return evidence;
    }
}
    

