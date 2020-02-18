package sockets;

import java.io.*;

/**
 *
 * @author kei98
 */
public class Servidor extends Conexion {

    private DataInputStream in;
    private BufferedReader input;
    private DataOutputStream out;
    private int num;
    private int cantIntentos = 5;
    private boolean acierto = false;
    
    
    public Servidor() throws IOException {
        super("servidor");

        // Inicializa el servidor
        try {
            num = (int) (Math.random()*200) +1;
            
            System.out.println("Servidor inicializado");

            System.out.println("Esperando al cliente ...");

            socketCliente = socketServer.accept();
            System.out.println("Cliente aceptado");
            
            System.out.println("El número es: " + num);
            
            // Recibe los mensajes desde el cliente 
            in = new DataInputStream(
                    new BufferedInputStream(socketCliente.getInputStream()));

            // Lee los mensajes del teclado
            input = new BufferedReader(new InputStreamReader(System.in));

            // Envía los mensajes al cliente
            out = new DataOutputStream(socketCliente.getOutputStream());
            
            out.writeUTF("Bienvenido. Adivina el número entre 1 y 200.\n          Tienes 5 "
                    + "intentos. Introduce un número para comenzar");
            
            String line;
            String s;
            
            
            // Lee los mensajes del cliente. El programa finaliza cuando la cantidad de
            // intentos sea igual a 0 o que ya el cliente haya acertado
            while (cantIntentos > 0 && !acierto) {
                try {
                    line = in.readUTF();
                    System.out.println("Cliente: " + line);
                    try{
                        s = calcular(Integer.parseInt(line));
                        out.writeUTF(s);
                    }catch(NumberFormatException e){
                        out.writeUTF("Debe ingresar solo números " + e);
                    }

                } catch (IOException i) {
                    System.out.println(i);
                }
            }
            System.out.println("Cerrando conexiones");
            // Cerrando conexiones
            socketCliente.close();
            socketServer.close();
            in.close();
            out.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }
/**
 * 
 * @param n: número digitado por el cliente
 * @return 
 *         String que contiene cuál es la relación del número introducido con el autogenerado
 */
    private String calcular(int n){
        String s = "";
        cantIntentos--;
        String cantI = cantIntentos == 1 ? " Te queda " + cantIntentos + " intento" : " Te quedan " + cantIntentos + " intentos";
        String cantZ = "\n          Te has quedado sin intentos. El número generado fue: " + this.num;
        if(n > 0 && n < 201){
            if(n > this.num){
                s += "El número es menor.";
            }else if(n < this.num){
                s += "El número es mayor.";
            }else{
                s += "Acertaste. Adiós";
                acierto = true;
            }
        }else{
            s += "Número fuera de rango.";
        }
        if(!s.contains("Acertaste")){
            s += cantIntentos != 0 ? cantI: cantZ;
        }
        
        return s;
    }

    public static void main(String args[]) throws IOException {
        Servidor server = new Servidor();
    }
}