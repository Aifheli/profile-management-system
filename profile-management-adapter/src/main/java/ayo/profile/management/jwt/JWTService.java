package ayo.profile.management.jwt;


import ayo.profile.management.exception.JwtInitializationException;
import ayo.profile.management.exception.JwtValidationException;
import ayo.profile.management.security.PemFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import java.util.function.Function;

/**
 * Created by Aifheli Maganya on 2022/05/19.
 */
@Slf4j
@Component
public class JWTService {

    @Value("${ayo.jwt.key-identifier}")
    private String keyIdentifier;
    @Value("${ayo.jwt.signing-algorithm}")
    private String signingAlgorithm;
    @Value("${ayo.jwt.type}")
    private String type;
    @Value("${ayo.jwt.expiry-time-in-seconds}")
    private int expiryTimeInSeconds;
    @Value("${ayo.jwt.issuer}")
    private String issuer;
    @Value("${ayo.public-key-path}")
    private String publicKeyPath;
    @Value("${ayo.jwt.private-key-path}")
    private String privateKeyPath;
    private RSAPrivateKey privateKey;

    @PostConstruct
    public void init() {
        try {
            getPrivateKey();
        } catch (JwtInitializationException e) {
            log.error(e.getMessage(), e.getStackTrace().toString());
        }
    }

    public RSAPrivateKey getPrivateKey() throws JwtInitializationException {
        if (privateKey == null) {
            try {
                privateKey = (RSAPrivateKey) readPrivateKey(KeyFactory.getInstance("RSA"), privateKeyPath);
            } catch (Exception e) {
                throw new JwtInitializationException("Failed to read private key: " + privateKeyPath, e);
            }
        }
        return privateKey;
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractIssuer(String token) {
        return extractClaim(token, Claims::getIssuer);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String audience) throws JwtInitializationException {
        getPrivateKey();

        return Jwts.builder()
                .setHeaderParam("kid", keyIdentifier)
                .setHeaderParam("alg", signingAlgorithm)
                .setHeaderParam("typ", type)
                .setIssuer(issuer)
                .setAudience(audience)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(Calendar.getInstance(TimeZone.getTimeZone("GMT+2")).getTimeInMillis()))
                .setExpiration(new Date(Calendar.getInstance(TimeZone.getTimeZone("GMT+2")).getTimeInMillis() + (1000 * expiryTimeInSeconds)))
                .signWith(SignatureAlgorithm.RS256, privateKey).compact();
    }

    public Claims parseJwtToken(String token) throws JwtValidationException {
        //todo: complete token validation logic
        Claims claims = null;
        try {
             claims = Jwts.parser()
                    .setSigningKey(readPublicKey(KeyFactory.getInstance("RSA"), publicKeyPath))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new JwtValidationException(e);
        }
        return claims;
    }

    public PrivateKey readPrivateKey(KeyFactory factory, String keyPath) throws InvalidKeySpecException, IOException {
        PemFile pemFile = new PemFile(keyPath);
        byte[] content = pemFile.getPemObject().getContent();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(content);
        return factory.generatePrivate(keySpec);
    }

    public PublicKey readPublicKey(KeyFactory factory, String keyPath) throws InvalidKeySpecException, IOException {
        PemFile pemFile = new PemFile(keyPath);
        byte[] content = pemFile.getPemObject().getContent();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(content);
        return factory.generatePublic(keySpec);
    }
}
