package ipt.lei.dam.ncrapi.utils;

public class ConstantsUtils {

    public static String recoverPasswordHTMLEmail = "<!DOCTYPE html>"
            + "<html>"
            + "<head>"
            + "    <style>"
            + "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; }"
            + "        .email-container { background-color: #ffffff; max-width: 600px; margin: 20px auto; padding: 20px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }"
            + "        .email-header { background-color: #4CAF50; color: white; padding: 10px; text-align: center; }"
            + "        .email-body { padding: 20px; text-align: center; }"
            + "        .email-footer { background-color: #dddddd; color: #333; padding: 10px; text-align: center; font-size: 12px; }"
            + "        .otp-code { font-weight: bold; font-size: 24px; margin: 20px 0; padding: 10px; background-color: #e7f4e4; display: inline-block; }"
            + "    </style>"
            + "</head>"
            + "<body>"
            + "    <div class=\"email-container\">"
            + "        <div class=\"email-header\">"
            + "            <h1>Recuperação de Palavra-passe</h1>"
            + "        </div>"
            + "        <div class=\"email-body\">"
            + "            <p>Olá,</p>"
            + "            <p>Você solicitou a recuperação da palavra-passe da sua conta. Use o código abaixo para definir uma nova palavra-passe:</p>"
            + "            <div class=\"otp-code\">[OTP_CODE]</div>"
            + "            <p>Este código é válido por 1 minuto. Se você não solicitou a recuperação de palavra-passe, por favor, ignore este email e verifique a segurança da sua conta.</p>"
            + "        </div>"
            + "        <div class=\"email-footer\">"
            + "            <p>Se precisar de ajuda, entre em contato conosco.</p>"
            + "            <p>&copy; 2023 Nucleo Conservação e Restauro</p>"
            + "        </div>"
            + "    </div>"
            + "</body>"
            + "</html>";

    public static String confirmAccountHTMLEmail = "<!DOCTYPE html>"
            + "<html>"
            + "<head>"
            + "    <style>"
            + "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; }"
            + "        .email-container { background-color: #ffffff; max-width: 600px; margin: 20px auto; padding: 20px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }"
            + "        .email-header { background-color: #4CAF50; color: white; padding: 10px; text-align: center; }"
            + "        .email-body { padding: 20px; text-align: center; }"
            + "        .email-footer { background-color: #dddddd; color: #333; padding: 10px; text-align: center; font-size: 12px; }"
            + "        .otp-code { font-weight: bold; font-size: 24px; margin: 20px 0; padding: 10px; background-color: #e7f4e4; display: inline-block; }"
            + "    </style>"
            + "</head>"
            + "<body>"
            + "    <div class=\"email-container\">"
            + "        <div class=\"email-header\">"
            + "            <h1>Confirmação de Conta</h1>"
            + "        </div>"
            + "        <div class=\"email-body\">"
            + "            <p>Olá,</p>"
            + "            <p>Obrigado por se registrar. Use o código de verificação único (OTP) abaixo para confirmar a sua conta:</p>"
            + "            <div class=\"otp-code\">[OTP_CODE]</div>"
            + "            <p>Este código é válido por 10 minutos. Se você não criou uma conta, por favor, ignore este email e verifique a segurança da sua conta.</p>"
            + "        </div>"
            + "        <div class=\"email-footer\">"
            + "            <p>Se precisar de ajuda, entre em contato conosco.</p>"
            + "            <p>&copy; 2023 Nucleo Conservação e Restauro</p>"
            + "        </div>"
            + "    </div>"
            + "</body>"
            + "</html>";



}
