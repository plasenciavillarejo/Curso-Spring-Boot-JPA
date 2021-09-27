package com.bolsadeideas.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

/* 1.- Le agregamos el componente para inyectarle el método del controlador FacturaController.java -> String Ver()
 * 2.- De este modo obtenemos el valor que se retorna al ver una facutra por id y lo podemos pasar a pdf. 
 * 3.- Lo renderizamos con el parámetro -> Format*/
@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {

	/* 1.- Implementamos el método para crear el documento PDF */

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		/*
		 * 1.- Capturamos los parámetros que no están enviando. 2.- Importamos Factura,
		 * la obtenemos atraves del model -> get("factra") es el mismo nombre con el que
		 * se guarda. 3.- Tenemos que realizar un cast ya que guardamos un objeto.
		 */
		Factura factura = (Factura) model.get("factura");

		/*
		 * 1.- Creamos una tabla : PdfPTable tabla = new PdfPTable(1); 2.- Número de
		 * columnas - 1. -> PdfPTable(1). 3.- Titulo de la factura. 4.- Nombre, Apellido
		 * y Correo del cliente.
		 */
		PdfPTable tabla = new PdfPTable(1);
		tabla.setSpacingAfter(20);
		
		
		/* Creamos la celda para customizar la vista PDF.
		 * Se añadel el titulo con una celda y se le da colot y un padding.*/
		PdfPCell cell = null;
		cell = new PdfPCell(new Phrase("Datos del cliente"));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		tabla.addCell(cell);
		
		tabla.addCell(factura.getCliente().getNombre() + "" + factura.getCliente().getApellido());
		tabla.addCell(factura.getCliente().getEmail());

		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setSpacingAfter(20);
		
		/*Se añadel el titulo con una celda y se le da colot y un padding.*/
		cell = new PdfPCell(new Phrase("Datos de la Factura"));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		tabla2.addCell(cell);
		tabla2.addCell("Folio: " + factura.getId());
		tabla2.addCell("Descripción: " + factura.getDescripcion());
		tabla2.addCell("Fecha: " + factura.getCreateAt());

		/*
		 * 5.- Por último ya tenemos las tablas, pasamos a guardar dichas tablas. 6.-
		 * Con esto ya tenemos nuestro PDF con los datos del cliente y de la Factura.
		 */
		document.add(tabla);
		document.add(tabla2);
		

		/*
		 * 7.- Tabla Detalle Factura. 8.- Creamos columnas
		 */
		PdfPTable tabla3 = new PdfPTable(4);
		
		/* Tamaño de las columnas. */
		tabla3.setWidths(new float [] {3.5f, 1, 1, 1});
		
		tabla3.addCell("Producto");
		tabla3.addCell("Precio");
		tabla3.addCell("Cantidad");
		tabla3.addCell("Total");

		/* 9.- Iteramos con un for para rellenar las líneas por cada item. */

		for (ItemFactura item : factura.getItems()) {
			tabla3.addCell(item.getProducto().getNombre());
			tabla3.addCell(item.getProducto().getPrecio().toString());
			
		/* Alineamos el texto. */
			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tabla3.addCell(cell);
			
			tabla3.addCell(item.calcularImporte().toString());
		}

		/* Creamos una celda para el gran total. */
		
		cell = new PdfPCell(new Phrase("Total: "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tabla3.addCell(cell);
		
		tabla3.addCell(factura.getTotal().toString());
		
		document.add(tabla3);
		
	}

}
