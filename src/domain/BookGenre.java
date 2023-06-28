package domain;


import javax.xml.bind.annotation.XmlEnumValue;
import java.io.Serializable;

public enum BookGenre implements Serializable {
    
    FANTASY("FANTASY"),
    FICTION("FICTION"),
    THRILLER("THRILLER"),
    CLASSIC("CLASSIC"),
    SCIENCE("SCIENCE");
    
    private final String displayName;
    
    BookGenre(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
}
