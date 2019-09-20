package com.mxlapps.app.gearspopguide.Utils;


import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encriptado {

    public Encriptado() {
    }

    private static final String key = "a0c8936f8d5b93383e7d24b99b4f5d8e";
    public String password = "";
    public String confirmation = "";
    public String passwordEncriptado = "";

    public static void main(String[] args) throws Exception {
        Encriptado x = new Encriptado();
        x.password = "123123";
        x.encriptar();
    }

    public void encriptar() throws Exception {
        //Leer password del objeto "Main"
        byte[] clean = password.getBytes(StandardCharsets.UTF_8);

        //Set IV ALEATORIO
        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        //IV parameter y Key Spec
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

        // Encriptar
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(clean);

        //Convertir Encriptado a Base64
//        byte[] enc64 = Base64.getEncoder().encode(encrypted);
        byte[] enc64 = android.util.Base64.encode(encrypted, android.util.Base64.NO_WRAP);
        String enc_string = new String(enc64, StandardCharsets.UTF_8);
        passwordEncriptado = enc_string;

        //Convertir Contra byte[] a String (pruebas)
        String texto  = new String(clean, StandardCharsets.UTF_8);
//        System.out.println("Texto Plano: " + texto);

        //Convertir IV a String (confirmation)
//        byte[] iv64 = Base64.getEncoder().encode(iv);
        byte[] iv64 = android.util.Base64.encode(iv, android.util.Base64.NO_WRAP);
        String iv_string = new String(iv64, StandardCharsets.UTF_8);
        confirmation = iv_string;
    }



}