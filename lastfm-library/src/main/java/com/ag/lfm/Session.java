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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * API session key.
 */
public class Session {

    /**
     * Key.
     */
    public static String sessionkey;

    /**
     * User name.
     */
    public static String username;


    public static int subscriber;


    public Session() {
    }

    public Session(JSONObject from) throws JSONException {
        parse(from.optJSONObject("session"));
    }

    private Session parse(JSONObject jo) {
        sessionkey = jo.optString("key");
        username = jo.optString("name");
        subscriber = jo.optInt("subscriber");
        return this;
    }


}
