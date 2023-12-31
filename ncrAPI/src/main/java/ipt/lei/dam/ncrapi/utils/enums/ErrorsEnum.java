package ipt.lei.dam.ncrapi.utils.enums;

import lombok.Getter;

@Getter
public enum ErrorsEnum {

    INVALID_CREDENTIALS("00001", "Palavra-passe ou email estão incorretos."),
    ACCOUNT_LOCKED("00002","Conta bloqueada. Por favor, tente novamente mais tarde."),
    UNKNOWN_ERROR("00003","Ocorreu um erro inesperado. Por favor, tente novamente."),
    NETWORK_ERROR("00004","Erro de rede. Por favor, verifique sua conexão."),
    SEND_OTP_ERROR("00005","Erro ao enviar código. Por favor, tente novamente."),
    VALIDATE_OTP_ERROR("00006","Erro ao validar código. Verifique que está correto."),
    CHANGE_PASSWORD_ERROR("00007","Erro ao alterar password. Por favor, tente novamente."),
    EMAIL_EXISTS_ERROR("00008","Uma conta com esse email já existe. Por favor, tente outro."),
    ERROR_GETTING_EVENT("00009","Ocorreu um erro ao tentar obter o evento. Por favor, tente mais tarde."),
    ERROR_GETTING_USER("00010","Ocorreu um erro ao tentar obter o utilizador. Por favor, tente mais tarde."),
    ERROR_CANCELING_SUBSCRIPTION("00011","Ocorreu um erro ao tentar cancelar a sua ida ao evento. Por favor, tente mais tarde."),
    ERROR_GETTING_STAFF("00011","Ocorreu um erro ao tentar obter o staff. Por favor, tente mais tarde."),
    OTP_ALREADY_USED("00012","O código já não é válido. Por favor, tente novamente."),
    OTP_EXPIRED("00013","O código já expirou. Por favor, tente novamente.");


    private final String errorCode;
    private final String message;

    ErrorsEnum(String errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }



}
