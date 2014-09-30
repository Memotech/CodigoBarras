import java.io.IOException;

import javax.swing.JOptionPane;

import com.itextpdf.text.DocumentException;

public class Prueba {
	static String ok = "";
	public static void main(String[] args) throws IOException, DocumentException {
		try {
			Principal.generarFactura("DGPT", "587458", "Computadora");
			ok = "vientos";
		} catch (Exception e) {
			e.printStackTrace();
			ok = "no ok";
		} finally {
			JOptionPane.showMessageDialog(null, ok);
		}

	}
	
}