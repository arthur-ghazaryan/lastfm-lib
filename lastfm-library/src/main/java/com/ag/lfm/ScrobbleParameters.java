//Copyright 2016 Arthur Ghazaryan

//        Licensed under the Apache License, Version 2.0 (the "License");
//        you may not use this file except in compliance with the License.
//        You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//        Unless required by applicable law or agreed to in writing, software
//        distributed under the License is distributed on an "AS IS" BASIS,
//        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//        See the License for the specific language governing permissions and
//        limitations under the License.


package com.ag.lfm;

import com.ag.lfm.util.LfmUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for generating Srobble signature.
 */
public class ScrobbleParameters extends LinkedList<LfmParameters> implements Comparator<LfmParameters> {


    String[] parametersArray = {
            "sk",
            "api_key",
            "artist",
            "track",
            "album",
            "timestamp",
            "context",
            "streamId",
            "chosenByUser",
            "trackNumber",
            "mbid",
            "albumArtist",
            "duration",
            "method"};


    private List<LfmParameters> scrobbleList = new ArrayList<>();

    boolean first = true;

    public void put(String key, String... values) {
        if (first) {
            first = false;
            for (String value : values) {
                LfmParameters p = new LfmParameters();
                if (!value.isEmpty())
                    p.put(key, value);
                this.add(p);
            }
        } else {
            for (int i = 0; i < values.length; i++) {
                if (!values[i].isEmpty())
                    this.get(i).put(key, values[i]);
            }
        }
    }

    public String parseSinglePrameter(String p) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).containsKey(p)) {
                builder.append(p).append("[").append(i).append("]").append(this.get(i).get(p));
            }
        }
        return builder.toString();
    }

    public String parseParameters() {
        StringBuilder builder = new StringBuilder();
        builder
                .append("method=track.scrobble");
        for (int i = 0; i < this.size(); i++) {
            for (String p : this.get(i).keySet()) {
                try {
                    builder.append("&")
                            .append(p)
                            .append("[")
                            .append(i)
                            .append("]")
                            .append("=")
                            .append(URLEncoder.encode(this.get(i).get(p), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        builder.append("&").append("api_key").append("=").append(Lfm.getApiKey()).append("&").append("sk=")
                .append(com.ag.lfm.Session.sessionkey).append("&").append("api_sig=")
                .append(LfmUtil.md5Custom(parseAllParameters())).append("&format=json");
        return builder.toString();
    }

    public String parseAllParameters() {
        Arrays.sort(parametersArray);
        Collections.sort(this, this);
        StringBuilder builder = new StringBuilder();
        for (String p : parametersArray) {
            switch (p) {
                case "api_key":
                    builder.append("api_key").append(Lfm.getApiKey());
                    break;
                case "method":
                    builder.append("method").append("track.scrobble");
                    break;
                case "sk":
                    builder.append("sk").append(com.ag.lfm.Session.sessionkey);
                default:
                    builder.append(parseSinglePrameter(p));
                    break;
            }
        }
        return builder.append(Lfm.getSecret()).toString();
    }

    @Override
    public int compare(LfmParameters lhs, LfmParameters rhs) {
        return lhs.get("artist").compareTo(rhs.get("artist"));
    }
}
