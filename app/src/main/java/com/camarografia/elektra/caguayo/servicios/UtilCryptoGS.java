package com.camarografia.elektra.caguayo.servicios;

import android.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class UtilCryptoGS {

    public static final String FORMAT_CHARACTER_ENCODING = "UTF-8";
    public static final String CIPHER_TRANSFORM_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String ALGORITHM_ENCRYPT_METHOD = "AES";
    public static final String MASTERKEY_ENCRYPT = "a2de2546f978b8ff1f93920603d11af5";
    // private static final Logger logger =
    // Logger.getLogger(UtilCryptoGS.class);
    /**
     * <summary>Cifra texto plano utilizando la clave de 128 bits AES y una
     * cadena Block Cipher y devuelve una cadena codificada en base64 </summary>
     * <param name="plainText">Texto sin formato para cifrar</param>
     * <param name="key">Clave secreta</param> <returns>Cadena codificada en
     * Base64</returns>
     *
     * @param plainText
     * @return
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encrypt(String plainText)
            throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        byte[] plainTextbytes = plainText.getBytes(FORMAT_CHARACTER_ENCODING);
        byte[] keyBytes = getKeyBytes(MASTERKEY_ENCRYPT);
        String encodedString = Base64.encodeToString(encrypt(plainTextbytes, keyBytes, keyBytes), Base64.DEFAULT);
        return encodedString;
    }

    /**
     * <summary> Descifra una cadena codificada en base64 con la clave dada
     * (clave de 128 bits AES y una cadena Block Cipher)</summary>
     * <param name="encryptedText">cadena codificada en Base64</param>
     * <param name="key">Clave secreta</param> <returns>Cadena des-encriptada
     * </returns>
     *
     * @param encryptedText
     * @param key
     * @return
     * @throws KeyException
     * @throws GeneralSecurityException
     * @throws GeneralSecurityException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static String decrypt(String encryptedText, String key)
            throws KeyException, GeneralSecurityException, GeneralSecurityException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, IOException {

        byte[] cipheredBytes = Base64.decode(encryptedText, Base64.DEFAULT);
        byte[] keyBytes = getKeyBytes(key);
        return new String(decrypt(cipheredBytes, keyBytes, keyBytes), FORMAT_CHARACTER_ENCODING).replaceAll("\n", "");
    }

    /**
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
        byte[] keyBytes = new byte[16];
        byte[] parameterKeyBytes = key.getBytes(FORMAT_CHARACTER_ENCODING);
        System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
        return keyBytes;
    }

    /**
     * @param cipherText
     * @param key
     * @param initialVector
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM_ALGORITHM);
        SecretKeySpec secretKeySpecy = new SecretKeySpec(key, ALGORITHM_ENCRYPT_METHOD);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
        cipherText = cipher.doFinal(cipherText);
        return cipherText;
    }

    /**
     * @param plainText
     * @param key
     * @param initialVector
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM_ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM_ENCRYPT_METHOD);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        plainText = cipher.doFinal(plainText);
        return plainText;
    }
}

