package br.com.cadastro.api.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

/**
 * Classe utilitária da aplicação
 * 
 * @author Leonardo Araújo
 */
public class Util {

	/**
	 * Retorna o valor formatado com a máscara informada
	 * @param valor
	 * @param mascara
	 * @return
	 */
	public static String formatarString(final String valor, final String mascara) {
		try {
			MaskFormatter formatter = new MaskFormatter(mascara);
			formatter.setValueContainsLiteralCharacters(false);
			return formatter.valueToString(valor);
		} catch(ParseException e) {
			return null;
		}
	}
}
