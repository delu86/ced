/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.Collection;

/**
 *
 * @author CRE0260
 */
public interface QueryInterface {
    
      public String getQuery();
      public void setQuery(String query);
      public void addParameter(String parameter);
      public String getParameter(int index);
      public Collection<String> getParameters();
}
