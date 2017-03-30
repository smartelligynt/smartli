# smartli
AUth Client

APIs

1) Enable Access
Method: POST
Endpoint: <host>:<port>/smartli/auth-client/customer/<cid>/tp/<tp_name>/enable_access

tp_name: SMART_THINGS, WINK

Request Body:

authorization_code request:
{
	"code" : "<code>",
  	"grant_type" : "authorization_code",
  	"redirect_uri" : "<redirect_uri>*"
	
}


password request:
{
	"username" : "<username>",
  	"grant_type" : "password",
  	"password" : "<password>*"
	
}

Response:
{"access_token" : "<access_token>"}

2) Get Access
Method: GET
Endpoint: <host>:<port>/smartli/auth-client/customer/<cid>/tp/<tp_name>/get_access

Response:
{"access_token" : "<access_token>"}

Refer: com.st.services.oauth.domain.dao.ThirdPartyInfoDAOTest for sample ThirdParty Info and the templates of Requests and Responses