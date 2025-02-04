package com.robertg.ppmtool.security;

import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityConstants {

	private SecurityConstants() {
	}

	public static final String SIGN_UP_URLS = "/api/users/**";
	public static final String H2_URL = "h2-console/**";
	public static final String JWT_SECRET_KEY = "SecretKeyToGenJWTs";
	public static final SignatureAlgorithm JWT_SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
	public static final String BEARER_TOKEN_PREFIX = "Bearer ";
	public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
	public static final long EXPIRATION_TIME = 3_600_000; // in milliseconds(60 minutes)
}