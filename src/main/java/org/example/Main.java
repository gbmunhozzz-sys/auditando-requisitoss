
package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        int menu = -1;

        // Controle do equipamento
        boolean paradaEmergencia = false;
        boolean roboLigado = false;
        boolean cameraAtiva = false;
        boolean gravacao = false;

        String situacao = "Aguardando";
        String indicador = "Verde";
        String rede = "Online";
        String visao = "Desligada";

        // Dados das pecas produzidas
        int pecasProduzidas = 0;
        int pecasAprovadas = 0;
        int tempoProducao = 0;

        int[] cores = new int[3];

        // Registro dos acontecimentos
        String[] eventos = new String[100];
        int quantidadeEventos = 0;

        while (menu != 0) {

            System.out.println("\n===== PAINEL INDUSTRIAL =====");
            System.out.println("1 - Ativar parada de emergencia");
            System.out.println("2 - Consultar equipamento");
            System.out.println("3 - Ligar ou desligar robo");
            System.out.println("4 - Conferir conexao");
            System.out.println("5 - Cadastrar peca produzida");
            System.out.println("6 - Ativar ou desativar camera");
            System.out.println("7 - Consultar acontecimentos");
            System.out.println("8 - Consultar producao");
            System.out.println("9 - Calcular eficiencia OEE");
            System.out.println("10 - Desbloquear emergencia");
            System.out.println("0 - Finalizar programa");

            System.out.print("Digite sua escolha: ");
            menu = entrada.nextInt();

            switch (menu) {

                // RF-01
                case 1:

                    if (!paradaEmergencia) {

                        paradaEmergencia = true;
                        roboLigado = false;

                        System.out.println("Emergencia ativada!");

                        quantidadeEventos = salvarEvento(
                                eventos, quantidadeEventos,
                                "Parada de emergencia ativada"
                        );

                    } else {
                        System.out.println("A emergencia ja esta ativada.");
                    }

                    break;

                // RF-02
                case 2:

                    System.out.println("\n--- INFORMACOES DO ROBO ---");
                    System.out.println("Situacao: " + situacao);
                    System.out.println("Indicador de cor: " + indicador);
                    System.out.println("Conexao: " + rede);

                    // RF-03
                    System.out.println("Programa: "
                            + (roboLigado ? "Ligado" : "Desligado"));

                    // RF-08 e RF-09
                    System.out.println("Camera: "
                            + (cameraAtiva ? "Ativa" : "Inativa"));

                    System.out.println("Gravacao REC: "
                            + (gravacao ? "Ligada" : "Desligada"));

                    // RF-10
                    System.out.println("Visao computacional: " + visao);

                    break;

                // RF-03
                case 3:

                    if (paradaEmergencia) {

                        System.out.println(
                                "Nao pode ligar o robo durante a emergencia."
                        );

                    } else {

                        if (roboLigado) {

                            roboLigado = false;
                            System.out.println("Robo desligado.");

                            quantidadeEventos = salvarEvento(
                                    eventos, quantidadeEventos,
                                    "Robo desligado"
                            );

                        } else {

                            roboLigado = true;
                            System.out.println("Robo ligado.");

                            quantidadeEventos = salvarEvento(
                                    eventos, quantidadeEventos,
                                    "Robo ligado"
                            );
                        }
                    }

                    break;

                // RF-04
                case 4:

                    System.out.print(
                            "Quantos segundos o robo ficou sem responder? "
                    );

                    int atraso = entrada.nextInt();

                    if (atraso < 0) {

                        System.out.println("Tempo invalido.");

                    } else if (atraso > 3) {

                        rede = "Offline";

                        System.out.println("Conexao perdida!");

                        quantidadeEventos = salvarEvento(
                                eventos, quantidadeEventos,
                                "Falha na comunicacao"
                        );

                    } else {

                        rede = "Online";

                        System.out.println("Conexao funcionando normalmente.");
                    }

                    break;

                // RF-05, RF-06, RF-07 e RF-14
                case 5:

                    if (paradaEmergencia || !roboLigado) {

                        System.out.println(
                                "Nao e possivel produzir neste momento."
                        );

                    } else {

                        System.out.println("\nEscolha a cor da peca:");
                        System.out.println("1 - Vermelha");
                        System.out.println("2 - Verde");
                        System.out.println("3 - Azul");

                        int escolhaCor = entrada.nextInt();

                        if (escolhaCor >= 1 && escolhaCor <= 3) {

                            System.out.print(
                                    "Informe o tempo do ciclo em segundos: "
                            );

                            int tempo = entrada.nextInt();

                            System.out.print(
                                    "A peca foi aprovada? (1-Sim / 0-Nao): "
                            );

                            int aprovada = entrada.nextInt();

                            if (tempo <= 0 ||
                                    (aprovada != 0 && aprovada != 1)) {

                                System.out.println("Dados invalidos.");

                            } else {

                                pecasProduzidas++;

                                tempoProducao += tempo;

                                cores[escolhaCor - 1]++;

                                if (aprovada == 1) {
                                    pecasAprovadas++;
                                }

                                System.out.println(
                                        "Peca cadastrada com sucesso!"
                                );

                                quantidadeEventos = salvarEvento(
                                        eventos, quantidadeEventos,
                                        "Producao da peca " + pecasProduzidas
                                );
                            }

                        } else {

                            System.out.println("Cor nao encontrada.");
                        }
                    }

                    break;

                // RF-08 e RF-09
                case 6:

                    if (!cameraAtiva) {

                        cameraAtiva = true;
                        gravacao = true;

                        System.out.println(
                                "Camera ligada. Gravacao iniciada."
                        );

                        quantidadeEventos = salvarEvento(
                                eventos, quantidadeEventos,
                                "Camera ativada"
                        );

                    } else {

                        cameraAtiva = false;
                        gravacao = false;

                        System.out.println(
                                "Camera desligada. Gravacao encerrada."
                        );

                        quantidadeEventos = salvarEvento(
                                eventos, quantidadeEventos,
                                "Camera desativada"
                        );
                    }

                    break;

                // RF-11
                case 7:

                    System.out.println("\n--- HISTORICO DO SISTEMA ---");

                    if (quantidadeEventos == 0) {

                        System.out.println("Historico vazio.");

                    } else {

                        for (int i = 0; i < quantidadeEventos; i++) {

                            System.out.println(
                                    (i + 1) + " - " + eventos[i]
                            );
                        }
                    }

                    break;

                // RF-05, RF-06, RF-07 e RF-14
                case 8:

                    System.out.println("\n--- RELATORIO DE PRODUCAO ---");

                    System.out.println(
                            "Total produzido: " + pecasProduzidas
                    );

                    if (pecasProduzidas > 0) {

                        double mediaTempo =
                                (double) tempoProducao / pecasProduzidas;

                        double percentual =
                                pecasAprovadas * 100.0 / pecasProduzidas;

                        System.out.printf(
                                "Media dos ciclos: %.2f segundos%n",
                                mediaTempo
                        );

                        System.out.printf(
                                "Percentual aprovado: %.2f%%%n",
                                percentual
                        );

                    } else {

                        System.out.println("Ainda nao existe producao.");
                    }

                    System.out.println("Pecas vermelhas: " + cores[0]);
                    System.out.println("Pecas verdes: " + cores[1]);
                    System.out.println("Pecas azuis: " + cores[2]);

                    break;

                // RF-12 e RF-13
                case 9:

                    if (pecasProduzidas == 0) {

                        System.out.println(
                                "Nao existem dados para calcular o OEE."
                        );

                    } else {

                        System.out.print(
                                "Tempo planejado em minutos: "
                        );

                        int tempoPlanejado = entrada.nextInt();

                        System.out.print(
                                "Tempo de parada em minutos: "
                        );

                        int tempoParado = entrada.nextInt();

                        System.out.print(
                                "Ciclo ideal em segundos: "
                        );

                        int cicloIdeal = entrada.nextInt();

                        if (tempoPlanejado <= 0 ||
                                tempoParado < 0 ||
                                tempoParado > tempoPlanejado ||
                                cicloIdeal <= 0) {

                            System.out.println("Valores incorretos.");

                        } else {

                            double disponibilidade =
                                    (tempoPlanejado - tempoParado)
                                            * 100.0 / tempoPlanejado;

                            double desempenho =
                                    cicloIdeal * pecasProduzidas
                                            * 100.0 / tempoProducao;

                            if (desempenho > 100) {
                                desempenho = 100;
                            }

                            double qualidade =
                                    pecasAprovadas * 100.0 / pecasProduzidas;

                            double resultado =
                                    disponibilidade * desempenho
                                            * qualidade / 10000.0;

                            System.out.printf(
                                    "Disponibilidade: %.2f%%%n",
                                    disponibilidade
                            );

                            System.out.printf(
                                    "Desempenho: %.2f%%%n",
                                    desempenho
                            );

                            System.out.printf(
                                    "Qualidade: %.2f%%%n",
                                    qualidade
                            );

                            System.out.printf(
                                    "Resultado OEE: %.2f%%%n",
                                    resultado
                            );
                        }
                    }

                    break;

                // Liberacao da emergencia
                case 10:

                    if (paradaEmergencia) {

                        paradaEmergencia = false;

                        System.out.println(
                                "Emergencia desbloqueada."
                        );

                        quantidadeEventos = salvarEvento(
                                eventos, quantidadeEventos,
                                "Emergencia desbloqueada"
                        );

                    } else {

                        System.out.println(
                                "Nao existe emergencia ativa."
                        );
                    }

                    break;

                case 0:

                    System.out.println("Sistema finalizado.");

                    break;

                default:

                    System.out.println("Opcao invalida.");
            }

            // Atualizacao do estado do robo
            if (paradaEmergencia) {

                situacao = "Parado por emergencia";
                indicador = "Vermelho";

            } else if (roboLigado) {

                situacao = "Em funcionamento";
                indicador = "Azul";

            } else {

                situacao = "Aguardando";
                indicador = "Verde";
            }

            // Atualizacao da visao computacional
            if (!cameraAtiva) {

                visao = "Desligada";

            } else if (roboLigado && !paradaEmergencia) {

                visao = "Analisando imagens";

            } else {

                visao = "Em espera";
            }
        }

        entrada.close();
    }

    // Metodo para guardar os acontecimentos do sistema
    public static int salvarEvento(
            String[] lista, int quantidade, String descricao
    ) {

        if (quantidade < lista.length) {

            lista[quantidade] = descricao;
            quantidade++;
        }

        return quantidade;
    }
}
