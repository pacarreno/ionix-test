package cl.ionix.testtecnico.utils;

public class Utils {

    /**
     * Permite contar desde los milisegundos en el instante de ser llamado
     * @return
     */
	public static long startCounting() {
		return System.currentTimeMillis();
	}

    /**
     * Calcula el tiempo trascurrido desde los milisegundos dados como parametros
     * @param started Milisegundos desde donde comenzar a contar
     * @return 
     */
	public static long calculateTimeElapsed(long started) {
		return System.currentTimeMillis() - started;
	}

}
