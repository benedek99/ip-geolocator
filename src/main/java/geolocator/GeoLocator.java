package geolocator;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import java.net.URL;

import java.io.IOException;

import com.google.gson.Gson;

import com.google.common.net.UrlEscapers;

import org.apache.commons.io.IOUtils;

/**
 * Class for obtaining geolocation information about an IP address or host
 * name. The class uses the <a href="http://ip-api.com/">IP-API.com</a>
 * service.
 */
public class GeoLocator {

    /**
     * URI of the geolocation service.
     */
    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static Gson GSON = new Gson();

    private static Logger logger = LoggerFactory.getLogger(GeoLocator.class);

    /**
     * Creates a <code>GeoLocator</code> object.
     */
    public GeoLocator() {}

    /**
     * Returns geolocation information about the JVM running the application.
     *
     * @return an object wrapping the geolocation information returned
     * @throws IOException if any I/O error occurs
     */
    public geolocator.GeoLocation getGeoLocation() throws IOException {
        return getGeoLocation(null);
    }

    /**
     * Returns geolocation information about the IP address or host name
     * specified. If the argument is <code>null</code>, the method returns
     * geolocation information about the JVM running the application.
     *
     * @param ipAddrOrHost the IP address or host name, may be {@code null}
     * @return an object wrapping the geolocation information returned
     * @throws IOException if any I/O error occurs
     */
    public geolocator.GeoLocation getGeoLocation(String ipAddrOrHost) throws IOException {
        URL url;
        if (ipAddrOrHost != null) {
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
            url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);
            logger.info("ipAddrOrHost != 0" );
        } else {
            url = new URL(GEOLOCATOR_SERVICE_URI);
            logger.info("ipAddrOrHost = 0");
        }
        logger.debug("This code runs so far :)");
        String s = IOUtils.toString(url, "UTF-8");
        logger.info("Data is downloaded from: {}", GEOLOCATOR_SERVICE_URI);
        logger.info("The JSON object: {}", s);
        logger.trace("Returning GeoLocation:");
        return GSON.fromJson(s, geolocator.GeoLocation.class);
    }

    public static void main(String[] args) throws IOException {
        try {
            String arg = args.length > 0 ? args[0] : null;
            System.out.println(new GeoLocator().getGeoLocation(arg));
        } catch (IOException e) {
            //System.err.println(e.getMessage());
            logger.error("Something is wrong: {}", e.getMessage());
        }
    }

}
