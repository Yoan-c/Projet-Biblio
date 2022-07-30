package com.example.biblio.functions.hash;

import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.Random;

public class Empreinte {

    public static final Random r = new Random();
    public static final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String getSalt(int taille) { //Renvoie une chaine de caractère aléatoire de taille "taille"
        String s = "";
        for (int i = 0; i < taille; i++) {
            s +=alphabet.charAt(r.nextInt(alphabet.length()));
        }
        return s;
    }

    public static String getHash(String toHash){  //Renvoie une empreinte Sha256 de la variable
        return DigestUtils.sha256Hex(toHash);
    }

    public static String getHashSalt(String toHashSalt, String salt){  //Renvoie une empreinte Sha256 de la variable après l'avoir salé
        return getHash(toHashSalt + salt);
    }

    public static String getScryptHashSalt(String toHashSalt, String salt){  //Renvoie une empreinte Scrypt de la variable après l'avoir salé
        return getScryptHash(toHashSalt + salt);
    }


    public static boolean isValidPassword(String hash, String password, String salt){ //Vérifie qu'une empreinte correspond à un couple mot de passe/sel
        return (getHashSalt(password, salt).equals(hash));
    }

    private static String getScryptHash(String s) { //Renvoie une empreinte Scrypt de la variable
        return SCryptUtil.scrypt(s, 2048, 16, 16);
    }

    public static boolean scryptCheck(String password, String salt, String hash){ //Vérifie qu'une empreinte Scrypt correspond à un couple mot de passe/sel
        return scryptCheck(password+salt, hash);
    }

    public static boolean scryptCheck(String toHash, String hash){ //Vérifie qu'une empreinte Scrypt correspond à une chaine de caractère
        return SCryptUtil.check(toHash, hash);
    }
 /*   public void test(String s ){
        System.out.println("mot de passe : " + s);
        String salt = getSalt(64);
        System.out.println("sel genere  : " + salt);
        String hash = getScryptHashSalt(s ,  salt);
        System.out.println("hash du mot de passe genere : " + hash);
        String s2 = "test";
        //scryptCheck(password+user.getSalt(),user.getHash())
        System.out.println("mot de passe valide : " + scryptCheck(s2+salt,hash));

    }
*/
}
