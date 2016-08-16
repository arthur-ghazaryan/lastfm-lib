# lastfm-library
Gradle:
```groovy
compile 'com.github.ag:lastfm-library:1.0.2
```
#Usage
1. Add the internet permission in your `AndroidManifest.xml`

   ```xml
   <uses-permission android:name="android.permission.INTERNET" /> 
   ```

1. Add this to the resource file (example strings.xml).

   ```xml
   <string name="api_key">your_api_key</string>
   ```
   If you want to use methods that require authorization add your API secret to the resource file (example strings.xml).
   ```xml
   <string name="secret">your_api_secret</string>
   ```
   
2. Initialize library on startup using the following method. Call it inside `onCreate` method of your `Application` class.
   ```java
   public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize here.
        Lfm.initializeWithSecret(this);
        //You can initialize without secret,but remember that you cannot use methods that require authentication.
        //Lfm.initialize(this);

       }
  }
   ```
   
# API Requests
1. Plain request.

   ```java
   LfmRequest lfmRequest = LfmApi.user().getInfo();
    ```
2. Request with parameters.

   ```java
    LfmParameters params = new LfmParameters();
    params.put("artist","The artist name");
    params.put("track","The track name");
        
    LfmRequest request = LfmApi.track().getInfo(params);
   ```
3. Track scrobble.
   
   **Remember** : This method requires authentication.
   It means that you should use `API secret` when initializing library at the beginning.
   ```java
   ScrobbleParameters params = new ScrobbleParameters();
   params.put("track","track1","track2","track3");
   params.put("artist","artist1","artist2","artist3");
        
   String timestamp = String.valueOf(System.currentTimeMillis()/1000);
   params.put("timestamp",timestamp,timestamp,timestamp);
        
   LfmRequest trackScrobble = LfmApi.track().scrobble(params);
   ```
# Requests Sending
```java
 request.executeWithListener(new LfmRequest.LfmRequestListener() {
    @Override
    public void onComplete(JSONObject response) {
         //Called if there were no errors
    }

    @Override
    public void onError(LfmError error) {
        //Called immediately if there was API error, or  if there was an HTTP error
    }
});
```
# User Authorization
**Remember** : Use `Lfm.initializeWithSecret(Context context);` method when initializing library at the beginning if you want to use user authorization .
```java
Lfm.login("username", "password", new Lfm.LfmCallback<Session>() {
    @Override
    public void onResult(Session result) {
        // User passed Authorization      
    }

    @Override
    public void onError(LfmError error) {
        // User didn't pass Authorization
    }
});
```
