import java.io.CharConversionException;
import java.nio.charset.Charset;

import com.bes.crane.http.util.ByteChunk;
import com.bes.crane.http.util.DataChunk;
import com.bes.crane.http.util.HttpRequestURIDecoder;
import com.bes.crane.http.util.RequestURIRef;
import com.bes.crane.http.util.URLDecoder;

public class Demo {

    public static void main(String[] args) throws CharConversionException {
        t2();
        String uri = "/cluster5803208224624851536/%C0%AE/WEB-INF/web.xml";
        byte[] bs = uri.getBytes();
        
        byte[] bs2 = new byte[bs.length];
        org.glassfish.grizzly.http.util.RequestURIRef ref = new org.glassfish.grizzly.http.util.RequestURIRef();
        ref.init(bs, 0, bs.length);
        //System.out.println(ref.getDecodedRequestURIBC(false));

        org.glassfish.grizzly.http.util.DataChunk d = org.glassfish.grizzly.http.util.DataChunk.newInstance();
        d.setBytes(bs2);
        org.glassfish.grizzly.http.util.URLDecoder.decode(ref.getOriginalRequestURIBC(), d, false);

        System.out.println("1"+d.toString());
        if (!org.glassfish.grizzly.http.util.HttpRequestURIDecoder.normalize(d)) {
          throw new CharConversionException("Invalid URI character encoding");
        }

        System.out.println("2"+d.toString());
        org.glassfish.grizzly.http.util.HttpRequestURIDecoder.convertToChars(d, Charset.forName("UTF-8"));
        System.out.println("3"+d.getCharChunk().toString());
    }

    private static void t2() throws CharConversionException {
        String uri = "/cluster5803208224624851536/%C0%AE/WEB-INF/web.xml";
        byte[] bs = uri.getBytes();
        
        byte[] bs2 = new byte[bs.length];
        RequestURIRef ref = new RequestURIRef();
        ref.init(bs, 0, bs.length);
        System.out.println(ref.getDecodedRequestURIBC(false));

        DataChunk d = DataChunk.newInstance();
        d.setBytes(bs2);
        URLDecoder.decode(ref.getOriginalRequestURIBC(), d, false);

        System.out.println("1"+d.getByteChunk().toString());
        if (!HttpRequestURIDecoder.normalize(d)) {
          throw new CharConversionException("Invalid URI character encoding");
        }

        System.out.println("2"+d.getByteChunk().toString());
        HttpRequestURIDecoder.convertToChars(d, Charset.forName("iso-8859-1"));
        System.out.println("3"+d.getCharChunk().toString());
    }

    private static void t1() throws CharConversionException {
        String uri = "/cluster5803208224624851536/%C0%AE/WEB-INF/web.xml";
        byte[] bs = uri.getBytes();
        
        RequestURIRef ref = new RequestURIRef();
        ref.init(bs, 0, bs.length);
        System.out.println(ref.getDecodedRequestURIBC(false));
        DataChunk d = DataChunk.newInstance();
        URLDecoder.decode(ref.getOriginalRequestURIBC(), d, false);
        System.out.println(d.getType().ordinal());
        System.out.println(d.getByteChunk().toString());
        System.out.println(HttpRequestURIDecoder.normalize(d));
        System.out.println(d.getByteChunk().toString());
        
        org.glassfish.grizzly.http.util.RequestURIRef ref2 = new org.glassfish.grizzly.http.util.RequestURIRef();
        ref2.init(uri);
        System.out.println(ref2.getDecodedRequestURIBC(true));
        
        org.glassfish.grizzly.http.util.DataChunk d1 = org.glassfish.grizzly.http.util.DataChunk.newInstance();
        org.glassfish.grizzly.http.util.URLDecoder.decode(ref2.getOriginalRequestURIBC(), d1, false);
        System.out.println(d1.getType().ordinal());
        System.out.println("111111111111"+d1.toString());
    }
}
