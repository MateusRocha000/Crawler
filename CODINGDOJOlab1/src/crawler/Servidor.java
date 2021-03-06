package crawler;

import java.util.ArrayList;
import java.util.LinkedHashMap;



public class Servidor {
	public static final long ACESSO_MILIS = 30*1000;
	
	
	private String nome;
	private long lastAccess;
	private LinkedHashMap<String, ArrayList<String>> linkedHashMap; 
        
	public Servidor(String nome)
	{
		this.nome = nome;
		this.lastAccess =0;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getTimeSinceLastAcess()
	{   
            return System.currentTimeMillis()-lastAccess;
	}
	
	/**
	 * 
	 * Atualiza o acesso
	 */
	public void acessadoAgora()
	{
            System.out.println("Horário atual: " + System.currentTimeMillis());
            this.lastAccess = System.currentTimeMillis();
	
        }
	
	/**
	 * Verifica se é possivel acessar o dominio
	 * @return
	 */
	public synchronized boolean isAccessible()
	{
            return (getTimeSinceLastAcess()>ACESSO_MILIS);
		
	}
	
	public long getLastAcess()
	{
		return this.lastAccess;
	}
        
        /**
         * 
        * @return 
         */
	public String getNome()
	{
		return this.nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servidor other = (Servidor) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
