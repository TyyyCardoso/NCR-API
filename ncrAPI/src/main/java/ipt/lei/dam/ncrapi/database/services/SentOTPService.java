package ipt.lei.dam.ncrapi.database.services;

import ipt.lei.dam.ncrapi.database.entities.SentOTP;
import ipt.lei.dam.ncrapi.database.repos.SentOTPRepository;
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


}
