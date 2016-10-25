package br.ufrj.dcc.thacilima.newsandyou.entityMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;

public class AttributeColector {
	public static void main( String[] args )
    {
		RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
		rdfParser.setRDFHandler(new FilterLinkedBrainzArtistTurtleHandler());
		
		try {
			String fileDir = "/Users/thacilima/Downloads/artist.nt.gz";
			File file = new File(fileDir);
			InputStream is = new GZIPInputStream(new FileInputStream(file));
			
			rdfParser.parse(is, "file://" + file.getCanonicalPath());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
    }
}
