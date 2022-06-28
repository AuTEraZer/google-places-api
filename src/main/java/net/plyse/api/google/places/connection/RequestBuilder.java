package net.plyse.api.google.places.connection;

import net.plyse.api.google.places.parameter.OutputType;
import net.plyse.api.google.places.parameter.RequestField;
import net.plyse.api.google.places.parameter.RequestPair;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Raphael Dichler on 28.06.2022.
 */
public abstract class RequestBuilder {

    private static final String FIELDS_PREFIX = "fields=";
    private static final String FIELDS_SEPARATOR = "%2C";
    private static final String PARAMETER_SEPARATOR = "&";

    protected final Set<RequestField> fields = new HashSet<>(16);
    protected final Set<RequestPair> pairs = new HashSet<>(16);

    protected String baseUrl;
    protected String apiKey;
    protected Connection connection;

    public abstract RequestBuilder addField(RequestField parameter);

    public abstract RequestBuilder addPair(RequestPair pair);

    public RequestBuilder(String apiKey) {
        this.apiKey = "key=" + apiKey;
    }

    public Connection build() {
        connection.setUrl(
                baseUrl + getFieldSection() + getPairSection() + PARAMETER_SEPARATOR + apiKey
        );

        return connection;
    }

    private String getPairSection() {
        if (pairs.isEmpty()) {
            return "";
        }

        StringBuilder s = new StringBuilder(FIELDS_PREFIX);

        for (RequestPair field : pairs) {
            s.append(field.toUrlValue());
            s.append(PARAMETER_SEPARATOR);
        }

        int size = s.length();
        s.delete(size - PARAMETER_SEPARATOR.length(), size);

        s.append(PARAMETER_SEPARATOR);

        return s.toString();
    }

    private String getFieldSection() {
        if (fields.isEmpty()) {
            return "";
        }

        StringBuilder s = new StringBuilder(FIELDS_PREFIX);

        for (RequestField field : fields) {
            s.append(field.toUrlValue());
            s.append(FIELDS_SEPARATOR);
        }

        int size = s.length();
        s.delete(size - FIELDS_SEPARATOR.length(), size);

        s.append(PARAMETER_SEPARATOR);

        return s.toString();
    }

}