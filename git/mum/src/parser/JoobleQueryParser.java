package parser;

import java.util.ResourceBundle;
import object.Query;
/**
 *
 * @author CRE0260
 */
public class JoobleQueryParser implements QueryParser{
    private final  String PROPERTIES_FILE_NAME="parser.joobleParserProperties";
    private final static String TOP_OPTION_PROP="TOP_OPTION";
    private final static String SELECT_BY_JOBNAME="SELECT_BY_JOBNAME";
    private final static String SELECT_TOP_CONSUMER="SELECT_TOP_CONSUMER";
    private final  ResourceBundle rb =   ResourceBundle.getBundle(PROPERTIES_FILE_NAME);
    private final static String LIMIT_COMMAND_CHAR="L";
    private final static String LIMIT_DEFAULT_PROPERTY="DEFAULT_LIMIT";
    

    @Override
    public Query getSQLQuery(String userQuery,String[] parameters) {
        Query querySQL=initializeQuery(parameters);
        fromUserQueryToSQL(userQuery,querySQL);
        return querySQL;
    }

    private Query initializeQuery(String[] parameters) {
        Query query=new Query();
        for(String parameter: parameters){
            query.addParameter(parameter);
         }
        return query;
    }

    private void fromUserQueryToSQL(String userQuery, Query querySQL) {
        String[] userQuerySplitted=userQuery.split("\\s*-");
        String endQuery=rb.getString(LIMIT_COMMAND_CHAR);
        String limitValue=rb.getString(LIMIT_DEFAULT_PROPERTY);
        String querySQLString=userQuerySplitted[0].equals(rb.getString(TOP_OPTION_PROP))?
                              rb.getString(SELECT_TOP_CONSUMER):rb.getString(SELECT_BY_JOBNAME);
        for (int i = 1; i < userQuerySplitted.length; i++) {
            String[] splitCommandFromParameter=userQuerySplitted[i].toUpperCase().split("\\s+");
            if(!splitCommandFromParameter[0].equals(LIMIT_COMMAND_CHAR)){
                 querySQLString=querySQLString.concat(" "+rb.getString(splitCommandFromParameter[0]));
                 if(splitCommandFromParameter.length==2)
                     querySQL.addParameter(splitCommandFromParameter[1]);
            }else{
                limitValue=userQuerySplitted[i].toUpperCase().split("\\s+")[1];
            }
        }
        querySQL.setQuery(querySQLString+" "+endQuery.replace("$limit", limitValue));
   }
    public static void main(String[] args) {
        JoobleQueryParser j=new JoobleQueryParser();
        Query q=j.getSQLQuery("CXPR -t toDate -f fromDate -l 100 -d date -w", new String[0]);
        System.out.println(q.getQuery());
        for(String s : q.getParameters())
            System.out.println(s);
    }    

}
