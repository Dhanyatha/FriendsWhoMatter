package PartTWO.OAuth;

public class OAuthToken {
	String UserAccessToken;
	String UserAccessSecret;

	public OAuthToken(String token,String secret)
	{
		this.setAccessToken(token);
		this.setAccessSecret(secret);
	}

	public String getAccessSecret() {
		return UserAccessSecret;
	}

	public void setAccessSecret(String AccessSecret) {
		this.UserAccessSecret = AccessSecret;
	}

	public String getAccessToken() {
		return UserAccessToken;
	}

	public void setAccessToken(String AccessToken) {
		this.UserAccessToken = AccessToken;
	}

	@Override
	public String toString()
	{
		return "Access Token: "+getAccessToken()+" Access Secret: "+getAccessSecret();
	}
}

