package br.ufrj.dcc.thacilima.newsandyou.entityMapper;

import org.openrdf.model.Statement;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.util.RDFInserter;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;

public class ChunkCommitter implements RDFHandler {
	
	private RDFInserter inserter;
	private RepositoryConnection conn;
	private long count = 0L;
	
	//do intermittent commit every 500,000 triples
	private long chunkSize = 500000L;
	
	public ChunkCommitter(RepositoryConnection conn)
	{
		inserter = new RDFInserter(conn);
		this.conn = conn;
	}
	
	public void startRDF() throws RDFHandlerException
	{
		inserter.startRDF();
	}
	
	public void endRDF() throws RDFHandlerException {
		inserter.endRDF();
	}
	
	public void handleNamespace(String prefix, String uri) throws RDFHandlerException
	{
		inserter.handleNamespace(prefix, uri);
	}
	
	public void handleStatement(Statement st) throws RDFHandlerException
	{
		//TODO Alterar para salvar no banco de dados
		inserter.handleStatement(st);
		count++;
		
		//do an intermittent commit whenever the number of triples
		//has reached a multiple of the chunk size
		if (count % chunkSize == 0) {
			try {
				conn.commit();
				System.out.println(chunkSize + " triples loaded...");
			}
			catch (RepositoryException e) {
				throw new RDFHandlerException(e);
			}
		}
	}
	
	public void handleComment(String comment) throws RDFHandlerException
	{
		inserter.handleComment(comment);
	}
}