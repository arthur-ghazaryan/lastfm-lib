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


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class for operate with parameters
 */
public class LfmParameters  extends LinkedHashMap<String,String> implements Serializable{

    public LfmParameters(){
        super();
    }

    /**
     * Create LFMParameters instance  from {@link Map}
     * @param fromMap {@link Map} with parameters.
     */
    public LfmParameters(Map<String, String> fromMap) {
        super(fromMap);
    }
}
