package com.studi.relancepret.batch;

import com.studi.relancepret.EmailSenderService;
import com.studi.relancepret.model.InfoPret;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.SendFailedException;

@Component
@Slf4j
public class InfoPretItemProcessor implements ItemProcessor<InfoPret, InfoPret> {
    @Autowired
    EmailSenderService emailSenderService;

    @Override
    public InfoPret process(InfoPret infoPret) throws Exception {
        try {
            emailSenderService.sendEmail(infoPret.getEmail(), "Relance "+infoPret.getTitre(), "Bonjour " +
                    infoPret.getPrenom()+", \nVous avez effectué un prêt concernant le livre "+infoPret.getTitre()+" " +
                    " le "+infoPret.getDateDebut()+" que vous auriez dû rendre le "+infoPret.getDateFin()+". \n" +
                    "Veuillez retourner le livre dans les plus bref délai.\n" +
                    "Cordialement mr Coudray");
        }catch (SendFailedException e){
            log.debug(e.getMessage());
        }
        return infoPret;
    }
}
