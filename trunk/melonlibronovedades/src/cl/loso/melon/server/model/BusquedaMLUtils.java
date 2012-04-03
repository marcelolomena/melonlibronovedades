package cl.loso.melon.server.model;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;

public class BusquedaMLUtils {
        
        
        private static final Logger log = Logger.getLogger(BusquedaMLUtils.class.getName());
        
        /** From StopAnalyzer Lucene 2.9.1 */
        /*
        public final static String[] stopWords = new String[]{
                    "a", "an", "and", "are", "as", "at", "be", "but", "by",
                    "for", "if", "in", "into", "is", "it",
                    "no", "not", "of", "on", "or", "such",
                    "that", "the", "their", "then", "there", "these",
                    "they", "this", "to", "was", "will", "with"
                  };
        */
        public final static String[] SPANISH_STOP_WORDS = new String[]{

        		"un", "una", "unas", "unos", "uno", "sobre", "todo", "también", "tras",
        		"otro", "algún", "alguno", "alguna",

        		"algunos", "algunas", "ser", "es", "soy", "eres", "somos", "sois", "estoy",
        		"esta", "estamos", "estais",

        		"estan", "en", "para", "atras", "porque", "por qué", "estado", "estaba",
        		"ante", "antes", "siendo",

        		"ambos", "pero", "por", "poder", "puede", "puedo", "podemos", "podeis",
        		"pueden", "fui", "fue", "fuimos",

        		"fueron", "hacer", "hago", "hace", "hacemos", "haceis", "hacen", "cada",
        		"fin", "incluso", "primero",

        		"desde", "conseguir", "consigo", "consigue", "consigues", "conseguimos",
        		"consiguen", "ir", "voy", "va",

        		"vamos", "vais", "van", "vaya", "bueno", "ha", "tener", "tengo", "tiene",
        		"tenemos", "teneis", "tienen",

        		"el", "la", "lo", "las", "los", "su", "aqui", "mio", "tuyo", "ellos",
        		"ellas", "nos", "nosotros", "vosotros",

        		"vosotras", "si", "dentro", "solo", "solamente", "saber", "sabes", "sabe",
        		"sabemos", "sabeis", "saben",

        		"ultimo", "largo", "bastante", "haces", "muchos", "aquellos", "aquellas",
        		"sus", "entonces", "tiempo",

        		"verdad", "verdadero", "verdadera", "cierto", "ciertos", "cierta",
        		"ciertas", "intentar", "intento",

        		"intenta", "intentas", "intentamos", "intentais", "intentan", "dos", "bajo",
        		"arriba", "encima", "usar",

        		"uso", "usas", "usa", "usamos", "usais", "usan", "emplear", "empleo",
        		"empleas", "emplean", "ampleamos",

        		"empleais", "valor", "muy", "era", "eras", "eramos", "eran", "modo", "bien",
        		"cual", "cuando", "donde",

        		"mientras", "quien", "con", "entre", "sin", "trabajo", "trabajar",
        		"trabajas", "trabaja", "trabajamos",

        		"trabajais", "trabajan", "podria", "podrias", "podriamos", "podrian",
        		"podriais", "yo", "aquel", "mi",

        		"de", "a", "e", "i", "o", "u"};        
        
        /**
         * Uses english stemming (snowball + lucene) + stopwords for getting the words.
         * 
         * @param index
         * @return
         */
        public static Set<String> getTokensForIndexingOrQuery(
                        String index_raw,
                        int maximumNumberOfTokensToReturn) {
                
                String indexCleanedOfHTMLTags = index_raw.replaceAll("\\<.*?>"," ");
                
                
                Set<String> returnSet = new HashSet<String>();
                
                try {
                        
                        Analyzer analyzer =  new SnowballAnalyzer(
                                        org.apache.lucene.util.Version.LUCENE_CURRENT,
                                        "Spanish",
                                        SPANISH_STOP_WORDS);

                        
                        TokenStream tokenStream = analyzer.tokenStream(
                                        "content", 
                                        new StringReader(indexCleanedOfHTMLTags));
                        
                        Token token = new Token();

                while (((token = tokenStream.next()) != null)
                                && (returnSet.size() < maximumNumberOfTokensToReturn)) {
                        
                                        returnSet.add(token.term());
                        
                        }
                        
                } catch (IOException e) {
                        log.severe(e.getMessage());
                }
                
                return returnSet;
                
                
        }
        
        
        
        
}