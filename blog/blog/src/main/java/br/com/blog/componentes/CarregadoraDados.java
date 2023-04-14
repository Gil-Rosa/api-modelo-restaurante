package br.com.blog.componentes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.blog.models.Papel;
import br.com.blog.repositories.PapelRepository;


@Component
public class CarregadoraDados implements CommandLineRunner {
	@Autowired
	private PapelRepository papelRepository ;
	
	@Override
	public void run(String... args) throws Exception {
		
		String[] papeis = {"ADMIN", "USER"};
		
		for (String papelString: papeis) {
			Papel papel = papelRepository.findByNome(papelString);
			if (papel == null) {
				papel = new Papel(papelString);
				papelRepository.save(papel);
			}
		}				
	}

}
