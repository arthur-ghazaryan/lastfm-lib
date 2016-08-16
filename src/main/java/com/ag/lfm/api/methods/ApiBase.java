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


package com.ag.lfm.api.methods;

import com.ag.lfm.LfmParameters;
import com.ag.lfm.LfmRequest;
import com.ag.lfm.ScrobbleParameters;

import java.util.Locale;

/**
 * Basic class for all API-requests builders (parts)
 */
public abstract class ApiBase {

    /**
     * Selected methods group
     */
    protected abstract String getMethodsGroup();

    protected LfmRequest prepareRequest(String methodName, LfmParameters methodParameters, boolean needAuth) {
        return new LfmRequest(String.format(Locale.US,"%s.%s",getMethodsGroup(),methodName),methodParameters,needAuth);
    }

    protected LfmRequest prepareRequest(ScrobbleParameters methodParameters){
        return new LfmRequest(methodParameters);
    }

}
