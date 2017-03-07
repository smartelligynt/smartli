/**
 * @author mpala
 * This class holds all the Static Error instances      
 * 
 */

package com.st.services.oauth;



public class AuthServiceErrors {
    
    public static final AuthError INVALID_REQUEST = new AuthError("116", "Invalid Request");
    
    public static final AuthError INVALID_CLIENT_ID = new AuthError("116", "Invalid Request - Invalid ClientId");
    
    public static final AuthError INVALID_REDIRECT_URI = new AuthError("116", "Invalid Request - Invalid Redirect URI");
    
    public static final AuthError INVALID_CLIENT_SECRET = new AuthError("116", "Invalid Request - Invalid ClientSecret");
    
    public static final AuthError UNKNOWN_FAILUE = new AuthError("100", "Unknown Application Failure");

    public static final AuthError THIRD_PARTY_NOT_FOUND = new AuthError("115", "Third Party Info Not Available");
    
    public static final AuthError INVALID_TP_RESPONSE = new AuthError("117", "Invalid Token Response from Third Party : ");

    public static final AuthError TP_ACCOUNT_NOT_LINKED = new AuthError("118", "Third Party Account not linked");
    
    public static final AuthError NO_DB_CONNECTION = new AuthError("106", "Error connecting to OAuth Database");
    
    public static final AuthError DB_ACTIVIT_FAILURE = new AuthError("107", "Error performing Database activity");
    
    public static final AuthError GET_SESSION_KEY_ERROR = new AuthError("108", "Error getting Session Key");
    
    public static final AuthError NO_SESSION_KEY = new AuthError("108", "Session key not provided");
    
    public static final AuthError VERIFICATION_CODE_GEN = new AuthError("118", "Error generating Verification Code");
    
    public static final AuthError INCOMPLETE_AUTH_HEADERS = new AuthError("120", "UnAuthorized Request - Incomplete authorization attributes");
    
    public static final AuthError REPLAY_REQUEST = new AuthError("120", "UnAuthorized Request - Replay Request");

	public static final AuthError INVALID_TOKEN = new AuthError("120", "UnAuthorized Request - Invalid Token");
	
	public static final AuthError INVALID_OAUTH_CONSUMER_KEY = new AuthError("120", "UnAuthorized Request - Invalid OAuth Consumer Key");
	
	public static final AuthError INVALID_SIGNATURE = new AuthError("120", "UnAuthorized Request - Invalid Signature");

	public static final AuthError INVALID_USERNAME_OR_PASSWORD = new AuthError("116", "Invalid Username/Password");

	public static final AuthError INVALID_SIGNATURE_METHOD = new AuthError("120", "UnAuthorized Request - Invalid Signature Method");;
	
	public static final AuthError INVALID_CUSTOMER_ID = new AuthError("120", "UnAuthorized Request - Invalid WM Customer Id");;
	
	public static final AuthError PROPERTIES_NOT_INITIALIZED = new AuthError("101", "System Properties not initialized");
	
	public static final AuthError GET_TP_ACCOUNT_INFO_ERROR = new AuthError("105", "Error getting Third Party Account Info");
	
}
