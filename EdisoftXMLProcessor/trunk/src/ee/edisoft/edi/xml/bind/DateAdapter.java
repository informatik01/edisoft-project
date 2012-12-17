package ee.edisoft.edi.xml.bind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * This class is used as an XmlAdapter.
 * It is required to successfully bind dates stored in XML
 * 
 * @author Levan Kekelidze
 * @version 0.2 Alpha
 */
public class DateAdapter extends XmlAdapter<String, Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String marshal(Date v) {
        return dateFormat.format(v);
    }

    @Override
    public Date unmarshal(String v) throws ParseException {
        return dateFormat.parse(v);
    }

}