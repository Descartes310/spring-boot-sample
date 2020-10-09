package com.geloka.api.configurations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import com.geloka.api.services.CustomUserDetailsService.CustomUserPrincipal;
import com.geloka.api.repositories.entities.User;
import com.geloka.api.services.UserService;


public final class Utilities
{
	
	/**
	 * Get current connected user if exist
	 * @return User 
	 */
	public static User getLoggedUser(UserService userService) 
	{
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			return userService.findByEmail(auth.getName());
		}
		
		return null;
	}
	
	
	
	public static void zipFiles(String[] srcFiles, String zipFile) throws IOException{
        
		// create byte buffer
        byte[] buffer = new byte[1024];
 
        FileOutputStream fos = new FileOutputStream(zipFile);
 
        ZipOutputStream zos = new ZipOutputStream(fos);
             
        for (int i=0; i < srcFiles.length; i++) {
                 
	        File srcFile = new File(srcFiles[i]);
	 
	        FileInputStream fis = new FileInputStream(srcFile);
	 
	                // begin writing a new ZIP entry, positions the stream to the start of the entry data
	        zos.putNextEntry(new ZipEntry(srcFile.getName()));
	                 
	        int length;
	 
	        while ((length = fis.read(buffer)) > 0) {
	            zos.write(buffer, 0, length);
	        }
	 
	        zos.closeEntry();
	 
	        // close the InputStream
	        fis.close();
     
        }
	 
	    // close the ZipOutputStream
	    zos.close(); 
    }

	
	
	
	public static OAuth2AccessToken getAccessTokenForZenoyeLearningApp(DefaultTokenServices tokenServices, User user) {
    	return getAccessToken(tokenServices, user, "zenoye-learning-app");
    }
    
    private static OAuth2AccessToken getAccessToken(DefaultTokenServices tokenServices, User user, String clientId) { 
        HashMap<String, String> authorizationParameters = new HashMap<String, String>();
        authorizationParameters.put("scope", "read");
        authorizationParameters.put("username", user.getEmail());
        authorizationParameters.put("client_id", clientId);
        authorizationParameters.put("grant", "password");

        Set<String> responseType = new HashSet<String>();
        responseType.add("password");

        Set<String> scopes = new HashSet<String>();
        scopes.add("read");
        scopes.add("write");
        scopes.add("trust");
        

        CustomUserPrincipal customUserPrincipal = new CustomUserPrincipal(user);
        
        OAuth2Request authorizationRequest = new OAuth2Request(authorizationParameters, clientId, customUserPrincipal.getAuthorities(), true,
                scopes, null, "", responseType, null);
 
        
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUserPrincipal,
                null, customUserPrincipal.getAuthorities());

        OAuth2Authentication authenticationRequest = new OAuth2Authentication(authorizationRequest,
                authenticationToken);
        authenticationRequest.setAuthenticated(true);
        OAuth2AccessToken accessToken = tokenServices.createAccessToken(authenticationRequest);

        return accessToken;
    }
    
   
}


