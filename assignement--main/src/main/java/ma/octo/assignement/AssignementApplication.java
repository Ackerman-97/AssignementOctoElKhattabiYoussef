package ma.octo.assignement;

import ma.octo.assignement.domain.AuditVersement;
import ma.octo.assignement.domain.AuditVirement;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.repository.AuditVersementRepository;
import ma.octo.assignement.repository.AuditVirementRepository;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.repository.VersementRepository;
import ma.octo.assignement.repository.VirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootApplication
public class AssignementApplication implements CommandLineRunner {
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private VirementRepository virementRepository;
	@Autowired
	private VersementRepository versementRepository;
	@Autowired
	private AuditVirementRepository auditVirementRepository;
	@Autowired
	private AuditVersementRepository auditVersementRepository;

	public static void main(String[] args) {
		SpringApplication.run(AssignementApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		
		Utilisateur utilisateur1 = new Utilisateur();
		utilisateur1.setUsername("user1");
		utilisateur1.setLastname("last1");
		utilisateur1.setFirstname("first1");
		utilisateur1.setGender("Male");
		utilisateur1.setBirthdate(new GregorianCalendar(1997, 07, 25).getTime());

		utilisateurRepository.save(utilisateur1);


		Utilisateur utilisateur2 = new Utilisateur();
		utilisateur2.setUsername("user2");
		utilisateur2.setLastname("last2");
		utilisateur2.setFirstname("first2");
		utilisateur2.setGender("Female");
		utilisateur2.setBirthdate(new GregorianCalendar(1958, 9, 01).getTime());

		utilisateurRepository.save(utilisateur2);
		

		Compte compte1 = new Compte();
		compte1.setNrCompte("010000A000001000");
		compte1.setRib("RIB1");
		compte1.setSolde(BigDecimal.valueOf(200000L));
		compte1.setUtilisateur(utilisateur1);

		compteRepository.save(compte1);

		Compte compte2 = new Compte();
		compte2.setNrCompte("010000B025001000");
		compte2.setRib("RIB2");
		compte2.setSolde(BigDecimal.valueOf(140000L));
		compte2.setUtilisateur(utilisateur2);

		compteRepository.save(compte2);
		

		
		Virement virement = new Virement();
		virement.setMontantVirement(BigDecimal.TEN);
		virement.setCompteBeneficiaire(compte2);
		virement.setCompteEmetteur(compte1);
		virement.setDateExecution(new Date());
		virement.setMotifVirement("Octo20212022");
		virement.getCompteBeneficiaire().setSolde(virement.getCompteBeneficiaire().getSolde().add(virement.getMontantVirement()));
		virement.getCompteEmetteur().setSolde(virement.getCompteEmetteur().getSolde().subtract(virement.getMontantVirement()));

		AuditVirement auditVirement = new AuditVirement();
		auditVirement.setEventType(EventType.VIREMENT);
		auditVirement.setMessage("Virement effectue");

		virementRepository.save(virement);
		compteRepository.save(virement.getCompteBeneficiaire());
		compteRepository.save(virement.getCompteEmetteur());
		auditVirementRepository.save(auditVirement);
		
		
		//Le versement
		
		Versement versement = new Versement();
		versement.setMotifVersement("Octo20212022");
		versement.setMontantVirement(BigDecimal.TEN);
		versement.setDateExecution(new Date());
		versement.setCompteBeneficiaire(compte1);
		versement.setNom_prenom_emetteur("ElKhattabiYoussefTest");
		versement.getCompteBeneficiaire().setSolde(versement.getCompteBeneficiaire().getSolde().add(versement.getMontantVirement()));
		
		AuditVersement auditVersement = new AuditVersement();
		auditVersement.setMessage("Versement effectue");
		auditVersement.setEventType(EventType.VERSEMENT);
		
		compteRepository.save(versement.getCompteBeneficiaire());
		versementRepository.save(versement);
		auditVersementRepository.save(auditVersement);
	}
}