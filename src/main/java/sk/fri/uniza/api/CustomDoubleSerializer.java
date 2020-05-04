package sk.fri.uniza.api;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CustomDoubleSerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        if (null == value) {
            //write the word 'null' if there's no value available
            jgen.writeNull();
        } else {
//            final String pattern = ".##";
            //final String pattern = "###,###,##0.00";
//            BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).toString();
//            final DecimalFormat myFormatter = new DecimalFormat(pattern);
//            final String output = myFormatter.format(value);
            jgen.writeNumber(BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).toString());
        }
    }
}