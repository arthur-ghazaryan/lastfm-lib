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

@SuppressWarnings("unused")
public class LfmApiCharts extends ApiBase {


    /**
     * http://www.last.fm/api/show/chart.getTopArtists
     */
    public LfmRequest getTopArtists() {
        return prepareRequest("getTopArtists", null, false);
    }

    /**
     * http://www.last.fm/api/show/chart.getTopArtists
     */
    public LfmRequest getTopArtists(LfmParameters params) {
        return prepareRequest("getTopArtists", params, false);
    }

    /**
     * http://www.last.fm/api/show/chart.getTopTags
     */
    public LfmRequest getTopTags() {
        return prepareRequest("getTopTags", null, false);
    }

    /**
     * http://www.last.fm/api/show/chart.getTopTags
     */
    public LfmRequest getTopTags(LfmParameters params) {
        return prepareRequest("getTopTags", params, false);
    }

    /**
     * http://www.last.fm/api/show/chart.getTopTracks
     */
    public LfmRequest getTopTracks(){
        return prepareRequest("getTopTracks",null,false);
    }

    /**
     * http://www.last.fm/api/show/chart.getTopTracks
     */
    public LfmRequest getTopTracks(LfmParameters params){
        return prepareRequest("getTopTracks",params,false);
    }



    @Override
    protected String getMethodsGroup() {
        return "chart";
    }
}
