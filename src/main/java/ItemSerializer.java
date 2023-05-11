import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ItemSerializer extends StdSerializer<Piece> { //Classe che permette di creare un serializzatore JSON personalizzato
    private String name = "asd";
    public ItemSerializer(){
        this(null);
    }

    public ItemSerializer(Class<Piece> t){
        super(t);
    }

    @Override
    public void serialize(Piece value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("name", "game");
        jgen.writeStartArray();
    }
}
