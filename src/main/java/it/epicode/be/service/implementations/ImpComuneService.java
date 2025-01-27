package it.epicode.be.service.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.exception.EntityNotFoundException;
import it.epicode.be.model.Comune;
import it.epicode.be.model.Provincia;
import it.epicode.be.repository.ComuneRepository;
import it.epicode.be.repository.ProvinciaRepository;
import it.epicode.be.service.ComuneService;

@Service
public class ImpComuneService implements ComuneService {

	@Autowired
	private ComuneRepository cor;
	@Autowired
	private ProvinciaRepository prr;
	@Value("${exception.entitynotfound}")
	String entitynotfound;

	@Override
	public Comune save(Comune comune) throws EntityNotFoundException {
		Comune aggiust = comune;
		if (aggiust.getProvincia().getId() != null) {
			Optional<Provincia> old = prr.findById(aggiust.getProvincia().getId());
			if (old.isEmpty()) {
				throw new EntityNotFoundException(entitynotfound, Provincia.class);
			}
			aggiust.setProvincia(old.get());

		} else if (aggiust.getProvincia().getNome() != null) {
			Optional<Provincia> old = prr.findByNome(aggiust.getProvincia().getNome());
			if (old.isEmpty()) {
				throw new EntityNotFoundException(entitynotfound, Provincia.class);
			}
			aggiust.setProvincia(old.get());
		}
		return cor.save(aggiust);
	}

	@Override
	public Comune update(Comune newComune) throws EntityNotFoundException {
		Optional<Comune> old = cor.findById(newComune.getId());
		if (old.isEmpty()) {
			throw new EntityNotFoundException(entitynotfound, Comune.class);
		}
		return cor.save(newComune);
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		try {
			cor.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(entitynotfound, Comune.class);
		}
	}

	@Override
	public Page<Comune> findAll(Pageable pageable) {
		return cor.findAll(pageable);
	}

	@Override
	public Comune findById(Long id) throws EntityNotFoundException {

		Optional<Comune> found = cor.findById(id);
		if (found.isEmpty()) {
			throw new EntityNotFoundException(entitynotfound, Comune.class);
		}

		return found.get();
	}
}
