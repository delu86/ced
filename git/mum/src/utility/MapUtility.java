/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.HashMap;

/**
 *
 * @author CRE0260
 */
public class MapUtility {
public static HashMap<String, String> mapSTCTable() {
        HashMap<String, String> map=new HashMap<String, String>();
		map.put("SIES", "smfacc.epv030_23_intrvl_t10_rm_STC");
		map.put("SIGE", "smfacc.epv030_23_intrvl_t10_rm_STC");
                map.put("ASDN", "smfacc.epv030_23_intrvl_t10_carige_STC");
		map.put("ASSV", "smfacc.epv030_23_intrvl_t10_carige_STC");
		return map;
    }

    public static HashMap<String, String> mapBatchTable() {
       
                HashMap<String, String> map=new HashMap<String, String>();
		map.put("SIES", "smfacc.epv030_5_jobterm_t10_rm");
		map.put("SIGE", "smfacc.epv030_5_jobterm_t10_rm");
                map.put("ASDN", "smfacc.epv030_5_jobterm_t10_carige");
		map.put("ASSV", "smfacc.epv030_5_jobterm_t10_carige");
		return map;
        
    }
	
     public static HashMap<String, String> mapTransactionTable() {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("SIES", "smfacc.epv110_1_trxacct_t10_rm");
		map.put("SIGE", "smfacc.epv110_1_trxacct_t10_rm");
                map.put("ASDN", "smfacc.epv110_1_trxacct_t10_carige");
		map.put("ASSV", "smfacc.epv110_1_trxacct_t10_carige");
		map.put("GSY7", "smfacc.epv110_1_trxacct_t10_sy7");
		return map;
	}
    
}