package kirill.sorokin.ru.calculatorcurrency.xml;


import android.content.Context;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;

import kirill.sorokin.ru.calculatorcurrency.R;
import kirill.sorokin.ru.calculatorcurrency.xml.elements.ValCurs;
import kirill.sorokin.ru.calculatorcurrency.xml.elements.Valute;

/**
 * Created by Kirill on 12.04.2017.
 */
public class XMLParser {
    private Serializer serializer;
    private String dataString;
    private Context context;

    public XMLParser(Context context, String dataString) {
        this.context = context;
        this.dataString = dataString;
        serializer = new Persister();
    }

    private void addRouble(ValCurs valCurs) {
        Valute valute = new Valute("", 643, "RUB", 1, context.getString(R.string.rouble), "1");
        boolean needAdd = true;
        if (valCurs.getList() == null) {
            valCurs.setList(new ArrayList<Valute>());
        } else {
            for (Valute val : valCurs.getList()) {
                if (val.getNumCode() == 643) {
                    needAdd = false;
                    break;
                }
            }
        }
        if (needAdd) {
            valCurs.getList().add(0, valute);
        }
    }

    public ValCurs parse() throws Exception {
        ValCurs valCurs = serializer.read(ValCurs.class, dataString);
        addRouble(valCurs);
        return valCurs;
    }
}
