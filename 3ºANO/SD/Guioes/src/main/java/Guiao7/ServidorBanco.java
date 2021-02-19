package Guiao7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

class BankTeller implements Runnable {

    private Socket socket;
    private Banco bank;

    public BankTeller(Socket s, Banco b) {
        this.socket = s;
        this.bank = b;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String line = "";
            while ((line = in.readLine()) != null && !line.equals("quit")) {
                System.out.println("Recebi a mensagem: " + line);
                String[] partes = line.split(" ");
                switch (partes[0]) {
                    case "criarConta":
                        try {
                            double preco = Double.parseDouble(partes[1]);
                            int val = this.bank.criarConta(preco);
                            out.println("ID " + val);
                            out.flush();
                        } catch (NumberFormatException e) {
                            out.println(e);
                            out.flush();
                        }
                        break;
                    case "fecharConta":
                        try {
                            int id = Integer.parseInt(partes[1]);
                            double val = this.bank.fecharConta(id);
                            out.println("Saldo " + val);
                            out.flush();
                        } catch (ContaInvalida c) {
                            out.println(c);
                            out.flush();
                        } catch (NumberFormatException e) {
                            out.println(e);
                            out.flush();
                        }
                        break;
                    case "transferir":
                        try {
                            int corigem = Integer.parseInt(partes[1]);
                            int cdestino = Integer.parseInt(partes[2]);
                            double valor = Double.parseDouble(partes[3]);
                            this.bank.transferir(corigem, cdestino, valor, partes[4]);
                            out.println("Sucess operation!");
                            out.flush();
                        } catch (SaldoInsuficiente s) {
                            out.println(s);
                            out.flush();
                        } catch (ContaInvalida c) {
                            out.println(c);
                            out.flush();
                        } catch (NumberFormatException e) {
                            out.println(e);
                            out.flush();
                        }
                        break;
                    case "consultarTotal":
                        int[] arraycontas = new int[partes.length - 1];
                        for (int i = 1; i < arraycontas.length; i++) {
                            try {
                                int conta = Integer.parseInt(partes[i]);
                                arraycontas[i - 1] = conta;
                            } catch (NumberFormatException e) {
                                out.println(e);
                                out.flush();
                            }
                        }
                        double res = this.bank.consultarTotal(arraycontas);
                        out.println(res);
                        out.flush();
                        break;
                    case "consultar":
                        try {
                            int id = Integer.parseInt(partes[1]);
                            double saldo = this.bank.consultar(id);
                            out.println("Saldo " + saldo);
                            out.flush();
                        } catch (ContaInvalida c) {
                            out.println(c);
                            out.flush();
                        } catch (NumberFormatException e) {
                            out.println(e);
                            out.flush();
                        }
                        break;
                    case "levantar":
                        try {
                            int id = Integer.parseInt(partes[1]);
                            int valor = Integer.parseInt(partes[2]);
                            this.bank.levantar(id, valor, partes[3]);
                            out.println("Sucess operation!");
                            out.flush();
                        } catch (ContaInvalida c) {
                            out.println(c);
                            out.flush();
                        } catch (SaldoInsuficiente c) {
                            out.println(c);
                            out.flush();
                        } catch (NumberFormatException e) {
                            out.println(e);
                            out.flush();
                        }
                        break;
                    case "depositar":
                        try {
                            int id = Integer.parseInt(partes[1]);
                            int valor = Integer.parseInt(partes[2]);
                            this.bank.depositar(id, valor, partes[3]);
                            out.println("Sucess operation!");
                            out.flush();
                        } catch (ContaInvalida c) {
                            out.println(c);
                            out.flush();
                        } catch (NumberFormatException e) {
                            out.println(e);
                            out.flush();
                        }
                        break;
                    case "movimentos":
                        try {
                            int id = Integer.parseInt(partes[1]);
                            ArrayList<Movimento> movs = this.bank.movimentos(id);
                            out.println(movs.size());
                            Iterator it = movs.iterator();
                            while (it.hasNext()) {
                                Movimento m = (Movimento) it.next();
                                out.println(m.toString());
                                out.flush();
                            }
                        } catch (ContaInvalida c) {
                            out.println("err " + c);
                            out.flush();
                        }
                        break;
                    default:
                        out.println("Invalid Command!");
                        out.flush();
                        break;
                }
            }
            System.out.println("Closing connection");
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

public class ServidorBanco {

    public static void main(String args[]) {
        try {
            Banco bank = new Banco();
            ServerSocket server = new ServerSocket(12345);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            while (true) {
                Socket socket = server.accept();
                System.out.println("Client accepted");
                Thread teller = new Thread(new BankTeller(socket, bank));
                teller.start();
            }
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
