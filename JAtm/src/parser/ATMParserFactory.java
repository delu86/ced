/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author CRE0260
 */
public class ATMParserFactory {
    
    public final static int FARO_NCH_PARSER_TYPE=0;
    public final static int NCH_PARSER_TYPE=1;
    
    
    public AtmParser getAtmParser(int parserType){
        switch(parserType){
            case FARO_NCH_PARSER_TYPE:
                return new LogFaroNCHParser();
            case NCH_PARSER_TYPE:
                return new LogNCHParser();
            default:return null;
        }
    }
}
