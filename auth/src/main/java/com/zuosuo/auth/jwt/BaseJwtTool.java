package com.zuosuo.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zuosuo.component.time.DiscTime;
import com.zuosuo.component.time.TimeTool;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class BaseJwtTool {

    public Map<String, Object> createHeader(String alg, String typ) {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", alg);
        map.put("typ", typ);
        return map;
    }

    public Map<String, Object> createHeader() {
        return createHeader("HS256", "JWT");
    }

    public String createToken(String secret, String issuer, String subject, DiscTime discTime, String[] audience) {
        Date nowDate = new Date();
        Date expireDate = TimeTool.getOffsetDate(nowDate, discTime);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withHeader(createHeader())
                .withIssuer(issuer)
                .withSubject(subject)
                .withAudience(audience)
                .withIssuedAt(nowDate)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    public abstract String createToken(String issuer, String subject, String... audience);

    public String createToken(String secret, String issuer, String subject, DiscTime discTime, Map<String, String> claims, String[] audience) {
        Date nowDate = new Date();
        Date expireDate = TimeTool.getOffsetDate(nowDate, discTime);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create();
        builder = builder.withHeader(createHeader())
                .withIssuer(issuer)
                .withSubject(subject)
                .withAudience(audience)
                .withIssuedAt(nowDate)
                .withExpiresAt(expireDate);
        for (Map.Entry<String, String> entry : claims.entrySet()) {
            builder = builder.withClaim(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return builder.sign(algorithm);
    }

    public abstract String createToken(String issuer, String subject, Map<String, String> claims, String... audience);

    public JWTResult getResult(String token, String secret, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT jwt = verifier.verify(token);
        return getResult(jwt);
    }

    public JWTResult getResult(String token, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return getResult(jwt);
    }

    public abstract JWTResult getResult(String token);

    public JWTResult getResult(DecodedJWT jwt) {
        JWTResult result = new JWTResult();
        result.setIssuer(jwt.getIssuer());
        result.setSubject(jwt.getSubject());
        result.setAudience(jwt.getAudience());
        result.setIssueAt(jwt.getIssuedAt());
        result.setExpireAt(jwt.getExpiresAt());
        Map<String, Claim> claims = jwt.getClaims();
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            result.setData(entry.getKey(), entry.getValue().asString());
        }
        return result;
    }

    public Map<String, String> getCustomPayload(DecodedJWT jwt, Function<String, Map<String, String>> convert) {
        String payloadJson = StringUtils.newStringUtf8(Base64.decodeBase64(jwt.getPayload()));
        return convert.apply(payloadJson);
    }
}
