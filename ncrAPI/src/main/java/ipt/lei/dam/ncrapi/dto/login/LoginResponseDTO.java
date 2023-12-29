package ipt.lei.dam.ncrapi.dto.login;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LoginResponseDTO(String token, boolean isValidated, String email, String name, String type, String registrationDate, String image, String about) {
}
