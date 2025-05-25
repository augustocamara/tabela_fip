package br.com.rootnet.tabelafip.principal;

import br.com.rootnet.tabelafip.model.Dados;
import br.com.rootnet.tabelafip.model.Modelos;
import br.com.rootnet.tabelafip.model.TipoVeiculo;
import br.com.rootnet.tabelafip.model.Veiculo;
import br.com.rootnet.tabelafip.service.ConsumoApi;
import br.com.rootnet.tabelafip.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        System.out.println("*** Opções ***\nCarro\nMoto\nCaminhão\n\nDigite uma das opções:");
        String opcao = leitura.nextLine().toLowerCase();

        TipoVeiculo tipo = opcao.contains("carr") ? TipoVeiculo.CARRO : opcao.contains("mot") ? TipoVeiculo.MOTO : TipoVeiculo.CAMINHAO;

        String endereco = URL_BASE + tipo.getPath() + "/marcas";
        List<Dados> marcas = conversor.obterLista(consumo.obterDados(endereco), Dados.class);

        marcas.stream().sorted(Comparator.comparing(Dados::codigo)).forEach(System.out::println);
        System.out.print("Digite o código da marca desejada: ");
        String codigoMarca = leitura.nextLine();

        endereco += "/" + codigoMarca + "/modelos";
        Modelos modelos = conversor.obterDados(consumo.obterDados(endereco), Modelos.class);

        System.out.println("\nModelos dessa marca:");
        modelos.modelos().stream().sorted(Comparator.comparing(Dados::codigo)).forEach(System.out::println);

        System.out.print("Digite um trecho do nome do modelo: ");
        String nomeVeiculo = leitura.nextLine().toLowerCase();
        List<Dados> modelosFiltrados = modelos.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo))
                .collect(Collectors.toList());

        if (modelosFiltrados.isEmpty()) {
            System.out.println("Nenhum modelo encontrado com esse nome.");
            return;
        }

        System.out.println("\nModelos encontrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.print("Digite o código do modelo desejado: ");
        String codigoModelo = leitura.nextLine();

        String anosUrl = endereco + "/" + codigoModelo + "/anos";
        List<Dados> anos = conversor.obterLista(consumo.obterDados(anosUrl), Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();
        for (Dados ano : anos) {
            String veiculoUrl = anosUrl + "/" + ano.codigo();
            veiculos.add(conversor.obterDados(consumo.obterDados(veiculoUrl), Veiculo.class));
        }

        System.out.println("\nVeículos encontrados:");
        veiculos.forEach(System.out::println);
    }
}
