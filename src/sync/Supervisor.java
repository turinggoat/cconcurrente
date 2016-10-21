package sync;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Modela un supervisor de varios hilos en ejecuci&ocute;n. Pone en
 * ejecuci&oacute;n los hilos y al final exhibe los resultados.
 * @author <a href="mailto:manuel_castillo_cc@ciencias.unam.mx" >Manuel
 * "nachintoch" Castillo</a>
 * @version 1.0, october 2016
 * @param <E> - Clase de los hilos que se van a supervisar.
 * @since Computaci&oacute;n concurrente 2017-1
 */
public class Supervisor<E extends Thread> {

    // atributos de clase

    /**
     * Recursos a compartir entre los procesos.
     * @since Supervisor 1.0, october 2016
     */
    protected final AtomicInteger X, Y;

    /**
     * Referencia a los procesos a administrar.
     * @since Supervisor 1.0, october 2016
     */
    protected final E[] PROCS;

    /**
     * Da formato a la fecha en la que se muestran los mensajes. El formato
     * es horas:mituos:segundo
     * @since Supervisor 1.0, october 2016
     */
    public static final SimpleDateFormat DATE_FORMATER =
            new SimpleDateFormat("hh:mm:ss");

    // bloque de código estático

    static {
        DATE_FORMATER.setTimeZone(TimeZone.getDefault());
    }

    // mÃ©todos constructores

    /**
     * Construye un supervisor con todos los atributos necesarios.
     * @param procs - Los procesos a supervisar.
     * @param x - Valor inicial del recurso compartido x.
     * @param y - Valor inicial del recurso compartido y.
     * @since Supervisor 1.0, october 2016
     */
    public Supervisor(E[] procs, int x, int y) {
        PROCS = procs;
        X = new AtomicInteger(x);
        Y = new AtomicInteger(y);
    }//constructor con todo

    // mÃ©todos de implementaciÃ³n

    /**
     * Ejecuta la aplicaci&oacute;n concurrente y muestra los resultados al
     * final. Pone a correr los procesos y espera a que terminen de ejcutarse
     * para finalmente mostrar los estados finales de los recursos compartidos.
     * @since Supervisor 1.0, october 2016
     */
    public void run() {
        printMessage("Estados iniciales de la memoria:\nX = " +X +"\nY = " +Y);
        printMessage("Inicializando procesos");
        for(Thread t : PROCS) {
            t.start();
        }//inicia los hilos
        printMessage("Procesos inicializados y en ejecuci\u00F3n");
        // synchronized(this) {
        //     try {
        //         wait();
        //     } catch(InterruptedException e) {}
        // }//duerme hasta que alguno lo despierte
        boolean retry;
        for(Thread t : PROCS) {
            try {
                retry = true;
                while(retry) {
                    t.join();
                    retry = false;
                }//intenta terminar el hilo
            } catch(InterruptedException e) {
                try {
                    Thread.sleep(100);
                } catch(InterruptedException e1) {}
            }//se protege de posibles excepciones
        }//trata de detener todos los procesos
        printMessage("Procesos terminados");
        printMessage("Resultado de la ejecuci\u00F3n:\nX = " +X +"\nY = " +Y);
    }//run

    // mÃ©todos estÃ¡ticos

    /**
     * Muestra un mensaje en la consola, anteponiendo la hora en la que se
     * muestra el mensaje.
     * @param msg - El mensaje a mostrar en pantalla
     */
    public static void printMessage(String msg) {
        System.out.println("[" +DATE_FORMATER.format(new Date()) +"] - " +msg);
    }//printMessage

}//clase supervisor
