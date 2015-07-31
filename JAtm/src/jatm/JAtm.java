/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jatm;

import parser.ATMParserFactory;
import parser.AtmParser;

/**
 *
 * @author CRE0260
 */
public class JAtm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ATMParserFactory factory=new ATMParserFactory();
        AtmParser parser=factory.getAtmParser(ATMParserFactory.FARO_NCH_PARSER_TYPE);
        AtmParser parser2=factory.getAtmParser(ATMParserFactory.NCH_PARSER_TYPE);
        //parser.parse("LOGSIA.FARONCH.BTD.V6032.txt");
        //parser.parse("LOGSIA.FARONCH.BTD.V6033.txt");
        parser2.parse("LOGATM.20150715.NCH.txt");
    }
    
}
