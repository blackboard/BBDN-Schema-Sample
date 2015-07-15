/**
 * 
 */
package bbdn.sample.schema;

/**
 * @author shurrey
 *
 */

import java.util.List;

import bbdn.sample.schema.Properties;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.dao.impl.SimpleDAO;
import blackboard.persist.impl.SimpleSelectQuery;
import blackboard.util.StringUtil;

public class PropertiesDAO extends SimpleDAO<Properties> {

	public PropertiesDAO() {
		super(Properties.class);
	}

	public PropertiesDAO(Class<Properties> cls) {
		super(cls);
	}

	public List<Properties> loadAll() {
		return getDAOSupport().loadAll();
	}

	public List<Properties> searchById(String id) 
			throws KeyNotFoundException {
		if (!StringUtil.isEmpty(id)) {
			SimpleSelectQuery query = new SimpleSelectQuery(getDAOSupport().getMap());
			//Change column name to whatever the name is in the bean
			query.addWhere("pk1", id);
			return getDAOSupport().loadList(query);
		}
		return null;  
	} 
	
    public Properties load() {
        List<Properties> globalSettings;
        globalSettings = getDAOSupport().loadAll();
        if(globalSettings!=null&&!globalSettings.isEmpty())
        return globalSettings.get(0);
        else return null;
    } 
    
    public void save(Properties globalSettings) {
        System.out.println(globalSettings);        
                
        getDAOSupport().persist(globalSettings);
    }
    
    public boolean isInstalled(){
        List<Properties> globalSettings = null;
        globalSettings = getDAOSupport().loadAll();
        if (globalSettings.size() > 0) {
            return true;
        }
        return false;
    }
    
    public void writeDefaults() {
    	Properties globalSettings = new Properties();
        
        getDAOSupport().persist(globalSettings);
        
    }
}
