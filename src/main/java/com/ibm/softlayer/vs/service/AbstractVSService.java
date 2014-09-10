package com.ibm.softlayer.vs.service;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.SLAPIUtil;

/**
 * The Class AbstractGetService.
 */
public abstract class AbstractVSService {

	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AbstractVSService.class);
	
	/**
	 * Instantiates a new abstract get service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public AbstractVSService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}		
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the api key.
	 *
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}
	
	public JSONArray findAll(String apiUrl) throws Exception {
		return findAll(apiUrl, null);
	}

	/**
	 * Find all.
	 *
	 * @param apiUrl the api url
	 * @param objectMasks the object masks
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	public JSONArray findAll(String apiUrl, List<String> objectMasks) throws Exception {
		logger.debug("Executing apiUrl: " + apiUrl);
		
		//setting the object masks
		StringBuffer url = new StringBuffer(apiUrl);		
		SLAPIUtil.processObjectMasks(url, objectMasks);
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString(), null);
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executed apiUrl: " + apiUrl + ", Response Status Code: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("Executed apiUrl: " + apiUrl + ": JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Error: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}	
	
	public JSONObject get(String apiUrl) throws Exception {
		return get(apiUrl, null);
	}
	
	public JSONObject get(String apiUrl, List<String> objectMasks) throws Exception {
		logger.debug("Executing apiUrl: " + apiUrl);
		//setting the object masks
		StringBuffer url = new StringBuffer(apiUrl);		
		SLAPIUtil.processObjectMasks(url, objectMasks);
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString(), null);
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executed apiUrl: " + apiUrl + ", Response Status Code: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("Executed apiUrl: " + apiUrl + ": JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Error: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}
}
