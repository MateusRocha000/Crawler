package crawler.escalonadorCurtoPrazo;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.trigonic.jrobotx.Record;
import com.trigonic.jrobotx.RobotExclusion;

import crawler.Servidor;
import crawler.URLAddress;
import static java.lang.Thread.sleep;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class EscalonadorSimples implements Escalonador{

        private LinkedHashMap<String, ArrayList<URLAddress>> allPages; 
	private HashSet<String> hash ; 
        int maxDepth ;
        private HashMap<String, Servidor> servidores;
        
        public EscalonadorSimples() {
            allPages = new LinkedHashMap();
            hash = new HashSet();
            maxDepth = 5;
            servidores = new HashMap();
        }
        
        @Override 
         
	public synchronized URLAddress getURL() {
            //<Entry<String, ArrayList<URLAddress>>> 
             URLAddress url_address ;
            Iterator it = allPages.entrySet().iterator();
            if(it.hasNext()){
                Map.Entry entry = (Map.Entry)it.next();
                
                ArrayList<URLAddress> url = (ArrayList<URLAddress>)entry.getValue();
                url_address = url.get(0);
                Servidor servidor = servidores.get(url.get(0).getDomain());
                servidor.acessadoAgora();
   
            } else {
                return null;
            }
            
          return url_address;
	}

	@Override
	public boolean adicionaNovaPagina(URLAddress urlAdd) {
            // TODO Auto-generated method stub
            ArrayList<URLAddress> array = allPages.get(urlAdd.getDomain());

            if(hash.contains(urlAdd.getAddress()) || urlAdd.getDepth()>maxDepth){
                return false;
            }
            
            hash.add(urlAdd.getAddress());
            
            if(!servidores.containsKey(urlAdd.getDomain()))
                servidores.put(urlAdd.getDomain(), new Servidor(urlAdd.getDomain()));
            
            array.add(urlAdd);
            return true;
	}


	@Override
	public Record getRecordAllowRobots(URLAddress url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putRecorded(String domain, Record domainRec) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean finalizouColeta() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void countFetchedPage() {
		// TODO Auto-generated method stub
		
	}

	
}
