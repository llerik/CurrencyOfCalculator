package kirill.sorokin.ru.calculatorcurrency.xml.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by kirill on 12.04.17.
 */
@Root(name = "ValCurs")
public class ValCurs {

    @Attribute
    private String Date;

    @Attribute
    private String name;

    @ElementList(inline = true)
    private List<Valute> list;

    public String getName() {
        return name;
    }

    public String getDate() {
        return Date;
    }

    public List<Valute> getList() {
        return list;
    }

    public void setList(List<Valute> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (list != null) {
            boolean first = true;
            for (Valute valute : list) {
                stringBuilder.append(valute.toString());
                if (first) {
                    first = false;
                } else {
                    stringBuilder.append(",\n");
                }
            }
        }
        return "Date : " + Date +
                " name : " + name +
                " list: [" +
                stringBuilder.toString() +
                "]";
    }
}
