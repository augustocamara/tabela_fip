package br.com.rootnet.tabelafip;

import br.com.rootnet.tabelafip.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TabelaFipApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(TabelaFipApplication.class, args);
	}

	@Override
	public void run(String... args) {
		new Principal().exibeMenu();
	}
}

