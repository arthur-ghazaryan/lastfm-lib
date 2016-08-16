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


package com.ag.lfm.util;

import com.ag.lfm.Lfm;
import com.ag.lfm.LfmParameters;
import com.ag.lfm.Session;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper class with important methods.
 */
public final class LfmUtil {


    /**
     * The API root URL.
     */
    private static final String ROOT_URL = "http://ws.audioscrobbler.com/2.0/";

    /**
     * Making string from LfmParameters.
     */
    public static String paramsParser(LfmParameters params) {
        StringBuilder builder = new StringBuilder();
        String p;
        for (String key : params.keySet()) {
            try {
                p = key + "=" + URLEncoder.encode(params.get(key), "UTF-8");
                builder.append("&").append(p);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return builder.toString();
    }


    private static String methodParser(String method) {
        return "?method=" + method;
    }

    public static String generateRequestURL(String method, LfmParameters params) {
        StringBuilder builder = new StringBuilder();
        builder.append(ROOT_URL)
                .append(methodParser(method))
                .append("&api_key=")
                .append(Lfm.getApiKey())
                .append("&format=json");
        if (params != null)
            builder.append(paramsParser(params));
        return builder.toString();
    }

    /**
     * Method for generating signature.
     */
    public static String generateSignature(String method, LfmParameters params) {
        List<String> parameters = new ArrayList<>(params.keySet());
        parameters.add("method" + method);
        parameters.add("sk");
        Collections.sort(parameters);
        StringBuilder builder = new StringBuilder();
        builder.append("api_key").append(Lfm.getApiKey());
        for (String p : parameters) {
            if (p.equals("method" + method)) {
                builder.append(p);
            } else if (p.equals("sk")) {
                builder.append("sk").append(Session.sessionkey);
            } else {
                builder.append(p).append(params.get(p));
            }
        }
        builder.append(Lfm.getSecret());
        return LfmUtil.md5Custom(builder.toString());
    }

    /**
     * Method for parsing parameters for REST request.
     */
    public static String parseRestRequestParams(String method, LfmParameters params) {
        StringBuilder builder = new StringBuilder();
        builder
                .append(methodParser(method).substring(1))
                .append(paramsParser(params))
                .append("&api_key=")
                .append(Lfm.getApiKey())
                .append("&sk=")
                .append(Session.sessionkey)
                .append("&format=json");
        return builder.toString();
    }


    /**
     * Method for generating MD5 hash.
     */
    public static String md5Custom(String st) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}
