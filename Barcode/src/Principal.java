import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class Principal {
	
	private static Font fontBold = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
    private static Font fontNormal = new Font(Font.FontFamily.COURIER, 11, Font.NORMAL);
    
    public Principal() {
    }
    
    public static void generarFactura(String direccion, String clave, String nombre) throws IOException, DocumentException {
    	
     	Document document = getDocument();
     	PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream("factura.pdf"));
     	Image img = Image.getInstance("src/logo2.jpg");
     	Image img2 = Image.getInstance("src/logo.jpg");
     	document.open();
     	
     	PdfPTable table = getTable();
  		
  		document.add(getInformation(" "));
        table.addCell(getImage(img));
        table.addCell(getDireccion(direccion));
        table.addCell(getImage(img2)).setPadding(20);
         
        document.add(table);
      
        document.add(getBarcode(document, pw, "tumama", clave));
        document.add(getInformation(" "));
        document.add(getInformationFooter(nombre));
     	document.close();
     	
     }
     
     private static Document getDocument(){
    	Document document = new Document(new Rectangle( getConvertCmsToPoints(13), getConvertCmsToPoints(10)));
      	document.setMargins(0, 0, 1, 1);
      	return document;
     }
     
     private static PdfPCell getDireccion(String text) throws DocumentException, IOException {
      	Chunk chunk = new Chunk();
      	chunk.append(text);
      	chunk.setFont(fontBold);
      	PdfPCell cell = new PdfPCell(new Paragraph(chunk));
  		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
  		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
  		cell.setBorder(Rectangle.NO_BORDER);
  		return cell;
      }
     
     private static Paragraph getInformation(String informacion) {
    	Paragraph paragraph = new Paragraph();
    	Chunk chunk = new Chunk();
  		paragraph.setAlignment(Element.ALIGN_CENTER);
  		chunk.append(informacion);
  		chunk.setFont(fontNormal);
  		paragraph.add(chunk);
   		return paragraph;
      }
     
     private static Paragraph getInformationFooter(String informacion) {
     	Paragraph paragraph = new Paragraph();
     	Chunk chunk = new Chunk();
   		paragraph.setAlignment(Element.ALIGN_CENTER);
   		chunk.append(informacion);
   		chunk.setFont(new Font(Font.FontFamily.COURIER, 8, Font.NORMAL));
   		paragraph.add(chunk);
    		return paragraph;
       }
  
     private static PdfPTable getTable() throws DocumentException {
     	PdfPTable table = new PdfPTable(3);
     	table.setWidths(new int[]{5, 5, 5});
		return table;
     }
     
     private static PdfPCell getImage(Image image) {
     	PdfPCell cell = new PdfPCell(new Paragraph());
     	cell.setImage(image);
 		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
 		cell.setBorder(Rectangle.NO_BORDER);
 		return cell;
     }
     
     private static float getConvertCmsToPoints(float cm) {
     	return cm * 28.4f;
     }
     
    private static Image getBarcode(Document document,  PdfWriter pdfWriter, String servicio,String  codigoTransaccion){
	 	PdfContentByte cimg = pdfWriter.getDirectContent();
	   	Barcode128 code128 = new Barcode128();
	   	code128.setCode(servicio + addZeroLeft(codigoTransaccion));
	   	code128.setCodeType(Barcode128.START_C);
		code128.setTextAlignment(Element.ALIGN_CENTER);
		code128.setBarHeight(30);
		code128.setBaseline(15);
		Image image = code128.createImageWithBarcode(cimg, null, null);
		float scaler = ((document.getPageSize().getWidth() - document.leftMargin()  - document.rightMargin() - 0) / image.getWidth()) * 70;
		image.scalePercent(scaler);
		image.setAlignment(Element.ALIGN_CENTER);
		return image;
	}
    
    private static String addZeroLeft(String num) {
    	NumberFormat formatter = new DecimalFormat("0000000");
     	return formatter.format((num != null) ? Integer.parseInt(num) : 0000000);
	}
    
}