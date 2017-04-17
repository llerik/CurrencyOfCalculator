package kirill.sorokin.ru.calculatorcurrency.xml.elements;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import kirill.sorokin.ru.calculatorcurrency.Log;

/**
 * Created by kirill on 12.04.17.
 */
@Root(name = "Valute")
public class Valute {
    public Valute() {

    }

    public Valute(String id, int numCode, String charCode, int nominal, String name, String value) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    @Attribute(name = "ID")
    private String id;

    @Element(name = "NumCode")
    private int numCode;

    @Element(name = "CharCode")
    private String charCode;

    @Element(name = "Nominal")
    private int nominal;

    @Element(name = "Name")
    private String name;

    @Element(name = "Value")
    private String value;

    public String getId() {
        return id;
    }

    public int getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return Float.valueOf(getFloatValue(value));
    }

    private String getFloatValue(String text) {
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        try {
            return String.valueOf(format.parse(value).floatValue());
        } catch (ParseException e) {
            Log.e(e.getMessage(), e);
        }
        return text;
    }

    @Override
    public String toString() {
        return "id : " + id +
                " NumCode : " + numCode +
                " CharCode : " + charCode +
                " Nominal : " + nominal +
                " Name : " + name +
                " Value : " + getValue();
    }
}
