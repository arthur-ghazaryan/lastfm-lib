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


package com.ag.lfm.api.model;

import android.os.Parcel;

import org.json.JSONObject;

/**
 * Track object that contains the following fields.
 */
@SuppressWarnings("unused")
public class LfmTrack implements android.os.Parcelable {

    /**
     * Artist name.
     */
    public String artist;

    /**
     * Track playcount.
     */
    public int playcount;
    /**
     * The <a href = "https://musicbrainz.org/">musicbrainz</a> id for the track.
     */
    public String mbid;

    /**
     * Track match.
     * See <a href="http://www.last.fm/api/show/track.getSimilar">track.getSimilar</a>
     */
    public int match;

    /**
     * Track url.
     */
    public String url;
    /**
     * Track duration (in milliseconds).
     */
    public int duration;

    /**
     * Track listeners number.
     */
    public int listeners;

    /**
     * Creates empty LfmTrack instance.
     */
    public LfmTrack() {

    }

    /**
     * Creates a LfmTrack instance from Parcel.
     */
    public LfmTrack(Parcel in) {
        artist = in.readString();
        mbid = in.readString();
        url = in.readString();
        duration = in.readInt();
        listeners = in.readInt();
        playcount = in.readInt();

    }


    public LfmTrack(JSONObject from){
        parse(from);
    }

    /**
     * Fills a LfmTrack instance from JSONObject.
     */
    private LfmTrack parse(JSONObject from){
        artist = from.optString("name");
        mbid = from.optString("mbid");
        url = from.optString("url");
        duration = setValueOf(from, "duration");
        listeners = setValueOf(from, "listeners");
        playcount = setValueOf(from, "playcount");
        return this;
    }

    private int setValueOf(JSONObject from, String value) {
        if (!from.optString(value).equals("")) {
            return Integer.valueOf(from.optString(value));
        } else {
            return from.optInt(value);
        }
    }

    public static final Creator<LfmTrack> CREATOR = new Creator<LfmTrack>() {
        @Override
        public LfmTrack createFromParcel(Parcel in) {
            return new LfmTrack(in);
        }

        @Override
        public LfmTrack[] newArray(int size) {
            return new LfmTrack[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeString(artist);
        dest.writeString(mbid);
        dest.writeString(url);
        dest.writeInt(duration);
        dest.writeInt(listeners);
        dest.writeInt(playcount);
    }
}
