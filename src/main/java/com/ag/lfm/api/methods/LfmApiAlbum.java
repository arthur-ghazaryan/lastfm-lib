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
import com.ag.lfm.util.LfmUtil;

import java.util.Locale;

@SuppressWarnings("unused")
public class LfmApiAlbum extends ApiBase {

    /**
     * http://www.last.fm/api/show/album.addTags
     */
    public LfmRequest addTags(LfmParameters params) {
        params.put("api_sig", LfmUtil.generateSignature(String.format(Locale.US, "%s.%s", getMethodsGroup(), "addTags"), params));
        return prepareRequest("addTags", params, true);
    }

    /**
     * http://www.last.fm/api/show/album.getInfo
     */
    public LfmRequest getInfo(LfmParameters params) {
        return prepareRequest("getInfo", params, false);
    }

    /**
     * http://www.last.fm/api/show/album.getTags
     */
    public LfmRequest getTags(LfmParameters params) {
        return prepareRequest("getTags", params, false);
    }

    /**
     * http://www.last.fm/api/show/album.getTopTags
     */
    public LfmRequest getTopTags(LfmParameters params) {
        return prepareRequest("getTopTags", params, false);
    }

    /**
     * http://www.last.fm/api/show/album.removeTag
     */
    public LfmRequest removeTag(LfmParameters params) {
        params.put("api_sig", LfmUtil.generateSignature(String.format(Locale.US, "%s.%s", getMethodsGroup(), "removeTag"), params));
        return prepareRequest("removeTag", params, true);
    }

    /**
     * http://www.last.fm/api/show/album.search
     */
    public LfmRequest search(LfmParameters params) {
        return prepareRequest("search", params, false);
    }

    @Override
    protected String getMethodsGroup() {
        return "album";
    }
}
