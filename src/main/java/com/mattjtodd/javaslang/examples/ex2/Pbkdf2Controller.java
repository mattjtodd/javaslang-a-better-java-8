package com.mattjtodd.javaslang.examples.ex2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * Simple Spring MVC controller.
 */
@RestController
class Pbkdf2Controller {

    private final ExecutorService executorService;

    public enum SecretKey {
        ONE,
        TWO,
        THREE
    }

    /**
     * Bind bulkhead pool to this controller.
     *
     * @param executorService the thread pool
     */
    @Autowired
    Pbkdf2Controller(@Qualifier(Application.ENCODING_EXECUTOR) ExecutorService executorService) {
        this.executorService = Objects.requireNonNull(executorService, "executorService cannot be null");
    }

    /**
     * Encodes the request into the response.  This is performed asynchronously using a {@link DeferredResult}.
     *
     * @param text the hashing request
     * @return the encoded response
     */
    @RequestMapping(value = "/hash", method = RequestMethod.GET)
    public char[] hash(@RequestParam char[] text, @RequestParam SecretKey secretKey) {

        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();

        deferredResult.onTimeout(() -> deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build()));

        // Map the SecretKey to one of PBKDF2WithHmacSHA1, PBKDF2WithHmacSHA256, PBKDF2WithHmacSHA512

        // handle the processing asynchronously

        return "deferredResult".toCharArray();
    }

    /**
     * Creates a Pbkdf2 has for the supplied request.  Not the best but easy enough to demonstrate and it gives some
     * sensible load to our cores.
     *
     * Read the following for more info:
     *
     * https://paragonie.com/blog/2016/02/how-safely-store-password-in-2016
     *
     * @param text text to encode
     * @param salt salt to seed the has with
     * @return the requested hash
     * @throws InvalidKeySpecException no spec defined
     * @throws NoSuchAlgorithmException no algo found
     */
    private static byte[] pbkdf2Encode(char[] text, byte[] salt, String secretKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        int iterations = 1000000;
        int keyLength = 256;

        PBEKeySpec spec = new PBEKeySpec(text, salt, iterations, keyLength);

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(secretKey);

        return secretKeyFactory.generateSecret(spec).getEncoded();
    }
}
