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

import java.util.Locale;

/**
 * Class for presenting Lastfm API errors.
 */
public class LfmError {


    /**
     * API error code/
     */
    public int errorCode;

    /**
     * Http Request error.
     */
    public boolean httpClientError;

    /**
     * API error message.
     */
    public String errorMessage;

    /**
     * API error reason.
     */
    public String errorReason;

    public LfmError() {
    }

    public LfmError(JSONObject json) throws JSONException {
        this.errorCode = json.optInt("error");
        if (json.optString("message").contains("-")) {
            this.errorReason = json.optString("message").substring(0, json.optString("message").lastIndexOf("-") - 1);
            this.errorMessage = json.optString("message").substring(json.optString("message").lastIndexOf("-") + 1);
        } else {
            this.errorMessage = json.optString("message");
        }
    }

    private void appendFields(StringBuilder builder) {
        if (errorReason != null)
            builder.append(String.format("; %s", errorReason));
        if (errorMessage != null & !httpClientError)
            builder.append(String.format("; %s", errorMessage));
        else builder.append(errorMessage);
    }

    @Override
    public String toString() {
        StringBuilder errorString = new StringBuilder("LfmError (");
        if (!httpClientError)
            errorString.append(String.format(Locale.US, "code: %d", this.errorCode));
        appendFields(errorString);
        return errorString.append(")").toString();
    }

}
