package ipt.lei.dam.ncrapi.database.services;

import ipt.lei.dam.ncrapi.database.entities.SentOTP;
import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.database.repos.SentOTPRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SentOTPService {

    @Autowired
    private SentOTPRepository sentOTPRepository;

    public void createNewOTP(SentOTP sentOTP){
        sentOTPRepository.save(sentOTP);
    }

    public Optional<SentOTP> getLastSentOTP(int userID) {
        return sentOTPRepository.findLastOTPByUserID(userID); }

    @Transactional
    public void useOTP(Integer otpID) {
        SentOTP sentOTP = sentOTPRepository.getSentOTPByOtpId(otpID);
        if (sentOTP != null) {
            sentOTP.setIsUsed(true);
            sentOTPRepository.save(sentOTP);
        }
    }

}
