package ipt.lei.dam.ncrapi.dto.login;

public record LoginResponseDTO(String token, boolean isValidated, String email) {
}
