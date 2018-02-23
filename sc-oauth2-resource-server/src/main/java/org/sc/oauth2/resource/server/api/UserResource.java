package org.sc.oauth2.resource.server.api;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.sc.oauth2.resource.server.configuration.scope.Scope;
import org.sc.oauth2.resource.server.model.User;
import org.sc.oauth2.resource.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {
	
	@Autowired
	UserRepository userRepo;
	
	@PreAuthorize("#oauth2.hasScope('trust') or "
				+ "#oauth2.hasScope('public_profile') or "
				+ "#oauth2.hasScope('email') or "
				+ "#oauth2.hasScope('date_of_birth') or "
				+ "#oauth2.hasScope('place_of_birth')")
	@RequestMapping("/me")
	public Map<String, Object> publicProfile(Principal principal) {

		Map<String, Object> response = new HashMap<>();
		OAuth2Authentication auth = (OAuth2Authentication) principal;

		// TODO: find user by user_name, unique user_name
		User user = userRepo.findByUsername(auth.getName());

		for (String scope : auth.getOAuth2Request().getScope()) {

			if (Scope.TRUST.toString().equalsIgnoreCase(scope)) {
				response.put("id", user.getId());
				response.put("email", user.getEmail());
				response.put("username", user.getUsername());
				response.put("date_of_birth", user.getDateOfBirth());
				response.put("place_of_birth", user.getPlaceOfBirth());
				return response;
			} else {
				if (Scope.PUBLIC_PROFILE.toString().equalsIgnoreCase(scope)) {
					response.put("id", user.getId());
					response.put("username", user.getUsername());
				} else if (Scope.EMAIL.toString().equalsIgnoreCase(scope)) {
					response.put("email", user.getEmail());
				} else if (Scope.DATE_OF_BIRTH.toString().equalsIgnoreCase(scope)) {
					response.put("date_of_birth", user.getDateOfBirth());
				} else if (Scope.PLACE_OF_BIRTH.toString().equalsIgnoreCase(scope)) {
					response.put("place_of_birth", user.getPlaceOfBirth());
				}
			}
		}

		return response;
	}
	
}
