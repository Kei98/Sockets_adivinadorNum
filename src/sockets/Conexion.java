package sockets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.Inet4Address;

/**
 *
 * @author kei98
 */
public class Conexion {
    /*
       Para no tener que cambiar la dirección del host cuando se cambia de dispositivo
       NOTA: si se va a correr servidor y cliente en diferentes dispositivos se deben comentar 
             las 2 líneas debajo de este comentario y especificar en ambos la dirección ip del que correrá 
             el servidor
    */
    private final Inet4Address ip = (Inet4Address) Inet4Address.getLocalHost(); //Devuelve la dirección local
    private final String s = ip.getHostAddress(); //Obtiene la ip del dispositivo
    
    
    protected final int PUERTO = 5000; //Puerto para la conexión
    protected final String HOST = s; //Host para la conexión

    protected String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor
    protected ServerSocket socketServer; //Socket del servidor
    protected Socket socketCliente; //Socket del cliente
    protected DataOutputStream salidaServidor, salidaCliente; //Flujo de datos de salida

    //Constructor
    public Conexion(String tipo) throws IOException {
        if (tipo.equalsIgnoreCase("servidor")) {
            socketServer = new ServerSocket(PUERTO);//Se crea el socket para el servidor en puerto seleccionado
//            socketCliente = new Socket(); //Socket para el cliente
        } else {
            socketCliente = new Socket(HOST, PUERTO); //Socket para el cliente en localhost en puerto seleccionado
        }
    }
}
