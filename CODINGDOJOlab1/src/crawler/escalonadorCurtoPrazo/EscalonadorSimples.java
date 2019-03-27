package crawler.escalonadorCurtoPrazo;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.trigonic.jrobotx.Record;
import com.trigonic.jrobotx.RobotExclusion;

import crawler.Servidor;
import crawler.URLAddress;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EscalonadorSimples implements Escalonador{

        private LinkedHashMap<String, ArrayList<URLAddress>> allPages; 
	private HashSet<String> hash ; 
        int maxDepth ;
        private HashMap<String, Servidor> servidores;
        private HashMap<String, Record> record;
        private Integer countPages, maxPages = 5;
        
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
                Servidor servidor = servidores.get(url_address.getDomain());
                servidor.acessadoAgora();
                return url_address;
            } else {
                try{
                    Thread.sleep(1000);
                    
                }
                catch(InterruptedException e){
                    System.err.println(e);
                }
                
            }
            
            return null;
	}

	@Override
	public boolean adicionaNovaPagina(URLAddress urlAdd) {
            // TODO Auto-generated method stub
            ArrayList<URLAddress> list = new ArrayList<>();

            if(hash.contains(urlAdd.getAddress()) && urlAdd.getDepth()>maxDepth){
                return false;
            }
            
            hash.add(urlAdd.getAddress());
            
            if(!servidores.containsKey(urlAdd.getDomain())){
                try {
                    servidores.put(urlAdd.getDomain(urlAdd.getAddress()), new Servidor(urlAdd.getDomain()));
                    URLAddress address = new URLAddress(urlAdd.getAddress(), Integer.MAX_VALUE);
                    list.add(address);
                    allPages.put(urlAdd.getDomain(), list);
            } catch (MalformedURLException ex) {
                Logger.getLogger(EscalonadorSimples.class.getName()).log(Level.SEVERE, null, ex);
            }

            }
            return true;
	}


	@Override
	public Record getRecordAllowRobots(URLAddress url) {
            try {
             RobotExclusion robot = new RobotExclusion();    
                 Record rec = robot.get(new URL(url.getAddress()), "daniBot");

                 if(servidores.containsKey(url.getDomain())){
                     return rec;    
                 } else {
                     return null;
                 }             
            }catch(Exception e){
                return null;
            }          
	}

	@Override
	public void putRecorded(String domain, Record domainRec) {
		record.put(domain, domainRec);
		
	}
	@Override
	public boolean finalizouColeta() {
            return countPages == maxPages ? true : false;
	}

	@Override
	public void countFetchedPage() {
		countPages++;
	}

	
}
