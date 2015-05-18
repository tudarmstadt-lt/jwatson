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

package jwatson.answer;


public class Metadatamap {

    private String _abstract;
    private String originalfile;
    private String title;
    private String corpusName;
    private String description;
    private String deepqaid;
    private String fileName;
    private String DOCNO;
    private String CorpusPlusDocno;

    public String get_abstract() {
        return _abstract;
    }

    public String getOriginalfile() {
        return originalfile;
    }

    public String getTitle() {
        return title;
    }

    public String getCorpusName() {
        return corpusName;
    }

    public String getDescription() {
        return description;
    }

    public String getDeepqaid() {
        return deepqaid;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDOCNO() {
        return DOCNO;
    }

    public String getCorpusPlusDocno() {
        return CorpusPlusDocno;
    }

    @Override
    public String toString() {
        return "Metadatamap{" +
                "_abstract='" + _abstract + '\'' +
                ", originalfile='" + originalfile + '\'' +
                ", title='" + title + '\'' +
                ", corpusName='" + corpusName + '\'' +
                ", description='" + description + '\'' +
                ", deepqaid='" + deepqaid + '\'' +
                ", fileName='" + fileName + '\'' +
                ", DOCNO='" + DOCNO + '\'' +
                ", CorpusPlusDocno='" + CorpusPlusDocno + '\'' +
                '}';
    }
}
