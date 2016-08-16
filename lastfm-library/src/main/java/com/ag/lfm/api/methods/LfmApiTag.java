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
public class LfmApiTag extends ApiBase {

    /**
     * http://www.last.fm/api/show/tag.getInfo
     */
    public LfmRequest getInfo(LfmParameters params) {
        return prepareRequest("getInfo", params, false);
    }

    /**
     * http://www.last.fm/api/show/tag.getSimilar
     */
    public LfmRequest getSimilar(LfmParameters params) {
        return prepareRequest("getSimilar", params, false);
    }

    /**
     * http://www.last.fm/api/show/tag.getTopAlbums
     */
    public LfmRequest getTopAlbums(LfmParameters params) {
        return prepareRequest("getTopAlbums", params, false);
    }

    /**
     * http://www.last.fm/api/show/tag.getTopArtists
     */
    public LfmRequest getTopArtists(LfmParameters params) {
        return prepareRequest("getTopArtists", params, false);
    }

    /**
     * http://www.last.fm/api/show/tag.getTopTags
     */
    public LfmRequest getTopTags(LfmParameters params) {
        return prepareRequest("getTopTags", params, false);
    }

    /**
     * http://www.last.fm/api/show/tag.getTopTracks
     */
    public LfmRequest getTopTracks(LfmParameters params) {
        return prepareRequest("getTopTracks", params, false);
    }

    /**
     * http://www.last.fm/api/show/tag.getWeeklyChartList
     */
    public LfmRequest getWeeklyChartList(LfmParameters params) {
        return prepareRequest("getWeeklyChartList", params, false);
    }

    @Override
    protected String getMethodsGroup() {
        return "tag";
    }
}
