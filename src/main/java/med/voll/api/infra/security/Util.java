package med.voll.api.infra.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Util {

    public  static String gerarHash(String senha){

       return  BCrypt.hashpw(senha, BCrypt.gensalt());
    }
}
