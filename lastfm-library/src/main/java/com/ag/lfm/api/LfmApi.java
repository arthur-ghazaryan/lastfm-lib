package com.ag.lfm.api;

import com.ag.lfm.api.methods.LfmApiAlbum;
import com.ag.lfm.api.methods.LfmApiCharts;
import com.ag.lfm.api.methods.LfmApiGeo;
import com.ag.lfm.api.methods.LfmApiLibrary;
import com.ag.lfm.api.methods.LfmApiTag;
import com.ag.lfm.api.methods.LfmApiTrack;
import com.ag.lfm.api.methods.LfmApiUser;

/**
 * Provides access for API parts.
 */
public class LfmApi {

    /**
     * Returns object for preparing requests to track part of API
     */
    public static LfmApiTrack track() {
        return new LfmApiTrack();
    }

    /**
     * Returns object for preparing requests to chart part of API
     */
    public static LfmApiCharts charts() {
        return new LfmApiCharts();
    }

    /**
     * Returns object for preparing requests to geo part of API
     */
    public static LfmApiGeo geo() {
        return new LfmApiGeo();
    }

    /**
     * Returns object for preparing requests to tag part of API
     */
    public static LfmApiTag tag() {
        return new LfmApiTag();
    }

    /**
     * Returns object for preparing requests to user part of API
     */
    public static LfmApiUser user() {
        return new LfmApiUser();
    }

    /**
     * Returns object for preparing requests to library part of API
     */
    public static LfmApiLibrary library() {
        return new LfmApiLibrary();
    }

    /**
     * Returns object for preparing requests to album part of API
     */
    public static LfmApiAlbum album() {
        return new LfmApiAlbum();
    }
}
