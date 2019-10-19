package br.com.cadastro.api.utils;

import java.util.InputMismatchException;

/**
 * Classe utilitária de CPF
 * 
 * @author Leonardo Araújo
 */
public class CpfUltil {

	private static final String mascaraCpf = "###.###.###-##";

	/**
	 * Formata o <b>CPF</b> com a máscara padrão
	 * <dd><i> Ex: 000.000.000-00</i></dd>
	 * 
	 * <br>
	 * 
	 * <dd>Caso ele já esteja formatado, os caracteres não numéricos 
	 * serão removidos e será aplicada a mascara padrão</dd>
	 * 
	 * <br>
	 * 
	 * @param cpf
	 * @return
	 */
	public static String formatarCpf(final String cpf) {
		String cpfSemMascara = cpf.replaceAll("[^0-9]", "");
		return Util.formatarString(cpfSemMascara, mascaraCpf);
	}

	/**
	 * <dt>Valida se um <i>CPF</i> válido com e sem máscara</dt> 
	 * <dd><i> Ex: 00000000000</i></dd>
	 * <dd><i> Ex: 000.000.000-00</i></dd>
	 * 
	 * @param cpf
	 * @return boolean
	 */
	public static boolean isValid(final String cpf) {
		String cpfSemMascara = cpf.replaceAll("[^0-9]", "");

        /*
         * considera-se erro CPF's formados por uma sequencia de numeros iguais
         * ou string nula
         */
        if (cpfSemMascara == null			    || 
        	cpfSemMascara.equals("00000000000") || cpfSemMascara.equals("11111111111") ||
            cpfSemMascara.equals("22222222222") || cpfSemMascara.equals("33333333333") ||
            cpfSemMascara.equals("44444444444") || cpfSemMascara.equals("55555555555") ||
            cpfSemMascara.equals("66666666666") || cpfSemMascara.equals("77777777777") ||
            cpfSemMascara.equals("88888888888") || cpfSemMascara.equals("99999999999") ||
            (cpfSemMascara.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
        	// Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;

            /*
             * converte o i-esimo caractere do CPF em um numero:
             * por exemplo, transforma o caractere '0' no inteiro 0
             * (48 eh a posicao de '0' na tabela ASCII) 
             */
            for (i=0; i<9; i++) {              
		        num = (int)(cpfSemMascara.charAt(i) - 48); 
		        sm = sm + (num * peso);
		        peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico
          
            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;

            for(i=0; i<10; i++) {
	            num = (int)(cpfSemMascara.charAt(i) - 48);
	            sm = sm + (num * peso);
	            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == cpfSemMascara.charAt(9)) && (dig11 == cpfSemMascara.charAt(10))) return(true);
            else return(false);

        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}