package ipt.lei.dam.ncrapi.utils.enums;

import lombok.Getter;

@Getter
public enum ErrorsEnum {

    INVALID_CREDENTIALS("00001", "Palavra-passe ou email incorretos. Por favor, tente novamente."),
    ACCOUNT_LOCKED("00002","Conta bloqueada. Por favor, tente novamente mais tarde."),
    UNKNOWN_ERROR("00003","Ocorreu um erro inesperado. Por favor, tente novamente."),
    NETWORK_ERROR("00004","Erro de rede. Por favor, verifique sua conexão."),
    SEND_OTP_ERROR("00005","Erro ao enviar código. Por favor, tente novamente.");


    private final String errorCode;
    private final String message;

    ErrorsEnum(String errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }



}
