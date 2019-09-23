import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.*;
import java.util.*;

public class cifrar_descifrar {
	public static void main(String[] args) {

		String comando1 = "-c";
		String comando2 = "-d";

		// COMANDO 1 o COMANDO 2
		if ((comando1.equals(args[0])) || (comando2.equals(args[0]))) {
			// leer clave por teclado
			try {
				InputStreamReader leer_clave = new InputStreamReader(System.in);
				BufferedReader buff_clave = new BufferedReader(leer_clave);
				System.out.print("Escriba una clave: ");
				String clave = buff_clave.readLine();

				// pasar clave a la clase SecretKey
				try {
					SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
					DESKeySpec kspec = new DESKeySpec(clave.getBytes());
					SecretKey ks = skf.generateSecret(kspec);

					// Inicializar el cifrado
					try {
						Cipher cifrado = Cipher.getInstance("DES");

						// Escojo modo cifrado o descifrado segun sea el caso

						if (comando1.equals(args[0])) {
							cifrado.init(Cipher.ENCRYPT_MODE, ks);
						} // MODO CIFRAR
						if (comando2.equals(args[0])) {
							cifrado.init(Cipher.DECRYPT_MODE, ks);
						} // MODO DESCIFRAR

						// Leer fichero

						InputStream archivo = new FileInputStream(args[1]);
						OutputStream fich_out = new FileOutputStream(args[2]);

						byte[] buffer = new byte[1024];
						byte[] bloque_cifrado;
						String textoCifrado = new String();
						int fin_archivo = -1;
						int leidos;// numero de bytes leidos

						leidos = archivo.read(buffer);

						while (leidos != fin_archivo) {
							bloque_cifrado = cifrado.update(buffer, 0, leidos);
							textoCifrado = textoCifrado + new String(bloque_cifrado, "ISO-8859-1");
							leidos = archivo.read(buffer);
						}

						archivo.close();

						bloque_cifrado = cifrado.doFinal();
						textoCifrado = textoCifrado + new String(bloque_cifrado, "ISO-8859-1");
						// ISO-8859-1 es ISO-Latin-1

						fich_out.write(textoCifrado.getBytes("ISO-8859-1"));// escribir fichero

					}
					// Inicializacion de cifrado
					catch (javax.crypto.NoSuchPaddingException nspe) {
					} // Instanciacion DES
					catch (javax.crypto.IllegalBlockSizeException ibse) {
					} // metodo doFinal
					catch (javax.crypto.BadPaddingException bpe) {
					} // metodo doFinal
				}
				// pasar clave a la clase SecretKey
				catch (java.security.InvalidKeyException ike) {
				} catch (java.security.spec.InvalidKeySpecException ikse) {
				} catch (java.security.NoSuchAlgorithmException nsae) {
				}
			}
			// leer del teclado la clave como String
			catch (java.io.IOException ioex) {
			}
		}
	}
}