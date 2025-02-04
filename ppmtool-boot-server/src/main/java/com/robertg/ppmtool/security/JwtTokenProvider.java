package com.robertg.ppmtool.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.robertg.ppmtool.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	protected static final Log logger = LogFactory.getLog(JwtTokenProvider.class);

	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);
		String userId = Long.toString(user.getId());

		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullname());
		claims.put("enabled", user.isEnabled());
		claims.put("locked", user.isLocked());
		claims.put("authorities", user.getAuthorities());

		return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SecurityConstants.JWT_SIGNATURE_ALGORITHM, SecurityConstants.JWT_SECRET_KEY).compact();
	}

	public long getUserIdFromJwtToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET_KEY).parseClaimsJws(token).getBody();
			return claims.get("id", Long.class);
		} catch (SignatureException e) {
			logger.error("Invalid JWT Signature");
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT Token");
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT Token");
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT Exception");
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty");
		} catch (Exception e) {
			logger.error("JWT - another exception occured");
		}

		throw new IllegalArgumentException("Failed to extract the user id from JWT token");
	}
}
