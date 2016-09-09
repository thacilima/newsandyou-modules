package br.ufrj.dcc.thacilima.newsandyou.entityMapper;

import org.openrdf.model.Statement;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;

import br.ufrj.dcc.thacilima.newsandyou.data.AttributeData;

public class FilterLinkedBrainzArtistTurtleHandler implements RDFHandler {
	
	private AttributeData attributeData;
	
	public FilterLinkedBrainzArtistTurtleHandler()
	{
		try {
			attributeData = new AttributeData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startRDF() throws RDFHandlerException
	{
	}
	
	public void endRDF() throws RDFHandlerException {

	}
	
	public void handleNamespace(String prefix, String uri) throws RDFHandlerException
	{
		
	}
	
	public void handleStatement(Statement st) throws RDFHandlerException
	{	
		String subject = st.getSubject().toString();
		subject = subject.replaceFirst("http://musicbrainz.org/artist/", "");
		
		try {
			attributeData.insert(subject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleComment(String comment) throws RDFHandlerException
	{

	}
}
