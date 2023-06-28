package com.lonar.artofliving.controller;

import java.io.IOException;
import java.rmi.ServerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.LtAolUsersMaster;
import com.lonar.artofliving.model.LtMastLogins;
import com.lonar.artofliving.model.RequestDto;
import com.lonar.artofliving.model.ResponceEntity;
import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.service.LtMastUsersService;

@RestController
@RequestMapping(value = "/aolusers")
public class LtMastUsersController implements CodeMaster {
	
	@Autowired
	LtMastUsersService ltMastUsersService;
	
	@Autowired
	private Environment env;

	@RequestMapping(value = "/sendOTP/{mobileNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> sendOTP(@PathVariable("mobileNumber") String mobileNumber)
			throws ServiceException, IOException {
		try {
			return new ResponseEntity<Status>(ltMastUsersService.sendOTPToUser(mobileNumber), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponceEntity> login(@RequestBody LtMastLogins ltMastLogins) throws ServiceException{
			ResponceEntity responseEntity = null;
	try {
			
			responseEntity = ltMastUsersService.login(ltMastLogins, null);
			if(responseEntity.getCode().equals(CodeMaster.SUCCESS)) {
				/*
				 * ResourceOwnerPasswordResourceDetails resource = new
				 * ResourceOwnerPasswordResourceDetails();
				 * resource.setAccessTokenUri(env.getProperty("tokenURI"));
				 * resource.setClientId(env.getProperty("clientId"));
				 * resource.setClientSecret(env.getProperty("clientSecrete"));
				 * resource.setGrantType(env.getProperty("grantType"));
				 * resource.setUsername(String.valueOf(responseEntity.getUserId()));
				 * resource.setPassword(ltMastLogins.getOtp().toString());
				 * resource.setClientAuthenticationScheme(AuthenticationScheme.header);
				 * OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource);
				 * OAuth2AccessToken oAuth2AccessToken = restTemplate.getAccessToken(); String
				 * token = oAuth2AccessToken.getValue(); //System.out.println("token = " +
				 * token);
				 * 
				 * HttpHeaders headers = new HttpHeaders();
				 */
				//headers.add("access_token", token);
	
				return new ResponseEntity<>(responseEntity, HttpStatus.OK);
			}
		} catch (Exception e) {
		e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Login Fail", e);
		}
		return new ResponseEntity<>(responseEntity,null,HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/getallactiveroles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getallactiveroles( )throws ServiceException, IOException {
		try {
			return new ResponseEntity<Status>(ltMastUsersService.getallactiveroles(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getallusers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getallusers(@RequestBody RequestDto requestDto )throws ServiceException, IOException {
		try {
			return new ResponseEntity<Status>(ltMastUsersService.getallusers(requestDto), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
    @RequestMapping(value= "/deleteUser/{userId}", method= RequestMethod.DELETE, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status> deleteUser(@PathVariable("userId") Long userId) throws ServerException{
    	try {
    		return new ResponseEntity<Status>(ltMastUsersService.deleteUser(userId), HttpStatus.OK);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
    	}
    }
    
    @RequestMapping(value = "/saveUserDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveUserDetails(@RequestBody LtAolUsersMaster ltAolUsersMaster )
			throws ServiceException,IOException {
		try {
				return new ResponseEntity<Status>(ltMastUsersService.saveUserDetails(ltAolUsersMaster), HttpStatus.OK);
		     }
		catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		}
		
		
	}
}
