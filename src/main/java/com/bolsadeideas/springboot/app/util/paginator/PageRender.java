package com.bolsadeideas.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

/* Usamos los Generic de Java ya que vamos a paginar una lísta de: 
 	Clientes, Productos o cualquier clase de Entidad. */
public class PageRender<T> {

	private String url;
	/* Listado Paginable de los clientes de tipo genérico'<T>' */
	private Page<T> page;
	private int totalPaginas;
	private int numElementosPorPagina;
	private int paginaActual;

	/* Vamos a obtener una Lista de PageItem ya que son varias páginas */
	public List<PageItem> paginas;

	/* Generar Constructor: Source / Generator Constructor using Fields */
	public PageRender(String url, Page<T> page) {
		super();
		this.url = url;
		this.page = page;
		/* Inicializamos <PageItem> paginas */
		this.paginas = new ArrayList<PageItem>();

		/*
		 * El page nos entrega la información tanto del totaPaginas como
		 * numElementosPagina numElementosPorPagina= Obtenemos el número de elementos
		 * por páginas descrito en el ClienteController, Pageable .. (5 páginas)
		 * Pageable pageRequest
		 */

		numElementosPorPagina = page.getSize();
		totalPaginas = page.getTotalPages();

		/*
		 * Obtenemos la página actual, debemos de indicar +1 ya que empieza en la página
		 * 0.
		 */
		paginaActual = page.getNumber() + 1;

		int desde, hasta;
		if (totalPaginas <= numElementosPorPagina) {
			desde = 1;
			hasta = totalPaginas;
		} else {
			/* Vamos dividir los campos a mostrar por cada pagina. */
			if (paginaActual <= numElementosPorPagina / 2) {
				desde = 1;
				hasta = numElementosPorPagina;
			} else if (paginaActual >= totalPaginas - numElementosPorPagina / 2) {
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
				/*
				 * No se cumple el rango inicial y rango final. Sabemos que estamos en el medio.
				 */
			} else {
				desde = paginaActual - numElementosPorPagina / 2;
				hasta = numElementosPorPagina;
			}
		}

		/*
		  Recorremos con un for para ir rellenando cada una de las paginas con sus items para mostrarse en la vista.
		 */
		for (int i = 0; i < hasta; i++) {
			/* Comprobamos que estamos en la página 1. */
			paginas.add(new PageItem(desde + i, paginaActual == desde + i));
		}
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}

	/*---------------------------- INICIO PAGINADOR ----------------------------
	  Saber si estamos en la primera pagina, última página, Página adelante o Página detras
	 */

	
	public boolean isFirst() {
		return page.isFirst();
	}
		
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}

	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
	/*---------------------------- FIN PAGINADOR ---------------------------- */


}
