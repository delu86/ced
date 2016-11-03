/*
 * 
 */
package parser;

import object.Query;

/**
 *
 * @author CRE0260
 */
public interface QueryParser {
    
    public Query getSQLQuery(String userQuery,String[] parameter);
    
}
