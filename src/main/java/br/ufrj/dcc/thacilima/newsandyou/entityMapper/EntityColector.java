package br.ufrj.dcc.thacilima.newsandyou.entityMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.sail.nativerdf.NativeStore;

/**
 * Hello world!
 *
 */
public class EntityColector 
{
    public static void main( String[] args )
    {
		String dataDir = "/Users/thacilima/Downloads/repo/";
		File dataFile = new File(dataDir);
		Repository repo = new SailRepository(new NativeStore(dataFile));
		repo.initialize();
		
		RepositoryConnection conn = repo.getConnection();
		conn.setAutoCommit(false);
		
		RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
		rdfParser.setRDFHandler(new ChunkCommitter(conn));
		
		try {
			String fileDir = "/Users/thacilima/Downloads/artist.nt.gz";
			File file = new File(fileDir);
			InputStream is = new GZIPInputStream(new FileInputStream(file));
			
			rdfParser.parse(is, "file://" + file.getCanonicalPath());
			conn.commit();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RDFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RDFHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
			
//			String baseURI = "http://musicbrainz.org";
//			Model model = new LinkedHashModel();
//			rdfParser.setRDFHandler(new StatementCollector(model));
//			rdfParser.parse(in, "/Users/thacilima/Downloads/artist.nt");
        
        
//        File dataDir = new File("/Users/thacilima/Downloads/repo");
//        File file = new File("/Users/thacilima/Downloads/artist.nt");
//        try {
//        	Repository repo = new SailRepository(new MemoryStore(dataDir));
//            repo.initialize();
//        	
//            RepositoryConnection con = repo.getConnection();
//            try {
//            	con.add(file, null, RDFFormat.NTRIPLES);
//            } catch (IOException e) {
//            	e.printStackTrace();
//            }
//            finally {
//            	con.close();
//            }
//        }
//        catch (OpenRDFException e) {
//        	e.printStackTrace();
//        }
            
    }
}
