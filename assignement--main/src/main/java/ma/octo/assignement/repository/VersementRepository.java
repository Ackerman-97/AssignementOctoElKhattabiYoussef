package ma.octo.assignement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.octo.assignement.domain.Versement;

public interface VersementRepository extends JpaRepository<Versement, Long>{

}
