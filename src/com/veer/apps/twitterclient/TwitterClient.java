package com.veer.apps.twitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	//TwitterApi is a wrapper for twitter apis provided by the scribe library
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "aQEo3PO9sqfaDIJfq0ykQ";       // Change this
    public static final String REST_CONSUMER_SECRET = "5wS8aJhlwylOPNJMvBrhS1RTHPAYe9394oV5rN6uDEI"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitterapp"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    //Get twitter feeds
    public void getHomeTimeline(long olderID, AsyncHttpResponseHandler handler){
    	String url = getApiUrl("statuses/home_timeline.json");
 
    	RequestParams params = new RequestParams();
    	if(olderID != 0)
    		params.put("max_id", String.valueOf(olderID));
    	else 
    		params = null;
    	
    	client.get(url, params, handler);
    }
    
    //Post new tweets   -- eg: for images also, we`ll use 'post' 
    public void postStatus(String nTweet, AsyncHttpResponseHandler handler){
    	String url = getApiUrl("statuses/update.json");
    	RequestParams params = new RequestParams();
    	params.put("status", nTweet);
    	
    	client.post(url, params, handler);
    }
    
    //Get mentions feeds
    public void getMentions(AsyncHttpResponseHandler handler){
    	String url = getApiUrl("statuses/mentions_timeline.json");
    	client.get(url, null, handler);
    }
    
    //Get your user credentials
    public void getMyInfo(AsyncHttpResponseHandler handler){
    	String url = getApiUrl("account/verify_credentials.json");
    	client.get(url, null, handler);
    }
    
    //Get your user timeline
    public void getUserTimeline(String username, AsyncHttpResponseHandler handler){
    	String url = getApiUrl("statuses/user_timeline.json");
    	RequestParams params;
    	
    	if(username != null){
    		params = new RequestParams();
    		params.put("screen_name", username);
    	} else {
    		params = null;
    	}
    	
    	client.get(url, params, handler);
    }
    
    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}