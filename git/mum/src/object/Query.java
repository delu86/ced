/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author CRE0260
 */
public class Query implements QueryInterface{
    
    private String queryString;
    private final ArrayList<String> queryParameters=new ArrayList<String>();
    

    
    @Override
    public String getQuery() {
        return queryString;
    }
    @Override
    public void setQuery(String query) {
        this.queryString=query;
    }

    @Override
    public void addParameter(String parameter) {
        queryParameters.add(parameter);
    }

    @Override
    public String getParameter(int index) {
        return queryParameters.get(index);
    }
    
    @Override
    public Collection<String> getParameters(){
        return queryParameters;
    }

    
}
