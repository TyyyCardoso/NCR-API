package ipt.lei.dam.ncrapi.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OTPService {

    private static final SecureRandom random = new SecureRandom();
    private static final int OTP_LENGTH = 7; // Length of the OTP

    public String generateOTPCode(){
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        return otp.toString();
    }




}
