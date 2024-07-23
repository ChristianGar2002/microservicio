package com.usuario.controllers.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {

    //El @Value le va a cargar al string key el propetirs que tengamos en applications propetirs en el ambiente que estemos
    @Value("${security.jwt.secret}")
    private String key;//La clave para firmar el jwt


    @Value("${security.jwt.issuer}")
    private String issuer;//Aqui guardamos quien creo la sesion


    @Value("${security.jwt.ttlMillis}")
    private long ttlMillis;//La fecha de caducidad de la sesion

    //Sirve para registrar mensajes durante la validacion y creacion del JWT
    private final Logger log = LoggerFactory.getLogger(JWTUtils.class);

    //Crear nuevo token

    /**
     * @param
     * @param
     * @return
     */

    //Esta funcion va a crear el jwt
    //Subjetc sujeto del token normalmente la informacion del usuario
    public String create (String id, String subject) {

        //El algoritmo para firmar el jwt
        //The JWT signature algorithm used to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //Se obtiene el tiempo actual en millisegundos
        long nowMillis = System.currentTimeMillis();
        //Lo convertirmos a Date
        Date now = new Date(nowMillis);

        //Decodifica la clave secreta codificada en base64 de la propiedad key y crea un objeto SecretKeySpec para firmarla
        //sign JWT with our Apikey secret
        //Codifico la key de propetris en base 64
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        //Luego la firmo con el algoritmo que declare anteriormente
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //set the JWT Claims
        //id: La ID proporcionada(opcional), issuedAt: El tiempo actual, lo que indica cuando se emitio el token
        //subject: La informacion del sujeto proporcionada, issuer: El emisor del token(por ejemplo, el nombre de la aplicacion)
        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        // es mayor a 0 lo el ttMillis que esta en propetirs
        if (ttlMillis >= 0) {
            //Se le agrega al tiempo actual mas el que esta en propetirs
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);//Se crea el objeto Date para estimar la fecha de caducaion de la sesion
            builder.setExpiration(exp);//Se le coloca al objeto JWT
        }

        //Se compacta el JWT ya firmado de forma segura
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    //Las funciones getValue y getKey nos van a devolver informacion que nosotros hayamos agregado al token
    //Para obtener informacion del usuario
    public String getValue(String jwt){
        //This line will throw an exception if it is not a signed JWS(as expect)
        //Ocupo la key de propetirs para poder obtener la informacion del JWT
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt).getBody();

        return claims.getSubject();//Obtengo la informacion del usuario
    }

    /**
     * Method to validate and read the JWT
     *
     * @param jwt
     * @return
     */

    //Recuperar el identificador unico
    public String getKey(String jwt){
        //This line will throw an exception if it is not a signed JWS(as expected
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt).getBody();

        return claims.getId();//Obtengo el id del token
    }

}
