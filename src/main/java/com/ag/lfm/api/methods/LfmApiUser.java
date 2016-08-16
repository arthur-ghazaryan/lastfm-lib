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
import com.ag.lfm.Session;

@SuppressWarnings("unused")
public class LfmApiUser extends ApiBase {

    /**
     * http://www.last.fm/api/show/user.getArtistTracks
     */
    public LfmRequest getArtistTracks(LfmParameters params) {
        return prepareRequest("getArtistTracks", params, false);
    }

    /**
     * http://www.last.fm/api/show/user.getFriends
     */
    public LfmRequest getFriends(LfmParameters params) {
        return prepareRequest("getFriends", params, false);
    }

    /**
     * http://www.last.fm/api/show/user.getInfo
     */
    public LfmRequest getInfo(String user) {
        LfmParameters parameters = new LfmParameters();
        parameters.put("user",user);
        return prepareRequest("getInfo",parameters , false);
    }

    /**
     * http://www.last.fm/api/show/user.getInfo
     */
    public LfmRequest getInfo() {
        LfmParameters parameters = new LfmParameters();
        parameters.put("user", Session.username);
        return prepareRequest("getInfo",parameters, false);
    }

    /**
     * http://www.last.fm/api/show/user.getLovedTracks
     */
    public LfmRequest getLovedTracks(LfmParameters params) {
        return prepareRequest("getLovedTracks", params, false);
    }

    /**
     * http://www.last.fm/api/show/user.getPersonalTags
     */
    public LfmRequest getPersonalTags(LfmParameters params) {
        return prepareRequest("getPersonalTags", params, false);
    }

    /**
     * http://www.last.fm/api/show/user.getRecentTracks
     */
    public LfmRequest getRecentTracks(LfmParameters params) {
        return prepareRequest("getRecentTracks", params, false);
    }

    /**
     * http://www.last.fm/api/show/user.getTopAlbums
     */
    public LfmRequest getTopAlbums(LfmParameters params) {
        return prepareRequest("getTopAlbums", params, false);
    }

    /**
     * http://www.last.fm/api/show/user.getTopArtists
     */
    public LfmRequest getTopArtists(LfmParameters params) {
        return prepareRequest("getTopArtists", params, false);
    }

    /**
     * http://www.last.fm/api/show/user.getTopTags
     */
    public LfmRequest getTopTags(LfmParameters params) {
        return prepareRequest("getTopTags", params, false);
    }

    /**
     * http://www.last.fm/api/show/user.getTopTracks
     */
    public LfmRequest getTopTracks(LfmParameters params) {
        return prepareRequest("getTopTracks", params, false);
    }

    /**
     * http://www.last.fm/api/show/user.getWeeklyAlbumChart
     */
    public LfmRequest getWeeklyAlbumChart(LfmParameters params) {
        return prepareRequest("getWeeklyAlbumChart", params, false);
    }

    /**
     * http://www.last.fm/api/show/user.getWeeklyArtistChart
     */
    public LfmRequest getWeeklyArtistChart(LfmParameters params) {
        return prepareRequest("getWeeklyArtistChart", params, false);
    }

    /**
     * http://www.last.fm/api/show/user.getWeeklyChartList
     */
    public LfmRequest getWeeklyChartList(LfmParameters params) {
        return prepareRequest("getWeeklyChartList", params, false);
    }

    /**
     * http://www.last.fm/api/show/user.getWeeklyTrackChart
     */
    public LfmRequest getWeeklyTrackChart(LfmParameters params) {
        return prepareRequest("getWeeklyTrackChart", params, false);
    }


    @Override
    protected String getMethodsGroup() {
        return "user";
    }
}
