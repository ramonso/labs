package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class $ {

    // --- Manipulacao de Caracteres (char) -----------------------------------

    /**
     * Method isDigit.
     *
     * @param p_chr
     * @return
     */
    public static final boolean isDigit(final char p_chr) {
        return (p_chr >= '0' && p_chr <= '9');
    }

    /**
     * Method isNotDigit.
     *
     * @param p_chr
     * @return
     */
    public static final boolean isNotDigit(final char p_chr) {
        return !isDigit(p_chr);
    }

    public static final boolean isLetter(char p_chr) {
        if ((p_chr >= 'A' && p_chr <= 'Z') || //
            (p_chr >= 'a' && p_chr <= 'z')) {
            return true;
        }

        p_chr = removeAccent(p_chr);
        return (p_chr >= 'A' && p_chr <= 'Z') || //
               (p_chr >= 'a' && p_chr <= 'z');
    }

    public static final boolean isNotLetter(final char p_chr) {
        return !isLetter(p_chr);
    }

    public static final boolean isSymbol(final char p_chr) {
        return !isDigit(p_chr) && !isLetter(p_chr);
    }

    public static final boolean isNotSymbol(final char p_chr) {
        return isDigit(p_chr) || isLetter(p_chr);
    }

    public static final char removeAccent(final char p_chr) {
        switch (p_chr) {
        // se for algum tipo de 'A' com qualquer acento
            case 'À':
            case 'Á':
            case 'Â':
            case 'Ã':
            case 'Ä':
            case 'Å':
                return 'A';

            case 'à':
            case 'á':
            case 'â':
            case 'ã':
            case 'ä':
            case 'å':
                return 'a';

                // se for algum tipo de 'C' com qualquer acento
            case 'Ç':
                return 'C';

            case 'ç':
                return 'c';

                // se for algum tipo de 'E' com qualquer acento
            case 'È':
            case 'É':
            case 'Ê':
            case 'Ë':
                return 'E';

            case 'è':
            case 'é':
            case 'ê':
            case 'ë':
                return 'e';

                // se for algum tipo de 'I' com qualquer acento
            case 'Ì':
            case 'Í':
            case 'Î':
            case 'Ï':
                return 'I';

            case 'ì':
            case 'í':
            case 'î':
            case 'ï':
                return 'i';

                // se for algum tipo de 'N' com qualquer acento
            case 'Ñ':
                return 'N';

            case 'ñ':
                return 'n';

                // se for algum tipo de 'O' com qualquer acento
            case 'Ò':
            case 'Ó':
            case 'Ô':
            case 'Õ':
            case 'Ö':
                return 'O';

            case 'ò':
            case 'ó':
            case 'ô':
            case 'õ':
            case 'ö':
                return 'o';

                // se for algum tipo de 'U' com qualquer acento
            case 'Ù':
            case 'Ú':
            case 'Û':
            case 'Ü':
                return 'U';

            case 'ù':
            case 'ú':
            case 'û':
            case 'ü':
                return 'u';

                // se for algum tipo de 'Y' com qualquer acento
            case 'Ý':
                return 'Y';

            case 'ý':
            case 'ÿ':
                return 'y';

                // Quaisquer outras letras...
            default:
                return p_chr;
        }
    }

    // --- Validacao de valores String ----------------------------------------

    public static String strip(final String p_str) {
        if (p_str == null || p_str.length() == 0) {
            return null;
        }

        final String stripped = p_str.trim();
        return (stripped.length() == 0) ? null : stripped;
    }

    /**
     * Efetua verificacao se determinado valor informado e nulo ou nao. Caso nao seja nulo, retorna
     * o proprio valor (primeiro argumento). Caso seja nulo, entao retorna o segundo argumento.
     *
     * @param p_value Valor a ser verificado se <code>NULL</code>.
     *
     * @param p_other Valor alternativo, a ser retornado caso o primeiro argumento seja nulo.
     *
     * @return Se o primeiro valor for nulo, retorna o segundo valor. Caso nao seja nulo,
     *         retorna o primeiro valor.
     */
    public static <V extends Object> V nvl(final V p_value, final V p_other) {
        // Parametrizado em V, para nao confundir com o T da declaracao da classe.
        return (p_value == null) ? p_other : p_value;
    }

    @SafeVarargs
    public static <V extends Object> V nvl(final V... p_args) {
        if (p_args == null) {
            return null;
        }

        final int argsLen = p_args.length;
        if (argsLen == 0) {
            return null;
        }

        // Percorre a lista e retorna o primeiro argumento que nao estiver nulo.
        for (int i = 0; i < argsLen; i++) {
            if (p_args[i] != null) {
                return p_args[i];
            }
        }

        return null;
    }

    public static <V extends Object> V nvlEmpty(final V p_value, final V p_other) {
        // Parametrizado em V, para nao confundir com o T da declaracao da classe.
        return (isEmpty(p_value)) ? p_other : p_value;
    }

    @SafeVarargs
    public static <V extends Object> V nvlEmpty(final V... p_args) {
        if (p_args == null || p_args.length == 0) {
            return null;
        }

        // Percorre a lista e retorna o primeiro argumento que nao estiver nulo.
        for (final V arg : p_args) {
            if (isEmpty(arg)) {
                return arg;
            }
        }

        return null;
    }

    /**
     * Verifica se um caracter informado esta vazio ou nao. Considera a semantica de programas Cobol, onde
     * o espaco em branco ou caractere x00 sao vazios.
     *
     * @param p_chr Valor a ser verificado.
     *
     * @return Retorna TRUE se o valor informado estiver vazio ou se apenas possuir caracter espaco, ou FALSE caso
     *         contrario.
     */
    public static final boolean isEmpty(final char p_chr) {
        return (p_chr == '\0' || p_chr == ' ');
    }

    /**
     * Verifica se um caracter informado nao e considerado vazio, pela semantica de programas Cobol - onde
     * o espaco em branco ou caractere x00 sao vazios.
     *
     * @param p_chr Valor a ser verificado.
     *
     * @return Retorna TRUE se o valor informado estiver valorado (diferente de vazio), ou FALSE caso
     *         contrario.
     */
    public static final boolean isNotEmpty(final char p_chr) {
        return !isEmpty(p_chr);
    }

    /**
     * Verifica se o argumento informado esta nulo ou vazio.
     *
     * @param p_str Valor a ser verificado; pode ser do tipo String, StringBuilder, StringBuffer ou CharBuffer.
     *
     * @return Retorna TRUE se o valor informado estiver nulo ou se apenas possuir caracter espaco, ou FALSE caso
     *         contrario.
     */
    public static final boolean isEmpty(final CharSequence p_str) {
        if (p_str == null) {
            return true;
        }

        final int strLen = p_str.length();
        if (strLen == 0) {
            return true;
        }

        // Percorre toda a cadeia de caracteres para verificar se existe algo diferente de
        // espaco-em-branco e x00.
        for (int i = 0; i < strLen; i++) {
            if (p_str.charAt(i) != ' ') {
                return false;
            }
        }

        // Se chegou ate aqui, entao 'p_str' esta vazio.
        return true;
    }

    public static final boolean isNotEmpty(final CharSequence p_str) {
        if (p_str == null) {
            return false;
        }

        final int strLen = p_str.length();
        if (strLen == 0) {
            return false;
        }

        // Percorre toda a cadeia de caracteres para verificar se existe algo diferente de
        // espaco-em-branco (e x00?)
        for (int i = 0; i < strLen; i++) {
            if (p_str.charAt(i) != ' ') {
                return true;
            }
        }

        // Se chegou ate aqui, entao 'p_str' esta vazio.
        return false;
    }

    /**
     * Verifica se o argumento informado esta nulo ou vazio.
     *
     * @param p_str Valor a ser verificado; pode ser de qualquer tipo.
     *
     * @return Retorna TRUE se o valor informado estiver nulo ou vazio conforme seu tipo, ou FALSE caso
     *         contrario.
     */
    public static final boolean isEmpty(final Object[] p_array) {
        return (p_array == null || p_array.length == 0);
    }

    public static final boolean isNotEmpty(final Object[] p_array) {
        return !isEmpty(p_array);
    }

    /**
     * Verifica se o argumento informado esta nulo ou vazio.
     *
     * @param p_str Valor a ser verificado; pode ser de qualquer tipo.
     *
     * @return Retorna TRUE se o valor informado estiver nulo ou vazio conforme seu tipo, ou FALSE caso
     *         contrario.
     */
    public static final boolean isEmpty(final Collection<?> p_rol) {
        return (p_rol == null || p_rol.size() == 0);
    }

    public static final boolean isNotEmpty(final Collection<?> p_rol) {
        return !isEmpty(p_rol);
    }

    /**
     * Verifica se o argumento informado esta nulo ou vazio.
     *
     * @param p_str Valor a ser verificado; pode ser de qualquer tipo.
     *
     * @return Retorna TRUE se o valor informado estiver nulo ou vazio conforme seu tipo, ou FALSE caso
     *         contrario.
     */
    public static final boolean isEmpty(final File p_file) {
        return (p_file == null || p_file.length() == 0);
    }

    public static final boolean isNotEmpty(final File p_file) {
        return !isEmpty(p_file);
    }

    /**
     * Verifica se o argumento informado esta nulo ou vazio.
     *
     * @param p_str Valor a ser verificado; pode ser de qualquer tipo.
     *
     * @return Retorna TRUE se o valor informado estiver nulo ou vazio conforme seu tipo, ou FALSE caso
     *         contrario.
     */
    public static final boolean isEmpty(final Object p_obj) {
        // Teste obvio.
        if (p_obj == null) { // Perspectiva pessimista.
            return true;

        } else if (p_obj instanceof Boolean) {
            // boolean.
            return !((Boolean) p_obj).booleanValue();

        } else if (p_obj instanceof Character) {
            // Character.
            return isEmpty(((Character) p_obj).charValue());

            // Efetua o teste de acordo com o tipo do valor informado.
        } else if (p_obj instanceof CharSequence) {
            // CharBuffer, Segment, String, StringBuffer, StringBuilder.
            return isEmpty((CharSequence) p_obj);

        } else if (p_obj instanceof Number) {
            // AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float, Integer, Long, Short.
            return isZero((Number) p_obj);

        } else if (p_obj instanceof Object[]) {
            // Arrays.
            return ((Object[]) p_obj).length == 0;

        } else if (p_obj instanceof Collection<?>) {
            // List, LinkedList, ArrayList, Vector, Set, TreeSet, HashSet, ...
            return ((Collection<?>) p_obj).isEmpty();

        } else if (p_obj instanceof File) {
            // File.
            return ((File) p_obj).length() == 0;

        } else { // no desespero de nao ter encontrado o tipo, pelo menos deve ter um literal valorizado.
            return isEmpty(p_obj.toString());
        }
    }

    public static final boolean isNotEmpty(final Object p_obj) {
        return !isEmpty(p_obj);
    }

    /**
     * Verifica se algum dos argumentos informados esta nulo ou vazio.
     *
     * @param p_args Relacao de valores a serem verificados.
     *
     * @return Retorna TRUE se um ou mais dos valores estiver nulo ou vazio, ou FALSE caso contrario.
     */
    public static final boolean isAnyEmpty(final Object... p_args) {
        if (isEmpty(p_args)) {
            return true;
        }

        // Percorre toda a lista de argumentos, para confirmar se todos estao nulos ou vazios.
        for (final Object obj : p_args) {
            if (isEmpty(obj)) { // Encontrou algum que esteja vazio (nulo), pula fora.
                return true;
            }
        }

        // Se chegou ate aqui, entao e porque todos estao preenchidos (nenhum esta vazio).
        return false;
    }

    public static final boolean isNoneEmpty(final Object... p_args) {
        return !isAnyEmpty(p_args);
    }

    /**
     * Verifica se um argumento informado (primeiro da lista) corresponde a algum dos varios outros
     * argumentos relacionados. Este teste e relevante quanto ao tipo e tambem case.
     *
     * @param p_obj Objeto a ser testado frente aos argumentos restantes.
     *
     * @param p_args Relacao de valores para teste.
     *
     * @return Retorna TRUE se p_obj e igual a algum dos argumentos, ou FALSE caso contrario.
     */
    public static final boolean isAnyEquals(final Object p_obj, final Object... p_args) {
        // Teste obvio para agilizar o processamento.
        if (isEmpty(p_args)) {
            // so verificar se o principal argumento tambem e nulo.
            return (p_obj == null);

        } else if (p_obj == null) {
            // Percorre toda a lista de argumentos, para verificar se ha algum nulo.
            for (final Object arg : p_args) {
                if (arg == null) {
                    return true;
                }
            }

        } else {
            // Percorre toda a lista de argumentos, para verificar se ha algum igual.
            for (final Object arg : p_args) {
                // Foca em p_obj porque sabe que neste local ele nao e nulo.
                if (p_obj.equals(arg) || String.valueOf(arg).equalsIgnoreCase(p_obj.toString())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static final boolean isNoneEquals(final Object p_obj, final Object... p_args) {
        return !isAnyEquals(p_obj, p_args);
    }

    public static final boolean isBetween(final int p_val, final int p_begin, final int p_end) {
        return (p_val >= p_begin) && (p_val <= p_end);
    }

    public static boolean isNotBetween(final int p_val, final int p_begin, final int p_end) {
        return !isBetween(p_val, p_begin, p_end);
    }

    public static final boolean isBetween(final long p_val, final long p_begin, final long p_end) {
        return (p_val >= p_begin) && (p_val <= p_end);
    }

    public static boolean isNotBetween(final long p_val, final long p_begin, final long p_end) {
        return !isBetween(p_val, p_begin, p_end);
    }

    public static final boolean isBetween(final float p_val, final float p_begin, final float p_end) {
        return (p_val >= p_begin) && (p_val <= p_end);
    }

    public static boolean isNotBetween(final float p_val, final float p_begin, final float p_end) {
        return !isBetween(p_val, p_begin, p_end);
    }

    public static final boolean isBetween(final double p_val, final double p_begin, final double p_end) {
        return (p_val >= p_begin) && (p_val <= p_end);
    }

    public static boolean isNotBetween(final double p_val, final double p_begin, final double p_end) {
        return !isBetween(p_val, p_begin, p_end);
    }

    public static final boolean isBetween(final char p_chr, final char p_begin, final char p_end) {
        return (p_chr >= p_begin) && (p_chr <= p_end);
    }

    public static boolean isNotBetween(final char p_chr, final char p_begin, final char p_end) {
        return !isBetween(p_chr, p_begin, p_end);
    }

    /**
     * Atende a String, Date, StringBuilder, etc.
     *
     * @param p_obj
     * @param p_begin
     * @param p_end
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final boolean isBetween(final Comparable p_obj, final Comparable p_begin, final Comparable p_end) {
        // Null-safe first!
        if (p_obj == null && p_begin == null && p_end == null) {
            return true; // Se todos forem nulos, entao podemos considerar tudo-na-faixa!

        } else if (p_obj == null || p_begin == null || p_end == null) {
            return false; // Se apenas um dos argumentos for nulo, entao invalida a comparacao!

        } else {
            return (p_obj.compareTo(p_begin) >= 0) && (p_obj.compareTo(p_end) <= 0);
        }
    }

    @SuppressWarnings({"rawtypes"})
    public static boolean isNotBetween(final Comparable p_obj, final Comparable p_begin, final Comparable p_end) {
        return !isBetween(p_obj, p_begin, p_end);
    }

    // --- Validacao de numeros -----------------------------------------------

    /**
     * Verifica se um numero inforamdo esta zerado ou nulo. Considera tambem qualquer descendente de
     * <code>Number</code>,
     * como <code>Integer</code>, <code>Long</code>, <code>BigDecimal</code>, etc.
     *
     * @param p_value Valor numerico a ser verificado.
     *
     * @return Retorna <code>TRUE</code> se o valor informado for <code>Null</code> ou <code>0D</code>, ou
     *         <code>FALSE</code> se estiver valorado.
     */
    public static final boolean isZero(final Number p_value) {
        return ((p_value == null) || (p_value.doubleValue() == 0d));
    }

    public static final boolean isNotZero(final Number p_value) {
        return ((p_value != null) && (p_value.doubleValue() != 0d));
    }

    public static final boolean isAnyZero(final Number... p_args) {
        if (isEmpty(p_args)) {
            return true;
        }

        // Percorre toda a lista de argumentos, para confirmar se todos estao nulos ou zerados.
        for (final Number val : p_args) {
            if (isZero(val)) { // Encontrou algum que esteja zerado (ou nulo), pula fora.
                return true;
            }
        }

        // Se chegou ate aqui, entao e porque todos estao preenchidos (nenhum esta vazio).
        return false;
    }

    public static final boolean isNoneZero(final Number... p_args) {
        return !isAnyZero(p_args);
    }

    /**
     * Verifica se uma cadeia de caracteres informada e um numero valido, inteiro ou decimal.
     *
     * @param p_str Valor a ser verificado. Pode ser um String, StringBuilder ou StringBuffer.
     *
     * @return Retorna TRUE se o valor for um numero valido, ou FALSE caso contrario.
     */
    public static final boolean isNumber(final CharSequence p_str) {
        // Validacao necessaria do parametro necessario a logica do metodo.
        if (p_str == null) {
            return false;
        }

        // Ja pega o tamanho da string e o mantem, para fazer apenas uma chamada a .length();
        final int strLen = p_str.length();
        if (strLen == 0) {
            return false;
        }

        // Se o numero esta iniciando por um sinal, entao ignora-o.
        char chrAt = p_str.charAt(0);
        int i = (chrAt == '-' || chrAt == '+') ? 1 : 0;
        if (strLen == i || chrAt == '.') { // o ponto na primeira posicao invalida o numero.
            // Se tem apenas uma posicao e e um sinal ou ponto, entao nao e um numero valido.
            return false;
        }

        // Pesquisa toda a cadeia para ver se ha algo que nao diga respeito a numeros.
        boolean achouPonto = false;
        for (; i < strLen; i++) {
            chrAt = p_str.charAt(i);

            // Ignora os digitos validos, e apenas verifica o que nao for digito.
            if (isNotDigit(chrAt)) {
                if (chrAt == '.') {
                    // Se ja encontrou um ponto decimal antes, entao o segundo significa numero invalido.
                    if (achouPonto) {
                        return false;
                    } else {
                        achouPonto = true;
                    }

                } else { // se algum caractere nao for digito e nem '.', entao nao e numero.
                    return false;
                }
            }
        }

        // Se chegou ate aqui, entao e um numero valido.
        return true;
    }

    public static final boolean isNotNumber(final CharSequence p_str) {
        return !isNumber(p_str);
    }

    /**
     * Verifica se uma cadeia de caracteres informada e um numero inteiro e natural (acima de zero) valido.
     *
     * @param p_str Valor a ser verificado. Pode ser um String, StringBuilder ou StringBuffer.
     *
     * @return Retorna TRUE se o valor for um numero inteiro natural valido, ou FALSE caso contrario.
     */
    public static final boolean isNatural(final CharSequence p_str) {
        // Validacao necessaria do parametro necessario a logica do metodo.
        if (p_str == null) {
            return false;
        }

        // Ja pega o tamanho da string e o mantem, para fazer apenas uma chamada a .length();
        final int strLen = p_str.length();
        if (strLen == 0) {
            return false;
        }

        // Pesquisa toda a cadeia para ver se ha algo que nao diga respeito a numeros.
        char chrAt;
        for (int i = 0; i < strLen; i++) {
            chrAt = p_str.charAt(i);

            // Ignora os digitos validos, e apenas verifica o que nao for digito.
            if (isNotDigit(chrAt)) { // se algum caractere nao for digito, entao nao e numero.
                return false;
            }
        }

        // Se chegou ate aqui, entao e um numero valido.
        return true;
    }

    public static final boolean isNotNatural(final CharSequence p_str) {
        return !isNatural(p_str);
    }

    /**
     * Verifica se uma cadeia de caracteres informada e um numero valido, inteiro ou decimal.
     *
     * @param p_str Valor a ser verificado. Pode ser um String, StringBuilder ou StringBuffer.
     *
     * @return Retorna TRUE se o valor for um numero valido, ou FALSE caso contrario.
     */
    public static final boolean isInteger(final CharSequence p_str) {
        // Validacao necessaria do parametro necessario a logica do metodo.
        if (p_str == null) {
            return false;
        }

        // Ja pega o tamanho da string e o mantem, para fazer apenas uma chamada a .length();
        final int strLen = p_str.length();
        if (strLen == 0) {
            return false;
        }

        // Se o numero esta iniciando por um sinal, entao ignora-o.
        char chrAt = p_str.charAt(0);
        int i = (chrAt == '-' || chrAt == '+') ? 1 : 0;
        if (strLen == i) {
            // Se tem apenas uma posicao e e um sinal ou ponto, entao nao e um numero valido.
            return false;
        }

        // Pesquisa toda a cadeia para ver se ha algo que nao diga respeito a numeros.
        for (; i < strLen; i++) {
            chrAt = p_str.charAt(i);

            // Ignora os digitos validos, e apenas verifica o que nao for digito.
            if (isNotDigit(chrAt)) { // se algum caractere nao for digito e nem '.', entao nao e numero.
                return false;
            }
        }

        // Se chegou ate aqui, entao e um numero valido.
        return true;
    }

    public static final boolean isNotInteger(final CharSequence p_str) {
        return !isInteger(p_str);
    }

    /**
     * Verifica se uma cadeia de caracteres informada e um numero valido, inteiro ou decimal.
     *
     * @param p_str Valor a ser verificado. Pode ser um String, StringBuilder ou StringBuffer.
     *
     * @return Retorna TRUE se o valor for um numero valido, ou FALSE caso contrario.
     */
    public static final boolean isDecimal(final CharSequence p_str) {
        // Validacao necessaria do parametro necessario a logica do metodo.
        if (p_str == null) {
            return false;
        }

        // Ja pega o tamanho da string e o mantem, para fazer apenas uma chamada a .length();
        final int strLen = p_str.length();
        if (strLen == 0) {
            return false;
        }

        // Se o numero esta iniciando por um sinal, entao ignora-o.
        char chrAt = p_str.charAt(0);
        int i = (chrAt == '-' || chrAt == '+') ? 1 : 0;
        if (strLen == i || chrAt == '.') { // o ponto na primeira posicao invalida o numero.
            // Se tem apenas uma posicao e e um sinal ou ponto, entao nao e um numero valido.
            return false;
        }

        // Pesquisa toda a cadeia para ver se ha algo que nao diga respeito a numeros.
        boolean temInteger = false;
        boolean temPonto = false;
        boolean temDecimal = false;

        for (; i < strLen; i++) {
            chrAt = p_str.charAt(i);

            if (isDigit(chrAt)) {
                if (temPonto) {
                    temDecimal = true;
                } else {
                    temInteger = true;
                }

            } else { // verifica o que nao for digito.
                if (chrAt == '.') {
                    // Se ja encontrou um ponto decimal antes, entao o segundo significa numero invalido.
                    if (temPonto) {
                        return false;
                    } else {
                        temPonto = true;
                    }

                } else { // se algum caractere nao for digito e nem '.', entao nao e numero.
                    return false;
                }
            }
        }

        // Se chegou ate aqui, entao e um numero valido se tiver no formato #.#
        return temInteger && temPonto && temDecimal;
    }

    public static final boolean isNotDecimal(final CharSequence p_str) {
        return !isDecimal(p_str);
    }

    // --- Funcoes para conversoes de valores entre tipos ---------------------

    /**
     * Converte um valor qualquer para caractere. Se o valor for numerico, seja <code>Long</code> ou
     * <code>Integer</code>, e tiver mais de um digito, apenas o primeiro sera considerado.
     * Se o valor for <code>boolean</code>, entao sera adotada a convencao de <code>"S"</code> para <code>TRUE</code> e
     * <code>"N"</code> para <code>FALSE</code>.
     *
     * @param p_obj Valor a ser convertido (truncado) para caractere.
     *
     * @return Retorna o valor informado na forma de um <code>char</code>.
     */
    public static final char toChar(final CharSequence p_str) {
        if (p_str == null || p_str.length() == 0) {
            return '\0'; // Valores nulos sao representados por x00.
        }

        return p_str.charAt(0); // Considera apenas a primeira posicao;
    }

    /**
     * Converte um valor qualquer para caractere. Se o valor for numerico, seja <code>Long</code> ou
     * <code>Integer</code>, e tiver mais de um digito, apenas o primeiro sera considerado.
     * Se o valor for <code>boolean</code>, entao sera adotada a convencao de <code>"S"</code> para <code>TRUE</code> e
     * <code>"N"</code> para <code>FALSE</code>.
     *
     * @param p_obj Valor a ser convertido (truncado) para caractere.
     *
     * @return Retorna o valor informado na forma de um <code>char</code>.
     */
    public static final char toChar(final Object p_obj) {
        if (p_obj == null) {
            return '\0'; // Valores nulos sao representados por x00.

        } else if (p_obj instanceof Boolean) { // Usa convencao classica de Sim/Nao.
            return ((Boolean) p_obj) ? 'Y' : 'N';

        } else if (p_obj instanceof Number) { // // Apenas considera o primeiro digito.
            // Garante que ira descartar sinal negativo.
            return String.valueOf(Math.abs(((Number) p_obj).longValue())).charAt(0);

        } else {
            return p_obj.toString().charAt(0); // Apenas considera a primeira posicao do literal.
        }
    }

    public static final char toChar(final int p_val) {
        final int dig = (p_val % 10);

        return (char) (dig + 48);
    }

    public static final int toDigit(final char p_chr) {
        if (p_chr >= '0' && p_chr <= '9') {
            return ((int) p_chr) - 48;
        } else {
            return -1;
        }
    }

    public static final int length(final int p_value) {
        if (p_value == 0) {
            return 1;
        } else if (p_value == Integer.MIN_VALUE || p_value == Integer.MAX_VALUE) {
            return 10;
        }

        final int val = (p_value < 0) ? -p_value : p_value;

        int p = 10;
        for (int i = 1; i < 10; i++) {
            if (val < p) {
                return i;
            }
            p *= 10;
        }

        return 10;
    }

    public static final int length(final long p_value) {
        if (p_value == 0) {
            return 1;
        } else if (p_value == Long.MIN_VALUE || p_value == Long.MAX_VALUE) {
            return 19;
        }

        final long val = (p_value < 0) ? -p_value : p_value;

        long p = 10;
        for (int i = 1; i < 19; i++) {
            if (val < p) {
                return i;
            }
            p *= 10;
        }

        return 19;
    }

    public static final int length(final double p_value) {
        if (p_value == 0.0) {
            return 1;
        }

        final double val = (p_value < 0) ? -p_value : p_value;
        final long left = (long) val;
        final int leftSize = length(left);

        double val1 = val * 1000000000000000000D;
        double val2 = left * 1000000000000000000D;
        long right = (long) (val1 - val2);
        while (right % 10 == 0) {
            right /= 10;
        }
        final int rightSize = length(right);

        return leftSize + rightSize + ((rightSize > 0) ? 1 : 0);
    }

    /*
     * retorna o maior valor dentre uma relacao de numeros fornecidos:
     */
    public static final int max(final int... p_args) {
        final int argsLen = (p_args == null) ? 0 : p_args.length;
        if (argsLen == 0) return 0;

        // Percorre a lista e retorna o maior dentre os argumentos.
        int maior = 0;
        for (int i = 0; i < argsLen; i++) {
            if (p_args[i] > maior) {
                maior = p_args[i];
            }
        }

        return maior;
    }

    public static final double parseDouble(final String p_value) throws NumberFormatException {
        if ($.isEmpty(p_value)) {
            return 0d;

        } else {
            final int len = p_value.length();
            char c;
            double number = 0d;

            for (int i = 0; i < len; i++) {
                number *= 10L;

                c = p_value.charAt(i);
                switch (c) {
                    case '1':
                        number += 1;
                        break;
                    case '2':
                        number += 2;
                        break;
                    case '3':
                        number += 3;
                        break;
                    case '4':
                        number += 4;
                        break;
                    case '5':
                        number += 5;
                        break;
                    case '6':
                        number += 6;
                        break;
                    case '7':
                        number += 7;
                        break;
                    case '8':
                        number += 8;
                        break;
                    case '9':
                        number += 9;
                        break;
                }
            }

            return number;
        }
    }

    public static final long parseLong(final String p_value) throws NumberFormatException {
        if ($.isEmpty(p_value)) {
            return 0L;

        } else {
            final int len = p_value.length();
            char c;
            long number = 0L;

            for (int i = 0; i < len; i++) {
                number *= 10L;

                c = p_value.charAt(i);
                switch (c) {
                    case '1':
                        number += 1;
                        break;
                    case '2':
                        number += 2;
                        break;
                    case '3':
                        number += 3;
                        break;
                    case '4':
                        number += 4;
                        break;
                    case '5':
                        number += 5;
                        break;
                    case '6':
                        number += 6;
                        break;
                    case '7':
                        number += 7;
                        break;
                    case '8':
                        number += 8;
                        break;
                    case '9':
                        number += 9;
                        break;
                }
            }

            return number;
        }
    }

    public static final int parseInteger(final String p_value) throws NumberFormatException {
        if ($.isEmpty(p_value)) {
            return 0;

        } else {
            final int len = p_value.length();
            char c;
            int number = 0;

            for (int i = 0; i < len; i++) {
                number *= 10;

                c = p_value.charAt(i);
                switch (c) {
                    case '1':
                        number += 1;
                        break;
                    case '2':
                        number += 2;
                        break;
                    case '3':
                        number += 3;
                        break;
                    case '4':
                        number += 4;
                        break;
                    case '5':
                        number += 5;
                        break;
                    case '6':
                        number += 6;
                        break;
                    case '7':
                        number += 7;
                        break;
                    case '8':
                        number += 8;
                        break;
                    case '9':
                        number += 9;
                        break;
                }
            }

            return number;
        }
    }

    public static final byte parseByte(final String p_value) throws NumberFormatException {
        if ($.isEmpty(p_value)) {
            return 0;

        } else {
            final int len = p_value.length();
            char c;
            byte number = 0;

            for (int i = 0; i < len; i++) {
                number *= 10;

                c = p_value.charAt(i);
                switch (c) {
                    case '1':
                        number += 1;
                        break;
                    case '2':
                        number += 2;
                        break;
                    case '3':
                        number += 3;
                        break;
                    case '4':
                        number += 4;
                        break;
                    case '5':
                        number += 5;
                        break;
                    case '6':
                        number += 6;
                        break;
                    case '7':
                        number += 7;
                        break;
                    case '8':
                        number += 8;
                        break;
                    case '9':
                        number += 9;
                        break;
                }
            }

            return number;
        }
    }

    public static final boolean parseBoolean(final String p_value) {
        if ($.isEmpty(p_value)) {
            return false;

        } else {
            final String text = p_value.trim().toLowerCase();
            return text.equals("sim") || text.equals("yes") || text.equals("1");
        }
    }

    public static Date parseDate(final String value, final String pattern) throws ParseException {
        if (value == null) {
            return null;

        } else {
            final DateFormat sdf = new SimpleDateFormat((pattern == null) ? "dd/MM/yyyy" : pattern, BRASIL);
            return sdf.parse(value);
        }
    }

    public static Date parseDate(final String date) throws ParseException {
        return parseDate(date, "dd/MM/yyyy");
    }

    // --- Funcoes para formatacao de valores ---------------------------------

    private static final Locale BRASIL = new Locale("pt", "BR");

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static String formatDate(final Date date) {
        return sdf.format(date);
    }

    public static String formatDate(final long date) {
        return sdf.format(new Date(date));
    }

    /**
     * Formata um valor monetario de acordo com a convencao padrao: '$' no
     * inicio e 2 casas decimais ao final.
     *
     * @param valor
     *            Valor a ser formatado.
     *
     * @return Retorna o valor formatado.
     */
    public static final String formatCurrency(final double valor) {
        long digs = new Double(valor * 100).longValue();
        long integer = digs / 100;
        long decimal = digs % 100;

        String fmt = ("," + decimal + "0").substring(0, 3);
        do {
            decimal = integer % 1000;
            integer /= 1000;

            if (integer > 0) {
                fmt = "." + formatMilhar((int) decimal) + fmt;
            } else {
                fmt = decimal + fmt;
            }
        } while (integer > 0);

        return "$" + fmt;
    }

    /**
     * Formata um valor monetario com um numero de casas decimais fornecido.
     * Tambem precede o valor retornado com '$'.
     *
     * @param valor
     *            Valor a ser formatado.
     *
     * @param dec
     *            Numero de casas decimais.
     *
     * @return Retorna o valor formatado com o numero de casas decimais
     *         fornecido.
     */
    public static final String formatCurrency(final double valor, final int dec) {
        final int casas = (int) pow(10, dec);

        long digs = new Double(valor * casas).longValue();
        long integer = digs / casas;
        long decimal = digs % casas;

        String fmt = ("," + decimal + "0000000000").substring(0, dec + 1);
        do {
            decimal = integer % 1000;
            integer /= 1000;

            if (integer > 0) {
                fmt = "." + formatMilhar((int) decimal) + fmt;
            } else {
                fmt = decimal + fmt;
            }
        } while (integer > 0);

        return "$" + fmt;
    }

    /**
     * Formata um numero de acordo com a convencao padrao, aplicando o numero de
     * casas decimais fornecido.
     *
     * @param valor
     *            Valor a ser formatado.
     *
     * @param dec
     *            Numero de casas decimais a serem consideradas.
     *
     * @return Retorna o valor formatado com o numero de casas decimais
     *         fornecido.
     */
    public static final String formatNumber(final double valor, final int dec) {
        final int casas = (int) pow(10, dec);

        long digs = new Double(valor * casas).longValue();
        long integer = digs / casas;
        long decimal = digs % casas;

        String fmt = (decimal == 0) ? "" : ("," + decimal + "0000000000").substring(0, dec + 1);
        long group;
        do {
            group = integer % 1000;
            integer /= 1000;

            if (integer > 0) {
                fmt = "." + formatMilhar((int) group) + fmt;
            } else {
                fmt = group + fmt;
            }
        } while (integer > 0);

        return fmt;
    }

    /**
     * Formata um numero de acordo com a convencao padrao, aplicando o numero de
     * casas decimais fornecido.
     *
     * @param valor
     *            Valor a ser formatado.
     *
     * @param dec
     *            Numero de casas decimais a serem consideradas.
     *
     * @return Retorna o valor formatado com o numero de casas decimais
     *         fornecido.
     */
    public static final String formatInteger(final long valor) {
        String fmt = "";

        long integer = valor;
        long group;
        do {
            group = integer % 1000;
            integer /= 1000;

            if (integer > 0) {
                fmt = "." + formatMilhar((int) group) + fmt;
            } else {
                fmt = group + fmt;
            }
        } while (integer > 0);

        return fmt;
    }

    /**
     * Formata um valor inteiro como milhar, utilizando ate 3 digitos -
     * precedidos por zero(s) se necessario.
     *
     * @param valor
     *            Valor a ser formatado.
     *
     * @return Retorna o valor ja formatado em tamanho 3 (milhar).
     */
    public static final String formatMilhar(final int valor) {
        final String milhar = "00" + valor;
        return milhar.substring(milhar.length() - 3);
    }

    public static final boolean isPrimo(final long valor) {
        long metade = valor / 2;
        for (int i = 2; i <= metade; i++) {
            if (valor % i == 0) {
                return false;
            }
        }

        return true;
    }

    /*
     * adiciona um novo inteiro no inicio de um array de inteiros.
     */
    public static int[] insertArray(final int p_val, final int[] p_array) {
        int[] newArray;

        if (p_array == null) {
            newArray = new int[1];

        } else {
            final int len = p_array.length;
            newArray = new int[len + 1];
            System.arraycopy(p_array, 0, newArray, 1, len);
        }

        newArray[0] = p_val; // insere o novo item no inicio do novo array.

        return newArray;
    }

    /*
     * adiciona um novo inteiro ao final de um array de inteiros.
     */
    public static int[] addArray(final int[] p_array, final int p_val) {
        int[] newArray;
        final int len = (p_array == null) ? 0 : p_array.length;

        if (len == 0) {
            newArray = new int[1];

        } else {
            newArray = new int[len + 1];
            System.arraycopy(p_array, 0, newArray, 0, len);
        }

        newArray[len] = p_val; // insere o novo item no final do novo array.

        return newArray;
    }

    // --- Funcoes para comparacao entre valores ------------------------------

    public final static int compare(final Character c1, final Character c2, final boolean ascending) {
        if (c1 != null && c2 != null) {
            return (ascending) ? c1.compareTo(c2) : c2.compareTo(c1);

        } else if (c1 == null && c2 == null) {
            return 0;

        } else if (c1 != null) {
            return (ascending) ? 1 : -1;

        } else {
            return (ascending) ? -1 : 1;
        }
    }

    /**
     * Efetua a comparacao basica entre dois valores do tipo String,
     * considerando o criterio de ordenacao fornecido.
     *
     * @param s1
     *            Primeiro valor a ser comparado (a esquerda).
     *
     * @param s2
     *            Segundo valor a ser comparado (a direita).
     *
     * @param ascending
     *            Criterio de ordenacao a ser levado em consideracao (True =
     *            ascendente, False = descendente).
     *
     * @return Retorna -1 se o primeiro valor � inferior ao segundo, 1 se o
     *         primeiro valor for superior ao segundo, ou 0 caso sejam iguais.
     */
    public final static int compare(final String s1, final String s2, final boolean ascending) {
        if (s1 != null && s2 != null) {
            return (ascending) ? s1.compareTo(s2) : s2.compareTo(s1);

        } else if (s1 == null && s2 == null) {
            return 0;

        } else if (s1 != null) {
            return (ascending) ? 1 : -1;

        } else {
            return (ascending) ? -1 : 1;
        }
    }

    /**
     * Efetua a comparacao basica entre dois valores do tipo inteiro,
     * considerando o criterio de ordenacao fornecido.
     *
     * @param i1
     *            Primeiro valor a ser comparado (a esquerda).
     *
     * @param i2
     *            Segundo valor a ser comparado (a direita).
     *
     * @param ascending
     *            Criterio de ordenacao a ser levado em consideracao (True =
     *            ascendente, False = descendente).
     *
     * @return Retorna -1 se o primeiro valor eh inferior ao segundo, 1 se o
     *         primeiro valor for superior ao segundo, ou 0 caso sejam iguais.
     */
    public final static int compare(final Integer i1, final Integer i2, final boolean ascending) {
        if (i1 != null && i2 != null) {
            return (ascending) ? i1.compareTo(i2) : i2.compareTo(i1);

        } else if (i1 == null && i2 == null) {
            return 0;

        } else if (i1 != null) {
            return (ascending) ? 1 : -1;

        } else {
            return (ascending) ? -1 : 1;
        }
    }

    public final static int compare(final Long l1, final Long l2, final boolean ascending) {
        if (l1 != null && l2 != null) {
            return (ascending) ? l1.compareTo(l2) : l2.compareTo(l1);

        } else if (l1 == null && l2 == null) {
            return 0;

        } else if (l1 != null) {
            return (ascending) ? 1 : -1;

        } else {
            return (ascending) ? -1 : 1;
        }
    }

    /**
     * Efetua a comparacao basica entre dois valores do tipo double,
     * considerando o criterio de ordenacao fornecido.
     *
     * @param d1
     *            Primeiro valor a ser comparado (a esquerda).
     *
     * @param d2
     *            Segundo valor a ser comparado (a direita).
     *
     * @param ascending
     *            Criterio de ordenacao a ser levado em consideracao (True =
     *            ascendente, False = descendente).
     *
     * @return Retorna -1 se o primeiro valor eh inferior ao segundo, 1 se o
     *         primeiro valor for superior ao segundo, ou 0 caso sejam iguais.
     */
    public final static int compare(final Double d1, final Double d2, final boolean ascending) {
        if (d1 != null && d2 != null) {
            return (ascending) ? d1.compareTo(d2) : d2.compareTo(d1);

        } else if (d1 == null && d2 == null) {
            return 0;

        } else if (d1 != null) {
            return (ascending) ? 1 : -1;

        } else {
            return (ascending) ? -1 : 1;
        }
    }

    public final static int compare(final Date d1, final Date d2, final boolean ascending) {
        if (d1 != null && d2 != null) {
            return (ascending) ? d1.compareTo(d2) : d2.compareTo(d1);

        } else if (d1 == null && d2 == null) {
            return 0;

        } else if (d1 != null) {
            return (ascending) ? 1 : -1;

        } else {
            return (ascending) ? -1 : 1;
        }
    }

    public final static int compare(final Boolean b1, final Boolean b2, final boolean ascending) {
        if (b1 != null && b2 != null) {
            return (ascending) ? b1.compareTo(b2) : b2.compareTo(b1);

        } else if (b1 == null && b2 == null) {
            return 0;

        } else if (b1 != null) {
            return (ascending) ? 1 : -1;

        } else {
            return (ascending) ? -1 : 1;
        }
    }

    // --- Helpers diversos ---------------------------------------------------

    public static final int getMonthDiff(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        return (12 * (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR))) + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH) + 1;
    }

    // --- Helpers diversos ---------------------------------------------------

    // --- Helpers diversos ---------------------------------------------------

    /**
     * Verifica se determinado item, qualquer que seja seu tipo, se encontra ou
     * nao na colecao de objetos fornecida.
     *
     * @param list
     *            Lista de valores a serem comparados com item.
     *
     * @param item
     *            Valor objeto a ser comparado com os objetos da lista.
     *
     * @return Retorna True se item foi encontrado em list, ou False caso
     *         contrario.
     */
    public final static boolean contains(final Object list, final Object item) {
        if (list != null) { // Null-Safe sempre!
            if (list instanceof Object[]) {
                final Object[] array = (Object[]) list;
                for (Object obj : array) {
                    if (obj != null && obj.equals(item)) {
                        return true;
                    }
                }

            } else if (list instanceof Collection<?>) {
                final Collection<?> collection = (Collection<?>) list;
                for (Object obj : collection) {
                    if (obj != null && obj.equals(item)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Eleva um determinado valor a uma potencia.
     *
     * @param valor
     *            Valor a ser elevado.
     *
     * @param exp
     *            Expoente para elevacao.
     *
     * @return Retorna o valor elevado a potencia fornecida (valor ^ exp).
     */
    public static final double pow(final double valor, final double exp) {
        return Math.exp(Math.log(valor) * exp);
    }

    public static final long somaDivisores(final long valor) {
        long soma = 1; // Considera sempre o 1, e nem precisa testar.

        long metade = valor / 2;
        for (int i = 2; i <= metade; i++) {
            if (valor % i == 0) {
                soma += i;
            }
        }

        return soma;
    }

    /*
     *
     */
    public static final String repeat(final char p_chr, final int p_qtd) {
        if (p_chr == '\0' || p_qtd <= 0) {
            return "";
        }

        final StringBuilder stbd = new StringBuilder(p_qtd);
        for (int i = 0; i < p_qtd; i++) {
            stbd.append(p_chr);
        }

        return stbd.toString();
    }

    /*
    *
    */
    public static final String repeat(final String p_str, final int p_qtd) {
        if (p_str == null || p_str.length() == 0 || p_qtd <= 0) {
            return "";
        }

        final StringBuilder stbd = new StringBuilder(p_qtd);
        for (int i = 0; i <= p_qtd; i++) {
            stbd.append(p_str);
        }

        return stbd.toString();
    }

    /*
     *
     */
    public static final String[] split(final String p_str, final char p_char) {
        if (p_str == null) {
            return null;
        }

        final int strLen = p_str.length();
        if (strLen == 0) {
            return null;
        }

        final List<String> terms = new ArrayList<String>();
        StringBuilder term = new StringBuilder(); // tamanho default == 16
        for (int i = 0; i < strLen; i++) {
            final char ch = p_str.charAt(i);
            if (ch == p_char) {
                if (term.length() > 0) { // ignora termos vazios.
                    terms.add(term.toString());
                    term = new StringBuilder();
                }
            } else {
                term.append(ch);
            }
        }
        // nao deixa ignorar o ultimo.
        if (term.length() > 0) {
            terms.add(term.toString());
        }

        // Nao vai criar array a toa, se nao tem termos
        return (terms.size() == 0) ? null : terms.toArray(new String[terms.size()]);
    }

    /*
     *
     */
    public static void log(final String p_msg, final Object... p_args) {
        if (isEmpty(p_args)) {
            System.out.println(p_msg);

        } else {
            System.out.println(MessageFormat.format(p_msg, p_args));
        }
    }

    public static void pause(final String p_msg) {
        System.out.print(isEmpty(p_msg) ? " " : p_msg);
        try {
            System.in.read(new byte[4]);
        } catch (IOException e) {
        }
    }

    public static String[] unzipFile(final String p_zipFile) {
        // Retorna lista dos arquivos descompactados.
        final List<String> files = new ArrayList<String>(2);

        // descompacta no mesmo diretorio do arquivo ZIP fornecido.
        String destinationFolder = (new File(p_zipFile)).getParent();
        // Se estiver no diretorio corrente (sem path), entao nao prefixa com '/'.
        // caso contrario, descompactaria no diretorio raiz do HD.
        destinationFolder = isEmpty(destinationFolder) ? "" : (destinationFolder + File.separator);

        // buffer for read and write data to file
        byte[] buffer = new byte[2048];

        try {
            FileInputStream fInput = new FileInputStream(p_zipFile);
            ZipInputStream zipInput = new ZipInputStream(fInput);

            ZipEntry entry = zipInput.getNextEntry();

            while (entry != null) {
                String entryName = entry.getName();
                File file = new File(destinationFolder + entryName);
                files.add(file.getAbsolutePath());
                log("\t Gravando arquivo: {0}", file.getAbsolutePath());

                // create the directories of the zip directory
                if (entry.isDirectory()) {
                    File newDir = new File(file.getAbsolutePath());
                    if (!newDir.exists()) {
                        boolean success = newDir.mkdirs();
                        if (success == false) {
                            System.out.println("ERRO: Nao foi possivel criar pasta para descompactar arquivo ZIP (" + p_zipFile + ").");
                        }
                    }
                } else {
                    FileOutputStream fOutput = new FileOutputStream(file);
                    int count = 0;
                    while ((count = zipInput.read(buffer)) > 0) {
                        // write 'count' bytes to the file output stream
                        fOutput.write(buffer, 0, count);
                    }
                    fOutput.close();
                }
                // close ZipEntry and take the next one
                zipInput.closeEntry();
                entry = zipInput.getNextEntry();
            }

            // close the last ZipEntry
            zipInput.closeEntry();

            zipInput.close();
            fInput.close();

        } catch (IOException e) {
            System.out.println("ERRO: Nao foi possivel descompactar arquivo ZIP (" + p_zipFile + ").");
            e.printStackTrace();
        }

        return files.toArray(new String[files.size()]);
    }

    public static <K, V extends Comparable<V>> List<Entry<K, V>> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> entries = new ArrayList<Entry<K, V>>(map.entrySet());
        Collections.sort(entries, new ByValue<K, V>());
        return entries;
    }

    private static class ByValue<K, V extends Comparable<V>> implements Comparator<Entry<K, V>> {
        public int compare(Entry<K, V> o1, Entry<K, V> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    }

    /*
     * 
     */
    public static int random(final int p_qtd) {
        return (int) Math.floor(Math.random() * p_qtd);
    }
}
